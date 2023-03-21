/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model.pow;

import MVC.model.blockchain.Block;
import MVC.model.utils.ByteUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;

/**
 *
 * @author ssopt
 */

public class ProofOfWork {
    public static final int TARGET_BITS = 8;

    private Block block;
    
    private BigInteger target;

    public ProofOfWork(Block block, BigInteger target) {
        this.block = block;
        this.target = target;
    }

    public ProofOfWork() {
    }

    // create new pow , set target diffirent
    // dich bit sang trai (256-target)
    public static ProofOfWork newProofOfWork(Block block) {
        BigInteger targetValue = BigInteger.valueOf(1).shiftLeft((256 - TARGET_BITS));
        return new ProofOfWork(block, targetValue);
    }
    
    //run pow, find hash if hash < target
    public PowResult run() {
        long nonce = 0;
        String shaHex = "";
        long startTime = System.currentTimeMillis();
        while (nonce < Long.MAX_VALUE) {
            byte[] data = this.prepareData(nonce);
            shaHex = DigestUtils.sha256Hex(data);
            if (new BigInteger(shaHex, 16).compareTo(this.target) == -1) {
        
                break;
            } else {
                nonce++;
            }
        }
        return new PowResult(nonce, shaHex);
    }
    
    // xac minh block hop le 
    public boolean validate() {
        byte[] data = this.prepareData(this.getBlock().getNonce());
        return new BigInteger(DigestUtils.sha256Hex(data), 16).compareTo(this.target) == -1;
    }
    
    private byte[] prepareData(long nonce) {
        byte[] prevBlockHashBytes = {};
        if (StringUtils.isNotBlank(this.getBlock().getPrevBlockHash())) {
            prevBlockHashBytes = new BigInteger(this.getBlock().getPrevBlockHash(), 16).toByteArray();
        }

        return ByteUtils.merge(
                prevBlockHashBytes,
                this.getBlock().hashTransaction(),
                ByteUtils.toBytes(this.getBlock().getTimeStamp()),
                ByteUtils.toBytes(TARGET_BITS),
                ByteUtils.toBytes(nonce)
        );
    }

    public Block getBlock() {
        return this.block;
    }

    public BigInteger getTarget() {
        return this.target;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public void setTarget(BigInteger target) {
        this.target = target;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ProofOfWork)) return false;
        final ProofOfWork other = (ProofOfWork) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$block = this.getBlock();
        final Object other$block = other.getBlock();
        if (this$block == null ? other$block != null : !this$block.equals(other$block)) return false;
        final Object this$target = this.getTarget();
        final Object other$target = other.getTarget();
        if (this$target == null ? other$target != null : !this$target.equals(other$target)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProofOfWork;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $block = this.getBlock();
        result = result * PRIME + ($block == null ? 43 : $block.hashCode());
        final Object $target = this.getTarget();
        result = result * PRIME + ($target == null ? 43 : $target.hashCode());
        return result;
    }

    public String toString() {
        return "ProofOfWork(block=" + this.getBlock() + ", target=" + this.getTarget() + ")";
    }
}
