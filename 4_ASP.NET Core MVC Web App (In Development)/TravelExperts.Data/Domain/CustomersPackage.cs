using System;
using System.Collections.Generic;

#nullable disable

namespace TravelExperts.Data.Domain
{
    public partial class CustomersPackage
    {
        public int CustomersPackageId { get; set; }
        public int CustomerId { get; set; }
        public int PackageId { get; set; }

        public virtual Customer Customer { get; set; }
        public virtual Package Package { get; set; }
    }
}
