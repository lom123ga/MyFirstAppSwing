/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model.transaction;

import MVC.model.blockchain.Blockchain;
import MVC.model.utils.BtcAddressUtils;
import MVC.model.utils.SerializeUtils;
import MVC.model.wallet.Wallet;
import MVC.model.wallet.WalletUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author ssopt
 */

public class Transaction {
    
    private static final int SUBSIDY = 10;
    
    private byte[] txId;
    
    private TXInput[] inputs;
    
    private TXOutput[] outputs;
    
    private long createTime;

    public Transaction(byte[] txId, TXInput[] inputs, TXOutput[] outputs, long createTime) {
        this.txId = txId;
        this.inputs = inputs;
        this.outputs = outputs;
        this.createTime = createTime;
    }

    public Transaction() {
    }

    // Tinh gia tri hash cua thong tin giao dich
    public byte[] hash(){
        byte[] serializeBytes = SerializeUtils.serialize(this);
        Transaction copyTx = (Transaction) SerializeUtils.deserialize(serializeBytes);
        copyTx.setTxId(new byte[]{});
        return DigestUtils.sha256(SerializeUtils.serialize(copyTx));
    }
    
    //create CoinBase
    public static Transaction newCoinbaseTX(String to, String data) {
        if (StringUtils.isBlank(data)) {
            data = String.format("Reward to '%s'", to);
        }
        
        TXInput txInput = new TXInput(new byte[]{}, -1, null, data.getBytes());
      
        TXOutput txOutput = TXOutput.newTXOutput(SUBSIDY, to);
        
        Transaction tx = new Transaction(null, new TXInput[]{txInput},
                new TXOutput[]{txOutput}, System.currentTimeMillis());
     
        tx.setTxId(tx.hash());
        return tx;
    }
    
    public boolean isCoinbase() {
        return     this.getInputs().length == 1
                && this.getInputs()[0].getTxId().length == 0
                && this.getInputs()[0].getTxOutputIndex() == -1;
    }
    
     public static Transaction newUTXOTransaction(String from, String to, int amount, Blockchain blockchain) throws Exception {
       
        Wallet senderWallet = WalletUtils.getInstance().getWallet(from);
        byte[] pubKey = senderWallet.getPublicKey();
        byte[] pubKeyHash = BtcAddressUtils.ripeMD160Hash(pubKey);

        SpendableOutputResult result = new UTXOSet(blockchain).findSpendableOutputs(pubKeyHash, amount);
        int accumulated = result.getAccumulated();
        Map<String, int[]> unspentOuts = result.getUnspentOuts();

        if (accumulated < amount) {
            throw new RuntimeException("ERROR: Not enough funds ! ");
        }
        Iterator<Map.Entry<String, int[]>> iterator = unspentOuts.entrySet().iterator();

        TXInput[] txInputs = {};
        while (iterator.hasNext()) {
            Map.Entry<String, int[]> entry = iterator.next();
            String txIdStr = entry.getKey();
            int[] outIds = entry.getValue();
            byte[] txId = Hex.decodeHex(txIdStr);
            for (int outIndex : outIds) {
                txInputs = ArrayUtils.add(txInputs, new TXInput(txId, outIndex, null, pubKey));
            }
        }

        TXOutput[] txOutput = {};
        txOutput = ArrayUtils.add(txOutput, TXOutput.newTXOutput(amount, to));
        if (accumulated > amount) {
            txOutput = ArrayUtils.add(txOutput, TXOutput.newTXOutput((accumulated - amount), from));
        }

        Transaction newTx = new Transaction(null, txInputs, txOutput, System.currentTimeMillis());
        newTx.setTxId(newTx.hash());
        
        blockchain.signTransaction(newTx, senderWallet.getPrivateKey());

        return newTx;
    }
    
     // tạo bản sao giao dịch để sign, pubkry và sign cần đặt thành null
    public Transaction trimmedCopy() {
        TXInput[] tmpTXInputs = new TXInput[this.getInputs().length];
        for (int i = 0; i < this.getInputs().length; i++) {
            TXInput txInput = this.getInputs()[i];
            tmpTXInputs[i] = new TXInput(txInput.getTxId(), txInput.getTxOutputIndex(), null, null);
        }

        TXOutput[] tmpTXOutputs = new TXOutput[this.getOutputs().length];
        for (int i = 0; i < this.getOutputs().length; i++) {
            TXOutput txOutput = this.getOutputs()[i];
            tmpTXOutputs[i] = new TXOutput(txOutput.getValue(), txOutput.getPubKeyHash());
        }

        return new Transaction(this.getTxId(), tmpTXInputs, tmpTXOutputs, this.getCreateTime());
    }
    
