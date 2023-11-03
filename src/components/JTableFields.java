/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import java.sql.Connection;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alvaro
 */
public class JTableFields {
    
    private Connection con;
    private JTable table;
    private DefaultTableModel model;
    
    public JTableFields(Connection con){
        this.con = con;
        
        String[] columns = {"Campos"};
        this.table = new JTable(new DefaultTableModel(columns, 0){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            }
        );
        this.table.setComponentPopupMenu(null);
        
        this.model = (DefaultTableModel) this.table.getModel();
    }
    
    public JTable getTable(){
        return this.table;
    }
    
    public void addRow(String column){
        this.model.addRow(new Object[]{column});
    }
    
    public DefaultTableModel getModel(){
        return this.model;
    }
}
