using System;
using System.Collections.Generic;
using System.Windows.Forms;
using TravelExpertsData.DBManager;
using TravelExpertsData.Models;
using TravelExpertsData.Models.DBManager;

namespace TravelExpertsGUI
{
    public partial class frmEditSupplierProducts : Form
    {
        //******************************************************************************************
        //     Properties
        //******************************************************************************************
        public List<ProductDTO> AvailableProducts {get; set;}
        public List<ProductDTO>  SelectedProducts {get;set;}

        public frmEditSupplierProducts()
        {
            InitializeComponent();
        }

        //******************************************************************************************
        //      Event Handlers
        //******************************************************************************************
        private void frmEditSupplierProducts_Load(object sender, EventArgs e)
        {
            InitializeAvailable();
            UpdateLists();

        }

        //only allow selection in one list box at a time
        private void lstAvailableProducts_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (lstAvailableProducts.SelectedIndex != -1)
                lstSelectedProducts.SelectedIndex = -1;
        }

        //only allow selection in one list box at a time
        private void lstSelectedProducts_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (lstSelectedProducts.SelectedIndex != -1)
                lstAvailableProducts.SelectedIndex = -1;
        }

        private void btnAddProduct_Click(object sender, EventArgs e)
        {
            if (lstAvailableProducts.SelectedIndex != -1)
            {
                SelectedProducts.Add((ProductDTO)lstAvailableProducts.SelectedItem);
                AvailableProducts.Remove((ProductDTO)lstAvailableProducts.SelectedItem);
                UpdateLists();
            }

        }

        private void btnRemoveProduct_Click(object sender, EventArgs e)
        {
            if (lstSelectedProducts.SelectedIndex != -1)
            {
                SelectedProducts.Remove((ProductDTO)lstSelectedProducts.SelectedItem);
                AvailableProducts.Add((ProductDTO)lstSelectedProducts.SelectedItem);
                UpdateLists();
            }
        }

        private void btnConfirm_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.OK;
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        //******************************************************************************************
        //      Helper Methods
        //******************************************************************************************

        private void UpdateLists()
        {
            lstAvailableProducts.Items.Clear();
            lstSelectedProducts.Items.Clear();

            foreach (ProductDTO a in AvailableProducts)
                lstAvailableProducts.Items.Add(a);

            foreach (ProductDTO s in SelectedProducts)
                lstSelectedProducts.Items.Add(s);
        }

        //load all products that have not been selected into AvailableProducts
        private void InitializeAvailable()
        {
            bool isAvailable = true;
            AvailableProducts = new List<ProductDTO>();
            List<ProductDTO> products = ProductsManager.GetAll();

            //if there are already packages assigned remove them from available
            if (SelectedProducts.Count > 0)
            {
                foreach (ProductDTO p in products)
                {
                    //assume p is available before testing against SelectedProducts
                    isAvailable = true;

                    //compare p to each selected product
                    foreach (ProductDTO s in SelectedProducts)
                    {
                        if (p.ProductId == s.ProductId)
                            isAvailable = false;
                    }

                    //if p matches any of the selected products then do not add it to available
                    if (isAvailable)
                        AvailableProducts.Add(p);
                }
            }
            else //no prodsups selected so all are available
            {
                AvailableProducts = products;
            }
        }

        
    }//class
}
