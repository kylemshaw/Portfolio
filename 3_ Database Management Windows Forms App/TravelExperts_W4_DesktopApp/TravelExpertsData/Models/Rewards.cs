using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

// Code scaffolded by EF Core assumes nullable reference types (NRTs) are not used or disabled.
// If you have enabled NRTs for your project, then un-comment the following line:
// #nullable disable

namespace TravelExpertsData.Models
{
    public partial class Rewards
    {
        public Rewards()
        {
            CustomersRewards = new HashSet<CustomersRewards>();
        }

        [Key]
        public int RewardId { get; set; }
        [StringLength(50)]
        public string RwdName { get; set; }
        [StringLength(50)]
        public string RwdDesc { get; set; }

        [InverseProperty("Reward")]
        public virtual ICollection<CustomersRewards> CustomersRewards { get; set; }
    }
}
