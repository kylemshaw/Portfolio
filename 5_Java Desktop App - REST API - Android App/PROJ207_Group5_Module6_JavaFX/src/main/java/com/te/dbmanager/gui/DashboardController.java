//------------------------------------
// PROJ 207 Threaded Project #3
// Group: 5
// Class: OOSD May 21
// Author (s): Adolphus Cox
// Description: Controller class that handles logic for application dashboard (dashboard-view.fxml)
//
//------------------------------------

package com.te.dbmanager.gui;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.te.dbmanager.data.BookingDB;
import com.te.dbmanager.model.Agent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;
    @FXML
    private Label lblHonolulu;

    @FXML
    private Label lblParis;

    @FXML
    private Label lblNumCustomers;

    @FXML
    private Label lblNumBookings;

    @FXML
    private Label lblYtdCommission;

    private Agent agent;

    private final BookingDB bookingDB = new BookingDB();

    @FXML
    void initialize() {
        assert lblDate != null : "fx:id=\"lblDate\" was not injected: check your FXML file 'dashboard-view.fxml'.";
        assert lblTime != null : "fx:id=\"lblTime\" was not injected: check your FXML file 'dashboard-view.fxml'.";
        assert lblHonolulu != null : "fx:id=\"lblVancouver\" was not injected: check your FXML file 'dashboard-view.fxml'.";
        assert lblParis != null : "fx:id=\"lblParis\" was not injected: check your FXML file 'dashboard-view.fxml'.";
        assert lblNumCustomers != null : "fx:id=\"lblNumCustomers\" was not injected: check your FXML file 'dashboard-view.fxml'.";
        assert lblNumBookings != null : "fx:id=\"lblNumBookings\" was not injected: check your FXML file 'dashboard-view.fxml'.";
        assert lblYtdCommission != null : "fx:id=\"lblYtdCommission\" was not injected: check your FXML file 'dashboard-view.fxml'.";

        // setting up timer loop and parameters
        Timer timer = new Timer();
        int period = 1000;
        int delay = 0;

        // initial instance of date and time
        getDate();
        getTime();

        // ensures the below methods run once the agent object, that has been passed from the
        // login screen, has been captured (no longer null)
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getCustomerCount();
                getTotalBookings();
                getYTDCommission();
            }
        });

        // runs timer loop for the live clock display
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        getTime();
                    }
                });
            }
        }, delay, period);
    }

    private void getYTDCommission() {
        lblYtdCommission.setText("$" + String.valueOf(bookingDB.getTotalCommissionByAgent(agent.getAgentId())));
    }

    private void getTotalBookings() {
        lblNumBookings.setText(String.valueOf(bookingDB.getTotalBookingsByAgent(agent.getAgentId())));
    }

    private void getCustomerCount() {
        lblNumCustomers.setText(String.valueOf(bookingDB.getTotalCustomersByAgent(agent.getAgentId())));
    }

    private void getTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mm:ss a");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime honolulu = now.plusHours(-4);
        LocalDateTime paris = now.plusHours(8);
        lblTime.setText(dtf.format(now));
        lblHonolulu.setText(dtf.format(honolulu));
        lblParis.setText(dtf.format(paris));


    }

    private void getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy");
        LocalDateTime now = LocalDateTime.now();
        lblDate.setText(dtf.format(now));
    }

    public void setAgent(Agent currentUser) {
        agent = currentUser;

        getCustomerCount();
        getTotalBookings();
        getYTDCommission();
    }}
