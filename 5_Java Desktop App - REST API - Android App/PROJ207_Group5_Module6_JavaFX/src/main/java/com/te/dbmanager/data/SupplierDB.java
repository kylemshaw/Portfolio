//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Snow Tran and Adolphus Cox
//     Description: Handles access to Products table in travelexperts DB
//----------------------------------------------------------------------------

package com.te.dbmanager.data;

import com.te.dbmanager.gui.DisplayAlert;
import com.te.dbmanager.model.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;


public class SupplierDB extends Supplier {

    private static ObservableList<Supplier> data = FXCollections.observableArrayList();
    private static int supplierId;

    public SupplierDB(int supplierId, String supName) {
        super(supplierId, supName);
    }

    // get all suppliers and display in the tableview
    public static void getSuppliers(TableView<Supplier> tvSuppliers, ObservableList<Supplier> data, TableColumn<Supplier, Integer> colSupplierId,
                                    TableColumn<Supplier, String> colSupName) {
        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Suppliers");
            tvSuppliers.getItems().clear();
            data.clear();
            while (rs.next()) {
                data.add(new Supplier(rs.getInt(1), rs.getString(2)));
                colSupplierId.setCellValueFactory(new PropertyValueFactory<Supplier, Integer>("SupplierId"));
                colSupName.setCellValueFactory(new PropertyValueFactory<Supplier, String>("SupName"));
                tvSuppliers.setItems(data);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // add a new supplier with info from tfSupName
    public static int addSupplier(TextField tfSupName) {
        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO `suppliers`(`SupplierId`, `SupName`) VALUES (?,?)");

            // NOTE: SupplierId in the database provided does not auto-increase the ID, while-loop is used to loop through
            // the current SupplierId's to find the first one that is not in use
            supplierId = 1;
            ResultSet rs = conn.createStatement().executeQuery("select * from Suppliers where SupplierId = " + supplierId);
            while (rs.next()) {
                supplierId++;
                rs = conn.createStatement().executeQuery("select * from Suppliers where SupplierId = " + supplierId);
            }
            preparedStatement.setString(1, String.valueOf(supplierId));
            preparedStatement.setString(2, tfSupName.getText());
            int numRows = preparedStatement.executeUpdate();
            if (numRows == 0) {
                System.out.println("Add failed");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplierId;
    }

    // edit supplier using the info from the tfSupplierId and tfSupName
    public static int editSupplier(TextField tfSupplierId, TextField tfSupName) {
        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE `suppliers` SET `SupName`=? WHERE `SupplierId`=?");
            preparedStatement.setString(1, tfSupName.getText());
            preparedStatement.setString(2, tfSupplierId.getText());
            int numRows = preparedStatement.executeUpdate();
            if (numRows == 0) {
                System.out.println("update failed");
            }
            supplierId = Integer.parseInt(tfSupplierId.getText());
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplierId;
    }

    // delete a supplier with the supplierId
    public static void deleteSupplier(int supplierId) {
        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM `suppliers` WHERE `SupplierId`=?");
            preparedStatement.setString(1, String.valueOf(supplierId));
            int numRows = preparedStatement.executeUpdate();
            if (numRows == 0) {
                System.out.println("delete failed");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //****************************************************************************************************
    //
    //  SupplierDB Methods for Working with Packages (by Adolphus)
    //
    //****************************************************************************************************

    public static String getSupplierName(int supplierId) {
        String supName = "";

        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());

            PreparedStatement stmt = conn.prepareStatement("select SupName from suppliers where `SupplierId`=?");
            stmt.setInt(1, supplierId);
            ResultSet rs = stmt.executeQuery();

            rs.next();

            supName = rs.getString(1);

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("A database error has occurred.");
        }

        return supName;
    }

    public static int getSupplierIdByName(String supplierName) {
        int supplierId = 0;

        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());

            PreparedStatement stmt = conn.prepareStatement("select SupplierId from suppliers where `SupName`=?");
            stmt.setString(1, supplierName);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            supplierId = rs.getInt(1);

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("A database error has occurred.");
        }

        return supplierId;
    }

    public static ArrayList<String> getOrderedList() {
        ArrayList<String> supNames = new ArrayList<>();

        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select SupName from suppliers");

            while(rs.next()) {
                supNames.add(rs.getString(1));
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("A database error has occurred.");
        }

        Collections.sort(supNames);

        return supNames;
    }

}
