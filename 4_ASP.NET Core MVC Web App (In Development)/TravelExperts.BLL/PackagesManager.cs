using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Linq;
using TravelExperts.Data.Domain;

namespace TravelExperts.BLL
{
    /// <summary>
    /// Handles CRUD Operations on the Authtication Table
    /// </summary>

    public static class PackagesManager
    {
        /// <summary>
        /// Gets a list of all packages from the database
        /// </summary>
        /// <returns>List of Package objects</returns>
        public static List<Package> GetAll()
        {
            TravelExpertsContext db = new TravelExpertsContext();
            List<Package> products = db.Packages.Include(a => a.CustomersPackages).ToList();
            return products;
        }

        /// <summary>
        /// Gets a list of packages that a customer has selected (for shopping cart)
        /// </summary>
        /// <param name="custId">Id of the customer</param>
        /// <returns>List of Package objects</returns>
        public static List<Package> GetByCustId(int custId)
        {
            TravelExpertsContext db = new TravelExpertsContext(); // context object
            Customer cust = db.Customers.Include(c => c.CustomersPackages)
                                        .SingleOrDefault(c => c.CustomerId == custId);
            
            List<Package> packs = new List<Package>();
            Package p = new Package();

            foreach (CustomersPackage c in cust.CustomersPackages)
            {
                //do another db query b/c CustomersPackages objects had a null Package Navigation property
                //(maybe lazy loading problem? add another Include() on line 33?)
                p = db.Packages.Find(c.PackageId); 
                packs.Add(p);
            }               

            return packs;
        }

        /// <summary>
        /// Find a package by ID
        /// </summary>
        /// <param name="packageId">ID of package to find</param>
        /// <returns>Package object if found, null otherwise</returns>
        public static Package FindByPackId(int packageId)
        {
            TravelExpertsContext db = new TravelExpertsContext(); // context object
            Package pack = db.Packages.SingleOrDefault(p => p.PackageId == packageId);
            return pack;
        }

        /// <summary>
        /// Adds a package to a customer's shopping cart
        /// </summary>
        /// <param name="packId">ID of the package to add</param>
        /// <param name="custId">Id of the customer who wants to add the package</param>
        public static void AddPackageToCust(int packId, int custId)
        {
            TravelExpertsContext db = new TravelExpertsContext(); // context object
            Package package = db.Packages.Find(packId);
            package.CustomersPackages.Add(new CustomersPackage
            {
                PackageId = packId,
                CustomerId = custId
            });
            db.SaveChanges();
        }
    }
}
