/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import components.JTableFields;
import components.JTableFieldsSelected;
import components.JTableResult;
import components.JTableSentence;
import components.ModalCon;
import components.ModalMessage;
import controllers.Connect;
import controllers.Execute;
import controllers.Export;
import controllers.Operation;
import controllers.Table;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Connection;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import models.SentenceModel;

/**
 *
 * @author alvaro
 */
public class Index {
    
    private Connect con_ctrl;
    private Table table_ctrl;
    private Operation operation_ctrl;
    private Execute execute_ctrl;
    private Export export_ctrl;
    
    private JTableFields jtable_fields;
    private JTableFieldsSelected jtable_fields_selected;
    private JTableSentence jtable_sentence;
    private ModalMessage modal_message;
    private JTableResult jtable_result; 
    
    private JFrame frame;
    private Connection con;
    private JComboBox<String> field_dropdown;
    private JTextArea sentence_area;
    private JButton execute_button;
    private JButton export_button;
    
    public Index(){
        ModalCon modal = new ModalCon();
        modal.getAccept().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                modal.getFrame().dispose();
                run(modal.getServer(),modal.getPort(),modal.getUser(),modal.getPass());
            }
        });;
    }
    
    public void run(String server, String port, String user, String pass){
        this.modal_message = new ModalMessage();
        this.con_ctrl = new Connect(server, port, user, pass, "xe");
        this.con = this.con_ctrl.getConnect();
        
        if(this.con == null){
            this.modal_message.showMessage("No se ha podido establecer la conexión", "Error de conexión", "error");
        }else{
            createGui();
        }
        
    }
    
    public void createGui(){
        this.operation_ctrl = new Operation();
        this.execute_ctrl = new Execute(this.con);
        this.table_ctrl = new Table(con);
        this.export_ctrl = new Export();
        
        this.frame = new JFrame("Metadatos");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(1200, 900);
        
        JPanel global_container = new JPanel();
        global_container.setPreferredSize(new Dimension(1200,900));
        global_container.setLayout(new FlowLayout());
        
        JPanel first_container = new JPanel();
        first_container.setLayout(new BoxLayout(first_container, BoxLayout.X_AXIS));
        first_container.setPreferredSize(new Dimension(1200, 300));

        JPanel second_container = new JPanel();
        second_container.setLayout(new BoxLayout(second_container, BoxLayout.X_AXIS));
        second_container.setPreferredSize(new Dimension(1200, 300));
        
        JPanel third_container = new JPanel();
        third_container.setLayout(new BoxLayout(third_container, BoxLayout.X_AXIS));
        third_container.setPreferredSize(new Dimension(1200, 300));
        
        createFirstContainer(first_container);
        createSecondContainer(second_container);
        createThirdContainer(third_container);
        
        global_container.add(first_container);
        global_container.add(second_container);
        global_container.add(third_container);
        
        frame.add(global_container);
        frame.setVisible(true);
    }
    
    public void createFirstContainer(JPanel first_container){
        JPanel left_container = new JPanel();
        left_container.setLayout(new BoxLayout(left_container, BoxLayout.Y_AXIS));
        
        createLeftContainer(left_container);
        
        JPanel right_container = new JPanel();
        right_container.setSize(700, 300);
        right_container.setLayout(new BoxLayout(right_container, BoxLayout.Y_AXIS));
        
        createRightContainer(right_container);
        
        first_container.add(left_container);
        first_container.add(right_container);
    }
    
    public void createLeftContainer(JPanel left_container){
        JPanel left_top = new JPanel();
        left_top.setSize(new Dimension(50, 50));
        left_top.setLayout(new BoxLayout(left_top, BoxLayout.X_AXIS));
        
        JLabel table_label = new JLabel("tabla");
        
        ArrayList<String> options = this.con_ctrl.getTables();

        JComboBox<String> dropdown = new JComboBox<>(options.toArray(new String[0]));
        dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // select the table
                String selected_value = (String) dropdown.getSelectedItem();
                table_ctrl.getColumnsName(selected_value);
                table_ctrl.getColumnsSelected().removeAll(table_ctrl.getColumnsSelected());
                
                if(table_ctrl.getColumns().size() == 0){
                    modal_message.showMessage("La tabla no tiene campos", "Tabla vacía", "succes");
                }else{
                    clearProgram(selected_value);
                }
            }
        });
        
        JPanel left_bot = new JPanel();
        left_bot.setSize(new Dimension(200, 200));
        left_bot.setLayout(new BoxLayout(left_bot, BoxLayout.X_AXIS));
        
        // create the table for the fields
        this.jtable_fields = new JTableFields(this.con);
        this.jtable_fields.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    if (e.getClickCount() == 1) {
                        int selected_row = jtable_fields.getTable().getSelectedRow();
                        
                        String field = (String) jtable_fields.getTable().getValueAt(selected_row, 0);
                        
                        table_ctrl.setField(field);
                    }
                }catch(Exception ex){
                    return;
                }
            }
        });
        
        JPanel buttons_container = new JPanel();
        buttons_container.setPreferredSize(new Dimension(50,200));
        buttons_container.setLayout(new BoxLayout(buttons_container, BoxLayout.Y_AXIS));
        
        JButton add_one = new JButton(">");
        add_one.setSize(50, 25);
        add_one.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!table_ctrl.getTableName().equals("")){
                    if(!table_ctrl.getField().equals("")){
                        table_ctrl.getColumns().remove(table_ctrl.getField());
                        table_ctrl.getColumnsSelected().add(table_ctrl.getField());

                        fillFieldsTable(table_ctrl.getColumns());
                        fillFieldsSelectedTable(table_ctrl.getColumnsSelected());

                        setSentenceSelect();

                        table_ctrl.setField("");
                    }
                }else{
                    modal_message.showMessage("Elija una tabla", "Tabla sin elegir", "succes");
                }
            }
        });
        
        JButton add_all = new JButton(">>");
        add_all.setSize(50, 25);
        add_all.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!table_ctrl.getTableName().equals("")){
                    if(table_ctrl.getColumns().size() > 0){
                        for(String column : table_ctrl.getColumns()){
                            table_ctrl.getColumnsSelected().add(column);
                        }

                        table_ctrl.getColumns().removeAll(table_ctrl.getColumns());

                        fillFieldsTable(table_ctrl.getColumns());
                        fillFieldsSelectedTable(table_ctrl.getColumnsSelected());

                        setSentenceSelect();

                        table_ctrl.setField("");
                    }
                }else{
                    modal_message.showMessage("Elija una tabla", "Tabla sin elegir", "succes");
                }
                
            }
        });

        JButton remove_one = new JButton("<");
        remove_one.setSize(50, 25);
        remove_one.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!table_ctrl.getFieldSelected().equals("")){
                    table_ctrl.getColumns().add(table_ctrl.getFieldSelected());
                    table_ctrl.getColumnsSelected().remove(table_ctrl.getFieldSelected());
                
                    fillFieldsTable(table_ctrl.getColumns());
                    fillFieldsSelectedTable(table_ctrl.getColumnsSelected());
                    
                    setSentenceSelect();
                    
                    table_ctrl.setFieldSelected("");
                }
            }
        });
        
        JButton remove_all = new JButton("<<");
        remove_all.setSize(50, 25);
        remove_all.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(table_ctrl.getColumnsSelected().size() > 0){
                    for(String column : table_ctrl.getColumnsSelected()){
                        table_ctrl.getColumns().add(column);
                    }

                    table_ctrl.getColumnsSelected().removeAll(table_ctrl.getColumnsSelected());

                    fillFieldsTable(table_ctrl.getColumns());
                    fillFieldsSelectedTable(table_ctrl.getColumnsSelected());
                    
                    setSentenceSelect();
                    
                    table_ctrl.setFieldSelected("");
                }
            }
        });
        
        buttons_container.add(add_one);
        buttons_container.add(add_all);
        buttons_container.add(remove_one);
        buttons_container.add(remove_all);
        
        // create the table for the fields selecteds
        this.jtable_fields_selected = new JTableFieldsSelected(this.con);
        this.jtable_fields_selected.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    if (e.getClickCount() == 1) {
                        int selected_row = jtable_fields_selected.getTable().getSelectedRow();
                        
                        String field = (String) jtable_fields_selected.getTable().getValueAt(selected_row, 0);
                        
                        table_ctrl.setFieldSelected(field);
                    }
                }catch(Exception ex){
                    return;
                }
            }
        });
        
        left_top.add(table_label);
        left_top.add(dropdown);
        
        left_bot.add(new JScrollPane(this.jtable_fields.getTable()), BorderLayout.LINE_START);
        left_bot.add(buttons_container);
        left_bot.add(new JScrollPane(this.jtable_fields_selected.getTable()), BorderLayout.LINE_END);
        
        left_container.add(left_top);
        left_container.add(left_bot);
    }
    
    public void createRightContainer(JPanel right_container){
        // create the right_top
        JPanel right_top = new JPanel();
        right_top.setSize(700, 150);
        right_top.setLayout(new BoxLayout(right_top, BoxLayout.X_AXIS));
        
        JPanel options_container = new JPanel();
        options_container.setSize(300, 150);
        options_container.setLayout(new BoxLayout(options_container, BoxLayout.Y_AXIS));
        
        JPanel field_container = new JPanel();
        field_container.setSize(100, 50);
        field_container.setLayout(new BoxLayout(field_container, BoxLayout.X_AXIS));
        
        JLabel field_label = new JLabel("campo");
        field_label.setSize(50,50);
        
        this.field_dropdown = new JComboBox<>();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        this.field_dropdown.setModel(model);
        
        this.field_dropdown.setSize(50,50);
        this.field_dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected_value = (String) field_dropdown.getSelectedItem();
                operation_ctrl.setField(selected_value);
            }
        });
        
        JPanel operator_container = new JPanel();
        operator_container.setSize(100, 50);
        operator_container.setLayout(new BoxLayout(operator_container, BoxLayout.X_AXIS));
        
        JLabel operator_label = new JLabel("operador");
        operator_label.setSize(50,50);
        
        String[] options_operators = {"=","<","<=",">",">=","LIKE","BETWEEN"};
        JComboBox<String> operator_dropdown = new JComboBox<>(options_operators);
        operator_dropdown.setSize(50,50);
        operation_ctrl.setOperatorCondition("=");
        operator_dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected_value = (String) operator_dropdown.getSelectedItem();
                operation_ctrl.setOperatorCondition(selected_value);
            }
        });
        
        JPanel inputs_container = new JPanel();
        inputs_container.setSize(100, 150);
        inputs_container.setLayout(new BoxLayout(inputs_container, BoxLayout.Y_AXIS));
        
        JPanel input_1 = new JPanel();
        input_1.setSize(100, 50);
        input_1.setLayout(new BoxLayout(input_1, BoxLayout.X_AXIS));
        
        JLabel input_1_label = new JLabel("valor 1");
        input_1_label.setSize(50,50);
        
        JTextArea input_1_area = new JTextArea();
        input_1_area.setSize(50,50);
        
        JPanel input_2 = new JPanel();
        input_2.setSize(100, 50);
        input_2.setLayout(new BoxLayout(input_2, BoxLayout.X_AXIS));
        
        JLabel input_2_label = new JLabel("valor 2");
        input_2_label.setSize(50,50);
        
        JTextArea input_2_area = new JTextArea();
        input_2_area.setSize(50,50);
        
        JPanel buttons_container = new JPanel();
        buttons_container.setLayout(new BoxLayout(buttons_container, BoxLayout.X_AXIS));
        
        JButton add_button = new JButton("+");
        add_button.setSize(20,20);
        add_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String value_1 = input_1_area.getText();
                String value_2 = input_2_area.getText();
                
                if (!value_1.equals("")) {
                    String condition = "";
                    if (operation_ctrl.getOperatorCondition().equals("BETWEEN")) {
                        if (!value_2.equals("")) {
                            condition = operation_ctrl.getField() + " " + operation_ctrl.getOperatorCondition() + " " + formatValue(value_1, operation_ctrl.getField(), table_ctrl.getTableName()) + " AND " + formatValue(value_2, operation_ctrl.getField(), table_ctrl.getTableName());
                        } else {
                            modal_message.showMessage("Rellene el segundo valor", "Sentencia BETWEEN incorrecta", "succes");
                        }
                    } else if(operation_ctrl.getOperatorCondition().equals("LIKE")) {
                        condition = operation_ctrl.getField() + " " + operation_ctrl.getOperatorCondition() + " " + "'%" + value_1 + "%'";
                    }else {
                        condition = operation_ctrl.getField() + " " + operation_ctrl.getOperatorCondition() + " " + formatValue(value_1, operation_ctrl.getField(), table_ctrl.getTableName());
                    }
                    
                    operation_ctrl.getSentence().add(new SentenceModel(operation_ctrl.getOperator(), condition));
                    
                    fillSentenceTable();
                    setSentenceAreaText();
                } else {
                    modal_message.showMessage("Rellene el primer valor", "Sentencia incorrecta", "succes");
                }
            }
        });
        
        String[] dropdown_options = {"AND","OR"};
        JComboBox<String> dropdown = new JComboBox<>(dropdown_options);
        dropdown.setSize(20,20);
        operation_ctrl.setOperator("AND");
        dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected_value = (String) dropdown.getSelectedItem();
                operation_ctrl.setOperator(selected_value);
            }
        });
        
        // add the right_top
        operator_container.add(operator_label);
        operator_container.add(operator_dropdown);
        
        field_container.add(field_label);
        field_container.add(field_dropdown);
        
        options_container.add(field_container);
        options_container.add(operator_container);
        
        input_1.add(input_1_label);
        input_1.add(input_1_area);
        
        input_2.add(input_2_label);
        input_2.add(input_2_area);
        
        buttons_container.add(dropdown);
        buttons_container.add(add_button);
        
        inputs_container.add(input_1);
        inputs_container.add(input_2);
        inputs_container.add(buttons_container);
        
        right_top.add(options_container);
        right_top.add(inputs_container);
        
        right_container.add(right_top);
        
        
        // create the right_bot
        JPanel right_bot = new JPanel();
        right_bot.setSize(new Dimension(200, 150));
        right_bot.setLayout(new BoxLayout(right_bot, BoxLayout.X_AXIS));
        
        this.jtable_sentence = new JTableSentence(this.con);
        this.jtable_sentence.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    if (e.getClickCount() == 1) {
                        jtable_sentence.setRowSelected(jtable_sentence.getTable().getSelectedRow());
                    }
                }catch(Exception ex){
                    return;
                }
            }
        });
        
        JButton delete_button = new JButton("-");
        delete_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(operation_ctrl.getSentence().size() > 3){
                    operation_ctrl.getSentence().remove(jtable_sentence.getRowSelected() + 3);
                
                    fillSentenceTable();
                    setSentenceAreaText();
                }else{
                    modal_message.showMessage("No hay sentencias que eliminar", "Sentencias vacias", "error");
                }

            }
        });
        
        right_bot.add(new JScrollPane(this.jtable_sentence.getTable()), BorderLayout.CENTER);
        right_bot.add(delete_button);
        
        // add the right_bot
        right_container.add(right_bot);
    }
    
    public String formatValue(String input, String field, String table) {
        if (isNumeric(input,field,table)) {
            return input;
        } else {
            return "'" + input + "'";
        }
    }
    
    public boolean isNumeric(String str, String field, String table) {
        if(this.execute_ctrl.getTypeField(field, table))
            return true;
        
        return false;
    }
    
    public void fillFieldsTable(ArrayList<String> columns){
        
        this.jtable_fields.getModel().setRowCount(0);
        
        for(String column : columns){
            this.jtable_fields.addRow(column);
        }
        
        if(columns.size() != 0){
            this.operation_ctrl.setField(columns.get(0));
            this.jtable_result.setColumns(columns);
        }
    }

    public void fillFieldsSelectedTable(ArrayList<String> columns){
        this.jtable_fields_selected.getModel().setRowCount(0);
        
        for(String column : columns){
            this.jtable_fields_selected.addRow(column);
        }

        if(columns.size() != 0){
            this.operation_ctrl.setField(columns.get(0));
            this.jtable_result.setColumns(columns);
        }
    }
    
    public void createSecondContainer(JPanel second_container){
        this.sentence_area = new JTextArea();
        setSentenceAreaText();
        
        execute_button = new JButton("ejecutar");
        execute_button.setEnabled(false);
        execute_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(operation_ctrl.getSentence().size() > 3){
                    execute_ctrl = new Execute(con);
                    ArrayList<ArrayList<Object>> result = new ArrayList<>();
                    if(table_ctrl.getColumnsSelected().size() > 0){
                        result = execute_ctrl.executeQuery(sentence_area.getText(), table_ctrl.getColumnsSelected());
                    }else{
                        result = execute_ctrl.executeQuery(sentence_area.getText(), table_ctrl.getColumns());
                    }
                    
                    
                    
                    if(result.size() > 0){
                        fillTableResult(result);
                        export_button.setEnabled(true);
                    }
                    else
                        modal_message.showMessage("La tabla está vacía", "Tabla sin regstros", "succes");
                }else{
                    modal_message.showMessage("Ponga alguna condición en la sentencia", "sentencia sin condición", "succes");
                }
            }
        });
        
        second_container.add(sentence_area);
        second_container.add(execute_button);
    }
    
    public void fillSentenceTable(){
        this.jtable_sentence.getModel().setRowCount(0);
        
        for(int i = 3; i < this.operation_ctrl.getSentence().size(); i++){
            String condition = this.operation_ctrl.getSentence().get(i).getCondition();
            String operator = this.operation_ctrl.getSentence().get(i).getOperator();
            this.jtable_sentence.addRow(condition, operator);
        }
    }
    
    public void setSentenceAreaText(){
        String area_text = "";
        this.sentence_area.setText(area_text);
        
        for(SentenceModel sentence : this.operation_ctrl.getSentence()){
            if(this.operation_ctrl.getSentence().indexOf(sentence) < 2){
                if(execute_button != null){
                    execute_button.setEnabled(false);
                }
                if(export_button != null){
                    export_button.setEnabled(false);
                }
                area_text += sentence.getOperator() + " " + sentence.getCondition() + "\n";
            }else if(this.operation_ctrl.getSentence().indexOf(sentence) < 3){
                area_text += sentence.getOperator() + " " + sentence.getCondition() ;
            }
            else{
                execute_button.setEnabled(true);
                int sentence_before = this.operation_ctrl.getSentence().indexOf(sentence)- 1;
                if(this.operation_ctrl.getSentence().get(sentence_before).getOperator().equals("where")){
                    area_text += " " + sentence.getCondition();
                }else{
                    area_text += "\n" + this.operation_ctrl.getSentence().get(sentence_before).getOperator() + " " + sentence.getCondition();
                }
            }
        }
        this.sentence_area.setText(area_text);
    }
    
    public void setSentenceAreaVoid(){
        int num_conditions = operation_ctrl.getSentence().size();
                    
        for(int i = 0; i < num_conditions; i++){
            if(operation_ctrl.getSentence().size() > 3){
                operation_ctrl.getSentence().remove(3);
            }
        }
    }
    
    public void setSentenceSelect(){
        this.operation_ctrl.getSentence().get(0).setCondition("");
        
        if(this.table_ctrl.getColumnsSelected().size() == 0){
            this.operation_ctrl.getSentence().get(0).addCondition("*");
        }else{
            for(String field : this.table_ctrl.getColumnsSelected()){
                if(this.operation_ctrl.getSentence().get(0).getCondition().equals("")){
                    this.operation_ctrl.getSentence().get(0).addCondition(field);
                }else{
                    this.operation_ctrl.getSentence().get(0).addCondition(", " + field);
                }
            }
        }
        
        setSentenceAreaText();
    }
    
    public void setSentenceTable(String table){
        this.operation_ctrl.getSentence().get(1).setCondition(table);
        setSentenceAreaText();
    }
    
    public void setSentenceWhere(String condition){
        this.operation_ctrl.getSentence().get(2).setCondition(condition);
        setSentenceAreaText();
    }
    
    public void clearProgram(String selected_value){
        setSentenceAreaVoid();
        setSentenceTable(selected_value);
        setSentenceSelect();
        setSentenceWhere("");

        fillFieldsTable(table_ctrl.getColumns());
        fillFieldsSelectedTable(table_ctrl.getColumnsSelected());
        jtable_result.getModel().setRowCount(0);
        jtable_sentence.getModel().setRowCount(0);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for(String column : table_ctrl.getColumns()){
            model.addElement(column);
        }
        field_dropdown.setModel(model);
        
        execute_button.setEnabled(false);
        export_button.setEnabled(false);
    }
    
    public void createThirdContainer(JPanel third_container){
        this.jtable_result = new JTableResult(this.con);
        
        export_button = new JButton("exportar");
        export_button.setSize(30, 30);
        export_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(execute_ctrl.getData().size() > 0){
                    String path = JOptionPane.showInputDialog("Escriba los archivos donde desea exportar los datos separados por una coma y sin espacios 'fichero1,fichero2'");
                    
                    String path1 =path.split(",")[0];
                    String path2 =path.split(",")[1];
                    
                    Boolean overwrite1 = true;
                    Boolean overwrite2 = true;
                    int user_option = 0;
                    
                    if(export_ctrl.checkPath(path1)){
                        user_option = JOptionPane.showConfirmDialog(null, "El archivo " + path1 + " ya existe, ¿desea sobreescribirlo?");
                    }
                    
                    if(user_option == 0){
                        overwrite1 = false;
                    }
                    
                    if(export_ctrl.checkPath(path2)){
                        user_option = JOptionPane.showConfirmDialog(null, "El archivo " + path2 + " ya existe, ¿desea sobreescribirlo?");
                    }
                    
                    if(user_option == 0){
                        overwrite2 = false;
                    }
                    
                    if(export_ctrl.exportData(path1, path2, execute_ctrl.getData(), overwrite1, overwrite2)){
                        modal_message.showMessage("Datos exportados a " + path1 + " y " + path2, "Datos exportados", "succes");
                    }else{
                        modal_message.showMessage("No se ha podido exportar la información", "Error al exportar", "error");
                    };
                }
            }
        });
        
        third_container.add(new JScrollPane(this.jtable_result.getTable()), BorderLayout.CENTER);
        third_container.add(export_button);
    }
    
    public void fillTableResult(ArrayList<ArrayList<Object>> result){
        this.jtable_result.getModel().setRowCount(0);
        
        for(ArrayList<Object> register : result){
            ArrayList<Object> data_array = new ArrayList<>();
            for(Object data : register){
                data_array.add(data);
            }
            this.jtable_result.getModel().addRow(data_array.toArray(new Object[0]));
        }
    }
}
