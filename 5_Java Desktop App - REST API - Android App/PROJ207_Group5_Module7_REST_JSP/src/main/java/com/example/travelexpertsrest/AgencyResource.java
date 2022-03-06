/**
 *  PROJ 207 Threaded Project #3
 *  Group 5
 *  Class: OOSD May 21
 *  Author: Xiaoyan (Kathy) Deng
 *  Description: web services working with agencies table
 */
package com.example.travelexpertsrest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;

@Path("/agency")
public class AgencyResource {

    //Get all agencies
    @GET
    @Path("/getagencies")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAgencies() throws SQLException {
        Connection conn = ConnectionResource.getDBConnection();
        Statement stmt = null;
        String returnString = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from agencies");
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
                message.put("message", "No agency found");
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

    //get agency based on given agency Id
    @GET
    @Path("/getagency/{ agencyId }")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAgency(@PathParam("agencyId") int agencyId) {
        Connection conn = ConnectionResource.getDBConnection();
        String returnString = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("select * from agencies where AgencyId=?");
            stmt.setInt(1, agencyId);
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
                message.put("message", "Please input valid agency Id");
                returnString = message.toJSONString();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnString;
    }

}
