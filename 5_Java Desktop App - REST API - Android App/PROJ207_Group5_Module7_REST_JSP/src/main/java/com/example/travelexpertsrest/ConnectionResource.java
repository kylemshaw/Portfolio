/**
 *  PROJ 207 Threaded Project #3
 *  Group 5
 *  Class: OOSD May 21
 *  Author: Xiaoyan (Kathy) Deng
 *  Description: connection to database
 */
package com.example.travelexpertsrest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionResource {
    public static Connection getDBConnection() {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/travelexperts","kathy","password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

}
