/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.view;

import java.io.Serializable;



/**
 *
 * @author ssopt
 */
public class UserInfor implements Serializable {
    public String userName = null;
    public String passWord = null;
    public String mail = null;
    public String address = null;
    
    public UserInfor(String name, String email, String pass){
        this.userName = name;
        this.mail = email;
        this.passWord = pass;
        
        //this.address = BlockchainManager.createWallet();
    }
    
    public String getName(){
        return userName;
    }
    
    public String getEmail(){
        return mail;
    }
    
    public String getPass(){
        return passWord;
    }
    
    public String getAddress(){
        return address;
    }
}
