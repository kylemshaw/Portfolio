//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Kyle Shaw
//     Description: Handles logic for customer-detail-view.fxml view for managing
//                  customer add/edit dialog.
//----------------------------------------------------------------------------
package com.te.dbmanager.gui;

import com.te.dbmanager.data.AgentDB;
import com.te.dbmanager.data.CustomerDB;
import com.te.dbmanager.model.Agent;
import com.te.dbmanager.model.Customer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CustomerDetailController {
//Properties
    private Customer customer;
    private Boolean isExisting;
    private double  mousePressedX, mousePressedY;

    @FXML
    private HBox hbTitle;

    @FXML
    public TextField tfTitle;

    @FXML
    private Circle btnMinimize;

    @FXML
    private Circle btnClose;

    @FXML
    private TextField tfCustFirstName;

    @FXML
    private TextField tfCustLastName;

    @FXML
    private TextField tfCustAddress;

    @FXML
    private TextField tfCustCity;

    @FXML
    private ComboBox<String> cboCustProvince;

    @FXML
    private TextField tfCustPostal;

    @FXML
    private TextField tfCustCountry;

    @FXML
    private Button btnSave;

    @FXML
    private TextField tfCustHomePhone;

    @FXML
    private TextField tfCustBusPhone;

    @FXML
    private TextField tfCustEmail;

    @FXML
    private ComboBox<Agent> cboAgents;

    @FXML
    private Button btnCancel;

    //Getter/Setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Boolean getExisting() {
        return isExisting;
    }

    public void setExisting(Boolean existing) {
        isExisting = existing;

        //if isExisting then load existing values into form
        if(isExisting !=null && isExisting && customer != null ){
            tfCustFirstName.setText(customer.getCustFirstName());
            tfCustLastName.setText(customer.getCustLastName());
            tfCustAddress.setText(customer.getCustAddress());
            tfCustCity.setText(customer.getCustCity());
            cboCustProvince.setValue(customer.getCustProv());
            tfCustPostal.setText(customer.getCustPostal());
            tfCustCountry.setText(customer.getCustCountry());
            tfCustHomePhone.setText(customer.getCustHomePhone());
            tfCustBusPhone.setText(customer.getCustBusPhone());
            tfCustEmail.setText(customer.getCustEmail());
            cboAgents.setValue(customer.getAgent());
        }
    }

