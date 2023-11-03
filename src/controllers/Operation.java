/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.util.ArrayList;
import models.SentenceModel;

/**
 *
 * @author alvaro
 */
public class Operation {
    private ArrayList<SentenceModel> sentence = new ArrayList<>();
    private String operator_condition;
    private String operator;
    private String field;
    
    public Operation(){
        this.sentence.add(new SentenceModel("select",""));
        this.sentence.add(new SentenceModel("from",""));
        this.sentence.add(new SentenceModel("where",""));
    }
    
    public ArrayList<SentenceModel> getSentence(){
        return this.sentence;
    }
    
    public String getOperatorCondition(){
        return this.operator_condition;
    }
    
    public void setOperatorCondition(String operator){
        this.operator_condition = operator;
    }
    
    public String getOperator(){
        return this.operator;
    }
    
    public void setOperator(String operator){
        this.operator = operator;
    }
    
    public String getField(){
        return this.field;
    }
    
    public void setField(String field){
        this.field = field;
    }
}
