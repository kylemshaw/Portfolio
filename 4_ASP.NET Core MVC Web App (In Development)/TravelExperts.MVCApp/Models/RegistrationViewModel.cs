using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace TravelExperts.MVCApp.Models
{
    public class RegistrationViewModel 
    {
        //[Display(Name = "ID")]
        //public int ID { get; set; }

        [Required(ErrorMessage = "Please enter you First Name")]
        [StringLength(25)]
        public string CustFirstName { get; set; }

        [Required(ErrorMessage = "Please enter your Last Name")]
        [StringLength(25)]
        public string CustLastName { get; set; }

        [Required(ErrorMessage = "Please enter an Address")]
        [StringLength(75)]
        public string CustAddress { get; set; }

        [Required(ErrorMessage = "Please enter a City")]
        [StringLength(50)]
        public string CustCity { get; set; }

        [Required(ErrorMessage = "Please enter a Province in the correct format: XY")]
        [StringLength(2)]
        public string CustProv { get; set; }

        [Required(ErrorMessage = "Please enter a Postal Code in the correct format: X1X 1X1")]
        [StringLength(7)]
        [RegularExpression(@"[ABCEGHJKLMNPRSTVXY][0-9][ABCEGHJKLMNPRSTVWXYZ] ?[0-9][ABCEGHJKLMNPRSTVWXYZ][0-9]",
            ErrorMessage = "Postal Code must be in the correct format: X1X 1X1")]
        public string CustPostal { get; set; }

        [Required(ErrorMessage = "Please enter a Country")]
        [StringLength(25)]
        public string CustCountry { get; set; }

        //[DataType(DataType.PhoneNumber)]
        //[Display(Name = "Phone Number")]
        //[Required(ErrorMessage = "Phone Number Required!")]
        //[RegularExpression(@"^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$",
        //    ErrorMessage = "Entered phone format is not valid.")]
        //public string CustPhoneNumber { get; set; }

        [RegularExpression("^[a-zA-Z0-9_\\.-]+@([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", 
            ErrorMessage = "please enter email in the correct format")]
        public string CustEmail { get; set; }

        [Required(ErrorMessage = "Please enter a Username")]
        [StringLength(16)]
        public string Username { get; set; }

        [Required(ErrorMessage = "Please enter a password")]
        [DataType(DataType.Password)]
        [StringLength(16, MinimumLength = 1)]   
        public string Password { get; set; }

        [Display(Name = "Confirm password")]
        [Required(ErrorMessage = "Please confirm your password")]
        [Compare("Password", ErrorMessage = "passwords do not match, please re-type")]
        [DataType(DataType.Password)]
        public string Confirmpwd { get; set; }
        public Nullable<bool> Is_Deleted { get; set; }


        

        
    }
}
