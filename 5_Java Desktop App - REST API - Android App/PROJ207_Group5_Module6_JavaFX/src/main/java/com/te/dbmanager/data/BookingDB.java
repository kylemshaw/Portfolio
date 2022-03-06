package com.te.dbmanager.data;

import com.te.dbmanager.gui.DisplayAlert;
import com.te.dbmanager.model.*;
import com.te.dbmanager.model.Package;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;

/**
 * BookingDB class has methods to deal with database
 Author: Xiaoyan Deng
 Date: 2021/10/12
 Course: PROJ-207
 Workshop 6
 */
public class BookingDB {
    public BookingDB() {
    }
    DatabaseLogin db = new DatabaseLogin();
    String url = db.getUrl();//"jdbc:mysql://localhost:3306/travelexperts";
    String user = db.getUsername();//"kathy";
    String passwd = db.getPassword();//"password";


    //------------------Methods dealing with table Bookings---------------------------------------------

    /**
     * method to get all bookings from bookings table
     * @return list of bookings
     */
    public ObservableList<Booking> getAllBookings(){
        ObservableList<Booking> data = FXCollections.observableArrayList();;
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from bookings");
            while(rs.next()){
                data.add(new Booking(rs.getString(1),rs.getString(2).substring(0,10),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getString(9)));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * method to get metadata of bookings
     * @return metadata
     */
    public ResultSetMetaData getBookingMetadata(){
        ResultSetMetaData rsmd = null;
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from bookings");
            rsmd = rs.getMetaData();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  rsmd;
    }

    /**
     * method to get corresponding booking based on provided bookingId
     * @param bookingId provided bookingId
     * @return corresponding booking
     */
    public Booking getBookingById(int bookingId){
        Booking booking=null;
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            PreparedStatement stmt = connection.prepareStatement("select * from Bookings where bookingId = ?");
            stmt.setInt(1,bookingId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                booking = new Booking(rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getString(9));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    /**
     * method to get all bookings for given agentId and within given time range
     * @param agentId provided agentId
     * @param months given time range
     * @return corresponding list of bookings
     */
    public ObservableList<Booking> getBookingsByAgentIdAndMonths(int agentId, List<String> months){
        ObservableList<Booking> data = FXCollections.observableArrayList();;
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from bookings");
            String time;
            String month;
            List<String> monthsList = months;
            while(rs.next()){
                time = rs.getString(2).substring(0,7);
                for (int i = 0; i < monthsList.size(); i++) {
                    month = monthsList.get(i);
                    if(rs.getInt(9)==agentId & time.matches(month)){
                        data.add(new Booking(rs.getString(1),rs.getString(2),rs.getString(3),
                                rs.getString(4),rs.getString(5),rs.getString(6),
                                rs.getString(7),rs.getString(8),rs.getString(9)));
                    }
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * method to get all bookings by customers themselves without agents within given time range
     * @param months given time range
     * @return corresponding list of bookings
     */
    public ObservableList<Booking> getCustBookingsByMonths(List<String> months){
        ObservableList<Booking> data = FXCollections.observableArrayList();;
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from bookings");
            String time;
            String month;
            List<String> monthsList = months;
            while(rs.next()){
                time = rs.getString(2).substring(0,7);
                for (int i = 0; i < monthsList.size(); i++) {
                    month = monthsList.get(i);
                    if(rs.getString(9)==null & time.matches(month)){
                        data.add(new Booking(rs.getString(1),rs.getString(2),rs.getString(3),
                                rs.getString(4),rs.getString(5),rs.getString(6),
                                rs.getString(7),rs.getString(8),rs.getString(9)));
                    }
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }


    /**
     * method to add a booking to bookings table
     * @param booking provided booking to be inserted
     * @return
     */
    public int insertBooking(Booking booking){
        int bookingId=0;
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            PreparedStatement stmt = connection.prepareStatement("insert bookings values(null,?,?,?,?,?,?,?,?)");

            stmt.setString(5, null);
            stmt.setString(8, null);
            if(booking.getTripTypeId()!=null) {
                stmt.setString(5, booking.getTripTypeId());
            }
            if(booking.getAgentId()!=null) {
                stmt.setInt(8, Integer.parseInt(booking.getAgentId()));
            }
            if(booking.getFeeId()!=null) {
                stmt.setString(7, booking.getFeeId());
            }else{
                stmt.setString(7, "BK");
            }
            stmt.setTimestamp(1, Timestamp.valueOf(booking.getBookingDate()));
            stmt.setString(2, booking.getBookingNo());
            stmt.setDouble(3,Double.parseDouble(booking.getTravelerCount()));
            stmt.setInt(4,Integer.parseInt(booking.getCustomerId()));
            stmt.setInt(6, Integer.parseInt(booking.getPackageId()));
            stmt.executeUpdate();
            //find the bookingId of added booking
            bookingId = 0;
            Statement stmt1 = connection.createStatement();
            ResultSet rs = stmt1.executeQuery("select bookingId from bookings");
            while(rs.next()){
                if (rs.getInt(1)>bookingId)
                    bookingId = rs.getInt(1);
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingId;
    }

    /**
     * method to update the given booking
     * @param booking the booking to be updated
     */
    public void updateBooking(Booking booking){
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            PreparedStatement stmt = connection.prepareStatement("update bookings set bookingDate = ?, bookingNo = ?," +
                    "travelerCount = ?, customerId = ?, tripTypeId = ?, packageId = ?, feeId = ?," +
                    "agentId = ? where bookingId = ?");
            stmt.setString(5, null);
            stmt.setString(8, null);
            if(booking.getTripTypeId()!=null) {
                stmt.setString(5, booking.getTripTypeId());
            }
            if(booking.getAgentId()!=null) {
                stmt.setInt(8, Integer.parseInt(booking.getAgentId()));
            }
            if(booking.getFeeId()!=null) {
                stmt.setString(7, booking.getFeeId());
            }else{
                stmt.setString(7, "BK");
            }
            stmt.setTimestamp(1, Timestamp.valueOf(booking.getBookingDate()));
            stmt.setString(2, booking.getBookingNo());
            stmt.setDouble(3,Double.parseDouble(booking.getTravelerCount()));
            stmt.setInt(4,Integer.parseInt(booking.getCustomerId()));
            stmt.setInt(6, Integer.parseInt(booking.getPackageId()));
            stmt.setInt(9, Integer.parseInt(booking.getBookingId()));
            stmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to delete corresponding booking by given bookingId
     * @param bookingId given bookingId
     */
    public void deleteBookingById(int bookingId){
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            PreparedStatement stmt = connection.prepareStatement("delete from Bookings where bookingId = ?");
            stmt.setInt(1,bookingId);
            stmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //****************************************************************************************************
    //
    //  BookingDB Methods used to display statistics on Dashboard (by Adolphus)
    //
    //****************************************************************************************************

    public int getTotalBookingsByAgent(int agentId) {
        int count = 0;

        try {
            Connection conn = DriverManager.getConnection(url,user,passwd);
            PreparedStatement stmt =
                    conn.prepareStatement("SELECT COUNT(BookingID) FROM bookings WHERE `AgentId`=?");

            stmt.setInt(1, agentId);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            count = rs.getInt(1);

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("Error deleting package.");
        }

        return count;
    }

    public int getTotalCustomersByAgent(int agentId) {
        int count = 0;

        try {
            Connection conn = DriverManager.getConnection(url,user,passwd);
            PreparedStatement stmt =
                    conn.prepareStatement("SELECT COUNT(DISTINCT CustomerID) FROM bookings WHERE `AgentId`=?");

            stmt.setInt(1, agentId);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            count = rs.getInt(1);

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("Error deleting package.");
        }

        return count;
    }

    public int getTotalCommissionByAgent(int agentId) {
        int amount = 0;

        try {
            Connection conn = DriverManager.getConnection(url,user,passwd);
            PreparedStatement stmt =
                    conn.prepareStatement("SELECT bookings.BookingId, bookings.AgentId, " +
                            "SUM(bookingdetails.AgencyCommission) FROM `bookings`\n" +
                            "JOIN bookingdetails ON bookings.BookingId = bookingdetails.BookingId\n" +
                            "WHERE AgentId=? GROUP BY AgentId");

            stmt.setInt(1, agentId);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            amount = rs.getInt(3);

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("Error deleting package.");
        }

        return amount;
    }


    //------------------Methods dealing with table Customers---------------------------------------------

    /**
     * method to get all customers
     * @return all customers in customers table
     */
    public ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from customers");
            while(rs.next()){
                customers.add(new Customer(rs.getInt(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getString(9),
                        rs.getString(10),rs.getString(11), rs.getString(12),rs.getInt(13)));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    /**
     * method to get the customer by given customerId
     * @param customerId given customerId
     * @return corresponding customer
     */
    public Customer getCustomerById(int customerId){
        Customer customer=null;
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            PreparedStatement stmt = connection.prepareStatement("select * from Customers where customerId = ?");
            stmt.setInt(1,customerId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                customer = new Customer(rs.getInt(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getString(9),
                        rs.getString(10),rs.getString(11), rs.getString(12), rs.getInt(13));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }


    //------------------Methods dealing with table TripTypes---------------------------------------------

    /**
     * method to get all trip types in triptypes table
     * @return all trip types
     */
    public ObservableList<TripType> getAllTripTypes(){
        ObservableList<TripType> tripTypes = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from triptypes");
            while(rs.next()){
                tripTypes.add(new TripType(rs.getString(1),rs.getString(2)));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tripTypes;
    }

    /**
     * method to get trip type by given tripTypeId
     * @param tripTypeId given trip type Id
     * @return corresponding trip type
     */
    public TripType getTripTypeById(String tripTypeId){
        TripType tripType=null;
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            PreparedStatement stmt = connection.prepareStatement("select * from triptypes where TripTypeId = ?");
            stmt.setString(1,tripTypeId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                tripType = new TripType(rs.getString(1),rs.getString(2));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tripType;
    }


    //------------------Methods dealing with table Packages---------------------------------------------

    /**
     * method to get all packages in packages table
     * @return all packages
     */
    public ObservableList<Package> getAllPackages(){
        ObservableList<Package> packages = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from packages");
            while(rs.next()){
                packages.add(new Package(rs.getInt(1),rs.getString(2),rs.getDate(3),
                        rs.getDate(4),rs.getString(5),rs.getDouble(6),
                        rs.getDouble(7)));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }

    /**
     * get package by given packageId
     * @param packageId given packageId
     * @return corresponding package
     */
    public Package getPackageById(int packageId){
        Package aPackage=null;
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            PreparedStatement stmt = connection.prepareStatement("select * from Packages where packageId = ?");
            stmt.setInt(1,packageId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                aPackage = new Package(rs.getInt(1),rs.getString(2),rs.getDate(3),
                        rs.getDate(4),rs.getString(5),rs.getDouble(6),
                        rs.getDouble(7));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aPackage;
    }


    //------------------------method deal with BookingDetails-------------------------

    /**
     * method to delete all booking details by given bookingId
     * @param bookingId given bookingId
     */
    public void deleteBookingDetailsByBookingId(int bookingId){
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            PreparedStatement stmt = connection.prepareStatement("delete from BookingDetails where bookingId = ?");
            stmt.setInt(1,bookingId);
            stmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //------------------Methods dealing with table Agents---------------------------------------------

    /**
     * method to get all agents in agents table
     * @return all agents
     */
    /*public ObservableList<Agent> getAllAgents(){
        ObservableList<Agent> agents = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from agents");
            while(rs.next()){
                agents.add(new Agent(rs.getInt(1),rs.getString(2),
                        rs.getString(3),rs.getString(4),rs.getString(5),
                        rs.getString(6),rs.getString(7),rs.getInt(8)));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agents;
    }*/

    /**
     * method to get agent by given agentId
     * @param agentId given agentId
     * @return corresponding agent
     */
    /*public Agent getAgentById(int agentId){
        Agent agent=null;
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            PreparedStatement stmt = connection.prepareStatement("select * from agents where AgentId = ?");
            stmt.setInt(1, agentId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                agent = new Agent(rs.getInt(1),rs.getString(2),
                        rs.getString(3),rs.getString(4),rs.getString(5),
                        rs.getString(6),rs.getString(7),rs.getInt(8));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agent;
    }*/


    //------------------Methods dealing with table Fees---------------------------------------------

    /**
     * method to get all fees in fees table
     * @return all fees
     */
    public ObservableList<Fee> getAllFees(){
        ObservableList<Fee> fees = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from fees");
            while(rs.next()){
                fees.add(new Fee(rs.getString(1),rs.getString(2),
                        rs.getDouble(3),rs.getString(4)));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fees;
    }

    /**
     * get fee by given feeId
     * @param feeId given feeId
     * @return corresponding fee
     */
    public Fee getFeeById(String feeId){
        Fee fee=null;
        try {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            PreparedStatement stmt = connection.prepareStatement("select * from fees where FeeId = ?");
            stmt.setString(1,feeId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                fee = new Fee(rs.getString(1),rs.getString(2),
                        rs.getDouble(3),rs.getString(4));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fee;
    }

}
