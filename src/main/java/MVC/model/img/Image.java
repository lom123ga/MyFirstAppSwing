package MVC.model.img;


public class Image {
    
    public String typeImg = "jpg";
    
    public String nameImg = "";
    
    public String ipfsHash = "";

    public Image(){
        
    }
    
    public Image(String name, String hash) {
        nameImg = name;
        ipfsHash = hash;
    }
    
    public String getName(){
        return nameImg;
    }
    
    public String getIpfsHash(){
        return ipfsHash;
    }
}
