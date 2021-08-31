using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

// Code scaffolded by EF Core assumes nullable reference types (NRTs) are not used or disabled.
// If you have enabled NRTs for your project, then un-comment the following line:
// #nullable disable

namespace TravelExpertsData.Models
{
    [Table("Packages_Products_Suppliers")]
    public partial class PackagesProductsSuppliers
    {
        [Key]
        public int PackageId { get; set; }
        [Key]
        public int ProductSupplierId { get; set; }

        [ForeignKey(nameof(PackageId))]
        [InverseProperty(nameof(Packages.PackagesProductsSuppliers))]
        public virtual Packages Package { get; set; }
        [ForeignKey(nameof(ProductSupplierId))]
        [InverseProperty(nameof(ProductsSuppliers.PackagesProductsSuppliers))]
        public virtual ProductsSuppliers ProductSupplier { get; set; }
    }
}
