/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftware1fxml;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author Admin
 */
public abstract class Part {
    private SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleDoubleProperty price = new SimpleDoubleProperty(0.0);
    private SimpleIntegerProperty stock = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty min = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty max = new SimpleIntegerProperty(0);

//    Part(int id, String name, double price, int stock, int min, int max){//No Contructor Needed for abstract Class
//        this.setId(id);
//        this.setPrice(price);
//        this.setStock(stock);
//        this.setMin(min);
//        this.setMax(max);
//    }
        
    public int getId() {
        return id.get();
    }
    
    public void setId(int PartID){
        this.id.set(PartID);
    }
    
    public String getName() {
        return name.get();
    }
    
    public void setName(String PartName) {
        this.name.set(PartName);
    }
    
    public int getStock() {
        return stock.get();
    }
    
    public void setStock(int PartInventory) {
        this.stock.set(PartInventory);
    }
    
    public Double getPrice() {
        return price.get();
    }
    
    public void setPrice(Double PartCost) {
        this.price.set(PartCost);
    }
    
    public int getMax() {
        return max.get();
    }
    
    public void setMax(int PartMax) {
        this.max.set(PartMax);
    }
    
    public int getMin() {
        return min.get();
    }
    
    public void setMin(int PartMin) {
        this.min.set(PartMin);
    }

}
