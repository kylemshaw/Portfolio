//------------------------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Kyle Shaw
//     Description:
//     Provides asynchronous methods to access database info from Travel Experts REST API.
//     While calling these methods we don't know how long it will take to get a response
//     from the API url and we don't want to tie up the main UI thread. So
//     in each method we send a request to volley then continue main thread execution.
//     Whenever we get the response back, the callback function defined in each method
//     listener interface is called. An interface is used for the listener to allow us to
//     override and customize the callback function wherever we call the original method.
//------------------------------------------------------------------------------------------

package com.example.travelexpertscustomerbookingview;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TravelExpertsDataService {

    public static final String QUERY_FOR_BOOKINGS_BY_CUSTOMER = "http://10.0.2.2:8080/TravelExpertsREST-1.0-SNAPSHOT/api/booking/getbookingsbycustomer/";
    public static final String QUERY_FOR_CUSTOMER = "http://10.0.2.2:8080/TravelExpertsREST-1.0-SNAPSHOT/api/customer/getcustomer/";
    public static final String QUERY_FOR_ALL_CUSTOMERS = "http://10.0.2.2:8080/TravelExpertsREST-1.0-SNAPSHOT/api/customer/getcustomers/";
    public static final String QUERY_FOR_UPDATE_CUSTOMER = "http://10.0.2.2:8080/TravelExpertsREST-1.0-SNAPSHOT/api/customer/postcustomer";
    public static final String QUERY_FOR_BOOKING = "http://10.0.2.2:8080/TravelExpertsREST-1.0-SNAPSHOT/api/booking/getbooking/";
    public static final String QUERY_FOR_PACKAGE = "http://10.0.2.2:8080/TravelExpertsREST-1.0-SNAPSHOT/api/package/getpackage/";
    public static final String QUERY_FOR_AGENT = "http://10.0.2.2:8080/TravelExpertsREST-1.0-SNAPSHOT/api/agent/getagent/";
    public static final String QUERY_FOR_AGENCY = "http://10.0.2.2:8080/TravelExpertsREST-1.0-SNAPSHOT/api/agency/getagency/";
    public static final String JSON_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    Context context;
    Customer customer;
    ArrayList<Customer> customerList;
    ArrayList<Booking> customerBookings;
    Booking booking;
    Package pack;
    Agent agent;
    Agency agency;

    //constructor
    public TravelExpertsDataService(Context context) {
        this.context = context;
    }

    //*****************************************************************
    // GET BOOKING
    //*****************************************************************
    //interface for callback functions
    public interface GetBookingListener {
        void onError(String message);
        void onResponse(Booking booking);
    }

    public void getBooking(int bookingId, GetBookingListener getBookingListener){
        //create volley request object
        String url = QUERY_FOR_BOOKING + bookingId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                booking = new Booking();
                try {
                    //parse JSON response from api into booking object
                    booking.setBookingId(response.getInt("bookingId"));
                    booking.setBookingDate(stringToDate(response.getString("bookingDate"), JSON_DATE_FORMAT));
                    booking.setBookingNo(response.getString("bookingNo"));
                    booking.setTravelerCount(response.getDouble("travelerCount"));
                    booking.setCustomerId(response.getInt("customerId"));
                    booking.setTripTypeId(response.getString("tripTypeId"));
                    booking.setPackageId(response.getInt("packageId"));
                    booking.setFeeId(response.getString("feeId"));
                    booking.setAgentId(response.getInt("agentId"));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                getBookingListener.onResponse(booking);//send to onResponse callback function
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getBookingListener.onError("Booking load failed."); //send error message to callback
            }
        });

        //create request queue and add the above request to it. Once added volley will send out request to url
        //request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    //*****************************************************************
    // GET BOOKING BY CUSTOMER
    //*****************************************************************
    public interface GetBookingsByCustomerListener {
        void onError(String message);
        void onResponse(ArrayList<Booking> custBookings);
    }

    public void getBookingsByCustomer(int customerId, GetBookingsByCustomerListener getBookingsByCustomerListener){
        String url = QUERY_FOR_BOOKINGS_BY_CUSTOMER + customerId;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                customerBookings = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Booking b = new Booking();
                        b.setBookingId(jsonObject.getInt("BookingId"));
                        b.setBookingDate(stringToDate(jsonObject.getString("BookingDate"), JSON_DATE_FORMAT));
                        b.setBookingNo(jsonObject.getString("BookingNo"));
                        b.setTravelerCount(jsonObject.getDouble("TravelerCount"));
                        b.setCustomerId(jsonObject.getInt("CustomerId"));
                        b.setTripTypeId(jsonObject.getString("TripTypeId"));
                        b.setPackageId(jsonObject.getInt("PackageId"));
                        b.setFeeId(jsonObject.getString("FeeId"));
                        b.setAgentId(jsonObject.getInt("AgentId"));
                        customerBookings.add(b);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                getBookingsByCustomerListener.onResponse(customerBookings);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getBookingsByCustomerListener.onError("booking load failed.");
            }
        });

        //request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    //*****************************************************************
    // GET All Customers
    //*****************************************************************
    public interface GetAllCustomersListener {
        void onError(String message);
        void onResponse(ArrayList<Customer> custList);
    }

    public void getAllCustomers(GetAllCustomersListener getAllCustomersListener){
        String url = QUERY_FOR_ALL_CUSTOMERS;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                customerList = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Customer c = new Customer();
                        c.setCustomerId(jsonObject.getInt("CustomerId"));
                        c.setCustFirstName(jsonObject.getString("CustFirstName"));
                        c.setCustLastName(jsonObject.getString("CustLastName"));
                        c.setCustAddress(jsonObject.getString("CustAddress"));
                        c.setCustCity(jsonObject.getString("CustCity"));
                        c.setCustProv(jsonObject.getString("CustProv"));
                        c.setCustPostal(jsonObject.getString("CustPostal"));
                        c.setCustCountry(jsonObject.getString("CustCountry"));
                        c.setCustHomePhone(jsonObject.getString("CustHomePhone"));
                        c.setCustBusPhone(jsonObject.getString("CustBusPhone"));
                        c.setCustEmail(jsonObject.getString("CustEmail"));
                        c.setCustPassword(jsonObject.getString("CustPassword"));
                        c.setAgentId(jsonObject.getInt("AgentId"));
                        customerList.add(c);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                getAllCustomersListener.onResponse(customerList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getAllCustomersListener.onError("Customer load failed.");
            }
        });

        //request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    //*****************************************************************
    // GET Customer
    //*****************************************************************
    public interface GetCustomerListener {
        void onError(String message);
        void onResponse(Customer customer);
    }

    public void getCustomer(int custId, GetCustomerListener getCustomerListener){
        String url = QUERY_FOR_CUSTOMER + custId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                customer = new Customer();
                try {
                    customer.setCustomerId(response.getInt("CustomerId"));
                    customer.setCustFirstName(response.getString("CustFirstName"));
                    customer.setCustLastName(response.getString("CustLastName"));
                    customer.setCustAddress(response.getString("CustLastName"));
                    customer.setCustCity(response.getString("CustCity"));
                    customer.setCustProv(response.getString("CustProv"));
                    customer.setCustPostal(response.getString("CustPostal"));
                    customer.setCustCountry(response.getString("CustCountry"));
                    customer.setCustHomePhone(response.getString("CustHomePhone"));
                    customer.setCustBusPhone(response.getString("CustBusPhone"));
                    customer.setCustEmail(response.getString("CustEmail"));
                    customer.setCustPassword(response.getString("CustPassword"));
                    customer.setAgentId(response.getInt("agentId"));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                getCustomerListener.onResponse(customer);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getCustomerListener.onError("Customer load failed.");
            }
        });

        //request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    //*****************************************************************
    // UPDATE Customer
    //*****************************************************************
    public interface GetUpdateCustomerListener {
        void onError(String message);
        void onResponse(String message);
    }

    public void updateCustomer(Customer customer, GetUpdateCustomerListener getUpdateCustomerListener){
        String url = QUERY_FOR_UPDATE_CUSTOMER;
        JSONObject customerUpdate = new JSONObject();
        try {
            customerUpdate.put("CustomerId", customer.getCustomerId());
            customerUpdate.put("CustFirstName", customer.getCustFirstName());
            customerUpdate.put("CustLastName", customer.getCustLastName());
            customerUpdate.put("CustAddress", customer.getCustAddress());
            customerUpdate.put("CustCity", customer.getCustCity());
            customerUpdate.put("CustProv", customer.getCustProv());
            customerUpdate.put("CustPostal", customer.getCustPostal());
            customerUpdate.put("CustCountry", customer.getCustCountry());
            customerUpdate.put("CustHomePhone", customer.getCustHomePhone());
            customerUpdate.put("CustBusPhone", customer.getCustBusPhone());
            customerUpdate.put("CustEmail", customer.getCustEmail());
            customerUpdate.put("CustPassword", customer.getCustPassword());
            customerUpdate.put("AgentId", customer.getAgentId());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, customerUpdate, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String message = null;
                try {
                    message = response.get("message").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getUpdateCustomerListener.onResponse(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getUpdateCustomerListener.onError("Customer update failed.");
                Log.d("kyle", "Get Customer Update Error: " + error);
            }
        });

        //request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }


    //*****************************************************************
    // GET PACKAGE
    //*****************************************************************
    public interface GetPackageListener {
        void onError(String message);
        void onResponse(Package pack);
    }

    public void getPackage(int packId, GetPackageListener getPackageListener){
        String url = QUERY_FOR_PACKAGE + packId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pack = new Package();
                try {
                    pack.setPackageId(response.getInt("PackageId"));
                    pack.setPkgName(response.getString("PkgName"));
                    pack.setPkgStartDate(stringToDate(response.getString("PkgStartDate"), JSON_DATE_FORMAT));
                    pack.setPkgEndDate(stringToDate(response.getString("PkgEndDate"), JSON_DATE_FORMAT));
                    pack.setPkgDesc(response.getString("PkgDesc"));
                    pack.setPkgBasePrice(response.getDouble("PkgBasePrice"));
                    pack.setPkgAgencyCommission(response.getDouble("PkgAgencyCommission"));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                getPackageListener.onResponse(pack);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getPackageListener.onError("Package load failed.");
            }
        });

        //request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    //*****************************************************************
    // GET AGENT
    //*****************************************************************
    public interface GetAgentListener {
        void onError(String message);
        void onResponse(Agent agent);
    }

    public void getAgent(int agentId, GetAgentListener getAgentListener){
        String url = QUERY_FOR_AGENT + agentId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                agent = new Agent();
                try {
                    agent.setAgentId(response.getInt("AgentId"));
                    agent.setAgtFirstName(response.getString("AgtFirstName"));
                    agent.setAgtMiddleInitial(response.getString("AgtMiddleInitial"));
                    agent.setAgtLastName(response.getString("AgtLastName"));
                    agent.setAgtBusPhone(response.getString("AgtBusPhone"));
                    agent.setAgtEmail(response.getString("AgtEmail"));
                    agent.setAgtPosition(response.getString("AgtPosition"));
                    agent.setAgencyId(response.getInt("AgencyId"));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                getAgentListener.onResponse(agent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getAgentListener.onError("Agent load failed.");
            }
        });

        //request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    //*****************************************************************
    // GET AGENCY
    //*****************************************************************
    public interface GetAgencyListener {
        void onError(String message);
        void onResponse(Agency agency);
    }

    public void getAgency(int agencyId, GetAgencyListener getAgencyListener){
        String url = QUERY_FOR_AGENCY + agencyId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                agency = new Agency();
                try {
                    agency.setAgencyId(response.getInt("AgencyId"));
                    agency.setAgencyAddress(response.getString("AgncyAddress"));
                    agency.setAgencyCity(response.getString("AgncyCity"));
                    agency.setAgencyProv(response.getString("AgncyProv"));
                    agency.setAgencyPostal(response.getString("AgncyPostal"));
                    agency.setAgencyCountry(response.getString("AgncyCountry"));
                    agency.setAgencyPhone(response.getString("AgncyPhone"));
                    agency.setAgencyFax(response.getString("AgncyFax"));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                getAgencyListener.onResponse(agency);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getAgencyListener.onError("Agency load failed.");
            }
        });

        //request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    //*****************************************************************
    // HELPER METHODS
    //*****************************************************************

    // ------
    //  stringToDate - convert string date received in json into Date object
    //      json date format: "yyyy-MM-dd HH:mm:ss"
    //
    //  util to sql conversion
    //      http://www.java2s.com/Tutorial/Java/0040__Data-Type/ConvertfromajavautilDateObjecttoajavasqlDateObject.htm
    //
    //  TODO: StackOverflow - Use java.time instead? does this work with our version of JDBC
    //      https://stackoverflow.com/questions/530012/how-to-convert-java-util-date-to-java-sql-date
    // -----
    private Date stringToDate(String dateString, String dateFormat){
        java.util.Date utilDate = null;
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        if(dateString.equals(""))
            return null;

        try {
            utilDate = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date(utilDate.getTime()); //convert from util to sql date
    }
}
