//------------------------------------
// PROJ 207 Threaded Project #3
// Group: 5
// Class: OOSD May 21
// Author (s): Adolphus Cox
// Description: Class that contains static methods for any database operations utilizing the
// packages products suppliers table
//
//------------------------------------

package com.te.dbmanager.data;

import com.te.dbmanager.gui.DisplayAlert;
import com.te.dbmanager.model.PackageProductSupplier;

import java.sql.*;
import java.util.ArrayList;

public class PackageProductSupplierDB {

    private static final DatabaseLogin db = new DatabaseLogin();

    public static void add(int packageId, int productSupplierId) {

        try {
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO `packages_products_suppliers` (`PackageId`, `ProductSupplierId`) VALUES (?, ?)");

            stmt.setInt(1, packageId);
            stmt.setInt(2, productSupplierId);

            int numRows = stmt.executeUpdate();

            if (numRows == 0) {
                System.out.println("Add failed");
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("Error adding service to selected package.");
        }

    }

    public static ArrayList<PackageProductSupplier> getPackageProductSuppliers(int packageId) {
        ArrayList<PackageProductSupplier> pkgProdSup = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());

            PreparedStatement stmt = conn.prepareStatement(
                    "select * from packages_products_suppliers where `PackageId`=?");
            stmt.setInt(1, packageId);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                pkgProdSup.add(new PackageProductSupplier(
                        rs.getInt(1),
                        rs.getInt(2)
                ));
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("A database error has occurred.");
        }

        return pkgProdSup;
    }

    public static void delete(int packageId, int productSupplierId) {

        try {
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());

            PreparedStatement stmt = conn.prepareStatement("DELETE FROM packages_products_suppliers WHERE `PackageId`=? and `ProductSupplierId`=?");

            stmt.setInt(1, packageId);
            stmt.setInt(2, productSupplierId);

            stmt.execute();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("Error removing service from selected package.");
        }
    }
}
