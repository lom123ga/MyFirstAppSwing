/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ssopt
 */
public class BINUtils {
    public static <T> List<T> read(String file){
        List<T> list = new ArrayList<>();
        try{
            ObjectInputStream a = new ObjectInputStream(new FileInputStream(file));
            list = (List<T>) a.readObject();
            a.close();
        } catch(Exception e){
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
        return list;
    }
    
    
    public static <T> void write(String file, List<T> list){
        try{
            FileOutputStream f = new FileOutputStream(new File(file));
            ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(file));
            a.writeObject(list);
            a.close();
        } catch(Exception e){
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
    }
}
