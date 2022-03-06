package com.te.dbmanager.model;

/**
 * TripType class
 Author: Xiaoyan Deng
 Date: 2021/10/12
 Course: PROJ-207
 Workshop 6
 */
public class TripType {
    private String tripTypeId;
    private String tTName;

    public TripType(String tripTypeId, String tTName) {
        this.tripTypeId = tripTypeId;
        this.tTName = tTName;
    }

    public String getTripTypeId() {
        return tripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        this.tripTypeId = tripTypeId;
    }

    public String gettTName() {
        return tTName;
    }

    public void settTName(String tTName) {
        this.tTName = tTName;
    }

    @Override
    public String toString() {
        return tripTypeId + ": " + tTName;
    }
}
