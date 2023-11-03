/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author alvaro
 */
public class Table {
    private Connection con;
    private DatabaseMetaData metadata;
    private ResultSet rs; 
    private ArrayList<String> columns = new ArrayList<>();
    private ArrayList<String> columns_selected = new ArrayList<>();
    private String field = "";
    private String field_selected = "";
    private String table_name = "";
    
    public Table(Connection con){
        this.con = con;
        try{
            this.metadata = con.getMetaData();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void getColumnsName(String table_name){
        this.table_name = table_name;
        this.columns.removeAll(this.columns);
        try{
            if(withConnection()){
                this.rs = this.metadata.getColumns(null, null, table_name, null);
                while(this.rs.next()){
                    String column_name = this.rs.getString("COLUMN_NAME");
                    this.columns.add(column_name);
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public String getTableName(){
        return this.table_name;
    }
    
    public void setField(String field){
        this.field = field;
    }
    
    public String getField(){
        return this.field;
    }
    
    public void setFieldSelected(String field){
        this.field_selected = field;
    }
    
    public String getFieldSelected(){
        return this.field_selected;
    }
    
    public ArrayList<String> getColumns(){
        return this.columns;
    }
    
    public ArrayList<String> getColumnsSelected(){
        return this.columns_selected;
    }
    
    public Boolean withConnection(){
        try{
            if(this.con == null || this.con.isClosed()){
                return false;
            }
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
