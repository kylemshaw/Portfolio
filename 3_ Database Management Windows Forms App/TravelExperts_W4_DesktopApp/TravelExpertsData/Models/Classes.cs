using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

// Code scaffolded by EF Core assumes nullable reference types (NRTs) are not used or disabled.
// If you have enabled NRTs for your project, then un-comment the following line:
// #nullable disable

namespace TravelExpertsData.Models
{
    public partial class Classes
    {
        public Classes()
        {
            BookingDetails = new HashSet<BookingDetails>();
        }

        [Key]
        [StringLength(5)]
        public string ClassId { get; set; }
        [Required]
        [StringLength(20)]
        public string ClassName { get; set; }
        [StringLength(50)]
        public string ClassDesc { get; set; }

        [InverseProperty("Class")]
        public virtual ICollection<BookingDetails> BookingDetails { get; set; }
    }
}
