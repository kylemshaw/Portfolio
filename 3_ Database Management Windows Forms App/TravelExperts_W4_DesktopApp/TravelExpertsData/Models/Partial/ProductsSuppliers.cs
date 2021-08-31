using System;
using System.Collections.Generic;
using System.Text;

namespace TravelExpertsData.Models
{
    public partial class ProductsSuppliers
    {
        //return string in format suitable for display in a listbox
        public override string ToString()
        {
            string output;

            if (this.Supplier == null && this.Product != null)
                output = this.Product.ProdName;
            else if (this.Product == null && this.Supplier != null)
                output = $"{ "", 20}{this.Supplier.SupName}";
            else if (this.Product == null && this.Supplier == null)
                output = $"{ "<empty>",20} <emtpy>";
            else
                output = this.Product.ProdName.PadRight(20) + " " + this.Supplier.SupName;
            
            return output;
        }
    }
}
