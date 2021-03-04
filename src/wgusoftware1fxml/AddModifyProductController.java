/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftware1fxml;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class AddModifyProductController implements Initializable {

    @FXML
    private TextField IDBox;
    @FXML
    private TextField NameBox;
    @FXML
    private TextField InvBox;
    @FXML
    private TextField PriceBox;
    @FXML
    private TextField MinBox;
    @FXML
    private TextField MaxBox;
    @FXML
    private Button SearchButton;
    @FXML
    private TextField SearchBox;
    @FXML
    private TableView<Part> AllPartsTable;
    @FXML
    private TableColumn<Part, Integer> AllPartsIds;
    @FXML
    private TableColumn<Part, String> AllPartsNames;
    @FXML
    private TableColumn<Part, Integer> AllPartsStock;
    @FXML
    private TableColumn<Part, Double> AllPartsCost;
    @FXML
    private Button AddButton;
    @FXML
    private TableView<Part> ProductsPartTable;
    @FXML
    private TableColumn<Part, Integer> ProductPartIds;
    @FXML
    private TableColumn<Part, String> ProductPartsNames;
    @FXML
    private TableColumn<Part, Integer> ProductPartsStock;
    @FXML
    private TableColumn<Part, Double> ProductsPartsPrice;
    @FXML
    private Button RemoveButton;
    @FXML
    private Button SaveButton;
    @FXML
    private Button CancelButton;
    @FXML
    private Label AddModifyLabel;
    
    private ObservableList<Part> addedParts = FXCollections.observableArrayList(); 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Setup table Colums
        AllPartsIds.setCellValueFactory(new PropertyValueFactory<Part,Integer>("id"));
        AllPartsNames.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
        AllPartsStock.setCellValueFactory(new PropertyValueFactory<Part,Integer>("stock"));
        AllPartsCost.setCellValueFactory(new PropertyValueFactory<Part,Double>("price"));
        
        ProductPartIds.setCellValueFactory(new PropertyValueFactory<Part,Integer>("id"));
        ProductPartsNames.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
        ProductPartsStock.setCellValueFactory(new PropertyValueFactory<Part,Integer>("stock"));
        ProductsPartsPrice.setCellValueFactory(new PropertyValueFactory<Part,Double>("price"));
        
        //use inventory to populate parts table
        AllPartsTable.setItems(Inventory.getAllParts());
        //use temp List addedParts to populate table
        ProductsPartTable.setItems(addedParts);
        
        IDBox.setDisable(true);
        IDBox.setText("Auto Gen - Disable");
    }  
    
    public void initData (Product selectedProduct) {//Fill in boexes if modifying product
        AddModifyLabel.setText("Modify Product");
        IDBox.setText(Integer.toString(selectedProduct.getId()));
        NameBox.setText(selectedProduct.getName());
        PriceBox.setText(Double.toString(selectedProduct.getPrice()));
        InvBox.setText(Integer.toString(selectedProduct.getStock()));
        MinBox.setText(Integer.toString(selectedProduct.getMin()));
        MaxBox.setText(Integer.toString(selectedProduct.getMax()));
        addedParts.setAll(selectedProduct.getAllAssociatedParts());
    }

    @FXML
    private void SearchButtonPressed(ActionEvent event) {// search button pressed
        String str = SearchBox.getText();
        
        if (str.equals("")) {// if search box empty set table to all parts
            AllPartsTable.setItems(Inventory.getAllParts());
            return;
        }
        
        ObservableList<Part> lookup = Inventory.lookupPart(str);//get a list of parts with name lookup
        
        int i;
        try {
            i = Integer.parseInt(str);//If search box is a number lookup part with that id number and add it to top of list
            lookup.add(0, Inventory.lookupPart(i));
        }
        catch (NumberFormatException c) {
            
        }
        AllPartsTable.setItems(lookup);// set all parts table to temp lookup list
    }

    @FXML
    private void AddButtonPressed(ActionEvent event) {// add part to temp addedparts list
        addedParts.add(AllPartsTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void RemoveButtonPressed(ActionEvent event) {// remove part from temp addedparts list
        addedParts.remove(ProductsPartTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void SaveButtonPressed(ActionEvent event) {//Save button pressed
        int id = Inventory.nextAvalibleProductId();
        String name = NameBox.getText();
        Double price = Double.parseDouble(PriceBox.getText());
        int stock = Integer.parseInt(InvBox.getText());
        int min = Integer.parseInt(MinBox.getText());
        int max = Integer.parseInt(MaxBox.getText());
        Product newProduct = new Product(id, name, price, stock, min, max);
        newProduct.setAssociatedParts(addedParts);//Make temp newProduct with next aval Id and from boxes
        
        Alert alert;
        if (addedParts.isEmpty() ){//Alert if addedParts is empty
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("A Product must have at least 1 Part!");

            alert.showAndWait();
            return;
        }
        double totalPrice = 0;
        int i = 0;
        while (i < addedParts.size()){// Caluculate total price of parts
            totalPrice += addedParts.get(i).getPrice();
            i++;
        }
        if (price < totalPrice) {// Alert if price less than total price of parts
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("This Product's price must higher than the parts total: " + totalPrice);

            alert.showAndWait();
            return;
        }
        if (min > max) {// Alert if min < Max
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("This Product's Min is Higher than Max!");
            alert.showAndWait();
            return;            
        }
        if (stock < min){//Alert if stock < min
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("This Product's Inventory is lower than the Min!");
            alert.showAndWait();
            return; 
        }
        if (stock > max){//Alert if Stock > Max
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("This Product's Inventory is Higher than the Max!");
            alert.showAndWait();
            return; 
        }        
        
        
        //Use AddModifyLabel to find if adding or Modify Products
        if(AddModifyLabel.getText().equals("Add Product")) {//if adding product add to inventory
            Inventory.addProduct(newProduct);
        }
        else {                                              //if modifying Product change id to previous modified product find index of previous product and update in Inventory
            newProduct.setId(Integer.parseInt(IDBox.getText()));
            Inventory.updateProduct(Inventory.getProductIndex(newProduct.getId()), newProduct);
        }
        
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void CancelButtonPressed(ActionEvent event) {//Ask user if they want to cancel and close window
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Cancel");
        alert.setContentText("Are you sure you want to Cancel? Changes will not be saved!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            Stage stage = (Stage) CancelButton.getScene().getWindow();
            stage.close();
        }
    }
}
