/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model.transaction;

import MVC.model.utils.Base58Check;

import java.util.Arrays;

/**
 *
 * @author ssopt
 */

public class TXOutput {
    
    private int value;
    
    private byte[] pubKeyHash;

    public TXOutput(int value, byte[] pubKeyHash) {
        this.value = value;
        this.pubKeyHash = pubKeyHash;
    }

    public TXOutput() {
    }

    public static TXOutput newTXOutput(int value, String address){
        byte[] versionedPayload = Base58Check.base58ToBytes(address);
        byte[] pubKeyHash = Arrays.copyOfRange(versionedPayload, 1, versionedPayload.length);
        return new TXOutput(value, pubKeyHash);
    }
    
    // check xem dau ra cua giao dich co the su dung khoa chi dinh hay khong
    
    public boolean isLockedWithKey(byte[] pubKeyHash) {
        return Arrays.equals(this.getPubKeyHash(), pubKeyHash);
    }

    public int getValue() {
        return this.value;
    }

    public byte[] getPubKeyHash() {
        return this.pubKeyHash;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setPubKeyHash(byte[] pubKeyHash) {
        this.pubKeyHash = pubKeyHash;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TXOutput)) return false;
        final TXOutput other = (TXOutput) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getValue() != other.getValue()) return false;
        if (!Arrays.equals(this.getPubKeyHash(), other.getPubKeyHash())) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TXOutput;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getValue();
        result = result * PRIME + Arrays.hashCode(this.getPubKeyHash());
        return result;
    }

    public String toString() {
        return "TXOutput(value=" + this.getValue() + ", pubKeyHash=" + Arrays.toString(this.getPubKeyHash()) + ")";
    }
}
