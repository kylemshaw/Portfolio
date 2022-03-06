//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Snow Tran and Adolphus Cox
//     Description: Handles access to ProductSupplier table in travelexperts DB
//----------------------------------------------------------------------------

package com.te.dbmanager.data;

import com.te.dbmanager.gui.DisplayAlert;
import com.te.dbmanager.model.Product;
import com.te.dbmanager.model.ProductSupplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.ArrayList;

public class ProductSupplierDB extends ProductSupplier {
    public static ObservableList<Product> dataProd = FXCollections.observableArrayList();
    private static boolean checkPackageProductSupplier;
    private static int productRowCount;

    public ProductSupplierDB(int productSupplierId, int productId, int supplierId) {
        super(productSupplierId, productId, supplierId);
    }

    // fill dataProd(observableList) with all products in the database to display when adding/modifying a supplier
    public static int getProductsAvailable(TableView<Product> tvProductsSupplied, TableColumn<Product, String> colProduct) {
        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Products");
            while (rs.next()) {
                dataProd.add(new Product(rs.getInt(1), rs.getString(2)));
                colProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("ProdName"));
                tvProductsSupplied.setItems(dataProd);
            }
            productRowCount = dataProd.size();
            for (Product p : dataProd) {
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM `products_suppliers` WHERE `ProductId`=? and `SupplierId`=?");
                preparedStatement.setString(1, String.valueOf(p.getProductId()));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productRowCount;
    }

    // get a list of productIds to be used with the checkboxes in the add/modify supplier window
    public static ArrayList<Integer> getAllProductsBySupplierId(int supplierId) {
        ArrayList<Integer> productIds = new ArrayList<>();
        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select ProductId from products_suppliers where SupplierId=" + supplierId);
            while (rs.next()) {
                productIds.add(rs.getInt(1));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productIds;
    }

    // add a new ProductSupplier when add/modify a supplier
    public static void addProductSupplier(int productId, int supplierId) {
        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO `products_suppliers`(`ProductId`, `SupplierId`) VALUES (?,?)");
            preparedStatement.setString(1, String.valueOf(productId));
            preparedStatement.setString(2, String.valueOf(supplierId));
            int numRows = preparedStatement.executeUpdate();
            if (numRows == 0) {
                System.out.println("Add failed");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // delete ProductSupplier, after checking the checkboxes (the checking is done in SupplierController)
    public static void deleteProductSupplier(int supplierId) {
        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            PreparedStatement preparedStatement = conn.prepareStatement("delete from Products_Suppliers where SupplierId=" + supplierId);
            int numRows = preparedStatement.executeUpdate();
            if (numRows == 0) {
                System.out.println("Delete failed");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // returns a boolean value after checking whether a ProductSupplier is found in the PackageProductSupplier, true if exists, false if doesn't
    public static boolean checkPackageProductSupplier(int productId, int supplierId) {
        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            PreparedStatement preparedStatement = conn.prepareStatement("select ProductSupplierId from `products_suppliers` where `ProductId`=? and `SupplierId`=?");
            preparedStatement.setString(1, String.valueOf(productId));
            preparedStatement.setString(2, String.valueOf(supplierId));
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                preparedStatement = conn.prepareStatement("select * from `packages_products_suppliers` where `ProductSupplierId`=?");
                preparedStatement.setString(1, String.valueOf(rs.getInt(1)));
                ResultSet rs2 = preparedStatement.executeQuery();
                if (rs2.next()) {
                    checkPackageProductSupplier = true;
                } else {
                    checkPackageProductSupplier = false;
                }
            } else {
                checkPackageProductSupplier = false;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checkPackageProductSupplier;
    }

    // check a ProductSupplier if it already exists in the ProductSupplier table, true if exists, false if doesn't
    public static boolean checkProductSupplier(int productId, int supplierId) {
        boolean existInProdSup = false;
        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            PreparedStatement preparedStatement = conn.prepareStatement("select ProductSupplierId from `products_suppliers` where `ProductId`=? and `SupplierId`=?");
            preparedStatement.setString(1, String.valueOf(productId));
            preparedStatement.setString(2, String.valueOf(supplierId));
            ResultSet rs = preparedStatement.executeQuery();
            existInProdSup = false;
            if (rs.next()) {
                existInProdSup = true;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existInProdSup;
    }

    // delete ProductSupplier, after checking the checkboxes (the checking is done in SupplierController)
    public static void deleteProductSupplier(int productId, int supplierId) {
        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            PreparedStatement preparedStatement = conn.prepareStatement("delete from Products_Suppliers where ProductId=? and SupplierId=?");
            preparedStatement.setString(1, String.valueOf(productId));
            preparedStatement.setString(2, String.valueOf(supplierId));
            int numRows = preparedStatement.executeUpdate();
            if (numRows == 0) {
                System.out.println("Delete failed");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //****************************************************************************************************
    //
    //  ProductSupplierDB Methods for Working with Packages (by Adolphus)
    //
    //****************************************************************************************************

    public static ProductSupplier getProductSupplier(int productSupplierId) {
        ProductSupplier prodSup = null;

        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());

            PreparedStatement stmt = conn.prepareStatement("select * from products_suppliers where `ProductSupplierId`=?");
            stmt.setInt(1, productSupplierId);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            prodSup = new ProductSupplier(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getInt(3)
            );

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("A database error has occurred.");
        }

        return prodSup;
    }

    public static ArrayList<ProductSupplier> getProductSuppliersBySupplierId(int supplierId) {
        ArrayList<ProductSupplier> prodSups = new ArrayList<>();

        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());

            PreparedStatement stmt = conn.prepareStatement("select * from products_suppliers where `SupplierId`=?");
            stmt.setInt(1, supplierId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                prodSups.add(new ProductSupplier(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3)));
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("A database error has occurred.");
        }

        return prodSups;
    }

    public static ProductSupplier getProductSupplierByProdSupId(int productId, int supplierId) {
        ProductSupplier ps = null;

        try {
            DatabaseLogin db = new DatabaseLogin();
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());

            PreparedStatement stmt = conn.prepareStatement("select * from products_suppliers where `ProductId`=? and `SupplierId`=?");
            stmt.setInt(1, productId);
            stmt.setInt(2, supplierId);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            ps = new ProductSupplier(rs.getInt(1), rs.getInt(2), rs.getInt(3));

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("A database error has occurred.");
        }

        return ps;
    }


}
