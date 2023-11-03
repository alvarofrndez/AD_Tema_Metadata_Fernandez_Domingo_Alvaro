/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvaro
 */
public class Execute {
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement ps; 
    private ArrayList<ArrayList<Object>> data = new ArrayList<>();
    
    public Execute(Connection con){
        this.con = con;
    }
    
    public ArrayList<ArrayList<Object>> executeQuery(String query, ArrayList<String>columns){
        this.data.removeAll(this.data);
        try{
            if(withConnection()){
                this.st = this.con.createStatement();
                this.rs = this.st.executeQuery(query);
                
                while(this.rs.next()){
                    ArrayList<Object> register = new ArrayList<>();
                    for(String field : columns){
                        register.add(rs.getObject(field));
                    }
                    data.add(register);
                }
            }
        }catch(SQLException e){
        }finally{
            return data;
        }
    }
    
    public Boolean getTypeField(String field, String table){
        String ins = "SELECT DATA_TYPE FROM USER_TAB_COLUMNS WHERE TABLE_NAME = ? AND COLUMN_NAME = ?";
        try {
            String result = "";
            if(withConnection()){
                this.ps = this.con.prepareStatement(ins);
                this.ps.setString(1, table);
                this.ps.setString(2, field);
                this.rs = this.ps.executeQuery();
                
                while(this.rs.next()){
                    result = this.rs.getString("DATA_TYPE");
                }
                
                if(result.equals("NUMBER") || result.equals("VARCHAR")){
                    return true;
                }else{
                    return false;
                }
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(Execute.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public ArrayList<ArrayList<Object>> getData(){
        return this.data;
    }
    
    public Boolean withConnection(){
        try{
            if(this.con == null || this.con.isClosed()){
                return false;
            }
            return true;
        }catch(SQLException e){
            return false;
        }
    }
}
