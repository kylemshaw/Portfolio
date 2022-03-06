package com.te.dbmanager.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * BookingReport class used in the report table view
 Author: Xiaoyan Deng
 Date: 2021/10/12
 Course: PROJ-207
 Workshop 6
 */
public class BookingReport {
    private SimpleStringProperty agentId;
    private SimpleStringProperty agentName;
    private SimpleStringProperty noOfBooking;
    private SimpleStringProperty bookingAmt;
    private SimpleStringProperty bookingPercent;

    public BookingReport(String agentId, String agentName, String noOfBooking,
                         String bookingAmt, String bookingPercent) {
        this.agentId = new SimpleStringProperty(agentId);
        this.agentName = new SimpleStringProperty(agentName);
        this.noOfBooking = new SimpleStringProperty(noOfBooking);
        this.bookingAmt = new SimpleStringProperty(bookingAmt);
        this.bookingPercent = new SimpleStringProperty(bookingPercent);
    }

    public String getAgentId() {
        return agentId.get();
    }

    public SimpleStringProperty agentIdProperty() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId.set(agentId);
    }

    public String getAgentName() {
        return agentName.get();
    }

    public SimpleStringProperty agentNameProperty() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName.set(agentName);
    }

    public String getNoOfBooking() {
        return noOfBooking.get();
    }

    public SimpleStringProperty noOfBookingProperty() {
        return noOfBooking;
    }

    public void setNoOfBooking(String noOfBooking) {
        this.noOfBooking.set(noOfBooking);
    }

    public String getBookingAmt() {
        return bookingAmt.get();
    }

    public SimpleStringProperty bookingAmtProperty() {
        return bookingAmt;
    }

    public void setBookingAmt(String bookingAmt) {
        this.bookingAmt.set(bookingAmt);
    }

    public String getBookingPercent() {
        return bookingPercent.get();
    }

    public SimpleStringProperty bookingPercentProperty() {
        return bookingPercent;
    }

    public void setBookingPercent(String bookingPercent) {
        this.bookingPercent.set(bookingPercent);
    }
}
