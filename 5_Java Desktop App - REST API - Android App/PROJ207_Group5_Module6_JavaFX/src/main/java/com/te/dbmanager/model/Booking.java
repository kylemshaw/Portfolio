package com.te.dbmanager.model;

import javafx.beans.property.SimpleStringProperty;

/**
 Author: Xiaoyan Deng
 Date: 2021/10/12
 Course: PROJ-207
 Workshop 6
 Booking class
 */
public class Booking {
    private SimpleStringProperty bookingId;
    private SimpleStringProperty bookingDate;
    private SimpleStringProperty bookingNo;
    private SimpleStringProperty travelerCount;
    private SimpleStringProperty customerId;
    private SimpleStringProperty tripTypeId;
    private SimpleStringProperty packageId;
    private SimpleStringProperty feeId;
    private SimpleStringProperty agentId;

    public Booking(String bookingId, String bookingDate, String bookingNo, String travelerCount, String customerId,
                   String tripTypeId, String packageId, String feeId, String agentId) {
        this.bookingId = new SimpleStringProperty(bookingId);
        this.bookingDate = new SimpleStringProperty(bookingDate);
        this.bookingNo = new SimpleStringProperty(bookingNo);
        this.travelerCount = new SimpleStringProperty(travelerCount);
        this.customerId = new SimpleStringProperty(customerId);
        this.tripTypeId = new SimpleStringProperty(tripTypeId);
        this.packageId = new SimpleStringProperty(packageId);
        this.feeId = new SimpleStringProperty(feeId);
        this.agentId = new SimpleStringProperty(agentId);
    }

    public String getBookingId() {
        return bookingId.get();
    }

    public SimpleStringProperty bookingIdProperty() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId.set(bookingId);
    }

    public String getBookingDate() {
        return bookingDate.get();
    }

    public SimpleStringProperty bookingDateProperty() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate.set(bookingDate);
    }

    public String getBookingNo() {
        return bookingNo.get();
    }

    public SimpleStringProperty bookingNoProperty() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo.set(bookingNo);
    }

    public String getTravelerCount() {
        return travelerCount.get();
    }

    public SimpleStringProperty travelerCountProperty() {
        return travelerCount;
    }

    public void setTravelerCount(String travelerCount) {
        this.travelerCount.set(travelerCount);
    }

    public String getCustomerId() {
        return customerId.get();
    }

    public SimpleStringProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId.set(customerId);
    }

    public String getTripTypeId() {
        return tripTypeId.get();
    }

    public SimpleStringProperty tripTypeIdProperty() {
        return tripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        this.tripTypeId.set(tripTypeId);
    }

    public String getPackageId() {
        return packageId.get();
    }

    public SimpleStringProperty packageIdProperty() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId.set(packageId);
    }

    public String getFeeId() {
        return feeId.get();
    }

    public SimpleStringProperty feeIdProperty() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId.set(feeId);
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
}
