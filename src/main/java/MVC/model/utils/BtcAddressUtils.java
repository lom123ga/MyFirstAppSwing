package MVC.model.utils;


import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.util.Arrays;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author ssopt
 */
public class BtcAddressUtils {
    
    // hash 2 lan bang thuat SHA256
    
    public static byte[] doubleHash(byte[] data) {
        return DigestUtils.sha256(DigestUtils.sha256(data));
    }
    
    // 
    
    public static byte[] ripeMD160Hash(byte[] pubKey) {
        //1. sha256
        byte[] shaHashedKey = DigestUtils.sha256(pubKey);
        RIPEMD160Digest ripemd160 = new RIPEMD160Digest();
        ripemd160.update(shaHashedKey, 0, shaHashedKey.length);
        byte[] output = new byte[ripemd160.getDigestSize()];
        ripemd160.doFinal(output, 0);
        return output;
    }
    
    public static byte[] checksum(byte[] payload) {
        return Arrays.copyOfRange(doubleHash(payload), 0, 4);
    }
}
