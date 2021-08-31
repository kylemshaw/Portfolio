using System;
using System.Collections.Generic;
using System.Text;

namespace TravelExpertsData.Models
{
    public partial class PackagesProductsSuppliers
    {
        public PackagesProductsSuppliers(int packageId, int productSupplierId)
        {
            PackageId = packageId;
            ProductSupplierId = productSupplierId;
        }
    }
}
