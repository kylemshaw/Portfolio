/**
 *  PROJ 207 Threaded Project #3
 *  Group 5
 *  Class: OOSD May 21
 *  Author: Xiaoyan (Kathy) Deng
 *  Description: web services working with packages table
 */
package com.example.travelexpertsrest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;

@Path("/package")
public class PackageResource {

    //get all packages
    @GET
    @Path("/getpackages")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPackages() {
        Connection conn = ConnectionResource.getDBConnection();
        Statement stmt = null;
        String returnString = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from packages");
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
                message.put("message", "No package found");
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

    //get corresponding package based on given package Id
    @GET
    @Path("/getpackage/{ packageId }")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPackage(@PathParam("packageId") int packageId) {
        Connection conn = ConnectionResource.getDBConnection();
        String returnString = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("select * from packages where packageId=?");
            stmt.setInt(1,packageId);
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
                message.put("message", "Please input valid package Id");
                returnString = message.toJSONString();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    //update package
    @POST
    @Path("/postpackage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public String postPackage(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject message = new JSONObject();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
            Connection conn = ConnectionResource.getDBConnection();
            String sql = "UPDATE packages SET PkgName=?, PkgStartDate=?, PkgEndDate =?," +
                    "PkgDesc=?, PkgBasePrice=?, PkgAgencyCommission=? WHERE PackageId=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, jsonObject.get("PkgName").toString());
            stmt.setTimestamp(2,Timestamp.valueOf(jsonObject.get("PkgStartDate").toString()));
            stmt.setTimestamp(3,Timestamp.valueOf(jsonObject.get("PkgEndDate").toString()));
            stmt.setString(4, jsonObject.get("PkgDesc").toString());
            stmt.setDouble(5, Double.parseDouble(jsonObject.get("PkgBasePrice").toString()));
            stmt.setDouble(6, Double.parseDouble(jsonObject.get("PkgAgencyCommission").toString()));
            stmt.setInt(7, Integer.parseInt(jsonObject.get("PackageId").toString()));

            if(stmt.executeUpdate()>0){
                message.put("message","Agent updated successfully");
            }else{
                message.put("message","Agent updated failed");
            }
            conn.close();

        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }
        return message.toJSONString();
    }

    //add one package
    @PUT
    @Path("/putpackage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public String putPackage(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject message = new JSONObject();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
            Connection conn = ConnectionResource.getDBConnection();
            String sql = "INSERT INTO packages VALUES (null,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, jsonObject.get("PkgName").toString());
            stmt.setTimestamp(2,Timestamp.valueOf(jsonObject.get("PkgStartDate").toString()));
            stmt.setTimestamp(3,Timestamp.valueOf(jsonObject.get("PkgEndDate").toString()));
            stmt.setString(4, jsonObject.get("PkgDesc").toString());
            stmt.setDouble(5, Double.parseDouble(jsonObject.get("PkgBasePrice").toString()));
            stmt.setDouble(6, Double.parseDouble(jsonObject.get("PkgAgencyCommission").toString()));

            if(stmt.executeUpdate()>0){
                message.put("message","Agent inserted successfully");
            }else{
                message.put("message","Agent inserted failed");
            }
            conn.close();

        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }
        return message.toJSONString();
    }

    //delete corresponding package based on given package Id
    @DELETE
    @Path("/deletepackage/{ packageId }")
    @Produces(MediaType.APPLICATION_JSON)
    public String deletePackage(@PathParam("packageId") int packageId) {
        Connection conn = ConnectionResource.getDBConnection();
        JSONObject message = new JSONObject();
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM `packages` WHERE PackageId=?");
            stmt.setInt(1, packageId);
            if(stmt.executeUpdate()>0){
                message.put("message","Agent deleted successfully");
            }else{
                message.put("message","Agent deleted failed");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return message.toJSONString();

    }

}
