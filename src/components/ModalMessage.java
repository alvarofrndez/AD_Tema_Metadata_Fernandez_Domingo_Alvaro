/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import javax.swing.JOptionPane;

/**
 *
 * @author alvaro
 */
public class ModalMessage {
    public ModalMessage(){
    }
    
    public void showMessage(String text, String title, String type){
        
        if(type.equals("error")){
            JOptionPane.showMessageDialog(
                null, 
                text, 
                title,
                JOptionPane.ERROR_MESSAGE
            );
        }else if(type.equals("succes")){
            JOptionPane.showMessageDialog(
                null, 
                text, 
                title,
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
