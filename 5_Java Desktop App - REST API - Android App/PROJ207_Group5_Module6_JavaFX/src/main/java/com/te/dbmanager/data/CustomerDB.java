//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Kyle Shaw
//     Description: Handles access to Customers table in travelexperts DB
//----------------------------------------------------------------------------

package com.te.dbmanager.data;

import com.te.dbmanager.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 *  Travel Experts Database - Customer table access
 */

public class CustomerDB {

    //Default android app password when new customer is created
    //TODO:Make unique for each customer based on their information (i.e. first initial + last name + postal)
    private static final String DEFAULT_CUSTOMER_PASSWORD = "password";
    private static final DatabaseLogin db = new DatabaseLogin(); //load database connection info

    /**
     * Returns the Customer object with the specified id. If no matching agent is found returns null.
     * @param customerId ID of customer object to get
     * @return Customer object or NULL
     * @throws SQLException
     */
    public static Customer getCustomer(int customerId) throws SQLException {
        Customer customer = null;

        //prepare and execute sql to find customer, if found construct customer
        Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Customers WHERE CustomerId=?");
        stmt.setInt(1, customerId);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
            customer = new Customer(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getString(10),
                    rs.getString(11),
                    rs.getInt(12)
            );
        }
        conn.close();
        return customer;
    }

    /**
     * Returns observable list of all customer records in the table
     * @return
     * @throws SQLException
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        //prepare and execute sql to find all customers, then use loop to store in list
        Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Customers");
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            customers.add(new Customer(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getString(10),
                    rs.getString(11),
                    rs.getString(12),
                    rs.getInt(13)
            ));
        }
        conn.close();
        return customers;
    }

    /**
     * Inserts new customer record.
     * @param customer
     * @throws SQLException
     */
    public static void insertCustomer(Customer customer) throws SQLException {
        //connect to database, prepare sql and load with new customer properties, execute insert
        Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO `Customers`" +
                    "(CustFirstName, CustLastName, CustAddress, CustCity, CustProv, CustPostal, CustCountry, " +
                        "CustHomePhone,CustBusPhone, CustEmail, CustPassword, AgentId)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );

        stmt.setString(1, customer.getCustFirstName());
        stmt.setString(2, customer.getCustLastName());
        stmt.setString(3, customer.getCustAddress());
        stmt.setString(4, customer.getCustCity());
        stmt.setString(5, customer.getCustProv());
        stmt.setString(6, customer.getCustPostal());
        stmt.setString(7, customer.getCustCountry());
        stmt.setString(8, customer.getCustHomePhone());
        stmt.setString(9, customer.getCustBusPhone());
        stmt.setString(10, customer.getCustEmail());
        stmt.setString(11, DEFAULT_CUSTOMER_PASSWORD);
        stmt.setInt(12, customer.getAgentId());

        int numRows = stmt.executeUpdate();
        System.out.println(numRows + " rows have been inserted.");
        conn.close();
    }

    /**
     * Updates the database customer record with matching id.
     * @param customer Customer object containing updated fields for the database
     * @throws SQLException
     */
    public static void updateCustomer(Customer customer) throws SQLException {
        //connect to database, prepare and attempt to execute sql update
        Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
        PreparedStatement stmt = conn.prepareStatement(
                "UPDATE `Customers` SET " +
                        "`CustFirstName`=?," +
                        "`CustLastName`=?," +
                        "`CustAddress`=?," +
                        "`CustCity`=?," +
                        "`CustProv`=?," +
                        "`CustPostal`=?," +
                        "`CustCountry`=?," +
                        "`CustHomePhone`=?," +
                        "`CustBusPhone`=?," +
                        "`CustEmail`=?," +
                        "`AgentId`=? " +
                        "WHERE `CustomerId`=?"
        );

        stmt.setString(1, customer.getCustFirstName());
        stmt.setString(2, customer.getCustLastName());
        stmt.setString(3, customer.getCustAddress());
        stmt.setString(4, customer.getCustCity());
        stmt.setString(5, customer.getCustProv());
        stmt.setString(6, customer.getCustPostal());
        stmt.setString(7, customer.getCustCountry());
        stmt.setString(8, customer.getCustHomePhone());
        stmt.setString(9, customer.getCustBusPhone());
        stmt.setString(10, customer.getCustEmail());
        stmt.setInt(11, customer.getAgentId());
        stmt.setInt(12, customer.getCustomerId());

        int numRows = stmt.executeUpdate();
        System.out.println(numRows + " rows have been updated.");
        conn.close();
    }

    /**
     * Deletes customer record by id.
     * @param customerId
     * @throws SQLException
     */
    public static void deleteCustomer(int customerId) throws SQLException {
        //connect to database, prepare and execute sql
        Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Customers WHERE CustomerId=?");
        stmt.setInt(1, customerId);
        int numRows = stmt.executeUpdate();
        System.out.println(numRows + " rows have been deleted.");
        conn.close();
    }

}
