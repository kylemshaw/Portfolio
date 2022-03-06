//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Kyle Shaw
//     Description: Customer object that maps to Customers table record
//----------------------------------------------------------------------------
package com.te.dbmanager.model;

import com.te.dbmanager.data.AgentDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Customer {
//properties
    private int customerId;
    private String custFirstName;
    private String custLastName;
    private String custAddress;
    private String custCity;
    private String custProv;
    private String custPostal;
    private String custCountry;
    private String custHomePhone;
    private String custBusPhone;
    private String custEmail;
    private String custPassword;
    private int agentId;

//Class constants
    public static final ObservableList<String> PROVINCES = FXCollections.observableArrayList(
            new String[] {"AB", "BC", "SK", "MB", "ON", "QB", "NL", "NS", "PE", "NB", "YK", "NW", "NV"});

//navigation properties
    private Agent agent;

//Constructors

    //null
    public Customer() {
    }

    //With password
    public Customer(int customerId, String custFirstName, String custLastName, String custAddress,
                    String custCity, String custProv, String custPostal, String custCountry,
                    String custHomePhone, String custBusPhone, String custEmail, String custPassword, int agentId) throws SQLException {
        this.customerId = customerId;
        this.custFirstName = custFirstName;
        this.custLastName = custLastName;
        this.custAddress = custAddress;
        this.custCity = custCity;
        this.custProv = custProv;
        this.custPostal = custPostal;
        this.custCountry = custCountry;
        this.custHomePhone = custHomePhone;
        this.custBusPhone = custBusPhone;
        this.custEmail = custEmail;
        this.custPassword = custPassword;
        this.agentId = agentId;

        this.agent = AgentDB.getAgent(agentId);
    }

    //Without password
    public Customer(int customerId, String custFirstName, String custLastName, String custAddress,
                    String custCity, String custProv, String custPostal, String custCountry,
                    String custHomePhone, String custBusPhone, String custEmail, int agentId) throws SQLException {
        this.customerId = customerId;
        this.custFirstName = custFirstName;
        this.custLastName = custLastName;
        this.custAddress = custAddress;
        this.custCity = custCity;
        this.custProv = custProv;
        this.custPostal = custPostal;
        this.custCountry = custCountry;
        this.custHomePhone = custHomePhone;
        this.custBusPhone = custBusPhone;
        this.custEmail = custEmail;
        this.custPassword = null;
        this.agentId = agentId;

        this.agent = AgentDB.getAgent(agentId);
    }

//Getter/Setter
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustFirstName() {
        return custFirstName;
    }

    public void setCustFirstName(String custFirstname) {
        this.custFirstName = custFirstname;
    }

    public String getCustLastName() {
        return custLastName;
    }

    public void setCustLastName(String custLastName) {
        this.custLastName = custLastName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    public String getCustProv() {
        return custProv;
    }

    public void setCustProv(String custProv) {
        this.custProv = custProv;
    }

    public String getCustPostal() {
        return custPostal;
    }

    public void setCustPostal(String custPostal) {
        this.custPostal = custPostal;
    }

    public String getCustCountry() {
        return custCountry;
    }

    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }

    public String getCustHomePhone() {
        return custHomePhone;
    }

    public void setCustHomePhone(String custHomePhone) {
        this.custHomePhone = custHomePhone;
    }

    public String getCustBusPhone() {
        return custBusPhone;
    }

    public void setCustBusPhone(String custBusPhone) {
        this.custBusPhone = custBusPhone;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

//Methods
    @Override
    public String toString() {
        return custFirstName + " " + custLastName;
    }
}
