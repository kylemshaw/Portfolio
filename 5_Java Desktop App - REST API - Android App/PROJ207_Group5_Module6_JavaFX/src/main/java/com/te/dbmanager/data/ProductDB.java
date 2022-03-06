//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Snow Tran and Adolphus Cox
//     Description: Handles access to Products table in travelexperts DB
//----------------------------------------------------------------------------


package com.te.dbmanager.data;

import com.te.dbmanager.gui.DisplayAlert;
import com.te.dbmanager.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.*;


public class ProductDB extends Product {

    private static ObservableList<Product> data = FXCollections.observableArrayList();
    private static ObservableList<Product> currentProductList = FXCollections.observableArrayList();

    public ProductDB(int productId, String prodName) {
        super(productId, prodName);
    }

    // get all products and add them to the table view
    public static void getProducts(TableView<Product> tvProducts, ObservableList<Product> data, TableColumn<Product, Integer> colProductId,
                                   TableColumn<Product, String> colProdName) {
        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Products");
            tvProducts.getItems().clear();
            data.clear();

            while (rs.next()) {
                data.add(new Product(rs.getInt(1), rs.getString(2)));
                colProductId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("ProductId"));
                colProdName.setCellValueFactory(new PropertyValueFactory<Product, String>("ProdName"));
                tvProducts.setItems(data);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // add Product using the text from the textfields
    public static void addProduct(MouseEvent mouseEvent, TextField tfProductId, TextField tfProdName) throws SQLException {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO `products`(`ProductId`, `ProdName`) VALUES (0,?)");
            preparedStatement.setString(1, tfProdName.getText());
            int numRows = preparedStatement.executeUpdate();
            if (numRows == 0) {
                System.out.println("Add failed");
            }
            conn.close();
    }

    //  edit Product  using the text from the textfields
    public static void editProduct(MouseEvent mouseEvent, TextField tfProductId, TextField tfProdName) {
        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE `products` SET `ProdName`=? WHERE `ProductId`=?");
            preparedStatement.setString(1, tfProdName.getText());
            preparedStatement.setString(2, tfProductId.getText());
            int numRows = preparedStatement.executeUpdate();
            if (numRows == 0) {
                System.out.println("update failed");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // delete product with the matching Id
    public static void deleteProduct(int productId) {
        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM `products` WHERE `ProductId`=?");
            preparedStatement.setString(1, String.valueOf(productId));
            int numRows = preparedStatement.executeUpdate();
            if (numRows == 0) {
                System.out.println("delete failed");
            }
            conn.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to delete this product because it is linked to one or more suppliers.", ButtonType.OK);
            alert.show();
            e.printStackTrace();
        }
    }

    //****************************************************************************************************
    //
    //  ProductDB Methods for Working with Packages (by Adolphus)
    //
    //****************************************************************************************************
    public static String getProductName(int productId) {
        String prodName = "";

        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());

            PreparedStatement stmt = conn.prepareStatement("select ProdName from products where `ProductId`=?");
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            rs.next();

            prodName = rs.getString(1);

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("A database error has occurred.");
        }

        return prodName;
    }

    public static int getProductIdByName(String prodName) {
        int productId = 0;

        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());

            PreparedStatement stmt = conn.prepareStatement("select ProductId from products where `ProdName`=?");
            stmt.setString(1, prodName);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            productId = rs.getInt(1);

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("A database error has occurred.");
        }

        return productId;
    }
}

