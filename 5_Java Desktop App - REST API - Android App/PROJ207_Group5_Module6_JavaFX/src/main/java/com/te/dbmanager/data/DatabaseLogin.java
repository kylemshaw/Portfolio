//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Kyle Shaw
//     Description: Reads database connection string and login information
//                  from a config file and stores it in an object for easy
//                  access. Config file  allows db connection to be easily
//                  adjusted for the entire app in one place.
//----------------------------------------------------------------------------

package com.te.dbmanager.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * DatabaseLogin
 *  Stores login information and connection string for database access
 *  Instantiated by providing path to database connection properties file
 */
public class DatabaseLogin {
    private String username;
    private String password;
    private String url;
    static final String DEFAULT_PROPERTIES = ".\\src\\main\\resources\\com\\te\\dbmanager\\config\\connection.properties";

    /**
     * Constructs object containing database login information using settings from properties file
     */
    public DatabaseLogin() {
        String path = DEFAULT_PROPERTIES;
        try {
            FileInputStream fis = new FileInputStream(path);
            Properties p = new Properties();
            p.load(fis);
            this.username = (String) p.get("user");
            this.password = (String) p.get("password");
            this.url = (String)p.get("URL");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
