//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Adolphus Cox
//     Description: Model class for any Packages Products Suppliers Table entries being
//     handled by the application
//----------------------------------------------------------------------------

package com.te.dbmanager.model;

import com.te.dbmanager.data.PackageDB;
import com.te.dbmanager.data.ProductSupplierDB;

public class PackageProductSupplier {
    private int packageId;
    private int productSupplierId;

    private String pkgName;
    private ProductSupplier productSupplier;

    public PackageProductSupplier(int packageId, int productSupplierId) {
        this.packageId = packageId;
        this.productSupplierId = productSupplierId;
        this.pkgName = PackageDB.getPackageName(packageId);
        this.productSupplier = ProductSupplierDB.getProductSupplier(productSupplierId);
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getProductSupplierId() {
        return productSupplierId;
    }

    public void setProductSupplierId(int productSupplierId) {
        this.productSupplierId = productSupplierId;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public ProductSupplier getProductSupplier() {
        return productSupplier;
    }

    public void setProductSupplier(ProductSupplier productSupplier) {
        this.productSupplier = productSupplier;
    }
}
