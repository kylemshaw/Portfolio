using System;
using System.Collections.Generic;

#nullable disable

namespace TravelExperts.Data.Domain
{
    public partial class PackagesProductsSupplier
    {
        public int PackageId { get; set; }
        public int ProductSupplierId { get; set; }

        public virtual Package Package { get; set; }
        public virtual ProductsSupplier ProductSupplier { get; set; }
    }
}
