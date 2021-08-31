using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using TravelExpertsData.Models;

namespace TravelExpertsData.DBManager
{
    /// <summary>
    /// CRUD Operations for Suppliers Table
    /// </summary>
    
    public static class SuppliersManager
    {
        //Get list of suppliers as SupplierDTO objects
        public static List<SupplierDTO> GetAll()
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            List<SupplierDTO> suppliers = db.Suppliers.Select(s => new SupplierDTO
                                            {
                                                SupplierId = s.SupplierId,
                                                SupName = s.SupName
                                            })
                                            .OrderBy(s => s.SupplierId).ToList();
            return suppliers;
        }

        //Find supplier by SupplierId
        public static Suppliers Find(int id)
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            Suppliers supplier = db.Suppliers.Find(id);
            return supplier;
        }

        //Add new supplier to database
        public static void Add(Suppliers supplier)
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            db.Suppliers.Add(supplier); // adds new package with selected Suppliers 
            db.SaveChanges();  //save database
        }

        //modify existing supplier
        public static void Modify(Suppliers supplier)
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object

            //find package in current context entity and update values
            Suppliers s = db.Suppliers.Find(supplier.SupplierId);
            s.SupplierId = supplier.SupplierId;
            s.SupName = supplier.SupName;
            s.ProductsSuppliers.Clear();
            s.ProductsSuppliers = supplier.ProductsSuppliers;
            //s.SupplierContacts = supplier.SupplierContacts; // not used in this demo

            //save updates made to current context
            db.SaveChanges();
        }

        //Delete supplier from database and clear any links to products
        public static void Remove(Suppliers supplier)
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            Suppliers s = db.Suppliers.Find(supplier.SupplierId); //get Supplier to be deleted 
            s.ProductsSuppliers.Clear();                          //clear any links to products
            db.Suppliers.Remove(s);                               //delete Supplier
            db.SaveChanges();                                     //write changes to the db
        }

    }//end class
}
