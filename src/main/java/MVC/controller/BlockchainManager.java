/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.controller;

import MVC.model.blockchain.Block;
import MVC.model.blockchain.Blockchain;
import MVC.model.img.Image;
import MVC.model.transaction.Transaction;
import MVC.model.transaction.UTXOSet;
import MVC.model.utils.IPFSUtils;
import MVC.model.utils.SerializeUtils;
import MVC.model.wallet.Wallet;
import MVC.model.wallet.WalletUtils;
import java.util.Set;

/**
 *
 * @author ssopt
 */
public class BlockchainManager {
    public static String curentHash = "";
    public static String cureentAddress = "1AhAMqaDYz4DjBdTtWJGTARA6i4D9EtXns";
    
    public static void upLoadImg(String path){
        String address = cureentAddress;
        String hash = IPFSUtils.addContent(path);
        String name = "";
        int be,ed;
        be = path.length() - 1;
        ed = path.length() - 1;
        while (!String.valueOf(path.charAt(ed)).equals(".")){
            ed -= 1;
        }
        while (!String.valueOf(path.charAt(be)).equals("\\")){
            be -= 1;
        }
        ed -= 1;
        be += 1;
        for (int i = be ; i <= ed; i++){
            name += String.valueOf(path.charAt(i));
        }
        Image img = new Image(name, hash);
        byte[] imgByte = SerializeUtils.serialize(img);
        
        Blockchain blockchain = Blockchain.createBlockchain(address);
        Transaction upTx = Transaction.newCoinbaseTX(address, imgByte);
        Block newBlock = blockchain.mineBlock(new Transaction[]{upTx});
        new UTXOSet(blockchain).update(newBlock);
        curentHash =  newBlock.getHash();
    }
    
    public static void PinImg(String hash){
        //String hash = curentHash;
        byte[] data = null;
        Blockchain blockchain = Blockchain.initBlockchainFromDB();
        for (Blockchain.BlockchainIterator iterator = blockchain.getBlockchainIterator(); iterator.hashNext(); ) {
            Block block = iterator.next();
            if (block != null) {
                if (block.getHash().equals(hash)){
                    data = block.getTransactions()[0].getInputs()[0].getPubKey();
                }
            }
        }
        Image img = (Image) SerializeUtils.deserialize(data);
        IPFSUtils.PinContent(img.getIpfsHash());
    }
    
    public static void UnPinImg(String hash){
        //String hash = curentHash;
        byte[] data = null;
        Blockchain blockchain = Blockchain.initBlockchainFromDB();
        for (Blockchain.BlockchainIterator iterator = blockchain.getBlockchainIterator(); iterator.hashNext(); ) {
            Block block = iterator.next();
            if (block != null) {
                if (block.getHash().equals(hash)){
                    data = block.getTransactions()[0].getInputs()[0].getPubKey();
                }
            }
        }
        Image img = (Image) SerializeUtils.deserialize(data);
        IPFSUtils.UnPinContnt(img.getIpfsHash());
    }
    
    public static String FindImg(String hash){
        byte[] data = null;
        Blockchain blockchain = Blockchain.initBlockchainFromDB();
        for (Blockchain.BlockchainIterator iterator = blockchain.getBlockchainIterator(); iterator.hashNext(); ) {
            Block block = iterator.next();
            if (block != null) {
                if (block.getHash().equals(hash)){
                    data = block.getTransactions()[0].getInputs()[0].getPubKey();
                }
            }
        }
        
        String path = "Can not find image";
        try{
            Image img = (Image) SerializeUtils.deserialize(data);
            IPFSUtils.readContent(img.getIpfsHash(), img.nameImg);
            path = "IMG\\" + img.getName() + ".jpg";

        }
        catch (Exception e){
            
        }
        return path;
    }
    
    public static Set<String> FindAllAddress(){
        Set<String> addresses = WalletUtils.getInstance().getAddresses();
        
        return addresses;
    }
    
    public static String createWallet(){
        Wallet wallet = WalletUtils.getInstance().createWallet();
        System.out.println("wallet address : " + wallet.getAddress());
        return wallet.getAddress();
    }
}
