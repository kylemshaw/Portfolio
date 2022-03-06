// PROJ 207 Threaded Project #3
// Group: 5
// Class: OOSD May 21
// Author (s): Adolphus Cox
// Description: Controller class for Add/Edit Packages dialog (package-detail-view.fxml)
//
//------------------------------------

package com.te.dbmanager.gui;

import com.te.dbmanager.data.PackageDB;
import com.te.dbmanager.data.PackageProductSupplierDB;
import com.te.dbmanager.model.Package;

import com.te.dbmanager.model.PackageProductSupplier;
import com.te.dbmanager.model.ProductSupplier;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class PackageDetailController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPackageId"
    private TextField txtPackageId; // Value injected by FXMLLoader

    @FXML // fx:id="txtPkgAgencyCommission"
    private TextField txtPkgAgencyCommission; // Value injected by FXMLLoader

    @FXML // fx:id="txtPkgName"
    private TextField txtPkgName; // Value injected by FXMLLoader

    @FXML // fx:id="dpPkgStartDate"
    private DatePicker dpPkgStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="dpPkgEndDate"
    private DatePicker dpPkgEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtPkgDesc"
    private TextField txtPkgDesc; // Value injected by FXMLLoader

    @FXML // fx:id="txtPkgBasePrice"
    private TextField txtPkgBasePrice; // Value injected by FXMLLoader

    @FXML // fx:id="lblDialogTitle"
    public Label lblDialogTitle; // Value injected by FXMLLoader

    @FXML // fx:id="btnSave"
    private Button btnSave; // Value injected by FXMLLoader

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    @FXML // fx:id="lblError"
    private Label lblError; // Value injected by FXMLLoader

    @FXML // fx:id="lvSuppliers"
    private ListView lvSuppliers;

    @FXML // fx:id="btnAddSupplier"
    private Button btnAddSupplier;

    @FXML // fx:id="btnRemoveSupplier"
    private Button btnRemoveSupplier;

    @FXML // fx:id="apDialog"
    private AnchorPane apDialog;

    @FXML
    private HBox hbTitle;

    @FXML
    public TextField tfTitle;

    @FXML
    private Circle btnMinimize;

    @FXML
    private Circle btnClose;

    private double  mousePressedX, mousePressedY;

    public boolean isAdd = false;
    private boolean isValid;

    private int packageId = 0;
    private int selectedProductSupplierId;

    private final ArrayList<ProductSupplier> prodSup = new ArrayList<>();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPackageId != null : "fx:id=\"txtPackageId\" was not injected: check your FXML file 'package-detail-view.fxml'.";
        assert txtPkgAgencyCommission != null : "fx:id=\"txtPkgAgencyCommission\" was not injected: check your FXML file 'package-detail-view.fxml'.";
        assert txtPkgName != null : "fx:id=\"txtPkgName\" was not injected: check your FXML file 'package-detail-view.fxml'.";
        assert dpPkgStartDate != null : "fx:id=\"txtPkgStartDate\" was not injected: check your FXML file 'package-detail-view.fxml'.";
        assert dpPkgEndDate != null : "fx:id=\"txtPkgEndDate\" was not injected: check your FXML file 'package-detail-view.fxml'.";
        assert txtPkgDesc != null : "fx:id=\"txtPkgDesc\" was not injected: check your FXML file 'package-detail-view.fxml'.";
        assert txtPkgBasePrice != null : "fx:id=\"txtPkgBasePrice\" was not injected: check your FXML file 'package-detail-view.fxml'.";
        assert lblDialogTitle != null : "fx:id=\"lblDialogTitle\" was not injected: check your FXML file 'package-detail-view.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'package-detail-view.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'package-detail-view.fxml'.";


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // This run method forces the below code to execute, since it won't run on its own
                // in the parent initialize() method

                txtPkgName.requestFocus();

                if (packageId > 0) {
                    displayServicesList(packageId);
                }

                if (isAdd) {
                    dpPkgStartDate.setValue(LocalDate.now());
                    dpPkgEndDate.setValue(LocalDate.now());
                }
            }
        });

        // Set Event Handlers ------------------------------------------------------------------------------

        btnAddSupplier.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("package-addsup-view.fxml"));
                try {
                    Parent parent = fxmlLoader.load();
                    PackageAddSupplierController dialogController = fxmlLoader.<PackageAddSupplierController>getController();

                    Scene scene = new Scene(parent);

                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(scene);
                    stage.showAndWait();

                    // suppliers(services) list will be displayed using this ArrayList
                    if (dialogController.productSuppliers.size() > 0) {
                        for (ProductSupplier ps : dialogController.productSuppliers) {
                            boolean duplicateId = false;

                            for (ProductSupplier _ps : prodSup) {
                                if (_ps.getProductSupplierId() == ps.getProductSupplierId()) {
                                    duplicateId = true;

                                    DisplayAlert.showWarning("'" + _ps.getProdName() + "' from supplier '"
                                            + _ps.getSupName() + "' is a duplicate service and will not be added.");
                                }

                                if (Objects.equals(_ps.getProdName(), ps.getProdName()) && !duplicateId) {
                                    Alert alert = DisplayAlert.showConfirmation(
                                            "The product '" + _ps.getProdName() + "' is already listed. Do you still want to add it?");

                                    if (alert.getResult() == ButtonType.NO) {
                                        duplicateId = true;
                                    }
                                }
                            }

                            if (!duplicateId) {
                                prodSup.add(ps);
                            }
                        }
                    }
                    updateServicesList();

                } catch (IOException e) {
                    e.printStackTrace();
                    DisplayAlert.showWarning("Error loading application window.");
                }
            }
        });

        btnRemoveSupplier.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = DisplayAlert.showConfirmation(
                        "You sure you want to delete '" + lvSuppliers.getSelectionModel().getSelectedItem() + "' ?");

                if (alert.getResult() == ButtonType.YES) {
                    prodSup.removeIf(ps -> ps.getProductSupplierId() == selectedProductSupplierId);
                    updateServicesList();
                }

                btnRemoveSupplier.setDisable(true);
            }
        });

        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                savePackage(mouseEvent);
            }
        });

        btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                closeDialog(mouseEvent);
            }
        });

        lvSuppliers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Retrieving string of selected item (the value)
                String serviceString = lvSuppliers.getSelectionModel().getSelectedItem().toString();

                // Split the above string by space and add to array
                String[] arr = serviceString.split(" ");

                // Select the first index (the identifying productSupplierId) and parse to integer
                // This value is captured so that the selected item can be removed
                selectedProductSupplierId = Integer.parseInt(arr[0]);

                btnRemoveSupplier.setDisable(false);
            }
        });

        // KeyPress event handler
        apDialog.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    savePackage(event);
                }

                if (event.getCode() == KeyCode.ESCAPE) {
                    closeDialog(event);
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
        });  }

    // Controller Methods ---------------------------------------------------------------------------

    private void savePackage(Event event) {
        isValid = true;

        validateTextField(txtPkgName);
        validateTextField(txtPkgBasePrice);
        validateTextField(txtPkgAgencyCommission);
        validateDateRange(dpPkgStartDate, dpPkgEndDate);

        if (isValid) {
            if (isAdd) {
                // Add new package
                PackageDB.addPackage(txtPkgName, dpPkgStartDate, dpPkgEndDate,
                        txtPkgDesc, txtPkgBasePrice, txtPkgAgencyCommission);

                // New package must be added first to generate a packageId
                // Last id (the largest id number) is retrieved from the database
                // This is used to create a package product supplier entry for the current package
                int lastId = PackageDB.getLastId();
                AddProductSuppliers(lastId);
            } else {
                // Modify package with current packageId
                PackageDB.updatePackage(txtPkgName, dpPkgStartDate, dpPkgEndDate,
                        txtPkgDesc, txtPkgBasePrice, txtPkgAgencyCommission, packageId);

                UpdateProductSuppliers(packageId);
            }
            closeDialog(event);
        }
    }

    private void UpdateProductSuppliers(int packageId) {
        ArrayList<PackageProductSupplier> pkgProdSuppliersToRemove =
                PackageProductSupplierDB.getPackageProductSuppliers(packageId);

        ArrayList<Integer> prodSupIdsToRemove = new ArrayList<>();

        // if any product supplier id of prodSup list is present in any of the package product suppliers
        // currently in the database, remove that package product supplier from the list

        for (PackageProductSupplier pps : pkgProdSuppliersToRemove) {
            for (ProductSupplier ps: prodSup) {
                if (ps.getProductSupplierId() == pps.getProductSupplierId()) {
                    prodSupIdsToRemove.add(pps.getProductSupplierId());
                }
            }
        }

        // remove entries from both lists that contain the same product supplier id
        for (Integer prodSupId : prodSupIdsToRemove) {
            pkgProdSuppliersToRemove.removeIf(pps -> (pps.getProductSupplierId() == prodSupId));
            prodSup.removeIf(ps -> (ps.getProductSupplierId() == prodSupId));
        }

        // if pkgProdSuppliersToRemove list is greater than 0, the remaining entries must be deleted from database
        if (pkgProdSuppliersToRemove.size() > 0) {
            for (PackageProductSupplier pps : pkgProdSuppliersToRemove) {
                PackageProductSupplierDB.delete(packageId, pps.getProductSupplierId());
            }
        }

        // if prodSup list is greater than 0, the remaining entries must be added to database
        AddProductSuppliers(packageId);
    }

    private void displayServicesList(int packageId) {

        // Initial clear of list view
        lvSuppliers.getItems().clear();

        // Get list of all package product supplier entries matching current package id
        ArrayList<PackageProductSupplier> pkgProdSup = PackageProductSupplierDB.getPackageProductSuppliers(packageId);

        // Loop through each package product supplier and retrieve its product supplier object
        for (PackageProductSupplier pps : pkgProdSup) {
            prodSup.add(pps.getProductSupplier());
        }

        // Loop through each product supplier and create string to add to lvSuppliers
        for (ProductSupplier ps : prodSup) {
            String prodName = ps.getProdName();
            String supName = ps.getSupName();

            // ProductSupplierId is retrieved as an identifier for the delete functionality of this dialog
            lvSuppliers.getItems().add(ps.getProductSupplierId() + " " + prodName + " (" + supName + ")");
        }

        if (lvSuppliers.getItems().size() > 0) {
            lvSuppliers.setDisable(false);
        }
    }

    private void AddProductSuppliers(int packageId) {
        if (prodSup.size() > 0) {
            for (ProductSupplier ps : prodSup) {
                PackageProductSupplierDB.add(packageId, ps.getProductSupplierId());
            }
        }
    }

    private void updateServicesList() {
        // similar to displayServicesList, but used to make subsequent updates to lvSuppliers

        lvSuppliers.getItems().clear();
        lvSuppliers.setDisable(false);

        for (ProductSupplier ps : prodSup) {

            String prodName = ps.getProdName();
            String supName = ps.getSupName();

            lvSuppliers.getItems().add(ps.getProductSupplierId() + " " + prodName + " (" + supName + ")");
        }
    }

    public void setSelectedPackage(Package pkg) {
        packageId = pkg.getPackageId();

        DecimalFormat decimal = new DecimalFormat("0.00");

        txtPackageId.setText(pkg.getPackageId() + "");
        txtPkgName.setText(pkg.getPkgName());
        dpPkgStartDate.setValue(new Date(pkg.getPkgStartDate().getTime()).toLocalDate());
        dpPkgEndDate.setValue(new Date(pkg.getPkgEndDate().getTime()).toLocalDate());
        txtPkgDesc.setText(pkg.getPkgDesc());
        txtPkgBasePrice.setText(decimal.format(pkg.getPkgBasePrice()) + "");
        txtPkgAgencyCommission.setText(decimal.format(pkg.getPkgAgencyCommission()) + "");
    }

    private void validateTextField(TextField textField) {

        // Checks for non-numerical characters in fields requiring double values
        if (!Objects.equals(textField.getText(), "") && textField != txtPkgName) {
            try {
                double d = Double.parseDouble(textField.getText());
            } catch (NumberFormatException e) {
                isValid = false;

                DisplayAlert.showWarning("Only numerical values can be enter in"
                        + " " + getLabel(textField) + " " + "field.");
                textField.requestFocus();
            }
        } else if (Objects.equals(textField.getText(), "") && textField != txtPkgName) {
            textField.setText("0");
        }

        if (textField == txtPkgName) {
            // The package Name field is the only required field
            if (Objects.equals(textField.getText(), "")) {
                isValid = false;

                DisplayAlert.showWarning("Please enter in a Package name.");
                textField.requestFocus();
            }

            if (textField.getLength() > 50) {
                isValid = false;

                DisplayAlert.showWarning("Package name is too long. Please limit to 50 characters or less.");
                textField.requestFocus();
            }
        }

        if (textField == txtPkgDesc) {
            if (textField.getLength() > 50) {
                isValid = false;

                DisplayAlert.showWarning("Package Description is too long. Please limit to 50 characters or less.");
                textField.requestFocus();
            }
        }
    }

    private void validateDateRange(DatePicker startDate, DatePicker endDate) {
        if (startDate.getValue().isAfter(endDate.getValue())) {
            isValid = false;
            DisplayAlert.showWarning("Invalid start date.");
            dpPkgStartDate.requestFocus();
        }
    }

    private String getLabel(TextField textField) {
        if (textField == txtPkgName) {
            return "'Package Name'";
        } else if (textField == txtPkgDesc) {
            return "'Description'";
        } else if (textField == txtPkgBasePrice) {
            return "'Base Price'";
        } else {
            return "'Agency Commission'";
        }
    }

    private void closeDialog(Event event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}

