using System;
using System.Collections.Generic;
using System.Windows.Forms;
using TravelExpertsData.DBManager;
using TravelExpertsData.Models;
using TravelExpertsData.Models.DBManager;

namespace TravelExpertsGUI
{
    public partial class frmEditPackageProducts : Form
    {
        //******************************************************************************************
        //      Properties
        //******************************************************************************************

        //disables the combo box change event until value member has been set to product id
        private bool isFormLoad;  

        public List<ProductsSuppliers> SelectedProdSups { get; set; } //prodsups related to current package
        public List<ProductsSuppliers> AvailableProdSups { get; set; } //prodsups available to be added to the package
        

        //constructor
        public frmEditPackageProducts()
        {
            InitializeComponent();
        }

        //******************************************************************************************
        //      Event Handlers
        //******************************************************************************************
        private void frmEditPackageProducts_Load(object sender, EventArgs e)
        {
            isFormLoad = true;//disable cboProducts on change event

            //get all products from database and fill the products filter cbo
            cboProduct.DataSource = ProductsManager.GetAll();
            cboProduct.DisplayMember = "ProdName";
            cboProduct.ValueMember = "ProductId"; //change value from default of entire object to just the ProductId
            cboProduct.SelectedIndex = 0;

            isFormLoad = false;//enable cboProducts on change event

            DisplayProducts(); //update list boxes                     
        }

        //when cbo changes filter Available products by the new selection
        private void cboProduct_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (!isFormLoad)
                DisplayProducts();
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
                SelectedProdSups.Add((ProductsSuppliers)lstAvailableProducts.SelectedItem);
                DisplayProducts();
            }            
        }

        private void btnRemoveProduct_Click(object sender, EventArgs e)
        {
            if(lstSelectedProducts.SelectedIndex != -1)
            {
                SelectedProdSups.Remove((ProductsSuppliers)lstSelectedProducts.SelectedItem);
                DisplayProducts();
            }            
        }

        private void btnConfirm_Click(object sender, EventArgs e)
        {
            // SelectedProducts contains products to add to package
            // return to add/modify package to update the current package
            // with the selected products
            this.DialogResult = DialogResult.OK;

        }

        //cancel changes
        private void btnCancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        //******************************************************************************************
        //      Helper Methods
        //******************************************************************************************

        //update list boxes with currently configured product lists
        private void DisplayProducts()
        {
            InitializeAvailable();
            
            //clear list boxes
            lstSelectedProducts.Items.Clear();
            lstAvailableProducts.Items.Clear();            
            
            foreach (ProductsSuppliers p in SelectedProdSups)
                lstSelectedProducts.Items.Add(p);

            foreach (ProductsSuppliers p in AvailableProdSups)
                lstAvailableProducts.Items.Add(p);
        }

        //get all available prodsups from database and then remove prodsups that
        //have been selected.
        // Future - consider moving filter logic to linq in ProdSupManager and 
        // selecting all prodsups except the list passed as a parameter
        private void InitializeAvailable()
        {
            bool isAvailable = true;//flag to prompt add of prodsup to AvailableProdSups
            AvailableProdSups = new List<ProductsSuppliers>();

            //get all prodsups by user selected product
            int prodFilter = Convert.ToInt32(cboProduct.SelectedValue);
            List<ProductsSuppliers> prodSups = ProdSupManager.GetByProductId(prodFilter);
           

            //if there are already packages assigned remove them from available
            if (SelectedProdSups.Count > 0)
            {
                foreach (ProductsSuppliers p in prodSups)
                {
                    //assume p is available before testing against SelectedProducts
                    isAvailable = true;

                    //compare p to each selected product
                    foreach (ProductsSuppliers s in SelectedProdSups)
                    {
                        if (p.ProductSupplierId == s.ProductSupplierId)
                            isAvailable = false;
                            //break?
                    }

                    //if p matches any of the selected products then do not add it to available
                    if (isAvailable)
                        AvailableProdSups.Add(p);
                }
            }
            else //no prodsups selected so all are available
            {
                AvailableProdSups = prodSups;
            } 
        }//init available

       
    }//class
}//namespace
