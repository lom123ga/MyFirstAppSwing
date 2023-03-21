/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model.pow;

/**
 *
 * @author ssopt
 */

public class PowResult {
    
    private long nonce;
    
    private String hash;

    public PowResult(long nonce, String hash) {
        this.nonce = nonce;
        this.hash = hash;
    }

    public PowResult() {
    }

    public long getNonce() {
        return this.nonce;
    }

    public String getHash() {
        return this.hash;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PowResult)) return false;
        final PowResult other = (PowResult) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getNonce() != other.getNonce()) return false;
        final Object this$hash = this.getHash();
        final Object other$hash = other.getHash();
        if (this$hash == null ? other$hash != null : !this$hash.equals(other$hash)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PowResult;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $nonce = this.getNonce();
        result = result * PRIME + (int) ($nonce >>> 32 ^ $nonce);
        final Object $hash = this.getHash();
        result = result * PRIME + ($hash == null ? 43 : $hash.hashCode());
        return result;
    }

    public String toString() {
        return "PowResult(nonce=" + this.getNonce() + ", hash=" + this.getHash() + ")";
    }
}
