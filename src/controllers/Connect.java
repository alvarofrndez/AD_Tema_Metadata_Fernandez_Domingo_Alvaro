/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author alvaro
 */
public class Connect {
    private Connection con = null;
    private DatabaseMetaData metadata;
    private String ins;
    private Statement st;
    private ResultSet rs;
    
    public Connect(String server, String port, String user, String password, String service){
        if(!checkData()){
            // throw error of data
            return;
        }
        
        this.con = createCon(server, port, user, password, service);
    }
    
    public Connection createCon(String server, String port, String user, String password, String service){
        // establish the connection with the database
        Connection con = null;
        try{
            String url = "jdbc:oracle:thin:@" + server + ":" + port + "/" + service;
            con = DriverManager.getConnection(url, user, password);
            this.metadata = con.getMetaData();
        }catch( SQLException e){
            System.out.println(e.getMessage());
            // create a controller for the messages with type of message (info,error,warning), where come the message (student, view, conection...), and the message
        }
        return con;
    }
    
    public ArrayList<String> getTables(){
        ArrayList<String> tables = new ArrayList<>();
        try{

            this.ins = "Select table_name from user_tables";
            this.st = this.con.createStatement();
            this.rs = this.st.executeQuery(this.ins);
            
            while(this.rs.next()){
                tables.add(this.rs.getString("table_name"));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return tables;
    }
    
    public Boolean checkData(){
        return true;
    }
    
    public Connection getConnect(){
        return this.con;
    }
}