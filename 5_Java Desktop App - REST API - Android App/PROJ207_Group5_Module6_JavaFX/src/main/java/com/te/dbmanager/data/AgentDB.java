//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Kyle Shaw
//     Description: Handles access to Agents table in travelexperts DB
//----------------------------------------------------------------------------

package com.te.dbmanager.data;

import com.te.dbmanager.model.Agent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 *   Travel Experts Database - Agent table access
 */
public class AgentDB {

    /**
     * Returns all records int the agents table
     * @return ObservableList of Agents
     * @throws SQLException
     */
    private static final DatabaseLogin db = new DatabaseLogin();

    public static ObservableList<Agent> getAllAgents() throws SQLException {
        ObservableList<Agent> agents = FXCollections.observableArrayList();

        //prepare and execute sql, store result in agentIds list
        Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Agents ORDER BY AgentId");
        while(rs.next()) {
            agents.add(new Agent(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getInt(8),
                    rs.getString(9),
                    rs.getString(10),
                    rs.getString(11)
            ));
        }
        conn.close();
        return agents;
    }

    /**
     * Returns the Agent with AgentId matching the agentId parameter from the database.
     * If no matching agent is found returns null.
     * @param agentId Id of agent object to return
     * @return Agent object or NULL
     * @throws SQLException
     */
    public static Agent getAgent(int agentId) throws SQLException {
        Agent agent = null; //default agent to null

        //prepare and execute sql to find agent, if found construct agent
        Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM agents WHERE AgentId=?");
        stmt.setInt(1, agentId);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
            agent = new Agent(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getInt(8),
                    rs.getString(9),
                    rs.getString(10),
                    rs.getString(11)
            );
        }
        conn.close();
        return agent;
    }

    /**
     * Updates the database agent record with an AgentId that matches the
     * agent parmeter.
     * @param agent Agent object containing updated fields for the database
     * @throws SQLException
     */
    public static void saveAgent(Agent agent) throws SQLException {
        //connect to database, prepare and attempt to execute sql update
        Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
        PreparedStatement stmt = conn.prepareStatement(
                "UPDATE `agents` SET " +
                        "`AgtFirstName`=?," +
                        "`AgtMiddleInitial`=?," +
                        "`AgtLastName`=?," +
                        "`AgtBusPhone`=?," +
                        "`AgtEmail`=?," +
                        "`AgtPosition`=?," +
                        "`AgencyId`=? " +
                        "`agtPhoto`=? " +
                        "`agtUsername`=? " +
                        "`agtPassword`=? " +
                        "WHERE `AgentId`=?"
        );
        stmt.setString(1, agent.getAgtFirstName());
        stmt.setString(2, agent.getAgtMiddleInitial());
        stmt.setString(3, agent.getAgtLastName());
        stmt.setString(4, agent.getAgtBusPhone());
        stmt.setString(5, agent.getAgtEmail());
        stmt.setString(6, agent.getAgtPosition());
        stmt.setInt(7, agent.getAgencyId());
        stmt.setString(8, agent.getAgtPhoto());
        stmt.setString(9, agent.getAgtUsername());
        stmt.setString(10, agent.getAgtPassword());
        stmt.setInt(11, agent.getAgentId());

        int numRows = stmt.executeUpdate();

        //If update fails provide debugging output, if successful provide confirmation
        if (numRows == 0){
            System.out.println("Update failed :(");
        }
        else{
            System.out.println(numRows + " rows have been updated.");
        }
        conn.close();
    }
}
