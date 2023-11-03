/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;

/**
 *
 * @author alvaro
 */
public class ModalCon {
    
    private JFrame frame;
    private JTextArea server_input;
    private JTextArea port_input;
    private JTextArea user_input;
    private JPasswordField pass_input;
    private JButton accept;
    
    public ModalCon(){
        this.frame = new JFrame("Datos conexión");
        this.frame.setSize(400,170);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel global_container = new JPanel();
        global_container.setPreferredSize(new Dimension(400,170));
        global_container.setLayout(new FlowLayout());
        
        JPanel server_container = new JPanel();
        server_container.setPreferredSize(new Dimension(350, 20));
        server_container.setLayout(new BoxLayout(server_container, BoxLayout.X_AXIS));
        
        JLabel server_label = new JLabel("Sevidor");
        server_label.setPreferredSize(new Dimension(100, 20));
        
        this.server_input = new JTextArea();
        this.server_input.setPreferredSize(new Dimension(100, 20));

        server_container.add(server_label);
        server_container.add(this.server_input);
        
        JPanel port_container = new JPanel();
        port_container.setPreferredSize(new Dimension(350, 20));
        port_container.setLayout(new BoxLayout(port_container, BoxLayout.X_AXIS));
        
        JLabel port_label = new JLabel("Puerto");
        port_label.setPreferredSize(new Dimension(100, 20));
        
        this.port_input = new JTextArea();
        this.port_input.setPreferredSize(new Dimension(100, 20));

        port_container.add(port_label);
        port_container.add(this.port_input);
        
        JPanel user_container = new JPanel();
        user_container.setPreferredSize(new Dimension(350, 20));
        user_container.setLayout(new BoxLayout(user_container, BoxLayout.X_AXIS));
        
        JLabel user_label = new JLabel("Usuario");
        user_label.setPreferredSize(new Dimension(100, 20));
        
        this.user_input = new JTextArea();
        this.user_input.setPreferredSize(new Dimension(100, 20));

        user_container.add(user_label);
        user_container.add(this.user_input);
        
        JPanel pass_container = new JPanel();
        pass_container.setPreferredSize(new Dimension(350, 20));
        pass_container.setLayout(new BoxLayout(pass_container, BoxLayout.X_AXIS));
        
        JLabel pass_label = new JLabel("Contraseña");
        pass_label.setPreferredSize(new Dimension(100, 20));
        
        this.pass_input = new JPasswordField(20);
        this.pass_input.setEchoChar('*');

        pass_container.add(pass_label);
        pass_container.add(this.pass_input);
        
        this.accept = new JButton("aceptar");
        this.accept.setSize(50, 50);
        
        global_container.add(server_container);
        global_container.add(port_container);
        global_container.add(user_container);
        global_container.add(pass_container);
        global_container.add(accept);
        
        frame.add(global_container);
        
        frame.setVisible(true);
    }
    
    public JFrame getFrame(){
        return this.frame;
    }
    
    public String getServer(){
        return this.server_input.getText();
    }
    
    public String getPort(){
        return this.port_input.getText();
    }
    
    public String getUser(){
        return this.user_input.getText();
    }
    
    public String getPass(){
        char[] password = this.pass_input.getPassword();
        return new String(password);
    }
    
    public JButton getAccept(){
        return this.accept;
    }
}