    public void sign(BCECPrivateKey privateKey, Map<String, Transaction> prevTxMap) throws Exception {
        // prrivateKey : khoa rieng tu
        // prevTxMap : tập hợp các giao dịch trước đây
        // coinbase không cần ký
        if (this.isCoinbase()) {
            return;
        }
        // check inputTX
        for (TXInput txInput : this.getInputs()) {
            if (prevTxMap.get(Hex.encodeHexString(txInput.getTxId())) == null) {
                throw new RuntimeException("ERROR: Previous transaction is not correct");
            }
        }

        Transaction txCopy = this.trimmedCopy();

        Security.addProvider(new BouncyCastleProvider());
        Signature ecdsaSign = Signature.getInstance("SHA256withECDSA", BouncyCastleProvider.PROVIDER_NAME);
        ecdsaSign.initSign(privateKey);

        for (int i = 0; i < txCopy.getInputs().length; i++) {
            TXInput txInputCopy = txCopy.getInputs()[i];
           
            Transaction prevTx = prevTxMap.get(Hex.encodeHexString(txInputCopy.getTxId()));
            //lấy output của giao dịch trong giao dịch trước tương ứng với đầu vào của giao dịch
            TXOutput prevTxOutput = prevTx.getOutputs()[txInputCopy.getTxOutputIndex()];
            txInputCopy.setPubKey(prevTxOutput.getPubKeyHash());
            txInputCopy.setSignature(null);
           
            txCopy.setTxId(txCopy.hash());
            txInputCopy.setPubKey(null);

            // ký toàn bộ thông tin giao dịch
            ecdsaSign.update(txCopy.getTxId());
            byte[] signature = ecdsaSign.sign();
            
            // gán chữ kỹ cho input
            this.getInputs()[i].setSignature(signature);
        }
    }
    
    // xác thực giao dịch
    public boolean verify(Map<String, Transaction> prevTxMap) throws Exception {
        // coinbase không cần ký lên luôn đúng
        if (this.isCoinbase()) {
            return true;
        }

        // check input
        for (TXInput txInput : this.getInputs()) {
            if (prevTxMap.get(Hex.encodeHexString(txInput.getTxId())) == null) {
                throw new RuntimeException("ERROR: Previous transaction is not correct");
            }
        }

        Transaction txCopy = this.trimmedCopy();

        Security.addProvider(new BouncyCastleProvider());
        ECParameterSpec ecParameters = ECNamedCurveTable.getParameterSpec("secp256k1");
        KeyFactory keyFactory = KeyFactory.getInstance("ECDSA", BouncyCastleProvider.PROVIDER_NAME);
        Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA", BouncyCastleProvider.PROVIDER_NAME);

        for (int i = 0; i < this.getInputs().length; i++) {
            TXInput txInput = this.getInputs()[i];
            
            Transaction prevTx = prevTxMap.get(Hex.encodeHexString(txInput.getTxId()));
           
            TXOutput prevTxOutput = prevTx.getOutputs()[txInput.getTxOutputIndex()];

            TXInput txInputCopy = txCopy.getInputs()[i];
            txInputCopy.setSignature(null);
            txInputCopy.setPubKey(prevTxOutput.getPubKeyHash());
           
            txCopy.setTxId(txCopy.hash());
            txInputCopy.setPubKey(null);

            // xử dụng thuật toán đường coing eliptic xử lý 2 điểm x, y để tạo publikey
            BigInteger x = new BigInteger(1, Arrays.copyOfRange(txInput.getPubKey(), 1, 33));
            BigInteger y = new BigInteger(1, Arrays.copyOfRange(txInput.getPubKey(), 33, 65));
            ECPoint ecPoint = ecParameters.getCurve().createPoint(x, y);

            ECPublicKeySpec keySpec = new ECPublicKeySpec(ecPoint, ecParameters);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update(txCopy.getTxId());
            if (!ecdsaVerify.verify(txInput.getSignature())) {
                return false;
            }
        }
        return true;
    }

    public byte[] getTxId() {
        return this.txId;
    }

    public TXInput[] getInputs() {
        return this.inputs;
    }

    public TXOutput[] getOutputs() {
        return this.outputs;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setTxId(byte[] txId) {
        this.txId = txId;
    }

    public void setInputs(TXInput[] inputs) {
        this.inputs = inputs;
    }

    public void setOutputs(TXOutput[] outputs) {
        this.outputs = outputs;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Transaction)) return false;
        final Transaction other = (Transaction) o;
        if (!other.canEqual((Object) this)) return false;
        if (!Arrays.equals(this.getTxId(), other.getTxId())) return false;
        if (!Arrays.deepEquals(this.getInputs(), other.getInputs())) return false;
        if (!Arrays.deepEquals(this.getOutputs(), other.getOutputs())) return false;
        if (this.getCreateTime() != other.getCreateTime()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Transaction;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + Arrays.hashCode(this.getTxId());
        result = result * PRIME + Arrays.deepHashCode(this.getInputs());
        result = result * PRIME + Arrays.deepHashCode(this.getOutputs());
        final long $createTime = this.getCreateTime();
        result = result * PRIME + (int) ($createTime >>> 32 ^ $createTime);
        return result;
    }

    public String toString() {
        return "Transaction(txId=" + Arrays.toString(this.getTxId()) + ", inputs=" + Arrays.deepToString(this.getInputs()) + ", outputs=" + Arrays.deepToString(this.getOutputs()) + ", createTime=" + this.getCreateTime() + ")";
    }
}
