using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using TravelExpertsData.DBManager;

namespace TravelExpertsData.Models.DBManager
{
    /// <summary>
    /// CRUD Operations for Products Table
    /// </summary>
    
    public static class ProductsManager
    {
        //Get list of all Products as ProductDTos 
        public static List<ProductDTO> GetAll()
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            List<ProductDTO> products = db.Products.Select(p => new ProductDTO { 
                                            ProductId = p.ProductId,
                                            ProdName = p.ProdName
                                        }).ToList();
            return products;
        }

        //Find Product by ProductId
        public static Products Find(int id)
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            Products product = db.Products.Find(id);
            return product;
        }

        //Add new product to database
        public static void Add(Products product)
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            db.Products.Add(product); // adds new package with selected products 
            db.SaveChanges();  //save database
        }
        
        //Modify existing product
        public static void Modify(Products product)
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object

            //find package in current context entity and update values
            Products p = db.Products.Find(product.ProductId);
            p.ProductId = product.ProductId;
            p.ProdName = product.ProdName;
            p.ProductsSuppliers.Clear();
            p.ProductsSuppliers = product.ProductsSuppliers;
            
            //save context updates to db
            db.SaveChanges();  
        }

        public static void Remove(Products product)
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            Products p = db.Products.Find(product.ProductId);     //get product to be deleted 
            p.ProductsSuppliers.Clear();                          //clear any links to suppliers
            db.Products.Remove(p);                                //delete product
            db.SaveChanges();                                     //write changes to the db
        }

        /// <summary>
        /// Retrieves all product/supplier pairs from the database
        /// </summary>
        /// <returns>list product supplier pairs (PrductDTO object)</returns>
        public static List<ProductSupplierDTO> GetAllProducts()
        {
            TravelExpertsContext db = new TravelExpertsContext(); //database context object
            List<ProductSupplierDTO> results = new List<ProductSupplierDTO>(); //stores products         

            //Join Products and Suppliers tables and select ProductSupplierId, Product Name, and Supplier Name
            var products = from ps in db.ProductsSuppliers
                           join prod in db.Products
                               on ps.ProductId equals prod.ProductId
                           join supp in db.Suppliers
                               on ps.SupplierId equals supp.SupplierId
                           orderby prod.ProductId
                           select new { ps.ProductSupplierId, prod.ProdName, supp.SupName };

            //run query and store results in a list of ProductDTOs
            foreach (var p in products)
                results.Add(new ProductSupplierDTO(p.ProductSupplierId, p.ProdName, p.SupName));

            return results;
        }
    }
}
