package com.te.dbmanager.model;

/**
 Author: Snow Tran
 Date: October 13, 2021
 Course: PROJ-207
 Workshop 6
 Supplier class
 */

public class Supplier {
    private int supplierId;
    private String supName;

    public Supplier(int supplierId, String supName) {
        this.supplierId = supplierId;
        this.supName = supName;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    @Override
    public String toString() {
        return supplierId + " | " + supName;
    }
}
