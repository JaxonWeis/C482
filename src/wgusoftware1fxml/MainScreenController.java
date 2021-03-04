/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftware1fxml;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


/**
 *
 * @author Admin
 */


public class MainScreenController implements Initializable {

    
    
    @FXML
    private Button ExitButton;
    @FXML
    private Button PartsSearchButton;
    @FXML
    private TextField PartsSearchTextBox;
    @FXML
    private TableView<Part> PartsTable;
    @FXML
    private TableColumn<Part, Integer> PartsIDColumn;
    @FXML
    private TableColumn<Part, String> PartsNameColumn;
    @FXML
    private TableColumn<Part, Integer> PartsInventoryColumn;
    @FXML
    private TableColumn<Part, Double> PartsCostColumn;
    @FXML
    private Button PartsAddButton;
    @FXML
    private Button PartsModifyButton;
    @FXML
    private Button PartsDeleteButton;
    @FXML
    private Button ProductsSearchButton;
    @FXML
    private TextField ProductsSearchTextBox;
    @FXML
    private Button ProductsAddButton;
    @FXML
    private Button ProductsModifyButton;
    @FXML
    private Button ProductsDeleteButton;
    @FXML
    private TableView<Product> ProductsTable;
    @FXML
    private TableColumn<Product, Integer> ProductsIDColumn;
    @FXML
    private TableColumn<Product, String> ProductsNameColumn;
    @FXML
    private TableColumn<Product, Integer> ProductsInventoryColumn;
    @FXML
    private TableColumn<Product, Double> ProductsCostColumn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Setup Table Columns 
        PartsIDColumn.setCellValueFactory(new PropertyValueFactory<Part,Integer>("id"));
        PartsNameColumn.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
        PartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part,Integer>("stock"));
        PartsCostColumn.setCellValueFactory(new PropertyValueFactory<Part,Double>("price"));
        
        ProductsIDColumn.setCellValueFactory(new PropertyValueFactory<Product,Integer>("id"));
        ProductsNameColumn.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
        ProductsInventoryColumn.setCellValueFactory(new PropertyValueFactory<Product,Integer>("stock"));
        ProductsCostColumn.setCellValueFactory(new PropertyValueFactory<Product,Double>("price"));
        
        PartsTable.setItems(Inventory.getAllParts());
        ProductsTable.setItems(Inventory.getAllProducts());
        
    } 
    
    @FXML
    private void ExitButtonAction(ActionEvent event) {// Exit button
        System.exit(0);
    }
    
    /////////////////////Parts Buttons/////////////////////
    
    @FXML
    private void PartsSearchButtonAction(ActionEvent event) {//Part search button pressed
        String str = PartsSearchTextBox.getText();
        
        if (str.equals("")) {//If Search box is empty set table to all parts
            PartsTable.setItems(Inventory.getAllParts());
            return;
        }
        
        ObservableList<Part> lookup = Inventory.lookupPart(str);//Find All parts with lookupPart method
        
        int i;
        try {
            i = Integer.parseInt(str);
            lookup.add(0, Inventory.lookupPart(i));//Find Part with exact ID and add to top of list
        }
        catch (NumberFormatException c) {
            
        }
        PartsTable.setItems(lookup);
        
    }
    
    @FXML
    private void PartsAddButtonAction(ActionEvent event) {// Add Button open Part add Modify Screen 
        openAddModifyScreen();
    }
    
    @FXML
    private void PartsModifyButtonAction(ActionEvent event) {// Modify Button open add modify screen
        Part selectedPart;
        
        //Find if selected part is InHouse or Outsourced
        if (PartsTable.getSelectionModel().getSelectedItem().getClass().toString().equals("class wgusoftware1fxml.InHouse")) {
            System.out.println("Modify InHouse Part");
            
            selectedPart = (InHouse) PartsTable.getSelectionModel().getSelectedItem();
            openAddModifyScreen((InHouse) selectedPart);
        }
        else {
            System.out.println("Modify Outsource Part");
            
            selectedPart = PartsTable.getSelectionModel().getSelectedItem();
            openAddModifyScreen((Outsourced) selectedPart);
        }
    }
    
    @FXML
    private void PartsDeleteButtonAction(ActionEvent event) {//Ask user if they want to delete part then deletes part
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Part Deletion");
        alert.setContentText("Are you sure you want to delete this Part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            Inventory.deletePart(PartsTable.getSelectionModel().getSelectedItem());
        }
    }
    
    /////////////////////Products Buttons////////////////////
    
    @FXML
    private void ProductsSearchButtonAction(ActionEvent event) {//Product search button
        String str = ProductsSearchTextBox.getText();
        
        if (str.equals("")) {//if search box is empty then set table to all Products
            ProductsTable.setItems(Inventory.getAllProducts());
            return;
        }
        
        
        ObservableList<Product> lookup = Inventory.lookupProduct(str);//Lookup Products using Inventory lookup
        
        int i;
        try {
            i = Integer.parseInt(str);
            if (Inventory.lookupProduct(i) != null)
                lookup.add(0, Inventory.lookupProduct(i));
        }
        catch (NumberFormatException c) {
            
        }
        ProductsTable.setItems(lookup);
    }
    
    @FXML
    private void ProductsAddButtonAction(ActionEvent event) {//add product button pressed open add product screen
        openProductScreen();
    }
    
    @FXML
    private void ProductsModifyButtonAction(ActionEvent event) {//Modify Product button pressed open modify product screen
        openProductScreen(ProductsTable.getSelectionModel().getSelectedItem());
        
    }
    
    @FXML
    private void ProductsDeleteButtonAction(ActionEvent event) {//ask user if they want to delete product then deletes
        System.out.println("Products Delete Button Pressed...");
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Part Deletion");
        alert.setContentText("Are you sure you want to delete this Product?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            Inventory.deleteProduct(ProductsTable.getSelectionModel().getSelectedItem());
        }
    }
    
    private void openAddModifyScreen(InHouse selectedPart) {//Open addModifyPart Screen with inHouse Part Selected
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModifyPart.fxml"));
            Parent root = loader.load();
            
            AddModifyPartController AddPage = loader.getController();
            AddPage.initData(selectedPart);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println("!!!!!!!!!!!!!EXCEPTION!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
    }
    
    private void openAddModifyScreen(Outsourced selectedPart) {// Open AddModify Part Screen with Outsoursed Part Selected
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModifyPart.fxml"));
            Parent root = loader.load();
            
            AddModifyPartController AddPage = loader.getController();
            AddPage.initData(selectedPart);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println("!!!!!!!!!!!!!EXCEPTION!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
    }
    
    private void openAddModifyScreen() {//OPen Add Part Screen
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModifyPart.fxml"));
            Parent root = loader.load();
            AddModifyPartController AddPage = loader.getController();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println("!!!!!!!!!!!!!EXCEPTION!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
    }
    
    private void openProductScreen() {//Open Add Product Screen
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModifyProduct.fxml"));
            Parent root = loader.load();
            AddModifyProductController ProductPage = loader.getController();
            
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println("!!!!!!!!!!!!!EXCEPTION!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
    }
    
        private void openProductScreen(Product selectedProduct) {//Open Modify Part Screen
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModifyProduct.fxml"));
            Parent root = loader.load();
            AddModifyProductController ProductPage = loader.getController();
            ProductPage.initData(selectedProduct);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println("!!!!!!!!!!!!!EXCEPTION!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
    }
}
