using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

// Code scaffolded by EF Core assumes nullable reference types (NRTs) are not used or disabled.
// If you have enabled NRTs for your project, then un-comment the following line:
// #nullable disable

namespace TravelExpertsData.Models
{
    public partial class Products
    {
        public Products()
        {
            ProductsSuppliers = new HashSet<ProductsSuppliers>();
        }

        [Key]
        public int ProductId { get; set; }
        [Required]
        [StringLength(50)]
        public string ProdName { get; set; }

        [InverseProperty("Product")]
        public virtual ICollection<ProductsSuppliers> ProductsSuppliers { get; set; }
    }
}