//Methods
    @FXML
    void initialize() {
        assert tfCustFirstName != null : "fx:id=\"tfCustFirstName\" was not injected: check your FXML file 'customer-detail-view.fxml'.";
        assert tfCustLastName != null : "fx:id=\"tfCustLastName\" was not injected: check your FXML file 'customer-detail-view.fxml'.";
        assert tfCustAddress != null : "fx:id=\"tfCustAddress\" was not injected: check your FXML file 'customer-detail-view.fxml'.";
        assert tfCustCity != null : "fx:id=\"tfCustCity\" was not injected: check your FXML file 'customer-detail-view.fxml'.";
        assert cboCustProvince != null : "fx:id=\"cboCustProvince\" was not injected: check your FXML file 'customer-detail-view.fxml'.";
        assert tfCustPostal != null : "fx:id=\"tfCustPostal\" was not injected: check your FXML file 'customer-detail-view.fxml'.";
        assert tfCustCountry != null : "fx:id=\"tfCustCountry\" was not injected: check your FXML file 'customer-detail-view.fxml'.";
        assert tfCustHomePhone != null : "fx:id=\"tfCustHomePhone\" was not injected: check your FXML file 'customer-detail-view.fxml'.";
        assert tfCustBusPhone != null : "fx:id=\"tfCustBusPhone\" was not injected: check your FXML file 'customer-detail-view.fxml'.";
        assert tfCustEmail != null : "fx:id=\"tfCustEmail\" was not injected: check your FXML file 'customer-detail-view.fxml'.";
        assert cboAgents != null : "fx:id=\"cboAgents\" was not injected: check your FXML file 'customer-detail-view.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'customer-detail-view.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'customer-detail-view.fxml'.";

        //Initialize combo boxes
        cboCustProvince.setItems(Customer.PROVINCES);
        try {
            cboAgents.setItems(AgentDB.getAllAgents());
        }
        catch (SQLException e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            a.show();
        }

        //Set Tool tips (also used as text field prompts for error messages)
        tfCustFirstName.setTooltip(new Tooltip("First Name"));
        tfCustLastName.setTooltip(new Tooltip("Last Name"));
        tfCustAddress.setTooltip(new Tooltip("Address"));
        tfCustCity.setTooltip(new Tooltip("City"));
        cboCustProvince.setTooltip(new Tooltip("Province"));
        tfCustPostal.setTooltip(new Tooltip("Postal code"));
        tfCustCountry.setTooltip(new Tooltip("Country"));
        tfCustHomePhone.setTooltip(new Tooltip("Home Phone"));
        tfCustBusPhone.setTooltip(new Tooltip("Business Phone"));
        tfCustEmail.setTooltip(new Tooltip("Email"));
        cboAgents.setTooltip(new Tooltip("Agent"));

        //close on cancel
        btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                closeCustomerDetail(mouseEvent);
            }
        });

        //Save, then close dialog
        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if (isExisting) {
                        if(validInput()){
                            CustomerDB.updateCustomer(customer);
                            closeCustomerDetail(mouseEvent);
                        }
                    }
                    else {
                        if(validInput()){
                            CustomerDB.insertCustomer(customer);
                            closeCustomerDetail(mouseEvent);
                        }
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    a.show();
                }
            }
        });

        //Click and drag window by title pane - get initial click coordinates
        hbTitle.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mousePressedX = mouseEvent.getX();
                mousePressedY = mouseEvent.getY();
            }
        });

        //Click and drag window by title pane - moving the window
        hbTitle.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double crrX = mouseEvent.getScreenX();
                double crrY = mouseEvent.getScreenY();
                Node source = (Node) mouseEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.setX(crrX - mousePressedX);
                stage.setY(crrY - mousePressedY);
            }
        });

        //close add/edit window
        btnClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node source = (Node) mouseEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        });

        //minimize add/edit window
        btnMinimize.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node source = (Node) mouseEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.setIconified(true);
            }
        });
    }//end_init

    private void closeCustomerDetail(MouseEvent mouseEvent) {
        //Close Customer Detail window
        Node source = (Node) mouseEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private Boolean validInput() {
        Boolean isValid = false;

        //Call validator to check all text fields on form
        if(Validator.IsPresent(tfCustFirstName) &&
               Validator.IsPresent(tfCustLastName) &&
               Validator.IsValidPhone(tfCustHomePhone) &&
               Validator.IsPresent(tfCustBusPhone) &&
               Validator.IsValidPhone(tfCustBusPhone) &&
               Validator.IsPresent(tfCustEmail) &&
               Validator.IsValidEmail(tfCustEmail) &&
               Validator.IsPresent(cboAgents) &&
               Validator.IsPresent(tfCustAddress) &&
               Validator.IsPresent(tfCustCity) &&
               Validator.IsPresent(cboCustProvince) &&
               Validator.IsPresent(tfCustPostal) &&
               Validator.IsValidPostal(tfCustPostal)
       )
       {
           //save text field data to customer object for submission to db
           customer.setCustFirstName(tfCustFirstName.getText());
           customer.setCustLastName(tfCustLastName.getText());
           customer.setCustAddress(tfCustAddress.getText());
           customer.setCustCity(tfCustCity.getText());
           customer.setCustProv(cboCustProvince.getValue());
           customer.setCustPostal(tfCustPostal.getText());
           customer.setCustCountry(tfCustCountry.getText());
           customer.setCustHomePhone(tfCustHomePhone.getText());
           customer.setCustBusPhone(tfCustBusPhone.getText());
           customer.setCustEmail(tfCustEmail.getText());
           customer.setAgentId(cboAgents.getSelectionModel().getSelectedItem().getAgentId());
           customer.setAgent(cboAgents.getSelectionModel().getSelectedItem());
           isValid = true;
        }
        return isValid;
    }//end validInput
    
}//end class
