/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author alvaro
 */
public class SentenceModel {
    
    private String operator;
    private String condition;
    
    public SentenceModel(String operator, String condition){
        this.operator = operator;
        this.condition = condition;
    }
    
    public String getCondition(){
        return this.condition;
    }
    
    public String getOperator(){
        return this.operator;
    }
    
    public void setCondition(String condition){
        this.condition = condition;
    }
    
    public void addCondition(String condition){
        this.condition += condition;
    }
    
    public void setOPerator(String operator){
        this.operator = operator;
    }
}
