/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model.blockchain;

import MVC.model.store.RocksDBUtils;
import MVC.model.transaction.TXInput;
import MVC.model.transaction.TXOutput;
import MVC.model.transaction.Transaction;
import MVC.model.utils.ByteUtils;
import com.google.common.collect.Maps;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;

import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author ssopt
 */

public class Blockchain {
    private String lastBlockHash;

    public Blockchain(String lastBlockHash) {
        this.lastBlockHash = lastBlockHash;
    }

    public Blockchain() {
    }

    // khoi phuc data chain from DB
    public static Blockchain initBlockchainFromDB() {
        String lastBlockHash = RocksDBUtils.getInstance().getLastBlockHash();
        if (lastBlockHash == null) {
            throw new RuntimeException("ERROR: Fail to init blockchain from db. ");
        }
        return new Blockchain(lastBlockHash);
    }
    
    public static Blockchain createBlockchain(String address) {
        String lastBlockHash = RocksDBUtils.getInstance().getLastBlockHash();
        if (StringUtils.isBlank(lastBlockHash)) {
           // create coinBase tx
            String genesisCoinbaseData = "The Times 03/Jan/2009 Chancellor on brink of second bailout for banks";
            Transaction coinbaseTX = Transaction.newCoinbaseTX(address, genesisCoinbaseData.getBytes());
            Block genesisBlock = Block.newGenesisBlock(coinbaseTX);
            lastBlockHash = genesisBlock.getHash();
            RocksDBUtils.getInstance().putBlock(genesisBlock);
            RocksDBUtils.getInstance().putLastBlockHash(lastBlockHash);
        }
        return new Blockchain(lastBlockHash);
    }
    
    // mine
    public Block mineBlock(Transaction[] transactions) {
       
        for (Transaction tx : transactions) {
            if (!this.verifyTransactions(tx)) {
                
                throw new RuntimeException("ERROR: Fail to mine block ! Invalid transaction ! ");
            }
        }
        String lastBlockHash = RocksDBUtils.getInstance().getLastBlockHash();
        if (lastBlockHash == null) {
            throw new RuntimeException("ERROR: Fail to get last block hash ! ");
        }

        Block block = Block.newBlock(lastBlockHash, transactions);
        this.addBlock(block);
        System.out.println(block.getHash());
        return block;
    }
    
    private void addBlock(Block block) {
        RocksDBUtils.getInstance().putLastBlockHash(block.getHash());
        RocksDBUtils.getInstance().putBlock(block);
        this.lastBlockHash = block.getHash();
    }

    public String getLastBlockHash() {
        return this.lastBlockHash;
    }

