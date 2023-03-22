/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model.utils;

import MVC.model.img.Image;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author ssopt
 */
public class IPFSUtils {
   
   
    public static IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
    
    public static String addContent(String path){
        String hash = "";
        try{
            NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File(path));
            MerkleNode response = ipfs.add(file).get(0);
            hash = response.hash.toBase58();
        }catch (IOException ex){
            throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
        }
        return hash;
    }
    
    public static void readContent(String hash, String name){
        try {
            Multihash multihash = Multihash.fromBase58(hash);
            byte[] content = ipfs.cat(multihash);
            ByteArrayInputStream bais = new ByteArrayInputStream(content);
            BufferedImage image = ImageIO.read(bais);
            String path = "IMG\\" + name + ".jpg";
            File output = new File(path);
            ImageIO.write(image, "jpg", output);
          } catch (IOException ex) {
            throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
          }
    }
    
    public static void PinContent(String hash){
        try {
            Multihash multihash = Multihash.fromBase58(hash);
            ipfs.pin.add(multihash);
          } catch (IOException ex) {
            throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
          }
    }
    
    public static void UnPinContnt(String hash){
        try {
            Multihash multihash = Multihash.fromBase58(hash);
            ipfs.pin.rm(multihash);
          } catch (IOException ex) {
            throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
          }
    }
}
