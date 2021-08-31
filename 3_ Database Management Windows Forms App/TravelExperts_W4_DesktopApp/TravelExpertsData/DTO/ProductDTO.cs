using System;
using System.Collections.Generic;
using System.Text;

namespace TravelExpertsData.DBManager
{
    /// <summary>
    /// lightweight object for use in GUI
    /// </summary>
    public class ProductDTO
    {
        public int ProductId { get; set; }
        public string ProdName { get; set; }

        //return string in format suitable for display in a listbox
        public override string ToString()
        {
            return ProdName;
        }

    }
}
