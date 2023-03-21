/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model.wallet;

import MVC.model.utils.Base58Check;
import com.google.common.collect.Maps;
import lombok.Cleanup;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ssopt
 */

public class WalletUtils {
    
    private volatile static WalletUtils instance;
    
    public static WalletUtils getInstance(){
        if (instance == null){
            synchronized (WalletUtils.class) {
                if (instance == null){
                    instance = new WalletUtils();
                }
            }
        }
        return instance;
    }
    
    private WalletUtils() {
        initWalletFile();
    }
    // file quan ly vi
    private final static String WALLET_FILE = "wallet.dat";
    // thuat toan ma hoa
    private static final String ALGORITHM = "AES";
    
    private static final byte[] CIPHER_TEXT = "2oF@5sC%DNf32y!TmiZi!tG9W5rLaniD".getBytes();
    
    
    // creaat walletfile
    private void initWalletFile() {
        File file = new File(WALLET_FILE);
        if (!file.exists()) {
            this.saveToDisk(new Wallets());
        } else {
            this.loadFromDisk();
        }
    }
    
    // nhan tat ca cac dia chi Vi
    public Set<String> getAddresses() {
        Wallets wallets = this.loadFromDisk();
        return wallets.getAddresses();
    }
    
    public Wallet getWallet(String address) {
        Wallets wallets = this.loadFromDisk();
        return wallets.getWallet(address);
    }
    
    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        Wallets wallets = this.loadFromDisk();
        wallets.addWallet(wallet);
        this.saveToDisk(wallets);
        return wallet;
    }
    
    private void saveToDisk(Wallets wallets) {
        try {
            if (wallets == null) {
                //log.error("Fail to save wallet to file ! wallets is null ");
                throw new Exception("ERROR: Fail to save wallet to file !");
            }
            SecretKeySpec sks = new SecretKeySpec(CIPHER_TEXT, ALGORITHM);
            // Create cipher
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, sks);
            SealedObject sealedObject = new SealedObject(wallets, cipher);
            // Wrap the output stream
            @Cleanup CipherOutputStream cos = new CipherOutputStream(
                    new BufferedOutputStream(new FileOutputStream(WALLET_FILE)), cipher);
            @Cleanup ObjectOutputStream outputStream = new ObjectOutputStream(cos);
            outputStream.writeObject(sealedObject);
        } catch (Exception e) {
            //log.error("Fail to save wallet to disk !", e);
            throw new RuntimeException("Fail to save wallet to disk !");
        }
    }
    
    private Wallets loadFromDisk() {
        try {
            SecretKeySpec sks = new SecretKeySpec(CIPHER_TEXT, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, sks);
            @Cleanup CipherInputStream cipherInputStream = new CipherInputStream(
                    new BufferedInputStream(new FileInputStream(WALLET_FILE)), cipher);
            @Cleanup ObjectInputStream inputStream = new ObjectInputStream(cipherInputStream);
            SealedObject sealedObject = (SealedObject) inputStream.readObject();
            return (Wallets) sealedObject.getObject(cipher);
        } catch (Exception e) {
            //log.error("Fail to load wallet from disk ! ", e);
            throw new RuntimeException("Fail to load wallet from disk ! ");
        }
    }
    
    public static class Wallets implements Serializable {

        private static final long serialVersionUID = -2542070981569243131L;

        private Map<String, Wallet> walletMap = Maps.newHashMap();

        public Wallets(Map<String, Wallet> walletMap) {
            this.walletMap = walletMap;
        }

        public Wallets() {
        }


        private void addWallet(Wallet wallet) {
            try {
                this.walletMap.put(wallet.getAddress(), wallet);
            } catch (Exception e) {
                //log.error("Fail to add wallet ! ", e);
                throw new RuntimeException("Fail to add wallet !");
            }
        }

        Set<String> getAddresses() {
            if (walletMap == null) {
                //log.error("Fail to get address ! walletMap is null ! ");
                throw new RuntimeException("Fail to get addresses ! ");
            }
            return walletMap.keySet();
        }

        
        Wallet getWallet(String address) {
            try {
                Base58Check.base58ToBytes(address);
            } catch (Exception e) {
                //log.error("Fail to get wallet ! address invalid ! address=" + address, e);
                throw new RuntimeException("Fail to get wallet ! ");
            }
            Wallet wallet = walletMap.get(address);
            if (wallet == null) {
                //log.error("Fail to get wallet ! wallet don`t exist ! address=" + address);
                throw new RuntimeException("Fail to get wallet ! ");
            }
            return wallet;
        }

        public Map<String, Wallet> getWalletMap() {
            return this.walletMap;
        }

        public void setWalletMap(Map<String, Wallet> walletMap) {
            this.walletMap = walletMap;
        }

        public boolean equals(final Object o) {
            if (o == this) return true;
            if (!(o instanceof Wallets)) return false;
            final Wallets other = (Wallets) o;
            if (!other.canEqual((Object) this)) return false;
            final Object this$walletMap = this.getWalletMap();
            final Object other$walletMap = other.getWalletMap();
            if (this$walletMap == null ? other$walletMap != null : !this$walletMap.equals(other$walletMap))
                return false;
            return true;
        }

        protected boolean canEqual(final Object other) {
            return other instanceof Wallets;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final Object $walletMap = this.getWalletMap();
            result = result * PRIME + ($walletMap == null ? 43 : $walletMap.hashCode());
            return result;
        }

        public String toString() {
            return "WalletUtils.Wallets(walletMap=" + this.getWalletMap() + ")";
        }
    }
}
