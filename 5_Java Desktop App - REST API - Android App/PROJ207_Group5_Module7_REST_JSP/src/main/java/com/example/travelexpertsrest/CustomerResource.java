/**
 *  PROJ 207 Threaded Project #3
 *  Group 5
 *  Class: OOSD May 21
 *  Author: postBooking by Adolphus Cox while rest by Xiaoyan (Kathy) Deng
 *  Description: web services working with customers table
 */
package com.example.travelexpertsrest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;

@Path("/customer")
public class CustomerResource {

    //get all customers
    @GET
    @Path("/getcustomers")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCustomers() throws SQLException {
        Connection conn = ConnectionResource.getDBConnection();
        Statement stmt = null;
        String returnString = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from customers");
            ResultSetMetaData rsmd = rs.getMetaData();
            JSONArray jsonArray = new JSONArray();
            while (rs.next()){
                JSONObject jsonObject = new JSONObject();
                for (int i = 1; i <=rsmd.getColumnCount() ; i++) {
                    jsonObject.put(rsmd.getColumnName(i), rs.getString(i));
                }
                jsonArray.add(jsonObject);
            }
            if(jsonArray.isEmpty()){
                JSONObject message = new JSONObject();
                message.put("message", "No customer found");
                returnString = message.toJSONString();
            }else{
                returnString = jsonArray.toJSONString();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnString;
    }

    //get corresponding customer based on given customer Id
    @GET
    @Path("/getcustomer/{ customerId }")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCustomer(@PathParam("customerId") int customerId) {
        Connection conn = ConnectionResource.getDBConnection();
        String returnString = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("select * from customers where CustomerId=?");
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            JSONObject jsonObject = null;
            if (rs.next()){
                jsonObject = new JSONObject();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    jsonObject.put(rsmd.getColumnName(i), rs.getString(i));
                }
                returnString = jsonObject.toJSONString();
            }else{
                JSONObject message = new JSONObject();
                message.put("message", "Please input valid customer Id");
                returnString = message.toJSONString();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnString;
    }

    // get corresponding customer based on given customer email
    @GET
    @Path("/getcustomerbyemail/{ custemail }")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCustomerByEmail(@PathParam("custemail") String custemail) {
        Connection conn = ConnectionResource.getDBConnection();
        String returnString = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("select * from customers where CustEmail=?");
            stmt.setString(1, custemail);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            JSONObject jsonObject = null;
            if (rs.next()){
                jsonObject = new JSONObject();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    jsonObject.put(rsmd.getColumnName(i), rs.getString(i));
                }
                returnString = jsonObject.toJSONString();
            }else{
                JSONObject message = new JSONObject();
                message.put("message", "Please input valid customer email");
                returnString = message.toJSONString();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnString;
    }

    //update booking by Adolphus Cox
    @POST
    @Path("/postcustomer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String postCustomer(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        JSONObject message = new JSONObject();
        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
            Connection conn = ConnectionResource.getDBConnection();
            String sql = "UPDATE customers SET CustFirstName=?, CustLastName=?, CustAddress=?, CustCity=?, CustProv=?," +
                    "CustPostal=?, CustCountry=?, CustHomePhone=?, CustBusPhone=?, CustEmail=?, CustPassword=?, AgentId=? WHERE CustomerId=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,jsonObject.get("CustFirstName").toString());
            stmt.setString(2,jsonObject.get("CustLastName").toString());
            stmt.setString(3,jsonObject.get("CustAddress").toString());
            stmt.setString(4,jsonObject.get("CustCity").toString());
            stmt.setString(5,jsonObject.get("CustProv").toString());
            stmt.setString(6,jsonObject.get("CustPostal").toString());
            stmt.setString(7,jsonObject.get("CustCountry").toString());
            stmt.setString(8,jsonObject.get("CustHomePhone").toString());
            stmt.setString(9,jsonObject.get("CustBusPhone").toString());
            stmt.setString(10,jsonObject.get("CustEmail").toString());
            stmt.setString(11,jsonObject.get("CustPassword").toString());
            stmt.setInt(12,Integer.parseInt(jsonObject.get("AgentId").toString()));
            stmt.setInt(13,Integer.parseInt(jsonObject.get("CustomerId").toString()));

            if (stmt.executeUpdate()>0){
                message.put("message","Customer updated successfully");
            }else{
                message.put("message","Customer update failed");
            }
            conn.close();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return message.toJSONString();
    }

}
