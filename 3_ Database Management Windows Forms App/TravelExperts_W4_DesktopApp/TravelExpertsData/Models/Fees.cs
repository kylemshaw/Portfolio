using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

// Code scaffolded by EF Core assumes nullable reference types (NRTs) are not used or disabled.
// If you have enabled NRTs for your project, then un-comment the following line:
// #nullable disable

namespace TravelExpertsData.Models
{
    public partial class Fees
    {
        public Fees()
        {
            BookingDetails = new HashSet<BookingDetails>();
        }

        [Key]
        [StringLength(10)]
        public string FeeId { get; set; }
        [Required]
        [StringLength(50)]
        public string FeeName { get; set; }
        [Column(TypeName = "money")]
        public decimal FeeAmt { get; set; }
        [StringLength(50)]
        public string FeeDesc { get; set; }

        [InverseProperty("Fee")]
        public virtual ICollection<BookingDetails> BookingDetails { get; set; }
    }
}
