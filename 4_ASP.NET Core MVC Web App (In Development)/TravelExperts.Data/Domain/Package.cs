using System;
using System.Collections.Generic;

#nullable disable

namespace TravelExperts.Data.Domain
{
    public partial class Package
    {
        public Package()
        {
            Bookings = new HashSet<Booking>();
            CustomersPackages = new HashSet<CustomersPackage>();
            PackagesProductsSuppliers = new HashSet<PackagesProductsSupplier>();
        }

        public int PackageId { get; set; }
        public string PkgName { get; set; }
        public DateTime? PkgStartDate { get; set; }
        public DateTime? PkgEndDate { get; set; }
        public string PkgDesc { get; set; }
        public decimal PkgBasePrice { get; set; }
        public decimal? PkgAgencyCommission { get; set; }

        public virtual ICollection<Booking> Bookings { get; set; }
        public virtual ICollection<CustomersPackage> CustomersPackages { get; set; }
        public virtual ICollection<PackagesProductsSupplier> PackagesProductsSuppliers { get; set; }
    }
}
