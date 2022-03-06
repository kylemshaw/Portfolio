package com.te.dbmanager.model;

/**
 * Fee class
 Author: Xiaoyan Deng
 Date: 2021/10/12
 Course: PROJ-207
 Workshop 6
 */
public class Fee {
    private String FeeId;
    private String FeeName;
    private double FeeAmt;
    private String FeeDesc;

    public Fee(String feeId, String feeName, double feeAmt, String feeDesc) {
        FeeId = feeId;
        FeeName = feeName;
        FeeAmt = feeAmt;
        FeeDesc = feeDesc;
    }

    public String getFeeId() {
        return FeeId;
    }

    public void setFeeId(String feeId) {
        FeeId = feeId;
    }

    public String getFeeName() {
        return FeeName;
    }

    public void setFeeName(String feeName) {
        FeeName = feeName;
    }

    public double getFeeAmt() {
        return FeeAmt;
    }

    public void setFeeAmt(double feeAmt) {
        FeeAmt = feeAmt;
    }

    public String getFeeDesc() {
        return FeeDesc;
    }

    public void setFeeDesc(String feeDesc) {
        FeeDesc = feeDesc;
    }

    @Override
    public String toString() {
        String feeAmtString = String.format("%.2f", FeeAmt); //not the best rounding but works for our dataset
        return FeeId +": " + FeeName + " $" + feeAmtString;//" ($" + FeeAmt +")";
    }
}
