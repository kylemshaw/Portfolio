using System;
using System.Collections.Generic;
using System.Windows.Forms;
using TechSupportGUI;
using TravelExpertsData.DBManager;
using TravelExpertsData.Models;
using TravelExpertsData.Models.DBManager;

namespace TravelExpertsGUI
{
    public partial class frmAddModifySuppliers : Form
    {
        //******************************************************************************************
        //      Properties
        //******************************************************************************************
        public Suppliers Supplier { get; set; }//Dillon
        public bool AddSupplier { get; set; }//Dillon

        private List<ProductDTO> selectedProducts = new List<ProductDTO>(); // keeps track of this supplier's products


        //******************************************************************************************
        //      Event Handlers
        //******************************************************************************************
        public frmAddModifySuppliers()
        {
            InitializeComponent();            
        }

        private void frmAddModifySuppliers_Load(object sender, EventArgs e)
        {

            if (AddSupplier)
            {
                this.Text = "Add Supplier";
                txtSupplierID.ReadOnly = false;  // allow entry of new supplier
            }
            else
            {
                this.Text = "Modify Supplier";
                txtSupplierID.ReadOnly = true;   // can't change existing supplier 

                //load products from the database
                Products temp = new Products();
                foreach (ProductsSuppliers p in Supplier.ProductsSuppliers)
                {
                    //Could not get p.Package to load when finding Selected supplier in frmMain -> ManageSupplier
                    //so used additional db search to get the full Product object
                    if (p.ProductId != null)
                        temp = ProductsManager.Find((int)p.ProductId);

                    selectedProducts.Add(new ProductDTO
                    {
                        ProductId = temp.ProductId,
                        ProdName = temp.ProdName
                    });
                }

                LoadExistingSupplier();
            }
            
        }

        private void btnEditProducts_Click(object sender, EventArgs e)
        {
            //initialize form and pass in selected products
            frmEditSupplierProducts editProductsForm = new frmEditSupplierProducts();
            editProductsForm.SelectedProducts = selectedProducts;

            //display modify package form and return state of form when it is closed
            DialogResult result = editProductsForm.ShowDialog();

            if (result == DialogResult.OK) //modify form has returned valid data
            {
                //update selectedProducts with changes from edit form
                selectedProducts = editProductsForm.SelectedProducts;

                DisplayProducts(); //Display updated Products table               
            }
        }

        //save form data once user has completed data entry
        private void btnConfirm_Click(object sender, EventArgs e)
        {
            if (IsValidData())
            {
                if (AddSupplier)
                {
                    // initialize the Supplier property with new Packages object
                    this.Supplier = new Suppliers();
                }
                this.SaveSupplierData();
                this.DialogResult = DialogResult.OK;
            }
        }

        //cancel form operation
        private void btnCancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }



        //******************************************************************************************
        //      Helper Methods
        //******************************************************************************************
        
        //load supplier selected in frmMain for modify operation
        private void LoadExistingSupplier()
        {
            txtSupplierID.Text = Supplier.SupplierId.ToString();

            //check for null supplier name 
            if (Supplier.SupName == null)
                txtSupplierName.Text = "";
            else
                txtSupplierName.Text = Supplier.SupName;

            //fill list box with current set of selected products
            lstSelectedProducts.Items.Clear();
            foreach (ProductDTO p in selectedProducts)
                lstSelectedProducts.Items.Add(p.ProdName);
        }

        //Take the information in the form and add it to the public Supplier object
        // so that it can be saved to the database in frmMain
        private void SaveSupplierData()
        {
            //collect supplier info from text boxes
            Supplier.SupplierId = Convert.ToInt32(txtSupplierID.Text);

            //if text box is empty save as null
            if (txtSupplierName.Text == "")
                Supplier.SupName = null;
            else
                Supplier.SupName = txtSupplierName.Text;

            //clear existing supplier/product relations
            Supplier.ProductsSuppliers.Clear();

            //add new product/suplier relations to navigation property 
            foreach (ProductDTO p in selectedProducts)
            {
                Supplier.ProductsSuppliers.Add(new ProductsSuppliers
                {
                    SupplierId = Supplier.SupplierId,
                    ProductId = p.ProductId
                });
            }              
        }

        //display the selected products for this supplier
        private void DisplayProducts()
        {
            //clear list to get rid of old entries
            lstSelectedProducts.Items.Clear();

            //add all selected products to the list box for display
            foreach (ProductDTO p in selectedProducts)
                lstSelectedProducts.Items.Add(p.ProdName);
        }

        private bool IsValidData()
        {
            //  Validation Requirements
            //
            //
            //  Supplier Name can be null so validation only requried for length
            //  (max 255 set in textbox properties
            //
            //  SupplierId is not an identity column in the database so assume user
            //  can select. Check user has entered postive int Supplier Id and make
            //  sure it has not been used already
            //
            //  Linked Products are always valid as values are controlled by code 
            //  and not created by the user (user only selects from options)
            //
            //  Validation only required for Add. Modify will have a valid SupplierId
            //  and this is the only value that needs checking in code

            
            bool isValid = true; //assume everything is valid

            if (AddSupplier)
            {
                if (Validator.IsNonNegativeInt(txtSupplierID))
                {
                    //try to find the SupplierId entered by the user
                    Suppliers sup = SuppliersManager.Find(Convert.ToInt32(txtSupplierID.Text));

                    if (sup != null)//if a supplier with that id was found then show validation error
                    {
                        MessageBox.Show($"This {txtSupplierID.Tag} has already been taken. Please choose another value.", "Input Error");
                        txtSupplierID.SelectAll();
                        txtSupplierID.Focus();
                        isValid = false;
                    }
                }
                else
                {
                    isValid = false;
                }
            }

            return isValid;

        }//valdiation

        
    }//class
}
