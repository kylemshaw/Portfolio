using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

// Code scaffolded by EF Core assumes nullable reference types (NRTs) are not used or disabled.
// If you have enabled NRTs for your project, then un-comment the following line:
// #nullable disable

namespace TravelExpertsData.Models
{
    [Table("Products_Suppliers")]
    public partial class ProductsSuppliers
    {
        public ProductsSuppliers()
        {
            BookingDetails = new HashSet<BookingDetails>();
            PackagesProductsSuppliers = new HashSet<PackagesProductsSuppliers>();
        }

        [Key]
        public int ProductSupplierId { get; set; }
        public int? ProductId { get; set; }
        public int? SupplierId { get; set; }

        [ForeignKey(nameof(ProductId))]
        [InverseProperty(nameof(Products.ProductsSuppliers))]
        public virtual Products Product { get; set; }
        [ForeignKey(nameof(SupplierId))]
        [InverseProperty(nameof(Suppliers.ProductsSuppliers))]
        public virtual Suppliers Supplier { get; set; }
        [InverseProperty("ProductSupplier")]
        public virtual ICollection<BookingDetails> BookingDetails { get; set; }
        [InverseProperty("ProductSupplier")]
        public virtual ICollection<PackagesProductsSuppliers> PackagesProductsSuppliers { get; set; }
    }
}
