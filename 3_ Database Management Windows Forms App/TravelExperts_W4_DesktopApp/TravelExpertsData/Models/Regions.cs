using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

// Code scaffolded by EF Core assumes nullable reference types (NRTs) are not used or disabled.
// If you have enabled NRTs for your project, then un-comment the following line:
// #nullable disable

namespace TravelExpertsData.Models
{
    public partial class Regions
    {
        public Regions()
        {
            BookingDetails = new HashSet<BookingDetails>();
        }

        [Key]
        [StringLength(5)]
        public string RegionId { get; set; }
        [StringLength(25)]
        public string RegionName { get; set; }

        [InverseProperty("Region")]
        public virtual ICollection<BookingDetails> BookingDetails { get; set; }
    }
}
