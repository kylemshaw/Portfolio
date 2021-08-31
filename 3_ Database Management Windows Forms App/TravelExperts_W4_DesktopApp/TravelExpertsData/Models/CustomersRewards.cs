using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

// Code scaffolded by EF Core assumes nullable reference types (NRTs) are not used or disabled.
// If you have enabled NRTs for your project, then un-comment the following line:
// #nullable disable

namespace TravelExpertsData.Models
{
    [Table("Customers_Rewards")]
    public partial class CustomersRewards
    {
        [Key]
        public int CustomerId { get; set; }
        [Key]
        public int RewardId { get; set; }
        [Required]
        [StringLength(25)]
        public string RwdNumber { get; set; }

        [ForeignKey(nameof(CustomerId))]
        [InverseProperty(nameof(Customers.CustomersRewards))]
        public virtual Customers Customer { get; set; }
        [ForeignKey(nameof(RewardId))]
        [InverseProperty(nameof(Rewards.CustomersRewards))]
        public virtual Rewards Reward { get; set; }
    }
}
