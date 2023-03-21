/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model.transaction;

import MVC.model.utils.BtcAddressUtils;

import java.util.Arrays;

/**
 *
 * @author ssopt
 */

public class TXInput {
    
    private byte[] txId;
    
    private int txOutputIndex;
    
    private byte[] signature;
    
    private byte[] pubKey;

    public TXInput(byte[] txId, int txOutputIndex, byte[] signature, byte[] pubKey) {
        this.txId = txId;
        this.txOutputIndex = txOutputIndex;
        this.signature = signature;
        this.pubKey = pubKey;
    }

    public TXInput() {
    }

    // check xem key da duoc su dung cho dau vao cua giao dich khong
    
    public boolean usesKey(byte[] pubKeyHash){
        byte[] lookingHash = BtcAddressUtils.ripeMD160Hash(this.getPubKey());
        return Arrays.equals(lookingHash, pubKeyHash);
    }

    public byte[] getTxId() {
        return this.txId;
    }

    public int getTxOutputIndex() {
        return this.txOutputIndex;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public byte[] getPubKey() {
        return this.pubKey;
    }

    public void setTxId(byte[] txId) {
        this.txId = txId;
    }

    public void setTxOutputIndex(int txOutputIndex) {
        this.txOutputIndex = txOutputIndex;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public void setPubKey(byte[] pubKey) {
        this.pubKey = pubKey;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TXInput)) return false;
        final TXInput other = (TXInput) o;
        if (!other.canEqual((Object) this)) return false;
        if (!Arrays.equals(this.getTxId(), other.getTxId())) return false;
        if (this.getTxOutputIndex() != other.getTxOutputIndex()) return false;
        if (!Arrays.equals(this.getSignature(), other.getSignature())) return false;
        if (!Arrays.equals(this.getPubKey(), other.getPubKey())) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TXInput;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + Arrays.hashCode(this.getTxId());
        result = result * PRIME + this.getTxOutputIndex();
        result = result * PRIME + Arrays.hashCode(this.getSignature());
        result = result * PRIME + Arrays.hashCode(this.getPubKey());
        return result;
    }

    public String toString() {
        return "TXInput(txId=" + Arrays.toString(this.getTxId()) + ", txOutputIndex=" + this.getTxOutputIndex() + ", signature=" + Arrays.toString(this.getSignature()) + ", pubKey=" + Arrays.toString(this.getPubKey()) + ")";
    }
}
