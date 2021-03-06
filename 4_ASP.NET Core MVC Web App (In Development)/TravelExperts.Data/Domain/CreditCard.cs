using System;
using System.Collections.Generic;

#nullable disable

namespace TravelExperts.Data.Domain
{
    public partial class CreditCard
    {
        public int CreditCardId { get; set; }
        public string Ccname { get; set; }
        public string Ccnumber { get; set; }
        public DateTime Ccexpiry { get; set; }
        public int CustomerId { get; set; }

        public virtual Customer Customer { get; set; }
    }
}
