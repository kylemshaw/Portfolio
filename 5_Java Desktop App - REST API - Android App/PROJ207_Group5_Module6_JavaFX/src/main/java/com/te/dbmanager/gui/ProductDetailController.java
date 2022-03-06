//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Snow Tran
//     Description: Handles logic for suppliers-view.fxml view for managing
//                 the products add/edit dialog
//----------------------------------------------------------------------------

package com.te.dbmanager.gui;

import com.te.dbmanager.data.ProductDB;
import com.te.dbmanager.model.Product;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProductDetailController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox hbTitle;

    @FXML
    public TextField tfTitle;

    @FXML
    private Circle btnMinimize;

    @FXML
    private Circle btnClose;
    @FXML
    private TextField tfProductId;

    @FXML
    private TextField tfProdName;

    @FXML
    private Button btnSaveProd;

    @FXML
    private Button btnCancelProd;

    private double  mousePressedX, mousePressedY;

    private ObservableList<Product> mainData;
    public boolean isAdd = true;
    private int selectedIndex;

    @FXML
    void initialize() {
        assert tfProductId != null : "fx:id=\"tfProductId\" was not injected: check your FXML file 'product-detail-view.fxml'.";
        assert tfProdName != null : "fx:id=\"tfProdName\" was not injected: check your FXML file 'product-detail-view.fxml'.";
        assert btnSaveProd != null : "fx:id=\"btnSaveProd\" was not injected: check your FXML file 'product-detail-view.fxml'.";
        assert btnCancelProd != null : "fx:id=\"btnCancelProd\" was not injected: check your FXML file 'product-detail-view.fxml'.";

        /*
        when save button is selected, check whether the name was entered. Add product to db if satisfied.
         */
        btnSaveProd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (tfProdName.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Must enter product name.", ButtonType.OK);
                    alert.show();
                } else {
                    if (isAdd) {
                        try {
                            ProductDB.addProduct(mouseEvent, tfProductId, tfProdName);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ProductDB.editProduct(mouseEvent, tfProductId, tfProdName);
                    }
                    Node source = (Node) mouseEvent.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
            }
        });

        // when cancel button is clicked, close the stage
        btnCancelProd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) btnCancelProd.getScene().getWindow();
                stage.close();
            }
        });

        // custom title bar
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

        // close button on custom title bar
        btnClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node source = (Node) mouseEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        });

        // minimize button on custom title bar
        btnMinimize.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node source = (Node) mouseEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.setIconified(true);
            }
        });   }

    public void setMainObservableList(ObservableList<Product> data) {
        this.mainData = data;
    }

    // receive the selected index from the ProductController and display the appropriate info
    public void setMainSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        Product p = mainData.get(selectedIndex);
        tfProductId.setText(p.getProductId() + "");
        tfProdName.setText(p.getProdName() + "");
    }
}
