/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftware1fxml;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Admin
 */
public class Outsourced extends Part{
    private SimpleStringProperty companyName = new SimpleStringProperty("");

    Outsourced (int id, String name, double price, int stock, int min, int max, String companyName){
        System.out.println("Outsource Constructor Started...");
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setCompanyName(companyName);
        System.out.println("Outsource Constructor Done!");
    }

    public void setCompanyName(String companyName){
        this.companyName.set(companyName);
    }

    public String getCompanyName(){
        return companyName.get();
    }
}
