using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using TravelExperts.BLL;
using TravelExperts.Data.Domain;
using TravelExperts.MVCApp.Models;

namespace TravelExperts.MVCApp.Controllers
{
    public class PackageController : Controller
    {
        // GET: List all packages
        public IActionResult Index()
        {
            List<Package> packages = PackagesManager.GetAll();

            //convert to View Model object for display
            List<PackageViewModel> viewModels = packages.Select(p => new PackageViewModel
            {
                PackageId = p.PackageId,
                PackagePicture = "pack" + p.PackageId + ".jpg",
                PkgName = p.PkgName,

                //nullable string does not have date format so use conditional to work around and set to "" if null
                PkgStartDate = p.PkgStartDate.HasValue ? p.PkgStartDate.Value.ToString("yyy-MM-dd") : "",
                PkgEndDate = p.PkgEndDate.HasValue ? p.PkgEndDate.Value.ToString("yyy-MM-dd") : "",
                
                PkgDesc = p.PkgDesc,
                PkgBasePrice = p.PkgBasePrice.ToString("c")                

            }).ToList();
            return View(viewModels);
        }

        //GET: List all packages in shoping cart of the current customer
        [Authorize]
        public IActionResult Purchases()
        {
            //grab the currently logged in customer's ID from the claims
            int custId = Convert.ToInt32(User.Claims.FirstOrDefault(c => c.Type == "CustomerId").Value);
            
            //Get their packages from the data base and convert to view model object for view
            List<Package> packages = PackagesManager.GetByCustId(custId);
            List<PackageViewModel> viewModels = packages.Select(p => new PackageViewModel
            {
                PackageId = p.PackageId,
                PackagePicture = "pack" + p.PackageId + ".jpg",
                PkgName = p.PkgName,

                //nullable string does not have date format so use conditional to work around and set to "" if null
                PkgStartDate = p.PkgStartDate.HasValue ? p.PkgStartDate.Value.ToString("yyy-MM-dd") : "",
                PkgEndDate = p.PkgEndDate.HasValue ? p.PkgEndDate.Value.ToString("yyy-MM-dd") : "",

                PkgDesc = p.PkgDesc,
                PkgBasePrice = p.PkgBasePrice.ToString("c")

            }).ToList();
            return View(viewModels);
        }

        //GET: Selected package 
        public ActionResult Selected(int id)
        {
            //find the package passed by asp-rout-id tag helper and display selected view
            Package p = PackagesManager.FindByPackId(id);
            
            //conver to view model for view
            PackageViewModel model = new PackageViewModel
            {
                PackageId = p.PackageId,
                PackagePicture = "pack" + p.PackageId + ".jpg",
                PkgName = p.PkgName,

                //nullable string does not have date format so use conditional to work around and set to "" if null
                PkgStartDate = p.PkgStartDate.HasValue ? p.PkgStartDate.Value.ToString("yyy-MM-dd") : "",
                PkgEndDate = p.PkgEndDate.HasValue ? p.PkgEndDate.Value.ToString("yyy-MM-dd") : "",

                PkgDesc = p.PkgDesc,
                PkgBasePrice = p.PkgBasePrice.ToString("c")
            };
            return View(model);
        }

        
        // Helper method to add a package to a customer's cart if logged in
        // if not logged in then they are redirected to the login page
        public ActionResult Buy(int packId)
        {
            try
            {
                if (User.Identity.IsAuthenticated)//if user is logged in
                {
                    //add package to CustomersPackage table with current user info from claim
                    int custId = Convert.ToInt32(User.Claims.FirstOrDefault(c => c.Type == "CustomerId").Value);
                    PackagesManager.AddPackageToCust(packId, custId);
                    return RedirectToAction(nameof(Purchases));
                }
                else
                {
                    //string currentUrl = $"~/Package/Selected/{packId}";
                    //redirect to login with return url set to the current page
                    return RedirectToAction("Login", "Account", new { returnUrl = Request.Path });
                }

            }
            catch
            {
                return View();
            }
        }

        //Placeholder method for checkout functionality that is currently out of Scope
        // Future: this will implement creating of Booking objects and all related data
        public ActionResult Checkout()
        {
            return View();
        }


    }//class
}
