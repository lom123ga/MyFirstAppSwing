/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model.wallet;

import MVC.model.utils.Base58Check;
import MVC.model.utils.BtcAddressUtils;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;

/**
 *
 * @author ssopt
 */

public class Wallet implements Serializable{
    private static final long serialVersionUID = 166249065006236265L;
 
    private static final int ADDRESS_CHECKSUM_LEN = 4;
 
    private BCECPrivateKey privateKey;
 
    private byte[] publicKey;
    
    public Wallet(){
        initWallet();
    }

    public Wallet(BCECPrivateKey privateKey, byte[] publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    private void initWallet(){
        try {
            KeyPair keyPair = newECKeyPair();
            BCECPrivateKey privateKeys = (BCECPrivateKey) keyPair.getPrivate();
            BCECPublicKey publicKeys = (BCECPublicKey) keyPair.getPublic();
 
            byte[] publicKeyBytes = publicKeys.getQ().getEncoded(false);
 
            this.setPrivateKey(privateKeys);
            this.setPublicKey(publicKeyBytes);
        } catch (Exception e) {
            throw new RuntimeException("Fail to init wallet ! ", e);
        }
    }
    
    private KeyPair newECKeyPair() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
 
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", BouncyCastleProvider.PROVIDER_NAME);
 
        ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256k1");
 
        keyPairGenerator.initialize(ecSpec, new SecureRandom());
 
        return keyPairGenerator.generateKeyPair();
    }

    public String getAddress() {
        try {
            // 1. ripemdHashedKey
            byte[] ripemdHashedKey = BtcAddressUtils.ripeMD160Hash(this.getPublicKey());
 
            // 2. add 0x00
            ByteArrayOutputStream addrStream = new ByteArrayOutputStream();
            addrStream.write((byte) 0);
            addrStream.write(ripemdHashedKey);
            byte[] versionedPayload = addrStream.toByteArray();
 
            // 3. checksum
            byte[] checksum = BtcAddressUtils.checksum(versionedPayload);
 
            // 4. version + paylod + checksum 
            addrStream.write(checksum);
            byte[] binaryAddress = addrStream.toByteArray();
 
            // 5. convert Base58
            return Base58Check.rawBytesToBase58(binaryAddress);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to get wallet address ! ");
        }
 
    }

    public BCECPrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public byte[] getPublicKey() {
        return this.publicKey;
    }

    public void setPrivateKey(BCECPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Wallet)) return false;
        final Wallet other = (Wallet) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$privateKey = this.getPrivateKey();
        final Object other$privateKey = other.getPrivateKey();
        if (this$privateKey == null ? other$privateKey != null : !this$privateKey.equals(other$privateKey))
            return false;
        if (!java.util.Arrays.equals(this.getPublicKey(), other.getPublicKey())) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Wallet;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $privateKey = this.getPrivateKey();
        result = result * PRIME + ($privateKey == null ? 43 : $privateKey.hashCode());
        result = result * PRIME + java.util.Arrays.hashCode(this.getPublicKey());
        return result;
    }

    public String toString() {
        return "Wallet(privateKey=" + this.getPrivateKey() + ", publicKey=" + java.util.Arrays.toString(this.getPublicKey()) + ")";
    }
}