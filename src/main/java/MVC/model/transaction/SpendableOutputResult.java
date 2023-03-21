/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model.transaction;

import java.util.Map;

/**
 *
 * @author ssopt
 */

public class SpendableOutputResult {
    
    // so tien thanh toan tai thoi diem giao dich
    private int accumulated;
    
    // giao dich chua chi tieu
    private Map<String, int[]> unspentOuts;

    public SpendableOutputResult(int accumulated, Map<String, int[]> unspentOuts) {
        this.accumulated = accumulated;
        this.unspentOuts = unspentOuts;
    }

    public SpendableOutputResult() {
    }

    public int getAccumulated() {
        return this.accumulated;
    }

    public Map<String, int[]> getUnspentOuts() {
        return this.unspentOuts;
    }

    public void setAccumulated(int accumulated) {
        this.accumulated = accumulated;
    }

    public void setUnspentOuts(Map<String, int[]> unspentOuts) {
        this.unspentOuts = unspentOuts;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SpendableOutputResult)) return false;
        final SpendableOutputResult other = (SpendableOutputResult) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getAccumulated() != other.getAccumulated()) return false;
        final Object this$unspentOuts = this.getUnspentOuts();
        final Object other$unspentOuts = other.getUnspentOuts();
        if (this$unspentOuts == null ? other$unspentOuts != null : !this$unspentOuts.equals(other$unspentOuts))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SpendableOutputResult;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getAccumulated();
        final Object $unspentOuts = this.getUnspentOuts();
        result = result * PRIME + ($unspentOuts == null ? 43 : $unspentOuts.hashCode());
        return result;
    }

    public String toString() {
        return "SpendableOutputResult(accumulated=" + this.getAccumulated() + ", unspentOuts=" + this.getUnspentOuts() + ")";
    }
}
