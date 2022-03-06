//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Adolphus Cox
//     Description: Data model class for any Bookings Table entries being handled by the application
//----------------------------------------------------------------------------

package com.example.travelexpertscustomerbookingview;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Locale;

public class Booking implements Serializable {
    private int bookingId;
    private Date bookingDate;
    private String bookingNo;
    private double travelerCount;
    private int customerId;
    private String tripTypeId;
    private int packageId;
    private Package pkg;
    private String feeId;
    private int agentId;

    public Booking(int bookingId, Date bookingDate, String bookingNo, double travelerCount,
                   int customerId, String tripTypeId, int packageId, String feeId, int agentId) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.bookingNo = bookingNo;
        this.travelerCount = travelerCount;
        this.customerId = customerId;
        this.tripTypeId = tripTypeId;
        this.packageId = packageId;
        this.feeId = feeId;
        this.agentId = agentId;
    }

    public Booking() {

    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public double getTravelerCount() {
        return travelerCount;
    }

    public void setTravelerCount(double travelerCount) {
        this.travelerCount = travelerCount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTripTypeId() {
        return tripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        this.tripTypeId = tripTypeId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public void setPkg(Package pkg) {
        this.pkg = pkg;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy", Locale.CANADA);
        if (pkg != null) {
            String formattedString = String.format("%-12s%-22s", bookingNo, pkg.getPkgName()) + sdf.format(bookingDate);
            return formattedString;
        } else {
            String formattedString = String.format("%-12s%-22s", bookingNo, "Package") + sdf.format(bookingDate);
            return formattedString;
        }


    }
}
