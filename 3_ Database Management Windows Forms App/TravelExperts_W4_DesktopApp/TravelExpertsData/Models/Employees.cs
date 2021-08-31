using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

// Code scaffolded by EF Core assumes nullable reference types (NRTs) are not used or disabled.
// If you have enabled NRTs for your project, then un-comment the following line:
// #nullable disable

namespace TravelExpertsData.Models
{
    public partial class Employees
    {
        [Required]
        [StringLength(20)]
        public string EmpFirstName { get; set; }
        [StringLength(5)]
        public string EmpMiddleInitial { get; set; }
        [Required]
        [StringLength(20)]
        public string EmpLastName { get; set; }
        [Required]
        [StringLength(20)]
        public string EmpBusPhone { get; set; }
        [Required]
        [StringLength(50)]
        public string EmpEmail { get; set; }
        [Required]
        [StringLength(20)]
        public string EmpPosition { get; set; }
    }
}
