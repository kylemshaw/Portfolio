//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Snow Tran
//     Description: Handles logic for suppliers-view.fxml view for managing
//                 the suppliers and ProductsSuppliers add/edit dialog.
//----------------------------------------------------------------------------

package com.te.dbmanager.gui;

import com.te.dbmanager.data.ProductSupplierDB;
import com.te.dbmanager.data.SupplierDB;
import com.te.dbmanager.model.Product;
import com.te.dbmanager.model.Supplier;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.te.dbmanager.data.ProductSupplierDB.dataProd;

public class SupplierDetailController {

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
    private TextField tfSupplierId;

    @FXML
    private TextField tfSupName;

    @FXML
    private Button btnSaveSup;

    @FXML
    private Button btnCancelSup;

    @FXML
    private TableView<Product> tvProductsSupplied;

    @FXML
    private TableColumn<Product, CheckBox> colCheck;

    @FXML
    private TableColumn<Product, String> colProduct;

    private double mousePressedX, mousePressedY;

    private ObservableList<Supplier> mainData;
    private ObservableList<Product> products;
    public static boolean isAdd = true;
    private int selectedIndex;
    private int totalProducts;

    @FXML
    void initialize() throws SQLException {
        assert tfSupplierId != null : "fx:id=\"tfSupplierId\" was not injected: check your FXML file 'supplier-detail-view.fxml'.";
        assert tfSupName != null : "fx:id=\"tfSupName\" was not injected: check your FXML file 'supplier-detail-view.fxml'.";
        assert btnSaveSup != null : "fx:id=\"btnSaveSup\" was not injected: check your FXML file 'supplier-detail-view.fxml'.";
        assert btnCancelSup != null : "fx:id=\"btnCancelSup\" was not injected: check your FXML file 'supplier-detail-view.fxml'.";
        assert tvProductsSupplied != null : "fx:id=\"tvProductsSupplied\" was not injected: check your FXML file 'supplier-detail-view.fxml'.";
        assert colCheck != null : "fx:id=\"colCheck\" was not injected: check your FXML file 'supplier-detail-view.fxml'.";
        assert colProduct != null : "fx:id=\"colProduct\" was not injected: check your FXML file 'supplier-detail-view.fxml'.";

        //setup data in the tableview with products and checkboxes for adding/modifying to the current/new supplier
        setupProductListAndCheckboxes();

        /*
        when save button is selected, check whether the name was entered and add/modify supplier/productsupplier
         */
        btnSaveSup.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                boolean isValid = true;
                if (tfSupName.getText().trim() == "") {
                    isValid = false;
                }
                if (isValid) {
                    if (isAdd) {
                        try {
                            int supplierId = SupplierDB.addSupplier(tfSupName);
                            addProductsSuppliers(supplierId);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            int supplierId = SupplierDB.editSupplier(tfSupplierId, tfSupName);
                            editProductSupplier(supplierId);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    Node source = (Node) mouseEvent.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Supplier name was not provided.");
                    alert.show();
                }
            }
        });


        // when cancel button is clicked, close the stage
        btnCancelSup.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) btnCancelSup.getScene().getWindow();
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
        });
    }


    // edit ProductSupplier byt going through the checkboxes and check the existence of productSupplier combinations
    private void editProductSupplier(int supplierId) throws SQLException {
        StringBuilder errorMessage = new StringBuilder();
        Product p;
        int prodId;
        for (int i = 0; i < totalProducts; i++) {
            p = dataProd.get(i);
            prodId = p.getProductId();
            if (colCheck.getCellData(i).isSelected()) {
                if (!ProductSupplierDB.checkProductSupplier(prodId, supplierId)) {
                    ProductSupplierDB.addProductSupplier(prodId, supplierId);
                }
            } else if (!colCheck.getCellData(i).isSelected()) {
                if (ProductSupplierDB.checkPackageProductSupplier(prodId, supplierId)) {
                    errorMessage.append("\n").append(p.getProdName());
                } else if (ProductSupplierDB.checkProductSupplier(prodId, supplierId)) {
                    ProductSupplierDB.deleteProductSupplier(prodId, supplierId);
                }
            }
        }
        if (!errorMessage.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot remove the following products from this supplier: "
                    + errorMessage, ButtonType.OK);
            alert.show();
        }
    }

    //used in the initialize(), to setup the product list and checkboxes
    private void setupProductListAndCheckboxes() throws SQLException {
        dataProd.clear();
        ProductSupplierDB.getProductsAvailable(tvProductsSupplied, colProduct);
        TableColumn<Product, CheckBox> colCheck = (TableColumn<Product, CheckBox>) tvProductsSupplied.getColumns().get(0);
        colCheck.setCellValueFactory(new ProductIsSuppliedValueFactory());
        colCheck.setStyle("-fx-alignment: CENTER;");   //center checkboxes, move to style.css in the future
    }

    //to add new products_suppliers by comparing the position of product in the table view (using productRowCount) and
    //locating that product's id using the dataProd observablelist, both contains all products in the database
    private void addProductsSuppliers(int supplierId) throws SQLException {
        int productRowCount = ProductSupplierDB.getProductsAvailable(tvProductsSupplied, colProduct);
        Product p;
        int prodId;
        int supId = supplierId;
        for (int i = 0; i < productRowCount; i++) {
            if (colCheck.getCellData(i).isSelected()) {
                p = dataProd.get(i);
                prodId = p.getProductId();
                ProductSupplierDB.addProductSupplier(prodId, supId);
            }
        }
    }

    //pass the supplierData observableList to this mainData observable list
    public void setMainObservableList(ObservableList<Supplier> data) {
        this.mainData = data;
    }

    public void setMainSelectedIndex(int selectedIndex) {
        isAdd = false;
        this.selectedIndex = selectedIndex;
        Supplier s = mainData.get(selectedIndex);
        tfSupplierId.setText(s.getSupplierId() + "");
        tfSupName.setText(s.getSupName() + "");
        dataProd.clear(); //clear the observable list before each use to avoid stacking, because it gets filled by ProductSupplierDB.getProductsAvailable()
        ArrayList<Integer> productIds = ProductSupplierDB.getAllProductsBySupplierId(Integer.parseInt(tfSupplierId.getText()));
        totalProducts = ProductSupplierDB.getProductsAvailable(tvProductsSupplied, colProduct);
        if (productIds != null) {
            Product p;
            int prodId;
            for (int i = 0; i < totalProducts; i++) {
                p = dataProd.get(i);
                prodId = p.getProductId();
                for (int j = 0; j < productIds.size(); j++) {
                    if (prodId == productIds.get(j)) {
                        colCheck.getCellData(i).setSelected(true);
                    }
                }
            }
        }
    }

    // a class to create checkboxes for product list
    private class ProductIsSuppliedValueFactory implements Callback<TableColumn.CellDataFeatures<Product, CheckBox>, ObservableValue<CheckBox>> {
        @Override
        public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<Product, CheckBox> param) {
            Product product = param.getValue();
            CheckBox checkBox = new CheckBox();
            checkBox.selectedProperty().setValue(product.isSupplied());
            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
                product.setSupplied(new_val);
            });
            return new SimpleObjectProperty<>(checkBox);
        }
    }
}
