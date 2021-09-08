using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

#nullable disable

namespace TravelExperts.Data.Domain
{
    public partial class Customer
    {
        public Customer()
        {
            Authentications = new HashSet<Authentication>();
            Bookings = new HashSet<Booking>();
            CreditCards = new HashSet<CreditCard>();
            CustomersPackages = new HashSet<CustomersPackage>();
            CustomersRewards = new HashSet<CustomersReward>();
        }

        public int CustomerId { get; set; }



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

        [Required(ErrorMessage = "Please enter a Postal Code")]
        [StringLength(7)]
        [RegularExpression(@"[ABCEGHJKLMNPRSTVXYabceghjklmnprstvxy][0-9][ABCEGHJKLMNPRSTVWXYZabceghjklmnprdtvwxyz] ?[0-9][ABCEGHJKLMNPRSTVWXYZabceghjklmnprstwxyz][0-9]",
            ErrorMessage = "Postal Code must be in the correct format: X1X 1X1")]
        public string CustPostal { get; set; }

        [Required(ErrorMessage = "Please enter a Country")]
        [StringLength(25)]
        public string CustCountry { get; set; }

        [DataType(DataType.PhoneNumber)]
        [Display(Name = "Phone Number")]
        [Required(ErrorMessage = "Please enter a Phone number")]
        [RegularExpression(@"^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$", 
            ErrorMessage = "Entered phone format is not valid.")]
        public string CustHomePhone { get; set; }

        [RegularExpression("^[a-zA-Z0-9_\\.-]+@([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            ErrorMessage = "Please enter email in the correct format")]
        public string CustEmail { get; set; }


        public string CustBusPhone { get; set; }

        
        

        public int? AgentId { get; set; }

        public virtual Agent Agent { get; set; }
        public virtual ICollection<Authentication> Authentications { get; set; }
        public virtual ICollection<Booking> Bookings { get; set; }
        public virtual ICollection<CreditCard> CreditCards { get; set; }
        public virtual ICollection<CustomersPackage> CustomersPackages { get; set; }
        public virtual ICollection<CustomersReward> CustomersRewards { get; set; }
    }
}
