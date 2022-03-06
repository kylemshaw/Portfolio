//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Kyle Shaw
//     Description: Handles logic for customers-view.fxml view for managing
//                  the customers table.
//----------------------------------------------------------------------------

package com.te.dbmanager.gui;

import com.te.dbmanager.data.CustomerDB;
import com.te.dbmanager.model.Customer;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerController {

    @FXML
    private VBox vbCustomers;

    @FXML
    private TableView<Customer> tvCustomers;

    @FXML
    private TableColumn<Customer, Integer> colCustomerId;

    @FXML
    private TableColumn<Customer, String> colCustFirstName;

    @FXML
    private TableColumn<Customer, String> colCustLastName;

    @FXML
    private TableColumn<Customer, String> colCustCity;

    @FXML
    private TableColumn<Customer, String> colCustProv;

    @FXML
    private TableColumn<Customer, Integer> colCustAgent;

    @FXML
    private Button btnAddCustomer;

    @FXML
    private Button btnEditCustomer;

    @FXML
    private Button btnDeleteCustomer;

    @FXML
    void initialize() {
        assert vbCustomers != null : "fx:id=\"vbCustomers\" was not injected: check your FXML file 'customers-view.fxml'.";
        assert tvCustomers != null : "fx:id=\"tvCustomers\" was not injected: check your FXML file 'customers-view.fxml'.";
        assert colCustomerId != null : "fx:id=\"colCustomerId\" was not injected: check your FXML file 'customers-view.fxml'.";
        assert colCustFirstName != null : "fx:id=\"colCustFirstName\" was not injected: check your FXML file 'customers-view.fxml'.";
        assert colCustLastName != null : "fx:id=\"colCustLastName\" was not injected: check your FXML file 'customers-view.fxml'.";
        assert colCustCity != null : "fx:id=\"colCustCity\" was not injected: check your FXML file 'customers-view.fxml'.";
        assert colCustProv != null : "fx:id=\"colCustProv\" was not injected: check your FXML file 'customers-view.fxml'.";
        assert colCustAgent != null : "fx:id=\"colCustAgentId\" was not injected: check your FXML file 'customers-view.fxml'.";
        assert btnAddCustomer != null : "fx:id=\"btnAddCustomer\" was not injected: check your FXML file 'customers-view.fxml'.";
        assert btnEditCustomer != null : "fx:id=\"btnEditCustomer\" was not injected: check your FXML file 'customers-view.fxml'.";
        assert btnDeleteCustomer != null : "fx:id=\"btnDeleteCustomer\" was not injected: check your FXML file 'customers-view.fxml'.";

        //Load customer records from Customers table
        try {
            loadCustomerTable();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            a.show();
        }


        tvCustomers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //don't enable edit/delete if the header row is clicked or no customer is selected
                if(tvCustomers.getSelectionModel().getSelectedIndex() != -1){
                    btnEditCustomer.setDisable(false);
                    btnDeleteCustomer.setDisable(false);
                }
            }
        });

        btnAddCustomer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    openNewDialog(tvCustomers.getSelectionModel().getSelectedIndex(), "new");
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    a.show();
                }
            }
        });

        btnEditCustomer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    openNewDialog(tvCustomers.getSelectionModel().getSelectedIndex(), "existing");
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    a.show();
                }
            }
        });

        btnDeleteCustomer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Customer selectedCustomer = tvCustomers.getSelectionModel().getSelectedItem();

                Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to delete this customer?", ButtonType.YES, ButtonType.CANCEL);
                alert.setTitle("Confirm Delete");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.YES) {
                    //delete from database
                    try {
                        CustomerDB.deleteCustomer(selectedCustomer.getCustomerId());
                        loadCustomerTable();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
                        a.show();
                    }
                }
            }
        });

    }//end_of_initialize


    private void openNewDialog(int selectedIndex, String mode) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("customer-detail-view.fxml"));
        Parent parent = fxmlLoader.load();

        if (mode.equals("new")) { //open blank dialog with Add config
            CustomerDetailController customerDetail = fxmlLoader.getController();
            customerDetail.setCustomer(new Customer());
            customerDetail.setExisting(false);
            customerDetail.tfTitle.appendText(" - Add Customer");
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); //MODAL - customer-view is locked
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.showAndWait();

            //tvCustomers.getItems().clear();
            loadCustomerTable();
        }
        else if (mode.equals("existing")) {//open dialog with Edit config and fill with existing values
            ObservableList<Customer> customers = CustomerDB.getAllCustomers();

            if (selectedIndex >= 0 && selectedIndex < customers.size()) { //make sure a customer has been selected
                CustomerDetailController customerDetail = fxmlLoader.<CustomerDetailController>getController();
                customerDetail.setCustomer(customers.get(selectedIndex));
                customerDetail.setExisting(true);
                customerDetail.tfTitle.appendText(" - Edit Customer");

                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL); //MODAL - customer-view is locked
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.getScene().getRoot().setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.rgb(4,16,81),20, 0.33, 0,0));
                stage.showAndWait();

                //on return from detail refresh the table
                loadCustomerTable();
            }
        }
    }


    private void loadCustomerTable() throws SQLException {
        //disable edit/delete by default (select event will enable)
        btnEditCustomer.setDisable(true);
        btnDeleteCustomer.setDisable(true);

        //setup the table headers
        colCustomerId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        colCustFirstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("custFirstName"));
        colCustLastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("custLastName"));
        colCustCity.setCellValueFactory(new PropertyValueFactory<Customer, String>("custCity"));
        colCustProv.setCellValueFactory(new PropertyValueFactory<Customer, String>("custProv"));
        colCustAgent.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("agent"));

        //load data from the database
        //tvCustomers.getItems().clear();
        tvCustomers.setItems(CustomerDB.getAllCustomers());
    }

}//class
