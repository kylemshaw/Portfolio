using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace TravelExperts.MVCApp.Models
{
    public class PackageViewModel
    {
        public int PackageId { get; set; }
        public string PackagePicture { get; set; }

        [Display(Name = "Package Name")]
        public string PkgName {get;set;}

        [Display(Name = "Start Date")]
        public string PkgStartDate { get; set; }

        [Display(Name = "End Date")]
        public string PkgEndDate { get; set; }

        [Display(Name = "Description")]
        public string PkgDesc { get; set; }

        [Display(Name = "Price")]
        public string PkgBasePrice { get; set; }
    }
}
