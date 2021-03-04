/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftware1fxml;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
/**
 *
 * @author Admin
 */
public class Product {
    public static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleDoubleProperty price = new SimpleDoubleProperty(0.0);
    private SimpleIntegerProperty stock = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty min = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty max = new SimpleIntegerProperty(0);
    
    Product(int id, String name, double price, int stock, int min, int max){
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setStock(stock);
        this.setMin(min);
        this.setMax(max);
    }
    
    public int getId() {
        return id.get();
    }
    
    public void setId(int id){
        this.id.set(id);
    }
    
    public String getName() {
        return name.get();
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    
    public double getPrice() {
        return price.get();
    }
    
    public void setPrice(double price) {
        this.price.set(price);
    }
    
    public int getStock() {
        return stock.get();
    }
    
    public void setStock(int stock) {
        this.stock.set(stock);
    }
    
    public int getMin() {
        return min.get();
    }
    
    public void setMin(int min) {
        this.min.set(min);
    }
    
    public int getMax() {
        return max.get();
    }
    
    public void setMax(int max) {
        this.max.set(max);
    }
    
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    
    public void setAssociatedParts(ObservableList<Part> Parts){
        associatedParts.setAll(Parts);
    }
    
    public Boolean deleteAssociatedPart(Part selectedPart){
        return associatedParts.remove(selectedPart);
    }
    
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
    
}
