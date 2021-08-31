using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

// Code scaffolded by EF Core assumes nullable reference types (NRTs) are not used or disabled.
// If you have enabled NRTs for your project, then un-comment the following line:
// #nullable disable

namespace TravelExpertsData.Models
{
    public partial class Affiliations
    {
        public Affiliations()
        {
            SupplierContacts = new HashSet<SupplierContacts>();
        }

        [Key]
        [StringLength(10)]
        public string AffilitationId { get; set; }
        [StringLength(50)]
        public string AffName { get; set; }
        [StringLength(50)]
        public string AffDesc { get; set; }

        [InverseProperty("Affiliation")]
        public virtual ICollection<SupplierContacts> SupplierContacts { get; set; }
    }
}
