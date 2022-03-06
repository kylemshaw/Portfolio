//------------------------------------
// PROJ 207 Threaded Project #3
// Group: 5
// Class: OOSD May 21
// Author (s): Adolphus Cox
// Description: Class that contains static methods for any database operations utilizing the
// packages table
//
//------------------------------------

package com.te.dbmanager.data;

import com.te.dbmanager.gui.DisplayAlert;
import com.te.dbmanager.model.Package;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.*;
import java.time.ZoneId;

public class PackageDB {

    private static final DatabaseLogin db = new DatabaseLogin();

    public static void addPackage(TextField txtPkgName, DatePicker dpPkgStartDate, DatePicker dpPkgEndDate,
                                  TextField txtPkgDesc, TextField txtPkgBasePrice,
                                  TextField txtPkgAgencyCommission) {

        // Formatting dates for entry into SQL Database
        Date sqlStartDate = formatDateForSQL(dpPkgStartDate);
        Date sqlEndDate = formatDateForSQL(dpPkgEndDate);

        // Adding new entry into Packages table
        try {

            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO `packages` (`PkgName`, `PkgStartDate`, " +
                    "`PkgEndDate`, `PkgDesc`, `PkgBasePrice`, `PkgAgencyCommission`) VALUES (?, ?, ?, ?, ?, ?)");

            stmt.setString(1, txtPkgName.getText());
            stmt.setDate(2, sqlStartDate);
            stmt.setDate(3, sqlEndDate);
            stmt.setString(4, txtPkgDesc.getText());
            stmt.setDouble(5, Double.parseDouble(txtPkgBasePrice.getText()));
            stmt.setDouble(6, Double.parseDouble(txtPkgAgencyCommission.getText()));

            int numRows = stmt.executeUpdate();

            if (numRows == 0) {
                System.out.println("Add failed");
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("Error Adding Package.");
        }

    }

    public static void deletePackage(Package selectedPackage) {
        try {
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM packages WHERE `PackageId`=?");

            stmt.setInt(1, selectedPackage.getPackageId());
            stmt.execute();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("Error deleting package.");
        }
    }

    public static void updatePackage(TextField txtPkgName, DatePicker dpPkgStartDate, DatePicker dpPkgEndDate,
                                     TextField txtPkgDesc, TextField txtPkgBasePrice,
                                     TextField txtPkgAgencyCommission, int selectedIndex) {

        // Formatting dates for entry into SQL Database
        Date sqlStartDate = formatDateForSQL(dpPkgStartDate);
        Date sqlEndDate = formatDateForSQL(dpPkgEndDate);

        // Updating Packages table in database
        try {
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());

            PreparedStatement stmt = conn.prepareStatement("UPDATE `packages` SET `PkgName`=?," +
                    "`PkgStartDate`=?,`PkgEndDate`=?,`PkgDesc`=?,`PkgBasePrice`=?,`PkgAgencyCommission`=?" +
                    " WHERE `PackageId`=?");

            stmt.setString(1, txtPkgName.getText());
            stmt.setDate(2, sqlStartDate);
            stmt.setDate(3, sqlEndDate);
            stmt.setString(4, txtPkgDesc.getText());
            stmt.setDouble(5, Double.parseDouble(txtPkgBasePrice.getText()));
            stmt.setDouble(6, Double.parseDouble(txtPkgAgencyCommission.getText()));
            stmt.setInt(7, selectedIndex);

            int numRows = stmt.executeUpdate();

            if (numRows == 0) {
                System.out.println("Update failed");
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("Error updating package.");
        }
    }

    public static String getPackageName(int packageId) {
        String pkgName = "";

        try {
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());

            PreparedStatement stmt = conn.prepareStatement("select PkgName from packages where `PackageId`=?");
            stmt.setInt(1, packageId);
            ResultSet rs = stmt.executeQuery();

            rs.next();

            pkgName = rs.getString(1);

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("A database error has occurred.");
        }

        return pkgName;
    }

    public static int getLastId() {
        int lastId = 0;

        try {
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());

            PreparedStatement stmt = conn.prepareStatement(
                    "select max(PackageId) from packages");

            ResultSet rs = stmt.executeQuery();
            rs.next();

            lastId = rs.getInt(1);

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("A database error has occurred.");
        }

        return lastId;
    }

    // Other Methods -------------------------------------------------------------------------

    private static Date formatDateForSQL(DatePicker dp) {
        java.util.Date date = java.util.Date.from(dp.getValue().atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
        // Returning SQL formatted date
        return new Date(date.getTime());
    }
}
