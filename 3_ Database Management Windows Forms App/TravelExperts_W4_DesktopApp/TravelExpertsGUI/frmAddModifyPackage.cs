using System;
using System.Collections.Generic;
using System.Globalization;
using System.Windows.Forms;
using TechSupportGUI;
using TravelExpertsData.DBManager;
using TravelExpertsData.Models;
using TravelExpertsData.Models.DBManager;

namespace TravelExpertsGUI
{
    public partial class frmAddModifyPackage : Form
    {
        //******************************************************************************************
        //      Properties
        //******************************************************************************************
        //private Properties
        // prodsups that have been added to the package
        private List<ProductsSuppliers> selectedProdSups = new List<ProductsSuppliers>(); 

        //Public Properties
        public Packages Package { get; set; } //package to be added or modified
        public bool AddPackage { get; set; } //flag is true if add operation is selected

        //******************************************************************************************
        //      Event Handlers
        //******************************************************************************************
        public frmAddModifyPackage()
        {
            InitializeComponent();
        }

        //setup form for add or for modify operation
        private void frmAddModifyPackage_Load(object sender, EventArgs e)
        {            
            if (AddPackage)
            {
                this.Text = "Add Package"; //set form title

                //enable start and end dates on load
                chkStartDate.Checked = true;
                chkEndDate.Checked = true;                                
            }
            else
            {
                this.Text = "Modify Product"; //set form title

                //get the products for the selected package               
                foreach(PackagesProductsSuppliers p in Package.PackagesProductsSuppliers)
                    selectedProdSups.Add(ProdSupManager.Find(p.ProductSupplierId));

                LoadExistingPackage();
                DisplaySelectedProducts();
            }

        }

        //enable/disbale the start date picker
        private void chkStartDate_CheckedChanged(object sender, EventArgs e)
        {
            if (chkStartDate.Checked)
                dtpStartDate.Visible = true;
            else
                dtpStartDate.Visible = false;
        }

        //enable/disbale the end date picker
        private void chkEndDate_CheckedChanged(object sender, EventArgs e)
        {
            if (chkEndDate.Checked)
                dtpEndDate.Visible = true;
            else
                dtpEndDate.Visible = false;
        }

        private void btnEditProducts_Click(object sender, EventArgs e)
        {
            //initialize modify package form and pass in selected package
            frmEditPackageProducts editProductsForm = new frmEditPackageProducts();
            editProductsForm.SelectedProdSups = selectedProdSups;            

            //display modify package form and return state of form when it is closed
            DialogResult result = editProductsForm.ShowDialog();

            if (result == DialogResult.OK) //modify form has returned valid data
            {
                //update selectedProducts with changes from edit form
                selectedProdSups = editProductsForm.SelectedProdSups;

                DisplaySelectedProducts(); //Display updated Products table               
            }

        }

        //save information in form into form package properties
        private void btnConfirm_Click(object sender, EventArgs e)
        {
            if (IsValidData())
            {
                if (AddPackage)
                {
                    // initialize the Product property with new Packages object
                    this.Package = new Packages();
                }
                SavePackageData();
                this.DialogResult = DialogResult.OK;
            }
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        //******************************************************************************************
        //      Helper Methods
        //******************************************************************************************

        private void LoadExistingPackage()
        {
            //fill form with selected package (set in main form)
            txtPackageName.Text = Package.PkgName;

            //If the start date is set then display, else disable start dtp
            if (Package.PkgStartDate != null)
            {
                chkStartDate.Checked = true;
                dtpStartDate.Value = Convert.ToDateTime(Package.PkgStartDate); // convert b/c dtp can't be a nullable type
            }
            else
            {
                chkStartDate.Checked = false; //default value on form load so does not trigger event to change dtp
                dtpStartDate.Visible = false; //so also need to set dtp
            }

            //If the end date is set then display, else disable end dtp
            if (Package.PkgEndDate != null)
            {
                chkEndDate.Checked = true;
                dtpEndDate.Value = Convert.ToDateTime(Package.PkgEndDate); // convert b/c dtp can't be a nullable type
            }
            else
            {
                chkEndDate.Checked = false; // default value on form load so does not trigger event to change dtp
                dtpEndDate.Visible = false;  //  so also need to set dtp
            }

            txtPackageDescription.Text = Package.PkgDesc;
            txtPackageBasePrice.Text = Package.PkgBasePrice.ToString("n");
            
            //only load commission if it is not null (or else 0.00 will be saved)
            if (Package.PkgAgencyCommission != null)
                txtAgencyCommission.Text = Convert.ToDecimal(Package.PkgAgencyCommission).ToString("n"); //convert to get rid of decimal
        }

        //Loads all data entered into modify form into package object
        //so it can be passed back to the main form and be saved to db
        private void SavePackageData()
        {            
            Package.PkgName = txtPackageName.Text;            

            //if start date is enabled save value, else set to null
            if (chkStartDate.Checked)
                Package.PkgStartDate = dtpStartDate.Value;
            else
                Package.PkgStartDate = null;

            //if end date is enabled save value, else set to null
            if (chkEndDate.Checked)
                Package.PkgEndDate = dtpEndDate.Value;
            else
                Package.PkgEndDate = null;

            //save description as null if not provided
            if (txtPackageDescription.Text == "")
                Package.PkgDesc = null;
            else
                Package.PkgDesc = txtPackageDescription.Text;
            
            Package.PkgBasePrice = Convert.ToDecimal(txtPackageBasePrice.Text);

            //If agency comission is provided save as decimal, else set to null
            if (txtAgencyCommission.Text != "")
                Package.PkgAgencyCommission = Convert.ToDecimal(txtAgencyCommission.Text);
            else
                Package.PkgAgencyCommission = null;

            //clear any previously assigned product/supplier pairs for this package
            Package.PackagesProductsSuppliers.Clear();

            //add the newly configured product/supplier pairs to this package
            //when package is saved to database these values will be added to PackagesProductsSuppliers link table
            foreach (ProductsSuppliers p in selectedProdSups)
                Package.PackagesProductsSuppliers.Add(new PackagesProductsSuppliers(Package.PackageId, p.ProductSupplierId));

        }//SavePackageData


        
        private void DisplaySelectedProducts()
        {
            lstProducts.Items.Clear();
          
            if (selectedProdSups != null)
            {
                foreach (ProductsSuppliers p in selectedProdSups)
                    lstProducts.Items.Add(p); // lst displays using ProductSupplier ToString
            }
        }

        //checks user input to meet database requriements
        private bool IsValidData()
        {
            bool isValid = false;

            //only check dates if both start date and end date are enabled;
            bool checkDates = chkStartDate.Checked && chkEndDate.Checked;
            
            if(Validator.IsPresent(txtPackageName) &&
               Validator.IsPresent(txtPackageDescription) &&
               Validator.IsPresent(txtPackageBasePrice) &&
               Validator.IsNonNegativeDecimal(txtPackageBasePrice) &&
               Validator.IsDecimalLessThan(txtAgencyCommission, txtPackageBasePrice)
               )
            {
                //b/c dates have extra condition they are in a nested if statement 
                //this keeps the chain of validator calls so that the user is only
                //presented with one error at a time
                if (checkDates) 
                {
                    //both dates entered so do one more validator check
                    if (Validator.IsDateAfter(dtpStartDate, dtpEndDate))
                        isValid = true;
                }
                else //all requried validation is complete and the data is valid
                {
                    isValid = true;
                }                    
            }


            return isValid;

        }//endIsValid

       
    }//frmAddModify Class

}//namespace
