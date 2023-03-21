package MVC.model.img;

public class Image {
    
    public String typeImg = "jpg";
    
    public String nameImg = "";
    
    public String ipfsHash = "";

    public Image(String name, String hash) {
        nameImg = name;
        ipfsHash = hash;
    }
}
