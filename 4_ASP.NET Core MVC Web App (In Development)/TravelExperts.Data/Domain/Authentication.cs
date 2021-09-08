using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

#nullable disable

namespace TravelExperts.Data.Domain
{
    public partial class Authentication
    {
        public int Id { get; set; }


        [Required(ErrorMessage = "Please enter a Username")]
        [StringLength(16)]
        public string Username { get; set; }

        [Required(ErrorMessage = "Please enter a password")]
        [DataType(DataType.Password)]
        [StringLength(16, MinimumLength = 1)]
        public string Password { get; set; }

        [Required(ErrorMessage = "Please confirm your password")]
        [DataType(DataType.Password)]
        [Compare("Password", ErrorMessage = "Passwords do not match. Please re-type your password")]
        [NotMapped]
        public string ConfirmPassword { get; set; }

        public int CustomerId { get; set; }

        public virtual Customer Customer { get; set; }
    }
}