    public void setLastBlockHash(String lastBlockHash) {
        this.lastBlockHash = lastBlockHash;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Blockchain)) return false;
        final Blockchain other = (Blockchain) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$lastBlockHash = this.getLastBlockHash();
        final Object other$lastBlockHash = other.getLastBlockHash();
        if (this$lastBlockHash == null ? other$lastBlockHash != null : !this$lastBlockHash.equals(other$lastBlockHash))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Blockchain;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $lastBlockHash = this.getLastBlockHash();
        result = result * PRIME + ($lastBlockHash == null ? 43 : $lastBlockHash.hashCode());
        return result;
    }

    public String toString() {
        return "Blockchain(lastBlockHash=" + this.getLastBlockHash() + ")";
    }


    // blockchain loop
    public class BlockchainIterator {

        private String currentBlockHash;

        private BlockchainIterator(String currentBlockHash) {
            this.currentBlockHash = currentBlockHash;
        }

        //check next block
        public boolean hashNext() {

            if (StringUtils.isBlank(currentBlockHash)) {
                return false;
            }
            Block lastBlock = RocksDBUtils.getInstance().getBlock(currentBlockHash);

            if (lastBlock == null) {
                return false;
            }
           
            if (lastBlock.getPrevBlockHash().length() == 0) {
                return true;
            }
            return RocksDBUtils.getInstance().getBlock(lastBlock.getPrevBlockHash()) != null;
        }


        // return block
        public Block next() {
            Block currentBlock = RocksDBUtils.getInstance().getBlock(currentBlockHash);
            if (currentBlock != null) {
                this.currentBlockHash = currentBlock.getPrevBlockHash();
                return currentBlock;
            }
            return null;
        }
        
        
    }
    
    public BlockchainIterator getBlockchainIterator() {
        return new BlockchainIterator(lastBlockHash);
    }
        
    // find all unspent transaction output
    public Map<String, TXOutput[]> findAllUTXOs() {
        Map<String, int[]> allSpentTXOs = this.getAllSpentTXOs();
        Map<String, TXOutput[]> allUTXOs = Maps.newHashMap();
            
        for (BlockchainIterator blockchainIterator = this.getBlockchainIterator(); blockchainIterator.hashNext(); ) {
            Block block = blockchainIterator.next();
            for (Transaction transaction : block.getTransactions()) {

                String txId = Hex.encodeHexString(transaction.getTxId());

                int[] spentOutIndexArray = allSpentTXOs.get(txId);
                TXOutput[] txOutputs = transaction.getOutputs();
                for (int outIndex = 0; outIndex < txOutputs.length; outIndex++) {
                    if (spentOutIndexArray != null && ArrayUtils.contains(spentOutIndexArray, outIndex)) {
                        continue;
                    }
                    TXOutput[] UTXOArray = allUTXOs.get(txId);
                    if (UTXOArray == null) {
                        UTXOArray = new TXOutput[]{txOutputs[outIndex]};
                    } else {
                        UTXOArray = ArrayUtils.add(UTXOArray, txOutputs[outIndex]);
                    }
                    allUTXOs.put(txId, UTXOArray);
                }
            }
        }
        return allUTXOs;
    }
        
    private Map<String, int[]> getAllSpentTXOs() {
        Map<String, int[]> spentTXOs = Maps.newHashMap();
        for (BlockchainIterator blockchainIterator = this.getBlockchainIterator(); blockchainIterator.hashNext(); ) {
            Block block = blockchainIterator.next();

            for (Transaction transaction : block.getTransactions()) {
                // neu la coinBase tx thi bo qua vi khong co dau ra
                if (transaction.isCoinbase()) {
                    continue;
                }
                for (TXInput txInput : transaction.getInputs()) {
                    String inTxId = Hex.encodeHexString(txInput.getTxId());
                    int[] spentOutIndexArray = spentTXOs.get(inTxId);
                    if (spentOutIndexArray == null) {
                        spentOutIndexArray = new int[]{txInput.getTxOutputIndex()};
                    } else {
                        spentOutIndexArray = ArrayUtils.add(spentOutIndexArray, txInput.getTxOutputIndex());
                    }
                    spentTXOs.put(inTxId, spentOutIndexArray);
                }
            }
        }
        return spentTXOs;
    }
        
    private Transaction findTransaction(byte[] txId) {
        for (BlockchainIterator iterator = this.getBlockchainIterator(); iterator.hashNext(); ) {
            Block block = iterator.next();
            for (Transaction tx : block.getTransactions()) {
               if (Arrays.equals(tx.getTxId(), txId)) {
                    return tx;
                }
          }
        }
        throw new RuntimeException("ERROR: Can not found tx by txId ! ");
    }
    public void signTransaction(Transaction tx, BCECPrivateKey privateKey) throws Exception {
        // truoc tien tim cac giao dịch trước đó được tham chiếu bởi đầu vào trong tx này
        Map<String, Transaction> prevTxMap = Maps.newHashMap();
        for (TXInput txInput : tx.getInputs()) {
            Transaction prevTx = this.findTransaction(txInput.getTxId());
            prevTxMap.put(Hex.encodeHexString(txInput.getTxId()), prevTx);
        }
        tx.sign(privateKey, prevTxMap);
    }
    
    private boolean verifyTransactions(Transaction tx) {
        if (tx.isCoinbase()) {
            return true;
        }
        Map<String, Transaction> prevTx = Maps.newHashMap();
        for (TXInput txInput : tx.getInputs()) {
            Transaction transaction = this.findTransaction(txInput.getTxId());
            prevTx.put(Hex.encodeHexString(txInput.getTxId()), transaction);
        }
        try {
            return tx.verify(prevTx);
        } catch (Exception e) {
            throw new RuntimeException("Fail to verify transaction ! transaction invalid ! ", e);
        }
    }
}
