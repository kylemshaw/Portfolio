/**
 *  PROJ 207 Threaded Project #3
 *  Group 5
 *  Class: OOSD May 21
 *  Author: Xiaoyan (Kathy) Deng
 *  Description: web services working with bookings table
 */
package com.example.travelexpertsrest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;

@Path("/booking")
public class BookingResource {

    //get all bookings
    @GET
    @Path("/getbookings")
    @Produces(MediaType.APPLICATION_JSON)
    public String getBookings() {
        Connection conn = ConnectionResource.getDBConnection();
        Statement stmt = null;
        String returnString = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from bookings");
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
                message.put("message", "No booking found");
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

    //get the booking based on given booking Id
    @GET
    @Path("/getbooking/{ bookingId }")
    @Produces(MediaType.APPLICATION_JSON)
    public String getBooking(@PathParam("bookingId") int bookingId) {
        Connection conn = ConnectionResource.getDBConnection();
        String returnString = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("select * from bookings where bookingId=?");
            stmt.setInt(1,bookingId);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            JSONObject jsonObject = null;
            if (rs.next()){
                jsonObject = new JSONObject();
                for (int i = 1; i <=rsmd.getColumnCount() ; i++) {
                    jsonObject.put(rsmd.getColumnName(i), rs.getString(i));
                }
                returnString = jsonObject.toJSONString();
            }else{
                JSONObject message = new JSONObject();
                message.put("message", "Please input valid booking Id");
                returnString = message.toJSONString();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    //get corresponding booking based on given customer Id
    @GET
    @Path("/getbookingsbycustomer/{ customerId }")
    @Produces(MediaType.APPLICATION_JSON)
    public String getBookingsByCustomerId(@PathParam("customerId") int customerId) {
        Connection conn = ConnectionResource.getDBConnection();
        String returnString = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("select * from bookings where customerId=?");
            stmt.setInt(1,customerId);
            ResultSet rs = stmt.executeQuery();
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
                message.put("message", "No booking found");
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


    //update the booking
    @POST
    @Path("/postbooking")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String postBooking(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        JSONObject message = new JSONObject();
        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
            Connection conn = ConnectionResource.getDBConnection();
            String sql = "UPDATE bookings SET BookingDate=?, BookingNo=?, TravelerCount=?, CustomerId=?, TripTypeId=?," +
                    "PackageId=?, FeeId=?, AgentId=? WHERE BookingId=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1,Timestamp.valueOf(jsonObject.get("BookingDate").toString()));
            stmt.setString(2,jsonObject.get("BookingNo").toString());
            stmt.setDouble(3,Double.parseDouble(jsonObject.get("TravelerCount").toString()));
            stmt.setInt(4,Integer.parseInt(jsonObject.get("CustomerId").toString()));
            stmt.setString(5,jsonObject.get("TripTypeId").toString());
            stmt.setInt(6,Integer.parseInt(jsonObject.get("PackageId").toString()));
            stmt.setString(7,jsonObject.get("FeeId").toString());
            stmt.setInt(8,Integer.parseInt(jsonObject.get("AgentId").toString()));
            stmt.setInt(9,Integer.parseInt(jsonObject.get("BookingId").toString()));

            if (stmt.executeUpdate()>0){
                message.put("message","Booking updated successfully");
            }else{
                message.put("message","Booking updated failed");
            }
            conn.close();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return message.toJSONString();
    }

    //add one booking
    @PUT
    @Path("/putbooking")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String putBooking(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        JSONObject message = new JSONObject();
        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
            Connection conn = ConnectionResource.getDBConnection();
            String sql = "INSERT INTO bookings VALUES(null,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1,Timestamp.valueOf(jsonObject.get("BookingDate").toString()));
            stmt.setString(2,jsonObject.get("BookingNo").toString());
            stmt.setDouble(3,Double.parseDouble(jsonObject.get("TravelerCount").toString()));
            stmt.setInt(4,Integer.parseInt(jsonObject.get("CustomerId").toString()));
            stmt.setString(5,jsonObject.get("TripTypeId").toString());
            stmt.setInt(6,Integer.parseInt(jsonObject.get("PackageId").toString()));
            stmt.setString(7,jsonObject.get("FeeId").toString());
            stmt.setInt(8,Integer.parseInt(jsonObject.get("AgentId").toString()));

            if (stmt.executeUpdate()>0){
                message.put("message","Booking inserted successfully");
            }else{
                message.put("message","Booking inserted failed");
            }
            conn.close();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return message.toJSONString();
    }

    //delete the corresponding booking based on given booking Id
    @DELETE
    @Path("/deletebooking/{ bookingId }")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteBooking(@PathParam("bookingId") int bookingId) {
        Connection conn = ConnectionResource.getDBConnection();
        JSONObject message = new JSONObject();
        try {
            PreparedStatement stmt = conn.prepareStatement("delete from bookings where bookingId=?");
            stmt.setInt(1,bookingId);
            if (stmt.executeUpdate()>0){
                message.put("message","Booking deleted successfully");
            }else{
                message.put("message","Booking deleted failed");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message.toJSONString();
    }

}