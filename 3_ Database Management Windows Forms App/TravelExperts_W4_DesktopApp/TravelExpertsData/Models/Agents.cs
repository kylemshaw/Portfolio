using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

// Code scaffolded by EF Core assumes nullable reference types (NRTs) are not used or disabled.
// If you have enabled NRTs for your project, then un-comment the following line:
// #nullable disable

namespace TravelExpertsData.Models
{
    public partial class Agents
    {
        public Agents()
        {
            Customers = new HashSet<Customers>();
        }

        [Key]
        public int AgentId { get; set; }
        [StringLength(20)]
        public string AgtFirstName { get; set; }
        [StringLength(5)]
        public string AgtMiddleInitial { get; set; }
        [StringLength(20)]
        public string AgtLastName { get; set; }
        [StringLength(20)]
        public string AgtBusPhone { get; set; }
        [StringLength(50)]
        public string AgtEmail { get; set; }
        [StringLength(20)]
        public string AgtPosition { get; set; }
        public int? AgencyId { get; set; }

        [ForeignKey(nameof(AgencyId))]
        [InverseProperty(nameof(Agencies.Agents))]
        public virtual Agencies Agency { get; set; }
        [InverseProperty("Agent")]
        public virtual ICollection<Customers> Customers { get; set; }
    }
}
