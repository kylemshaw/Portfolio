using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

// Code scaffolded by EF Core assumes nullable reference types (NRTs) are not used or disabled.
// If you have enabled NRTs for your project, then un-comment the following line:
// #nullable disable

namespace TravelExpertsData.Models
{
    public partial class Customers
    {
        public Customers()
        {
            Bookings = new HashSet<Bookings>();
            CreditCards = new HashSet<CreditCards>();
            CustomersRewards = new HashSet<CustomersRewards>();
        }

        [Key]
        public int CustomerId { get; set; }
        [Required]
        [StringLength(25)]
        public string CustFirstName { get; set; }
        [Required]
        [StringLength(25)]
        public string CustLastName { get; set; }
        [Required]
        [StringLength(75)]
        public string CustAddress { get; set; }
        [Required]
        [StringLength(50)]
        public string CustCity { get; set; }
        [Required]
        [StringLength(2)]
        public string CustProv { get; set; }
        [Required]
        [StringLength(7)]
        public string CustPostal { get; set; }
        [StringLength(25)]
        public string CustCountry { get; set; }
        [StringLength(20)]
        public string CustHomePhone { get; set; }
        [Required]
        [StringLength(20)]
        public string CustBusPhone { get; set; }
        [Required]
        [StringLength(50)]
        public string CustEmail { get; set; }
        public int? AgentId { get; set; }

        [ForeignKey(nameof(AgentId))]
        [InverseProperty(nameof(Agents.Customers))]
        public virtual Agents Agent { get; set; }
        [InverseProperty("Customer")]
        public virtual ICollection<Bookings> Bookings { get; set; }
        [InverseProperty("Customer")]
        public virtual ICollection<CreditCards> CreditCards { get; set; }
        [InverseProperty("Customer")]
        public virtual ICollection<CustomersRewards> CustomersRewards { get; set; }
    }
}
