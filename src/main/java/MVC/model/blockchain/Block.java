/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model.blockchain;

import MVC.model.pow.PowResult;
import MVC.model.pow.ProofOfWork;
import MVC.model.transaction.MerkleTree;
import MVC.model.transaction.Transaction;
import MVC.model.utils.ByteUtils;

import java.time.Instant;

/**
 *
 * @author ssopt
 */

public class Block {
    // hash cua block hien tai
    private String hash;
    // hash cua block truoc do
    private String prevBlockHash;
    
    private Transaction[] transactions;
    
    private long timeStamp;
    
    private long nonce;

    public Block(String hash, String prevBlockHash, Transaction[] transactions, long timeStamp, long nonce) {
        this.hash = hash;
        this.prevBlockHash = prevBlockHash;
        this.transactions = transactions;
        this.timeStamp = timeStamp;
        this.nonce = nonce;
    }

    public Block() {
    }

    // create genesis Block
    public static Block newGenesisBlock(Transaction coinbase) {
        return Block.newBlock("", new Transaction[]{coinbase});
    }
    
    public static Block newBlock(String previousHash, Transaction[] transactions) {
        Block block = new Block("", previousHash, transactions, Instant.now().getEpochSecond(), 0);
        ProofOfWork pow = ProofOfWork.newProofOfWork(block);
        PowResult powResult = pow.run();
        block.setHash(powResult.getHash());
        block.setNonce(powResult.getNonce());
        return block;
    }
    
    public byte[] hashTransaction() {
        byte[][] txIdArrays = new byte[this.getTransactions().length][];
        for (int i = 0; i < this.getTransactions().length; i++) {
            txIdArrays[i] = this.getTransactions()[i].hash();
        }
        return new MerkleTree(txIdArrays).getRoot().getHash();
    }

    public String getHash() {
        return this.hash;
    }

    public String getPrevBlockHash() {
        return this.prevBlockHash;
    }

    public Transaction[] getTransactions() {
        return this.transactions;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public long getNonce() {
        return this.nonce;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setPrevBlockHash(String prevBlockHash) {
        this.prevBlockHash = prevBlockHash;
    }

    public void setTransactions(Transaction[] transactions) {
        this.transactions = transactions;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Block)) return false;
        final Block other = (Block) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$hash = this.getHash();
        final Object other$hash = other.getHash();
        if (this$hash == null ? other$hash != null : !this$hash.equals(other$hash)) return false;
        final Object this$prevBlockHash = this.getPrevBlockHash();
        final Object other$prevBlockHash = other.getPrevBlockHash();
        if (this$prevBlockHash == null ? other$prevBlockHash != null : !this$prevBlockHash.equals(other$prevBlockHash))
            return false;
        if (!java.util.Arrays.deepEquals(this.getTransactions(), other.getTransactions())) return false;
        if (this.getTimeStamp() != other.getTimeStamp()) return false;
        if (this.getNonce() != other.getNonce()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Block;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $hash = this.getHash();
        result = result * PRIME + ($hash == null ? 43 : $hash.hashCode());
        final Object $prevBlockHash = this.getPrevBlockHash();
        result = result * PRIME + ($prevBlockHash == null ? 43 : $prevBlockHash.hashCode());
        result = result * PRIME + java.util.Arrays.deepHashCode(this.getTransactions());
        final long $timeStamp = this.getTimeStamp();
        result = result * PRIME + (int) ($timeStamp >>> 32 ^ $timeStamp);
        final long $nonce = this.getNonce();
        result = result * PRIME + (int) ($nonce >>> 32 ^ $nonce);
        return result;
    }

    public String toString() {
        return "Block(hash=" + this.getHash() + ", prevBlockHash=" + this.getPrevBlockHash() + ", transactions=" + java.util.Arrays.deepToString(this.getTransactions()) + ", timeStamp=" + this.getTimeStamp() + ", nonce=" + this.getNonce() + ")";
    }
}
