/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author alvaro
 */
public class Export {
    
    public Export(){
    }
    
    public Boolean exportData(String path1, String path2, ArrayList<ArrayList<Object>> data, Boolean overwrite1, Boolean overwrite2){
        
        if(exportNormal(path1, data, overwrite1) && exportSerializable(path2, data, overwrite2)){
            return true;
        }
        
        return false;
    }
    
    public Boolean exportSerializable(String path, ArrayList<ArrayList<Object>> data, Boolean overwrite){
        try {
            FileOutputStream fos = new FileOutputStream(path, overwrite);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            fos.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public Boolean exportNormal(String path, ArrayList<ArrayList<Object>> data, Boolean overwrite){
        FileWriter writer = null;
        
        try {
            writer = new FileWriter(path, overwrite);
            
            String data_string = "";
            
            for(ArrayList<Object> register : data){
                String line = "";
                for(Object value : register){
                    line += value + ",";
                }
                line = line.substring(0, line.length() - 1);
                
                data_string += line + "\n";  
            }
            
            writer.write(data_string);
            return true;
        } catch (IOException ex) {
            return false;
        }finally{
            try {
                writer.close();
            } catch (IOException ex) {
            }
        }
    }
    
    public Boolean checkPath(String path){
        File file = new File(path);
        
        if(file.exists()){
            return true;
        }else{
            return false;
        }
    }
}
