/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvaro
 */
public class JTableFieldsSelected {
    private JTable table;
    private DefaultTableModel model;
    private Connection con;
    
    public JTableFieldsSelected(Connection con){
        this.con = con;
        
        String[] columns = {"Campos seleccionados"};
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
    
    public void addRow(String column){
        this.model.addRow(new Object[]{column});
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
