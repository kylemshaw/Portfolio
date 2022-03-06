//------------------------------------
// PROJ 207 Threaded Project #3
// Group: 5
// Class: OOSD May 21
// Author (s): Adolphus Cox
// Description: Controller class for Packages table view (packages-view.fxml)
//
//------------------------------------

package com.te.dbmanager.gui;

import com.te.dbmanager.data.DatabaseLogin;
import com.te.dbmanager.data.PackageDB;
import com.te.dbmanager.model.Agent;
import com.te.dbmanager.model.Package;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class PackageController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tvPackages"
    private TableView<Package> tvPackages; // Value injected by FXMLLoader

    @FXML // fx:id="colPackageId"
    private TableColumn<Package,Integer> colPackageId; // Value injected by FXMLLoader

    @FXML // fx:id="colPkgName"
    private TableColumn<Package, String> colPkgName; // Value injected by FXMLLoader

    @FXML // fx:id="colPkgStartDate"
    private TableColumn<Package, Date> colPkgStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="colPkgEndDate"
    private TableColumn<Package, Date> colPkgEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="colPkgDesc"
    private TableColumn<Package, String> colPkgDesc; // Value injected by FXMLLoader

    @FXML // fx:id="colPkgBasePrice"
    private TableColumn<Package, Double> colPkgBasePrice; // Value injected by FXMLLoader

    @FXML // fx:id="colPkgAgencyCommission"
    private TableColumn<Package, Double> colPkgAgencyCommission; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdd"
    private Button btnAdd; // Value injected by FXMLLoader

    @FXML // fx:id="btnEdit"
    private Button btnEdit; // Value injected by FXMLLoader

    @FXML // fx:id="btnDelete"
    private Button btnDelete; // Value injected by FXMLLoader

    private final ObservableList<Package> DATA = FXCollections.observableArrayList();


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tvPackages != null : "fx:id=\"tvPackages\" was not injected: check your FXML file 'packages-view.fxml'.";
        assert colPackageId != null : "fx:id=\"colPackageId\" was not injected: check your FXML file 'packages-view.fxml'.";
        assert colPkgName != null : "fx:id=\"colPkgName\" was not injected: check your FXML file 'packages-view.fxml'.";
        assert colPkgStartDate != null : "fx:id=\"colPkgStartDate\" was not injected: check your FXML file 'packages-view.fxml'.";
        assert colPkgEndDate != null : "fx:id=\"colPkgEndDate\" was not injected: check your FXML file 'packages-view.fxml'.";
        assert colPkgDesc != null : "fx:id=\"colPkgDesc\" was not injected: check your FXML file 'packages-view.fxml'.";
        assert colPkgBasePrice != null : "fx:id=\"colPkgBasePrice\" was not injected: check your FXML file 'packages-view.fxml'.";
        assert colPkgAgencyCommission != null : "fx:id=\"colPkgAgencyCommission\" was not injected: check your FXML file 'packages-view.fxml'.";
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'packages-view.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'packages-view.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'packages-view.fxml'.";

        // Populate table view with Packages table content

        displayPackages();

        // Set event handlers ---------------------------------------------------------------------------------

        tvPackages.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (tvPackages.getSelectionModel().getSelectedItem() != null) {
                    btnEdit.setDisable(false);
                    btnDelete.setDisable(false);
                }
            }
        });

        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("package-detail-view.fxml"));
                try {
                    Parent parent = fxmlLoader.load();

                    PackageDetailController dialogController = fxmlLoader.<PackageDetailController>getController();
                    dialogController.tfTitle.appendText(" - Add Package");
                    dialogController.isAdd = true;

                    openDialog(parent);

                    displayPackages();

                } catch (IOException e) {
                    e.printStackTrace();
                    DisplayAlert.showWarning("Error loading application window.");
                }
            }
        });

        btnEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("package-detail-view.fxml"));
                try {
                    Parent parent = fxmlLoader.load();

                    PackageDetailController dialogController = fxmlLoader.<PackageDetailController>getController();
                    dialogController.tfTitle.appendText(" - Edit Package");
                    dialogController.isAdd = false;
                    dialogController.setSelectedPackage(tvPackages.getSelectionModel().getSelectedItem());

                    openDialog(parent);

                    displayPackages();

                } catch (IOException e) {
                    e.printStackTrace();
                    DisplayAlert.showWarning("Error loading application window.");
                }
            }
        });

        btnDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Package selectedPackage = tvPackages.getSelectionModel().getSelectedItem();

                Alert alert = DisplayAlert.showConfirmation(
                        "Are you sure you want to delete '" + selectedPackage.getPkgName() + "' ?");

                if (alert.getResult() == ButtonType.YES) {
                    PackageDB.deletePackage(selectedPackage);
                }
                displayPackages();
            }
        });
    }

    // Controller Methods -----------------------------------------------------------------------------------

    private void openDialog(Parent parent) {
        Scene scene = new Scene(parent);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void displayPackages() {

        // Initial clearing of data upon populating table (applicable during table updates)
        tvPackages.getItems().clear();

        btnEdit.setDisable(true);
        btnDelete.setDisable(true);

        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from packages");

            while(rs.next()) {
                DATA.add(new Package(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getDate(4),
                        rs.getString(5),
                        rs.getDouble(6),
                        rs.getDouble(7)
                        )
                );

                colPackageId.setCellValueFactory(new PropertyValueFactory<Package, Integer>("packageId"));
                colPkgName.setCellValueFactory(new PropertyValueFactory<Package, String>("pkgName"));
                colPkgStartDate.setCellValueFactory(new PropertyValueFactory<Package, Date>("pkgStartDate"));
                colPkgEndDate.setCellValueFactory(new PropertyValueFactory<Package, Date>("pkgEndDate"));
                colPkgDesc.setCellValueFactory(new PropertyValueFactory<Package, String>("pkgDesc"));
                colPkgBasePrice.setCellValueFactory(new PropertyValueFactory<Package, Double>("pkgBasePrice"));
                colPkgAgencyCommission.setCellValueFactory(new PropertyValueFactory<Package, Double>("pkgAgencyCommission"));
                tvPackages.setItems(DATA);

                formatCurrencyValue(colPkgBasePrice);
                formatCurrencyValue(colPkgAgencyCommission);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("Error displaying table data.");
        }
    }

    private void formatCurrencyValue(TableColumn<Package, Double> col) {
        DecimalFormat cur = new DecimalFormat("$###,###.##");

        col.setCellFactory(tc -> new TableCell<Package, Double>() {
            @Override
            protected void updateItem(Double pkgBasePrice, boolean b) {
                super.updateItem(pkgBasePrice, b);
                if (b) {
                    setText(null);
                } else {
                    String formattedPrice = cur.format(pkgBasePrice);
                    setText(formattedPrice);
                }
            }
        });
    }


}

