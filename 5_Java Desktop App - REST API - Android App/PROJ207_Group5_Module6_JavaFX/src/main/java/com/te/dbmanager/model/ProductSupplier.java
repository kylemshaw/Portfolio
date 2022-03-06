package com.te.dbmanager.model;

import com.te.dbmanager.data.ProductDB;
import com.te.dbmanager.data.SupplierDB;

/**
 Author: Snow Tran
 Date: October 13, 2021
 Course: PROJ-207
 Workshop 6
 Product-Supplier class
 */
public class ProductSupplier {
    int productSupplierId;
    int productId;
    int supplierId;

    //navigation properties
    private String prodName;
    private String supName;

    public ProductSupplier(int productSupplierId, int productId, int supplierId) {
        this.productSupplierId = productSupplierId;
        this.productId = productId;
        this.supplierId = supplierId;
        this.prodName = ProductDB.getProductName(productId);
        this.supName = SupplierDB.getSupplierName(supplierId);
    }


    public int getProductSupplierId() {
        return productSupplierId;
    }

    public void setProductSupplierId(int productSupplierId) {
        this.productSupplierId = productSupplierId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    @Override
    public String toString() {
        return productSupplierId + " | " + productId + " |" + supplierId;
    }
}
