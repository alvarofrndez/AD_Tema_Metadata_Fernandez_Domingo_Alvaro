/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alvaro
 */
public class JTableResult {
    private JTable table;
    private DefaultTableModel model;
    private Connection con;
    private ArrayList<String> columns = new ArrayList<>();
    
    public JTableResult(Connection con){
        this.con = con;
        
        this.table = new JTable(new DefaultTableModel(new String[columns.size()], 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        
        this.model = (DefaultTableModel) this.table.getModel();
    }
    
    public JTable getTable(){
        return this.table;
    }
    
    public DefaultTableModel getModel(){
        return this.model;
    }
    
    public void setColumns(ArrayList<String> columns){
        this.model.setColumnCount(0);
        
        for(String column : columns){
            this.model.addColumn(column);
        }
    }
    
    public void addRow(String condition, String operator){
        this.model.addRow(new Object[]{condition, operator});
    }
    
    public Boolean withConnection(){
        try {
            if(this.con == null | this.con.isClosed()){
                return false;
            }
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
