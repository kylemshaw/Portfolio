using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

// Code scaffolded by EF Core assumes nullable reference types (NRTs) are not used or disabled.
// If you have enabled NRTs for your project, then un-comment the following line:
// #nullable disable

namespace TravelExpertsData.Models
{
    public partial class Packages
    {
        public Packages()
        {
            Bookings = new HashSet<Bookings>();
            PackagesProductsSuppliers = new HashSet<PackagesProductsSuppliers>();
        }

        [Key]
        public int PackageId { get; set; }
        [Required]
        [StringLength(50)]
        public string PkgName { get; set; }
        [Column(TypeName = "datetime")]
        public DateTime? PkgStartDate { get; set; }
        [Column(TypeName = "datetime")]
        public DateTime? PkgEndDate { get; set; }
        [StringLength(50)]
        public string PkgDesc { get; set; }
        [Column(TypeName = "money")]
        public decimal PkgBasePrice { get; set; }
        [Column(TypeName = "money")]
        public decimal? PkgAgencyCommission { get; set; }

        [InverseProperty("Package")]
        public virtual ICollection<Bookings> Bookings { get; set; }
        [InverseProperty("Package")]
        public virtual ICollection<PackagesProductsSuppliers> PackagesProductsSuppliers { get; set; }
    }
}
