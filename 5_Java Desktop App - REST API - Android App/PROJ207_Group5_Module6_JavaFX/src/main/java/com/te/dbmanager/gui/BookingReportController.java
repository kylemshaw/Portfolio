/**
 * Sample Skeleton for 'booking-report-view.fxml' Controller Class
 Author: Xiaoyan Deng
 Date: 2021/10/12
 Course: PROJ-207
 Workshop 6
 */
package com.te.dbmanager.gui;

import com.te.dbmanager.data.AgentDB;
import com.te.dbmanager.data.BookingDB;
import com.te.dbmanager.model.Booking;
import com.te.dbmanager.model.BookingReport;
import com.te.dbmanager.model.Fee;
import com.te.dbmanager.model.Package;
import com.te.dbmanager.model.Agent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class BookingReportController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cbFromYear"
    private ComboBox<String> cbFromYear; // Value injected by FXMLLoader

    @FXML // fx:id="cbFromMonth"
    private ComboBox<String> cbFromMonth; // Value injected by FXMLLoader

    @FXML // fx:id="cbToYear"
    private ComboBox<String> cbToYear; // Value injected by FXMLLoader

    @FXML // fx:id="cbToMonth"
    private ComboBox<String> cbToMonth; // Value injected by FXMLLoader

    @FXML // fx:id="btnGetReport"
    private Button btnGetReport; // Value injected by FXMLLoader

    @FXML // fx:id="lbTotalBookingNo"
    private Label lbTotalBookingNo; // Value injected by FXMLLoader

    @FXML // fx:id="lbCustBookingNo"
    private Label lbCustBookingNo; // Value injected by FXMLLoader

    @FXML // fx:id="lbAgtBookingNo"
    private Label lbAgtBookingNo; // Value injected by FXMLLoader

    @FXML // fx:id="lbTotalBookingAmt"
    private Label lbTotalBookingAmt; // Value injected by FXMLLoader

    @FXML // fx:id="lbCustBookingAmt"
    private Label lbCustBookingAmt; // Value injected by FXMLLoader

    @FXML // fx:id="lbAgtBookingAmt"
    private Label lbAgtBookingAmt; // Value injected by FXMLLoader

    @FXML // fx:id="lbTotalBookingPercent"
    private Label lbTotalBookingPercent; // Value injected by FXMLLoader

    @FXML // fx:id="lbCustBookingPercent"
    private Label lbCustBookingPercent; // Value injected by FXMLLoader

    @FXML // fx:id="lbAgtBookingPercent"
    private Label lbAgtBookingPercent; // Value injected by FXMLLoader

    @FXML // fx:id="tbAgentBookingDetail"
    private TableView<BookingReport> tbAgentBookingDetail; // Value injected by FXMLLoader

    @FXML // fx:id="colAgentId"
    private TableColumn<BookingReport, String> colAgentId; // Value injected by FXMLLoader

    @FXML // fx:id="colAgentName"
    private TableColumn<BookingReport, String> colAgentName; // Value injected by FXMLLoader

    @FXML // fx:id="colBookingNo"
    private TableColumn<BookingReport, String> colBookingNo; // Value injected by FXMLLoader

    @FXML // fx:id="colBookingAmt"
    private TableColumn<BookingReport, String> colBookingAmt; // Value injected by FXMLLoader

    @FXML // fx:id="colBookingAmtPercent"
    private TableColumn<BookingReport, String> colBookingAmtPercent; // Value injected by FXMLLoader

    private ObservableList<String> mon = FXCollections.observableArrayList();
    private ObservableList<String> year = FXCollections.observableArrayList();
    private BookingDB bookingDB = new BookingDB();
    private ObservableList<Booking> bookings;

    /**
     * handle event when Get Report Button is clicked
     * @param event
     */
    @FXML
    void onClickGetReport(MouseEvent event) {
        List<String> months = new ArrayList<>();
        String fromMonth = cbFromYear.getValue()+"-"+cbFromMonth.getValue();
        String toMonth = cbToYear.getValue()+"-"+cbToMonth.getValue();

        if(fromMonth.equals(toMonth)){                             // if dateFrom equals dateTo
            months.add(fromMonth);
            getDataShown(months);
        }else{                                                      // if dateFrom not equals dateTo
            String from = fromMonth+"-01";
            String to = toMonth+"-01";
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar date1 = Calendar.getInstance();
                date1.setTime(dateFormat.parse(from));
                Calendar date2 = Calendar.getInstance();
                date2.setTime(dateFormat.parse(to));
                Calendar date = Calendar.getInstance();

                if(date2.compareTo(date1) <0){                      // flip value if dateTo is smaller than dateFrom
                    date= date1;
                    date1 = date2;
                    date2 = date;
                }
                //add all months into months list
                date = date1;
                date2.add(Calendar.HOUR,1);
                while (date2.compareTo(date)>0){
                    String month = dateFormat.format(date.getTime()).substring(0,7);
                    months.add(month);
                    date.add(Calendar.MONTH,1);
                }
                getDataShown(months);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Do statistic on given list of bookings
     * @param lists given list of bookings
     * @return a list includes total number of bookings and total booking amount
     */
    private List<String> monthlyStatistic(ObservableList<Booking> lists){
        ObservableList<Booking> bookings = lists;
        List<String> output = new ArrayList<>();
        Booking b;
        Package p;
        Fee f;
        int bookingCount = bookings.size();
        double bookingAmt = 0;
        for (int j = 0; j < bookings.size(); j++) {
            b = bookings.get(j);
            p = bookingDB.getPackageById(Integer.parseInt(b.getPackageId()));
            f = bookingDB.getFeeById(b.getFeeId());
            double amount = (p.getPkgBasePrice()+p.getPkgAgencyCommission()+f.getFeeAmt())*Double.parseDouble(b.getTravelerCount());
            bookingAmt+=amount;
        }
        output.add(Integer.toString(bookingCount));
        output.add(Double.toString(bookingAmt));
        return output;
    }


    /**
     * get all information needed for the report and show, based on given time range
     * @param months given time range
     */
    private void getDataShown(List<String> months) {
        // get information of bookings by customers themselves without agents
        bookings = null;
        bookings = bookingDB.getCustBookingsByMonths(months);
        int custBookingCount=0;
        double custBookingAmt=0;
        if(bookings!=null){
            List<String> output = monthlyStatistic(bookings);
            custBookingCount =Integer.parseInt(output.get(0));
            custBookingAmt = Double.parseDouble(output.get(1));
        }

        //get information of bookings with agents
        ObservableList<BookingReport> data = FXCollections.observableArrayList();
        ObservableList<Agent> agents = null;
        try {
            agents = AgentDB.getAllAgents();
        } catch (SQLException e) {
            e.printStackTrace();
            DisplayAlert.showWarning("Database error when retrieving agent list from database.");
        }

        //get bookingCount and bookingAmount for each agent and create corresponding BookingReport Object
        int totalAgtBookingCount = 0;
        double totalAgtBookingAmt = 0;
        for (int i = 0; i < agents.size(); i++) {
            Agent a = agents.get(i);
            bookings = bookingDB.getBookingsByAgentIdAndMonths(a.getAgentId(), months);
            String agentName = a.getAgtFirstName() + " " + a.getAgtLastName();
            int agentBookingCount = 0;
            double agentBookingAmt = 0;

            if (bookings != null) {
                List<String> output = monthlyStatistic(bookings);
                agentBookingCount = Integer.parseInt(output.get(0));
                agentBookingAmt = Double.parseDouble(output.get(1));
            }
            totalAgtBookingAmt += agentBookingAmt;
            totalAgtBookingCount += agentBookingCount;

            //create a BookingReport Object
            BookingReport bookingReport = new BookingReport(Integer.toString(a.getAgentId()), agentName,
                    Double.toString(agentBookingCount), Double.toString(agentBookingAmt), "-");
            data.add(bookingReport);
        }

        // calculate total Booking Count and Amount for all
        int totalBookingCount = totalAgtBookingCount + custBookingCount;
        double totalBookingAmt = totalAgtBookingAmt + custBookingAmt;

        //calculate Percentage for customers and agents
        double agtPercent = 0;
        double custPercent = 0;
        double totalPercent = 0;
        if(totalBookingAmt!=0){
            agtPercent = totalAgtBookingAmt / totalBookingAmt;
            custPercent = custBookingAmt / totalBookingAmt;
            totalPercent = 1;
        }

        //update bookingPercent for each agent if total booking amount is larger than zero
        DecimalFormat intFormat = new DecimalFormat("0");
        DecimalFormat currencyFormat = new DecimalFormat("$###,###.##");
        DecimalFormat percentFormat = new DecimalFormat("0.0%");
        if(totalBookingAmt >0){
            for (int i = 0; i < data.size(); i++) {
                BookingReport br = data.get(i);
                br.setBookingPercent(percentFormat.format(Double.parseDouble(br.getBookingAmt())/totalBookingAmt));
                br.setBookingAmt(currencyFormat.format(Double.parseDouble(br.getBookingAmt())));
                br.setNoOfBooking(intFormat.format(Double.parseDouble(br.getNoOfBooking())));
            }
        }

        //----------show data-----------------
        lbTotalBookingNo.setText(intFormat.format(totalBookingCount));
        lbAgtBookingNo.setText(intFormat.format(totalAgtBookingCount));
        lbCustBookingNo.setText(intFormat.format(custBookingCount));
        lbTotalBookingAmt.setText(currencyFormat.format(totalBookingAmt));
        lbAgtBookingAmt.setText(currencyFormat.format(totalAgtBookingAmt));
        lbCustBookingAmt.setText(currencyFormat.format(custBookingAmt));
        lbTotalBookingPercent.setText(percentFormat.format(totalPercent));
        lbAgtBookingPercent.setText(percentFormat.format(agtPercent));
        lbCustBookingPercent.setText(percentFormat.format(custPercent));
        colAgentId.setCellValueFactory(new PropertyValueFactory<BookingReport, String>("agentId"));
        colAgentName.setCellValueFactory(new PropertyValueFactory<BookingReport, String>("agentName"));
        colBookingNo.setCellValueFactory(new PropertyValueFactory<BookingReport, String>("noOfBooking"));
        colBookingAmt.setCellValueFactory(new PropertyValueFactory<BookingReport, String>("bookingAmt"));
        colBookingAmtPercent.setCellValueFactory(new PropertyValueFactory<BookingReport, String>("bookingPercent"));
        tbAgentBookingDetail.setItems(data); // show data in the table
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cbFromYear != null : "fx:id=\"cbFromYear\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert cbFromMonth != null : "fx:id=\"cbFromMonth\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert cbToYear != null : "fx:id=\"cbToYear\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert cbToMonth != null : "fx:id=\"cbToMonth\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert btnGetReport != null : "fx:id=\"btnGetReport\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert lbTotalBookingNo != null : "fx:id=\"lbTotalBookingNo\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert lbCustBookingNo != null : "fx:id=\"lbCustBookingNo\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert lbAgtBookingNo != null : "fx:id=\"lbAgtBookingNo\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert lbTotalBookingAmt != null : "fx:id=\"lbTotalBookingAmt\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert lbCustBookingAmt != null : "fx:id=\"lbCustBookingAmt\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert lbAgtBookingAmt != null : "fx:id=\"lbAgtBookingAmt\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert lbTotalBookingPercent != null : "fx:id=\"lbTotalBookingPercent\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert lbCustBookingPercent != null : "fx:id=\"lbCustBookingPercent\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert lbAgtBookingPercent != null : "fx:id=\"lbAgtBookingPercent\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert tbAgentBookingDetail != null : "fx:id=\"tbAgentBookingDetail\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert colAgentId != null : "fx:id=\"colAgentId\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert colAgentName != null : "fx:id=\"colAgentName\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert colBookingNo != null : "fx:id=\"colBookingNo\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert colBookingAmt != null : "fx:id=\"colBookingAmt\" was not injected: check your FXML file 'booking-report-view.fxml'.";
        assert colBookingAmtPercent != null : "fx:id=\"colBookingAmtPercent\" was not injected: check your FXML file 'booking-report-view.fxml'.";

        setUp();
    }

    /**
     * set up combobox of year and month for from and to
     */
    private void setUp() {
        for (int i = 1; i < 13; i++) {
            String mth;
            if(i<10){
                mth = "0" + i;
            }else{
                mth = String.valueOf(i);
            }
            mon.add(mth);
        }
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        for (int i = 1999; i <= currentYear; i++) {
            year.add(i+"");
        }
        cbFromMonth.setItems(mon);
        cbToMonth.setItems(mon);
        cbFromYear.setItems(year);
        cbToYear.setItems(year);
        cbFromMonth.setValue("01");
        cbToMonth.setValue("01");
        cbFromYear.setValue("1999");
        cbToYear.setValue("1999");
    }

}
