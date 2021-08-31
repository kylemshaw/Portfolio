using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

// Code scaffolded by EF Core assumes nullable reference types (NRTs) are not used or disabled.
// If you have enabled NRTs for your project, then un-comment the following line:
// #nullable disable

namespace TravelExpertsData.Models
{
    public partial class Suppliers
    {
        public Suppliers()
        {
            ProductsSuppliers = new HashSet<ProductsSuppliers>();
            SupplierContacts = new HashSet<SupplierContacts>();
        }

        [Key]
        public int SupplierId { get; set; }
        [StringLength(255)]
        public string SupName { get; set; }

        [InverseProperty("Supplier")]
        public virtual ICollection<ProductsSuppliers> ProductsSuppliers { get; set; }
        [InverseProperty("Supplier")]
        public virtual ICollection<SupplierContacts> SupplierContacts { get; set; }
    }
}
