package com.te.dbmanager.model;

/**
 Author: Snow Tran
 Date: October 13, 2021
 Course: PROJ-207
 Workshop 6
 Product class
 */


public class Product {
    private int productId;
    private String prodName;

    private boolean isSupplied;

    public Product(int productId, String prodName) {
        this.productId = productId;
        this.prodName = prodName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Boolean isSupplied() {
        return isSupplied;
    }

    public void setSupplied(Boolean supplied) {
        isSupplied = supplied;
    }

    @Override
    public String toString() {
        return productId + " | " + prodName;
    }

//    public class ProductIsSuppliedValueFactory implements Callback<TableColumn.CellDataFeatures<Product, CheckBox>, ObservableValue<CheckBox>>{
//        @Override
//        public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<Product, CheckBox> param) {
//            Product product = param.getValue();
//            CheckBox checkBox = new CheckBox();
//            checkBox.selectedProperty().setValue(product.isSupplied());
//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//                product.setSupplied(new_val);
//            });
//            return new SimpleObjectProperty<>(checkBox);
//        }
//    }
}
