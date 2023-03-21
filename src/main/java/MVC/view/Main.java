/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.view;

import MVC.model.utils.IPFSUtils;


/**
 *
 * @author ssopt
 */
public class Main {
    public static void main(String[] args) {
//        try {
////            String[] argss = {"createwallet"};
//
////           String[] argss = {"printchain"};
////          String[] argss = {"createblockchain", "-address", "1AhAMqaDYz4DjBdTtWJGTARA6i4D9EtXns"};
////            String[] argss = {"mineblock", "-address", "1HBatwW6ocmSZtrHTa7Lgcu65zhDNohgtZ", "QmXeDxLvtgC13ukBuw8vSRiS5Ss71jGufSx8bgSEgWekzC"};
////            Wallet address: 1AhAMqaDYz4DjBdTtWJGTARA6i4D9EtXns
////            Wallet address: 1HBatwW6ocmSZtrHTa7Lgcu65zhDNohgtZ
//
////           String[] argss = {"printaddresses"};
////          String[] argss = {"getbalance", "-address", "1AhAMqaDYz4DjBdTtWJGTARA6i4D9EtXns"};
////            String[] argss = {"send", "-from", "1AhAMqaDYz4DjBdTtWJGTARA6i4D9EtXns", "-to", "1HBatwW6ocmSZtrHTa7Lgcu65zhDNohgtZ", "-amount", "4"};
////            0053953d908a57ad99528b2a29bcbba24b28311d72a3bd90c529fbad4cd57bb5
//            String[] argss = {"getdata", "-address", "00f27e09c585ee99210f2d9d641d96f78b7d004f8c9ab88410a6ea839bfd5922"};
//
//            CLI cli = new CLI(argss);
//            cli.parse();
////                String s = RocksDBUtils.getInstance().getLastBlockHash();
////                Block b = RocksDBUtils.getInstance().getBlock(s);
////            System.out.println(new String(b.getTransactions()[0].getInputs()[0].getPubKey(),"US-ASCII"));
//            //System.out.println(ByteUtils.ZERO_HASH);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

     try{
         String hash = IPFSUtils.addContent("C:\\Users\\ssopt\\ipfsserver\\img1.jpg");
            IPFSUtils.readContent(hash, "imgr");
         System.out.println(hash);
     }catch(Exception ex){
         ex.printStackTrace();
     }

    }
}
