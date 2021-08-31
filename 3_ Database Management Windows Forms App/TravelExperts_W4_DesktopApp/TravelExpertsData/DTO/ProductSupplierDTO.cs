using System;
using System.Collections.Generic;
using System.Text;

namespace TravelExpertsData.Models
{
    /// <summary>
    /// lightweight object for use in GUI
    /// </summary>
    /// 
    public class ProductSupplierDTO
    {
        //Public Properties
        public int ProductSupplierId { get; set; }
        public string ProdName { get; set; }
        public string SupName { get; set; }

        //Constructor
        public ProductSupplierDTO(int productSupplierId, string prodName, string supName)
        {
            ProductSupplierId = productSupplierId;
            ProdName = prodName;
            SupName = supName;
        }

        //return string in format suitable for display in a listbox
        public override string ToString()
        {
            return ProdName.PadRight(20) + " " + SupName;
        }

    }
}
