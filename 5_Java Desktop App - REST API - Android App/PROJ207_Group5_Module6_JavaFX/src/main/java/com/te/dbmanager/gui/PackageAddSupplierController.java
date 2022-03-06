//------------------------------------
// PROJ 207 Threaded Project #3
// Group: 5
// Class: OOSD May 21
// Author (s): Adolphus Cox
// Description: Controller class for the 'Add Service' dialog of the Packages section of the application
// (package-addsup-view.fxml)
//------------------------------------

package com.te.dbmanager.gui;

import com.te.dbmanager.data.ProductDB;
import com.te.dbmanager.data.ProductSupplierDB;
import com.te.dbmanager.data.SupplierDB;
import com.te.dbmanager.model.ProductSupplier;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class PackageAddSupplierController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private HBox hbTitle;

    @FXML
    private Circle btnMinimize;

    @FXML
    private Circle btnClose;

    @FXML // fx:id="cboSelectSupplier"
    private ComboBox<String> cboSelectSupplier; // Value injected by FXMLLoader

    @FXML // fx:id="cboSelectProduct"
    private ComboBox<String> cboSelectProduct; // Value injected by FXMLLoader

    @FXML // fx:id="lvProducts"
    private ListView<String> lvProducts; // Value injected by FXMLLoader

    @FXML // fx:id="btnAddProduct"
    private Button btnAddProduct; // Value injected by FXMLLoader

    @FXML // fx:id="btnRemoveProduct"
    private Button btnRemoveProduct; // Value injected by FXMLLoader

    @FXML // fx:id="btnSave"
    private Button btnSave; // Value injected by FXMLLoader

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    private double  mousePressedX, mousePressedY;

    public int packageId;
    ArrayList<ProductSupplier> productSuppliers = new ArrayList<>();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cboSelectSupplier != null : "fx:id=\"cboSelectSupplier\" was not injected: check your FXML file 'package-addsup-view.fxml'.";
        assert cboSelectProduct != null : "fx:id=\"cboSelectProduct\" was not injected: check your FXML file 'package-addsup-view.fxml'.";
        assert lvProducts != null : "fx:id=\"lvProducts\" was not injected: check your FXML file 'package-addsup-view.fxml'.";
        assert btnAddProduct != null : "fx:id=\"btnAddProduct\" was not injected: check your FXML file 'package-addsup-view.fxml'.";
        assert btnRemoveProduct != null : "fx:id=\"btnRemoveProduct\" was not injected: check your FXML file 'package-addsup-view.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'package-addsup-view.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'package-addsup-view.fxml'.";

        fillSuppliersComboBox();
        productSuppliers.clear();

        // Controller EVENT HANDLERS -----------------------------------------------------------------------------

        btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node source = (Node) mouseEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        });

        cboSelectSupplier.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                cboSelectProduct.setDisable(false);

                // resets products list if supplier is changed (maybe add a confirmation/warning msg box)
                lvProducts.getItems().clear();
                lvProducts.setDisable(true);

                fillProductsComboBox(cboSelectSupplier.getValue());
            }
        });

        cboSelectProduct.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                btnAddProduct.setDisable(false);
            }
        });

        btnAddProduct.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                // add combo box selection to listview
                lvProducts.getItems().add(cboSelectProduct.getValue());

                // get selected index and remove from combobox
                int selectedIndex = cboSelectProduct.getSelectionModel().getSelectedIndex();
                cboSelectProduct.getItems().remove(selectedIndex);

                // disable combobox if all items have been removed
                if (cboSelectProduct.getItems().size() < 1) {
                    cboSelectProduct.setDisable(true);
                }

                // if combobox selection has no value, disable add button
                if (cboSelectProduct.getValue() == null) {
                    btnAddProduct.setDisable(true);
                }

                lvProducts.setDisable(false);
            }
        });

        lvProducts.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnRemoveProduct.setDisable(false);
            }
        });

        btnRemoveProduct.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                int selectedIndex = lvProducts.getSelectionModel().getSelectedIndex();

                // adds removed product back to combobox selection
                cboSelectProduct.getItems().add(lvProducts.getItems().get(selectedIndex));
                sortComboBox(cboSelectProduct);

                // removes selected line from listview
                lvProducts.getItems().remove(selectedIndex);

                // if listview is empty, disable it
                if (lvProducts.getItems().size() < 1) {
                    lvProducts.setDisable(true);
                }

                // reset controls
                btnRemoveProduct.setDisable(true);
                btnAddProduct.setDisable(true);
                cboSelectProduct.setDisable(false);
            }
        });

        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ArrayList<String> prodList = new ArrayList<>();

                if (lvProducts.getItems().size() > 0) {
                    int supplierId = SupplierDB.getSupplierIdByName(cboSelectSupplier.getValue());

                    for (int i = 0; i < lvProducts.getItems().size(); i++) {
                        prodList.add(lvProducts.getItems().get(i));
                    }

                    for (String prodName : prodList) {
                        int productId = ProductDB.getProductIdByName(prodName);

                        // products suppliers set to be passed on to the add/edit package form
                        productSuppliers.add(ProductSupplierDB.getProductSupplierByProdSupId(productId, supplierId));
                    }

                    Node source = (Node) mouseEvent.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();

                } else {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setContentText("Please select a product first.");
                    a.show();
                }
            }
        });

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

        btnClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node source = (Node) mouseEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        });

        btnMinimize.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node source = (Node) mouseEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.setIconified(true);
            }
        });   }

    // Controller METHODS ----------------------------------------------------------------------------------

    private void sortComboBox(ComboBox<String> cbo) {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < cbo.getItems().size(); i++) {
            list.add(cbo.getItems().get(i));
        }

        Collections.sort(list);

        cbo.getItems().clear();
        cbo.getItems().addAll(list);
    }

    private void fillProductsComboBox(String supplierName) {
        cboSelectProduct.getItems().clear();

        int supplierId = SupplierDB.getSupplierIdByName(supplierName);

        ArrayList<ProductSupplier> prodSups =
                ProductSupplierDB.getProductSuppliersBySupplierId(supplierId);

        for (ProductSupplier ps : prodSups) {
            cboSelectProduct.getItems().add(ProductDB.getProductName(ps.getProductId()));
        }
    }

    private void fillSuppliersComboBox() {

        ArrayList<String> supNames = SupplierDB.getOrderedList();

        for (String supName : supNames) {
            cboSelectSupplier.getItems().add(supName);
        }

    }
}