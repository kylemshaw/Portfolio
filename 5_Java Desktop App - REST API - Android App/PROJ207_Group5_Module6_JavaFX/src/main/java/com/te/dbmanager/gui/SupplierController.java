//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Snow Tran
//     Description: Handles logic for suppliers-view.fxml view for managing
//                 the suppliers table
//----------------------------------------------------------------------------

package com.te.dbmanager.gui;

import com.te.dbmanager.data.ProductDB;
import com.te.dbmanager.data.ProductSupplierDB;
import com.te.dbmanager.data.SupplierDB;
import com.te.dbmanager.model.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class SupplierController {

    @FXML
    private TableView<Supplier> tvSuppliers;

    @FXML
    private TableColumn<Supplier, Integer> colSupplierId;

    @FXML
    private TableColumn<Supplier, String> colSupName;

    @FXML
    private Button btnAddSup;

    @FXML
    private Button btnEditSup;

    @FXML
    private Button btnDeleteSup;

    private static ObservableList<Supplier> supplierData = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException {
        assert tvSuppliers != null : "fx:id=\"tvSuppliers\" was not injected: check your FXML file 'main-view.fxml'.";
        assert colSupplierId != null : "fx:id=\"colSupplierId\" was not injected: check your FXML file 'main-view.fxml'.";
        assert colSupName != null : "fx:id=\"colSupName\" was not injected: check your FXML file 'main-view.fxml'.";
        assert btnAddSup != null : "fx:id=\"btnAddSup\" was not injected: check your FXML file 'main-view.fxml'.";
        assert btnEditSup != null : "fx:id=\"btnEditSup\" was not injected: check your FXML file 'main-view.fxml'.";
        assert btnDeleteSup != null : "fx:id=\"btnDeleteSup\" was not injected: check your FXML file 'main-view.fxml'.";

        // fill the tableview with suppliers from the db
        SupplierDB.getSuppliers(tvSuppliers, supplierData, colSupplierId, colSupName);

        // enabling the edit & delete buttons when a table row is selected
        tvSuppliers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnEditSup.setDisable(false);
                btnDeleteSup.setDisable(false);
            }
        });

        /*
        Open dialog to add new supplier & refresh the table view
        */
        btnAddSup.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    SupplierDetailController.isAdd = true;
                    openNewSupplierDialog();
                    refreshSupData();
                } catch (IOException
                        | SQLException
                        e) {
                    e.printStackTrace();
                }
            }
        });

        /*
        Open dialog to edit the selected supplier & refresh the table view
         */
        btnEditSup.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Supplier s = tvSuppliers.getSelectionModel().getSelectedItem();
                if (s == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "A supplier must be selected!", ButtonType.OK);
                    alert.show();
                } else {
                    try {
                        SupplierDetailController.isAdd = false;
                        openEditSupplierDialog(tvSuppliers.getSelectionModel().getSelectedIndex());
                        refreshSupData();
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        /*
        when supplier delete is selected, check row is selected and confirm before deleting & refresh table after deleting
         */
        btnDeleteSup.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Supplier s = tvSuppliers.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Do you want to delete Supplier ID: " + s.getSupplierId() + ", name: " + s.getSupName(),
                        ButtonType.YES, ButtonType.NO);
                ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
                if (ButtonType.YES.equals(result)) {
                    try {
                        ProductSupplierDB.deleteProductSupplier(s.getSupplierId());
                        SupplierDB.deleteSupplier(s.getSupplierId());
                        refreshSupData();
                    } catch (SQLException e) {
                        alert = new Alert(Alert.AlertType.ERROR, "Unable to delete this supplier because its products" +
                                " are linked to one or more packages.", ButtonType.OK);
                        alert.show();
                    }
                }
            }
        });
    }


    /*
    method used to refresh suppliers table-view after each add/edit/delete
    */
    private void refreshSupData() throws SQLException {
        btnEditSup.setDisable(true);
        btnDeleteSup.setDisable(true);
        tvSuppliers.getSelectionModel().clearSelection();
        tvSuppliers.getItems().clear();
        SupplierDB.getSuppliers(tvSuppliers, supplierData, colSupplierId, colSupName);
    }

    /*
    setup dialog for new supplier adding
     */
    private void openNewSupplierDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("supplier-detail-view.fxml"));
        Parent parent = fxmlLoader.load();
        SupplierDetailController supplierDetailController = fxmlLoader.getController();
        supplierDetailController.setMainObservableList(supplierData);
        supplierDetailController.tfTitle.appendText(" - Add Supplier");
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /*
    setup dialog for edit selected supplier
   */
    public void openEditSupplierDialog(int selectedIndex) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("supplier-detail-view.fxml"));
        Parent parent = fxmlLoader.load();
        SupplierDetailController supplierDetailController = fxmlLoader.getController();
        supplierDetailController.setMainObservableList(supplierData);
        supplierDetailController.setMainSelectedIndex(selectedIndex);
        supplierDetailController.tfTitle.appendText(" - Edit Supplier");
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
