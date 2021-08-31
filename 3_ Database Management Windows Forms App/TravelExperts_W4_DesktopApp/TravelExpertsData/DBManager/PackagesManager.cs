using System.Collections.Generic;
using System.Linq;
using TravelExpertsData.DBManager;

namespace TravelExpertsData.Models.DBManager
{
    /// <summary>
    /// CRUD Operations for Packages Table
    /// </summary>
    
    public static class PackagesManager
    {
        //Get all packages as PackageDTO objects
        public static List<PackageDTO> GetAll()
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            
            //Select all packages and convert to PackageDTO for display
            List<PackageDTO> packages = db.Packages.OrderBy(p => p.PackageId)
                                      .Select(p => new PackageDTO { 
                                          PackageId = p.PackageId,
                                          PkgName = p.PkgName, 
                                          PkgStartDate = p.PkgStartDate, 
                                          PkgEndDate = p.PkgEndDate, 
                                          PkgDesc = p.PkgDesc, 
                                          PkgBasePrice = p.PkgBasePrice, 
                                          PkgAgencyCommission = p.PkgAgencyCommission 
                                      })
                                      .ToList();
            return packages;
        }

        //Find package by PackageId
        public static Packages Find(int id)
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            Packages package = db.Packages.Find(id);
            return package;
        }

        public static List<PackageDTO> FindBySupplier(int supId)
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            
            //Find all PPS objects that match supId
            List<PackagesProductsSuppliers> PPS = db.PackagesProductsSuppliers
                .Where(p => p.ProductSupplier.SupplierId == supId).ToList();

            List<PackageDTO> packages = new List<PackageDTO>(); //packages that use the specified supplier

            //Use PPS list to find packages the include the specified supplier 
            foreach (PackagesProductsSuppliers p in PPS)
            {  
                packages.Add(new PackageDTO
                {
                    PackageId = p.Package.PackageId,
                    PkgName = p.Package.PkgName,
                    PkgStartDate = p.Package.PkgStartDate,
                    PkgEndDate = p.Package.PkgEndDate,
                    PkgDesc = p.Package.PkgDesc,
                    PkgBasePrice = p.Package.PkgBasePrice,
                    PkgAgencyCommission = p.Package.PkgAgencyCommission
                });
            }

            return packages;
        }

        //Add new package to db
        public static void Add(Packages package)
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            db.Packages.Add(package); // adds new package with selected products 
            db.SaveChanges();  //save database
        }

       //Update the database with the modified package
        public static void Modify(Packages package)
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object

            //find package in current context entity and update values
            Packages p = db.Packages.Find(package.PackageId);
            p.PackageId = package.PackageId;
            p.PkgName = package.PkgName;
            p.PkgStartDate = package.PkgStartDate;
            p.PkgEndDate = package.PkgEndDate;
            p.PkgDesc = package.PkgDesc;
            p.PkgBasePrice = package.PkgBasePrice;
            p.PkgAgencyCommission = package.PkgAgencyCommission;

            //Also transfer navigation properties beacause this is changed in the add/modify package form
            p.PackagesProductsSuppliers.Clear();
            p.PackagesProductsSuppliers = package.PackagesProductsSuppliers;
            //p.Bookings = package.Bookings; //not used for this demo

            db.SaveChanges();  //save database

            // Notes:
            // Changes to entities in the main form are not tracked by the context
            // because the context is only defined here. Therefore , we need to
            // manually find entity object and modify it so that entity framework will
            // know to update the database.
            //
            // db.Packages.Update(package) will mark the object as changed so that
            // Savechanges() will create SQL to update the database for that record. 
            //
        }


        public static void Remove(Packages package)
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            
            Packages p = db.Packages.Find(package.PackageId); //get the package to be deleted 

            //clear records linking the package to product(s)/supplier(s) and then remove
            //package from package table
            p.PackagesProductsSuppliers.Clear(); 
            db.Packages.Remove(p); 
            db.SaveChanges(true);
        }


        /// <summary>
        /// Collects all of the products that have been included in the specified package
        /// </summary>
        /// <param name="packageID">PackageID of the paackage for which you want to find the products for</param>
        /// <returns>list of products associated with the package</returns>
        public static List<ProductSupplierDTO> GetProducts(int packageID)
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            List<ProductSupplierDTO> results = new List<ProductSupplierDTO>(); //stores products that are related to package
           
            //Extension alterntaive but it is more complicated to look at...
            //var products2 = db.Packages.Join(db.PackagesProductsSuppliers, pack => pack.PackageId, pps => pps.PackageId, (pack, pps) => new { pack.PackageId, pps.ProductSupplierId })
            //                           .Where(result => result.PackageId == packageID)
            //                           .Join(db.ProductsSuppliers, pps => pps.ProductSupplierId, ps => ps.ProductSupplierId, (pps, ps) => new {ps.ProductId })
            //                           .Join(db.Products, ps => ps.ProductId, prod => prod.ProductId, (ps, prod) => new {prod.ProductId, prod.ProdName })
            //                           .OrderBy(result => result.ProductId)
            //                           .Select(result => new { result.ProductId, result.ProdName })
            //                           .ToList();


            //Join tables to get the relationship between packages, products, and suppliers.
            //Select columns that will used in forms for display and processing
            //
            var products = from pack in db.Packages
                            join pps in db.PackagesProductsSuppliers
                                on pack.PackageId equals pps.PackageId
                            join ps in db.ProductsSuppliers
                                on pps.ProductSupplierId equals ps.ProductSupplierId
                            join prod in db.Products
                                on ps.ProductId equals prod.ProductId
                            join supp in db.Suppliers
                                on ps.SupplierId equals supp.SupplierId
                            where pack.PackageId == packageID
                            orderby prod.ProductId
                            select new { pps.ProductSupplierId, prod.ProdName, supp.SupName };

            //run query and store results in a list of PackProdsupDTOs
            foreach (var p in products)
                results.Add(new ProductSupplierDTO(p.ProductSupplierId, p.ProdName, p.SupName));
            
            return results;
        }//GetProducts

       
       
    }
}
