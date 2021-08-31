using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using TravelExpertsData.Models;

namespace TravelExpertsData.DBManager
{
    /// <summary>
    /// CRUD Operations for Products_Suppliers Link Table
    /// </summary>
    
    public class ProdSupManager
    {
        //Return a list of all ProductSupplier records
        public static List<ProductsSuppliers> GetAll()
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            List<ProductsSuppliers> prodsups = db.ProductsSuppliers.OrderBy(p => p.ProductId).ToList();
            return prodsups;
        }

        //Find ProductSupplier by ProductId
        public static List<ProductsSuppliers> GetByProductId(int prodId)
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            List<ProductsSuppliers> prodsups = db.ProductsSuppliers.Where(p => p.ProductId == prodId)
                                                                   .OrderBy(p => p.ProductId).ToList();
            return prodsups;
        }

        //Find product supplier by ProDuctSupplierId
        public static ProductsSuppliers Find(int id)
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            ProductsSuppliers prodsup = db.ProductsSuppliers.Find(id);
            return prodsup;
        }
    }
}
