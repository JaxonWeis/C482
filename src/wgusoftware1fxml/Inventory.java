/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftware1fxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Admin
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();//Make allParts list 
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();//Make AllProducts List
    
    
    public static void addPart(Part newPart){//Add new Part
        allParts.add(newPart);
    }
    
    public static void addProduct (Product newProduct) {//Add new Product
        allProducts.add(newProduct);
    }
    
    public static Part lookupPart (int partId) {//find Part with id 
        int i = 0;
        while (i < allParts.size()){
            if (allParts.get(i).getId() == partId){
                return allParts.get(i);
            }
            i++;
        }
        return null;
    }
    
    public static Product lookupProduct (int productId) {//find Product with id
        int i = 0;
        while (i < allProducts.size()){
            if (allProducts.get(i).getId() == productId){
                return allProducts.get(i);
            }
            i++;
        }
        return null;
    }
    
    public static ObservableList<Part> lookupPart (String name) {//find List of Parts with Name that includes string
        ObservableList<Part> lookup = FXCollections.observableArrayList();
        int i = 0;
        while (i < allParts.size()){
            if (allParts.get(i).getName().contains(name)){
                lookup.add(allParts.get(i));
            }
            i++;
        }
        return lookup;
    }
    
    public static ObservableList<Product> lookupProduct (String name) {//Find list of Products with Name that include String
        ObservableList<Product> lookup = FXCollections.observableArrayList();
        int i = 0;
        while (i < allProducts.size()){
            if (allProducts.get(i).getName().contains(name)){
                lookup.add(allProducts.get(i));
            }
            i++;
        }
        return lookup;
    }
    
    public static void updatePart (int index, Part selectedPart) {//Update Part at index with new part
        allParts.set(index, selectedPart);
    }
    
    public static void updateProduct (int index, Product selectedProduct) {//Update Product at index with new Product
        allProducts.set(index, selectedProduct);
    }
    
    public static Boolean deletePart (Part selectedPart) {//Delete part from list
        return allParts.remove(selectedPart);
    }
    
    public static Boolean deleteProduct (Product selectedProduct) {//Delete Producty from list
        return allProducts.remove(selectedProduct);
    }
    
    public static ObservableList<Part> getAllParts() {//Get all Parts in list
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts() {//get all Products in List
        return allProducts;
    }
    
    public static int nextAvaliblePartId() {//Find new Avail Id not being used by a Part
        int checkingId = 0;
        Boolean idAvalible = true;
        int i = 0;
        while(true) {
            while(i < allParts.size()){
                if(allParts.get(i).getId() == checkingId) {
                    idAvalible = false;
                    break;
                }
                i++;
            }
            
            
            if (idAvalible){
                break;
            }
            else {
                idAvalible = true;
                checkingId++;
            }
        }
        return checkingId;
    }
    
        public static int nextAvalibleProductId() {// Find new Avail ID not being used by a Product
        int checkingId = 0;
        Boolean idAvalible = true;
        int i = 0;
        while(true) {
            while(i < allProducts.size()){
                if(allProducts.get(i).getId() == checkingId) {
                    idAvalible = false;
                    break;
                }
                i++;
            }
            
            
            if (idAvalible){
                break;
            }
            else {
                idAvalible = true;
                checkingId++;
            }
        }
        return checkingId;
    }
    
    public static int getPartIndex(int id) {//Get Index of part in List with ID number
        int partIndex = 0;
        
        while (partIndex < Inventory.allParts.size()){
            if(Inventory.allParts.get(partIndex).getId() == id) {
                break;
            }
            partIndex++;
        }
        
        return partIndex;
    }
    
    public static int getProductIndex(int id) {//Get Index of Product in List with ID Number
        int productIndex = 0;
        
        while (productIndex < Inventory.allProducts.size()){
            if(Inventory.allProducts.get(productIndex).getId() == id) {
                return productIndex;
            }
            productIndex++;
        }
        
        return -1;
    }
}
