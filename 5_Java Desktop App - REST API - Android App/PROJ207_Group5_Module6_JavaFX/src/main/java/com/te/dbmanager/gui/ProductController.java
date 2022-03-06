//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Snow Tran
//     Description: Handles logic for suppliers-view.fxml view for managing
//                 the products table
//----------------------------------------------------------------------------

package com.te.dbmanager.gui;

import com.te.dbmanager.data.ProductDB;
import com.te.dbmanager.model.Product;
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

public class ProductController {

    @FXML
    private TableView<Product> tvProducts;

    @FXML
    private TableColumn<Product, Integer> colProductId;

    @FXML
    private TableColumn<Product, String> colProdName;

    @FXML
    private Button btnAddProd;

    @FXML
    private Button btnEditProd;

    @FXML
    private Button btnDeleteProd;

    private static ObservableList<Product> productData = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException {
        assert tvProducts != null : "fx:id=\"tvProducts\" was not injected: check your FXML file 'main-view.fxml'.";
        assert colProductId != null : "fx:id=\"colProductId\" was not injected: check your FXML file 'main-view.fxml'.";
        assert colProdName != null : "fx:id=\"colProdName\" was not injected: check your FXML file 'main-view.fxml'.";
        assert btnAddProd != null : "fx:id=\"btnAddProd\" was not injected: check your FXML file 'main-view.fxml'.";
        assert btnEditProd != null : "fx:id=\"btnEditProd\" was not injected: check your FXML file 'main-view.fxml'.";
        assert btnDeleteProd != null : "fx:id=\"btnDeleteProd\" was not injected: check your FXML file 'main-view.fxml'.";

        // fill the tableview with products from the db
        ProductDB.getProducts(tvProducts, productData, colProductId, colProdName);

        // check whether the row selection before enabling the edit & delete buttons
        tvProducts.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (tvProducts.getSelectionModel().getSelectedItem() != null) {
                    btnEditProd.setDisable(false);
                    btnDeleteProd.setDisable(false);
                }
            }
        });

        /*
        Open dialog to add new product & refresh the table view
         */
        btnAddProd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    openNewProductDialog();
                    refreshProdData();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        /*
        Open dialog to edit the selected product & refresh the table view
         */
        btnEditProd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Product p = tvProducts.getSelectionModel().getSelectedItem();
                if (p == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "A product must be selected!", ButtonType.OK);
                    alert.show();
                } else {
                    try {
                        openEditProductDialog(tvProducts.getSelectionModel().getSelectedIndex());
                        refreshProdData();
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        /*
        when Product delete is selected, check row is selected and confirm before deleting & refresh table after deleting
         */
        btnDeleteProd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Product p = tvProducts.getSelectionModel().getSelectedItem();
                if (p == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "A product must be selected!", ButtonType.OK);
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Do you want to delete Product ID: " + p.getProductId() + ", name: " + p.getProdName(),
                            ButtonType.YES, ButtonType.NO);
                    ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
                    if (ButtonType.YES.equals(result)) {
                        ProductDB.deleteProduct(p.getProductId());
                        try {
                            refreshProdData();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }//init_end


    /*
    method used to refresh products table-view after each add/edit/delete
     */
    private void refreshProdData() throws SQLException {
        btnEditProd.setDisable(true);
        btnDeleteProd.setDisable(true);
        tvProducts.getSelectionModel().clearSelection();
        tvProducts.getItems().clear();
        ProductDB.getProducts(tvProducts, productData, colProductId, colProdName);
    }

    /*
    setup dialog for new product adding
     */
    public void openNewProductDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product-detail-view.fxml"));
        Parent parent = fxmlLoader.load();
        ProductDetailController productDetailController = fxmlLoader.getController();
        productDetailController.setMainObservableList(productData);
        productDetailController.tfTitle.appendText(" - Add Product");
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /*
    setup dialog for edit selected product
   */
    public void openEditProductDialog(int selectedIndex) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product-detail-view.fxml"));
        Parent parent = fxmlLoader.load();
        ProductDetailController productDetailController = fxmlLoader.getController();
        productDetailController.setMainObservableList(productData);
        productDetailController.tfTitle.appendText(" - Edit Product");
        productDetailController.setMainSelectedIndex(selectedIndex);
        productDetailController.isAdd = false;
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle("Edit Product");
        stage.showAndWait();
    }
}
