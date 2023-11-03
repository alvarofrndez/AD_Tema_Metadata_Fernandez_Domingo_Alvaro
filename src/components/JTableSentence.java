/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alvaro
 */
public class JTableSentence {
    private JTable table;
    private DefaultTableModel model;
    private Connection con;
    private int row_selected;
    
    public JTableSentence(Connection con){
        this.con = con;
        
        String[] columns = {"Condicion", "Operador"};
        this.table = new JTable(new DefaultTableModel(columns, 0){
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
    
    public void setRowSelected(int row){
        this.row_selected = row;
    }
    
    public int getRowSelected(){
        return this.row_selected;
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
