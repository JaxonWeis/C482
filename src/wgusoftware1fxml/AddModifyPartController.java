/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wgusoftware1fxml;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class AddModifyPartController implements Initializable {

    @FXML
    private Label AddModifyLabel;
    @FXML
    private RadioButton InHouse;
    @FXML
    private ToggleGroup Source;
    @FXML
    private RadioButton Outsourced;
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
    private HBox MachineContainer;
    @FXML
    private TextField MachineIDBox;
    @FXML
    private HBox CompanyContainer;
    @FXML
    private TextField CompanyNameBox;
    @FXML
    private Button SaveButton;
    @FXML
    private Button CancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CompanyContainer.managedProperty().bind(CompanyContainer.visibleProperty());//makes container gone when visibility is false
        MachineContainer.managedProperty().bind(MachineContainer.visibleProperty());//makes container gone when visibility is false
        
        IDBox.setDisable(true);//Disabke ID box at all times
        
        AddModifyLabel.setText("Add Part");
        IDBox.setText("Auto Gen - Disable");
    }

    public void initData(Outsourced selectedPart) {//Populate Boxes if Part is Outsourced
        IDBox.setDisable(true);
        Outsourced.fire();
        
        AddModifyLabel.setText("Modify Part");
        IDBox.setText(Integer.toString(selectedPart.getId()));
        NameBox.setText(selectedPart.getName());
        PriceBox.setText(Double.toString(selectedPart.getPrice()));
        InvBox.setText(Integer.toString(selectedPart.getStock()));
        MinBox.setText(Integer.toString(selectedPart.getMin()));
        MaxBox.setText(Integer.toString(selectedPart.getMax()));
        CompanyNameBox.setText(selectedPart.getCompanyName());
    }
    
    public void initData(InHouse selectedPart) {// Populate Boxes If Part is InHouse
        IDBox.setDisable(true);
        InHouse.fire();
        
        AddModifyLabel.setText("Modify Part");
        IDBox.setText(Integer.toString(selectedPart.getId()));
        NameBox.setText(selectedPart.getName());
        PriceBox.setText(Double.toString(selectedPart.getPrice()));
        InvBox.setText(Integer.toString(selectedPart.getStock()));
        MinBox.setText(Integer.toString(selectedPart.getMin()));
        MaxBox.setText(Integer.toString(selectedPart.getMax()));
        MachineIDBox.setText(Integer.toString(selectedPart.getMachineId()));
    }

    @FXML
    private void InHousePressed(ActionEvent event) {//Hides the company Container Box and Shows the machine ID Box
        MachineContainer.setVisible(true);
        CompanyContainer.setVisible(false);
    }

    @FXML
    private void OutsourcedPressed(ActionEvent event) {//Hides the Machine ID Box and Shows the Company Box
        MachineContainer.setVisible(false);
        CompanyContainer.setVisible(true);
    }

    @FXML
    private void SaveButtonPressed(ActionEvent event) {// Save Button Method
        System.out.println("Save Button Pressed: " + AddModifyLabel.getText());
        Part TempPart;
        
        int id = Inventory.nextAvaliblePartId();
        String name = NameBox.getText();
        double price = Double.parseDouble(PriceBox.getText());
        int stock = Integer.parseInt(InvBox.getText());
        int min = Integer.parseInt(MinBox.getText());
        int max = Integer.parseInt(MaxBox.getText());
        
        Alert alert;
        if (min > max) {//Alert if min > Max
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("This Part's Min is Higher than Max!");
            alert.showAndWait();
            return;            
        }
        if (stock < min){// Alert if Stock < Min
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("This Part's Inventory is lower than the Min!");
            alert.showAndWait();
            return; 
        }
        if (stock > max){//Alert if Stock > Max
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("This Part's Inventory is Higher than the Max!");
            alert.showAndWait();
            return; 
        }
        
        
        //Use Radio Button group to make either an InHouse or Outsourced Part
        RadioButton selectedRadioButton = (RadioButton) Source.getSelectedToggle();
        if (selectedRadioButton.equals(InHouse)) {
            System.out.println("Adding New InHouse Part");
            int machineID = Integer.parseInt(MachineIDBox.getText());
            TempPart = new InHouse(id, name, price, stock, min, max, machineID);
        }
        else {
            System.out.println("Adding New Outsource Part");
            String compName = CompanyNameBox.getText();
            TempPart = new Outsourced(id, name, price, stock, min, max, compName);
        }
        
        
        //Use AddModifyLabel to find if modifying ot Adding 
        if (AddModifyLabel.getText().equals("Add Part")) {
            System.out.println("Adding New Part");
            Inventory.addPart(TempPart);
        }
        else {
            TempPart.setId(Integer.parseInt(IDBox.getText()));//Change id from next avalible to Modified Part's Id 
            Inventory.updatePart(Inventory.getPartIndex(TempPart.getId()), TempPart);//Find Modufied Part's Index and replace Part
        }
        
        Stage stage = (Stage) SaveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void CancelButtonPressed(ActionEvent event) {//check to see if user wants to cancel then close
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
