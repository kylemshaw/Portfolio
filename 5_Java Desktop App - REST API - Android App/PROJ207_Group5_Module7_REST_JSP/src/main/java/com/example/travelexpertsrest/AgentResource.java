/**
 *  PROJ 207 Threaded Project #3
 *  Group 5
 *  Class: OOSD May 21
 *  Author: getFile by Kyle Shaw while rest by Xiaoyan (Kathy) Deng
 *  Description: web services working with agents table
 */
package com.example.travelexpertsrest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.sql.*;

@Path("/agent")
public class AgentResource {

    //get all agents
    @GET
    @Path("/getagents")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAgents() throws SQLException {
        Connection conn = ConnectionResource.getDBConnection();
        Statement stmt = null;
        String returnString = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from agents");
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
                message.put("message", "No agent found");
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

    //get corresponding agent based on given agent Id
    @GET
    @Path("/getagent/{ agentId }")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAgent(@PathParam("agentId") int agentId) {
        Connection conn = ConnectionResource.getDBConnection();
        String returnString = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("select * from agents where AgentId=?");
            stmt.setInt(1, agentId);
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
                message.put("message", "Please input valid agent Id");
                returnString = message.toJSONString();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnString;
    }

    //update booking
    @POST
    @Path("/postagent")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public String postAgent(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject message = new JSONObject();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
            Connection conn = ConnectionResource.getDBConnection();
            String sql = "UPDATE agents SET AgtFirstName=?, AgtMiddleInitial=?, AgtLastName=?, AgtBusPhone =?," +
                    "AgtEmail=?, AgtPosition=?, AgencyId=?, AgtPhoto=?, AgtUserName=?, AgtPassword=? WHERE AgentId=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, jsonObject.get("AgtFirstName").toString());
            stmt.setString(2, jsonObject.get("AgtMiddleInitial").toString());
            stmt.setString(3, jsonObject.get("AgtLastName").toString());
            stmt.setString(4, jsonObject.get("AgtBusPhone").toString());
            stmt.setString(5, jsonObject.get("AgtEmail").toString());
            stmt.setString(6, jsonObject.get("AgtPosition").toString());
            stmt.setInt(7, Integer.parseInt(jsonObject.get("AgencyId").toString()));
            stmt.setString(8, jsonObject.get("AgtPhoto").toString());
            stmt.setString(9, jsonObject.get("AgtUserName").toString());
            stmt.setString(10, jsonObject.get("AgtPassword").toString());
            stmt.setInt(11, Integer.parseInt(jsonObject.get("AgentId").toString()));

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

    //add an agent
    @PUT
    @Path("/putagent")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public String putAgent(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject message = new JSONObject();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
            Connection conn = ConnectionResource.getDBConnection();
            String sql = "INSERT INTO agents VALUES (null,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, jsonObject.get("AgtFirstName").toString());
            stmt.setString(2, jsonObject.get("AgtMiddleInitial").toString());
            stmt.setString(3, jsonObject.get("AgtLastName").toString());
            stmt.setString(4, jsonObject.get("AgtBusPhone").toString());
            stmt.setString(5, jsonObject.get("AgtEmail").toString());
            stmt.setString(6, jsonObject.get("AgtPosition").toString());
            stmt.setInt(7, Integer.parseInt(jsonObject.get("AgencyId").toString()));
            stmt.setString(8, jsonObject.get("AgtPhoto").toString());
            stmt.setString(9, jsonObject.get("AgtUserName").toString());
            stmt.setString(10, jsonObject.get("AgtPassword").toString());

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

    //delete correspondiong agent based on given agent Id
    @DELETE
    @Path("/deleteagent/{ agentId }")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteAgent(@PathParam("agentId") int agentId) {
        Connection conn = ConnectionResource.getDBConnection();
        JSONObject message = new JSONObject();
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM `agents` WHERE AgentId=?");
            stmt.setInt(1, agentId);
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

    //get agent photo by Kyle Shaw
    @GET
    @Path("/getagentimg/{ agentPhoto }")
    @Produces("image/jpeg")
    public Response getFile(@PathParam("agentPhoto") String agentPhoto) {

        String fileName = "/agents/" + agentPhoto;

        if(getClass().getResource(fileName) == null)
            fileName = "/agents/default.jpg";

        File file = new File(getClass().getResource(fileName).getFile());
        Response.ResponseBuilder response = Response.ok((Object) file);
        //response.header("Content-Disposition","attachment; filename=\"javatpoint_image.png\""); //pops up download dialog
        return response.build();
    }
}
