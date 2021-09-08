using System;
using System.Collections.Generic;

#nullable disable

namespace TravelExperts.Data.Domain
{
    public partial class CustomersReward
    {
        public int CustomerId { get; set; }
        public int RewardId { get; set; }
        public string RwdNumber { get; set; }

        public virtual Customer Customer { get; set; }
        public virtual Reward Reward { get; set; }
    }
}
