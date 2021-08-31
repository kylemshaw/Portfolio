using Microsoft.Data.SqlClient;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;
using TravelExpertsData.DBManager;
using TravelExpertsData.Models;
using TravelExpertsData.Models.DBManager;

namespace TravelExpertsGUI
{
    public partial class frmMain : Form
    {
        //******************************************************************************************
        //      Properties
        //******************************************************************************************

        //holds add/modify data entered by user on respective subform
        private Packages selectedPackage = null;
        private Products selectedProduct = null;
        private Suppliers selectedSupplier = null;

        //Flags that indicate which table the user has selected to work with
        private bool isPackage = false;
        private bool isProduct = false;
        private bool isSupplier = false;
        

        //******************************************************************************************
        //      Event Handlers
        //******************************************************************************************

        public frmMain()
        {
            InitializeComponent();
        }

        //Sets package flag to true and displays packages table
        private void btnPackages_Click(object sender, EventArgs e)
        {
            isPackage = true;
            isProduct = false;
            isSupplier = false;

            DisplayPackages();
        }

        //Sets products flag to true and displays products table
        private void btnProducts_Click(object sender, EventArgs e)
        {
            isPackage = false;
            isProduct = true;
            isSupplier = false;

            DisplayProducts();

        }

        //Sets suppliers flag to true and displays suppliers table
        private void btnSuppliers_Click(object sender, EventArgs e)
        {
            isPackage = false;
            isProduct = false;
            isSupplier = true;

            DisplaySuppliers();

        }

        //calls the add/modify subform that corresponds to the currently selected table
        private void btnAdd_Click(object sender, EventArgs e)
        {
            if (isPackage)
                AddPackage();
            else if (isProduct)
                AddProduct();
            else if (isSupplier)
                AddSupplier();
            else
                MessageBox.Show("You must select a table before Add is allowed.", "Table Not Selected");
        }
             
        //calls function that manages modify/delete actions for the currently selected table
        private void dgvTableView_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            if (isPackage)
                ManagePackage(e);
            else if (isProduct)
                ManageProduct(e);
            else if (isSupplier)
                ManageSupplier(e);

        }

        //closes the application
        private void btnExit_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        //******************************************************************************************
        //      Helper Methods
        //******************************************************************************************
                                                                                           
        //display packages table in the DGV on the main form
        private void DisplayPackages()
        {
            //Set dgv souce to the packages table
            dgvTableView.Columns.Clear();
            dgvTableView.DataSource = PackagesManager.GetAll();
            
            TableSetup();

            //// format the first column
            dgvTableView.Columns[0].HeaderText = "Package ID";
            dgvTableView.Columns[0].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[0].Width = 60;
            dgvTableView.Columns[0].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;

            //// format the second column
            dgvTableView.Columns[1].HeaderText = "Package Name";
            dgvTableView.Columns[1].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[1].Width = 130;
            dgvTableView.Columns[1].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;

            //// format the third column
            dgvTableView.Columns[2].HeaderText = "Start Date";
            dgvTableView.Columns[2].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[2].Width = 70;
            dgvTableView.Columns[2].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[2].DefaultCellStyle.Format = "yyyy-MM-dd";

            //// format the fourth column
            dgvTableView.Columns[3].HeaderText = "End Date";
            dgvTableView.Columns[3].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[3].Width = 70;
            dgvTableView.Columns[3].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[3].DefaultCellStyle.Format = "yyyy-MM-dd";

            //// format the fifth column
            dgvTableView.Columns[4].HeaderText = "Description";
            dgvTableView.Columns[4].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[4].Width = 270;
            dgvTableView.Columns[4].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;

            //// format the sixth column
            dgvTableView.Columns[5].HeaderText = "Base Price";
            dgvTableView.Columns[5].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[5].Width = 60;
            dgvTableView.Columns[5].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[5].DefaultCellStyle.Format = "c";

            //// format the seventh column
            dgvTableView.Columns[6].HeaderText = "Agency Commission";
            dgvTableView.Columns[6].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[6].Width = 80;
            dgvTableView.Columns[6].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[6].DefaultCellStyle.Format = "c";

            dgvTableView.Columns[7].HeaderText = "Modify";
            dgvTableView.Columns[7].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[7].Width = 75;
            dgvTableView.Columns[7].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;

            dgvTableView.Columns[8].HeaderText = "Delete";
            dgvTableView.Columns[8].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[8].Width = 75;
            dgvTableView.Columns[8].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
        }


        //display products table in the DGV on the main form
        private void DisplayProducts()
        {
            dgvTableView.Columns.Clear();
            dgvTableView.DataSource = ProductsManager.GetAll();

            TableSetup();

            //// format the first column
            dgvTableView.Columns[0].HeaderText = "Product ID";
            dgvTableView.Columns[0].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[0].Width = 100;
            dgvTableView.Columns[0].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;

            //// format the second column
            dgvTableView.Columns[1].HeaderText = "Product Name";
            dgvTableView.Columns[1].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[1].Width = 200;
            dgvTableView.Columns[1].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;

            //// format the third column
            dgvTableView.Columns[2].HeaderText = "Modify";
            dgvTableView.Columns[2].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[2].Width = 100;
            dgvTableView.Columns[2].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;

            //// format the fourth column
            dgvTableView.Columns[3].HeaderText = "Delete";
            dgvTableView.Columns[3].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[3].Width = 100;
            dgvTableView.Columns[3].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;

        }

        //display Suppliers table in the DGV on the main form
        private void DisplaySuppliers()
        {
            dgvTableView.Columns.Clear();
            dgvTableView.DataSource = SuppliersManager.GetAll();

            TableSetup();

            dgvTableView.Columns[0].HeaderText = "Supplier ID";
            dgvTableView.Columns[0].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[0].Width = 100;
            dgvTableView.Columns[0].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;

            //// format the second column
            dgvTableView.Columns[1].HeaderText = "Supplier Name";
            dgvTableView.Columns[1].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[1].Width = 250;
            dgvTableView.Columns[1].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;

            //// format the third column
            dgvTableView.Columns[2].HeaderText = "Modify";
            dgvTableView.Columns[2].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[2].Width = 100;
            dgvTableView.Columns[2].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;

            //// format the fourth column
            dgvTableView.Columns[3].HeaderText = "Delete";
            dgvTableView.Columns[3].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvTableView.Columns[3].Width = 100;
            dgvTableView.Columns[3].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleLeft;           

        }

        //Formats the main form data grid view and adds modify/delete button columns
        private void TableSetup()
        {
            // add column for modify button
            var modifyColumn = new DataGridViewButtonColumn()
            {
                UseColumnTextForButtonValue = true,
                HeaderText = "",
                Text = "Modify"
            };
            dgvTableView.Columns.Add(modifyColumn);

            // add column for delete button
            var deleteColumn = new DataGridViewButtonColumn()
            {
                UseColumnTextForButtonValue = true,
                HeaderText = "",
                Text = "Delete"
            };
            dgvTableView.Columns.Add(deleteColumn);

            // format the column header
            dgvTableView.EnableHeadersVisualStyles = false;
            dgvTableView.ColumnHeadersDefaultCellStyle.Font = new Font("Arial", 9, FontStyle.Bold);
            dgvTableView.ColumnHeadersDefaultCellStyle.BackColor = Color.DarkBlue;
            dgvTableView.ColumnHeadersDefaultCellStyle.ForeColor = Color.White;

            // format the odd numbered rows
            dgvTableView.AlternatingRowsDefaultCellStyle.BackColor = Color.LightBlue;

        }//endTableSetup

        //Determines if the modify or delete buttons have been clicked and calls
        //The appropriate subform/alert to complete the desired action
        private void ManagePackage(DataGridViewCellEventArgs e)
        {
            const int ModifyIndex = 7;
            const int DeleteIndex = 8;

            if (e.RowIndex >= 0 && (e.ColumnIndex == ModifyIndex || e.ColumnIndex == DeleteIndex))
            {
                string packageID = dgvTableView.Rows[e.RowIndex].Cells[0].Value.ToString().Trim();
                selectedPackage = PackagesManager.Find(Convert.ToInt32(packageID));

                if (e.ColumnIndex == ModifyIndex)
                {
                    ModifyPackage();
                }
                else if (e.ColumnIndex == DeleteIndex)
                {
                    DeletePackage();
                }
            }            
        }

        //Determines if the modify or delete buttons have been clicked and calls
        //The appropriate subform/alert to complete the desired action
        private void ManageProduct(DataGridViewCellEventArgs e)
        {
            const int ModifyIndex = 2;
            const int DeleteIndex = 3;

            if (e.RowIndex >= 0 && (e.ColumnIndex == ModifyIndex || e.ColumnIndex == DeleteIndex))
            {
                string productID = dgvTableView.Rows[e.RowIndex].Cells[0].Value.ToString().Trim();
                selectedProduct = ProductsManager.Find(Convert.ToInt32(productID));

                if (e.ColumnIndex == ModifyIndex)
                {
                    ModifyProduct();
                }
                else if (e.ColumnIndex == DeleteIndex)
                {
                    DeleteProduct();
                }
            }

            
        }

        //Determines if the modify or delete buttons have been clicked and calls
        //The appropriate subform/alert to complete the desired action
        private void ManageSupplier(DataGridViewCellEventArgs e)
        {
            const int ModifyIndex = 2;
            const int DeleteIndex = 3;

            if (e.RowIndex >= 0 && (e.ColumnIndex == ModifyIndex || e.ColumnIndex == DeleteIndex))
            {
                string supplierID = dgvTableView.Rows[e.RowIndex].Cells[0].Value.ToString().Trim();
                selectedSupplier = SuppliersManager.Find(Convert.ToInt32(supplierID));

                if (e.ColumnIndex == ModifyIndex)
                {
                    ModifySupplier();
                }
                else if (e.ColumnIndex == DeleteIndex)
                {
                    DeleteSupplier();
                }
            }

            
        }//Managesupplier      

        
        //******************************************************************************************
        //      Package Database Manipulation 
        //******************************************************************************************
        private void AddPackage()
        {
            var addModifyPackageForm = new frmAddModifyPackage()
            {
                AddPackage = true,
                Package = null
            };

            DialogResult result = addModifyPackageForm.ShowDialog();
            if (result == DialogResult.OK)
            {
                try
                {
                    selectedPackage = addModifyPackageForm.Package;
                    PackagesManager.Add(selectedPackage);
                    DisplayPackages(); // redisplay with the new package that was just added
                }
                catch (DbUpdateException ex)
                {
                    HandleDatabaseError(ex, "Adding Package");
                }
                catch (Exception ex)
                {
                    HandleGeneralError(ex, "Adding Package");
                }
            }
        }

        private void ModifyPackage()
        {
            //initialize modify package form
            //selectedPackage has been set by ManagePackage() method
            frmAddModifyPackage modifyForm = new frmAddModifyPackage()
            {
                AddPackage = false, //set form to modify
                Package = selectedPackage //pass in package to be modified
            };


            //display modify package form and return state of form when it is closed
            DialogResult result = modifyForm.ShowDialog();

            if (result == DialogResult.OK) //modify form has returned valid data
            {                
                try
                {
                    selectedPackage = modifyForm.Package;
                    PackagesManager.Modify(selectedPackage);
                    DisplayPackages(); 
                }
                catch (DbUpdateException ex)
                {
                    HandleDatabaseError(ex, "Modifying Package");
                }
                catch (Exception ex)
                {
                    HandleGeneralError(ex, "Modifying Package");
                }
            }
        }//EndModifyPackage


        private void DeletePackage()
        {
            //Confirm removal with user
            DialogResult result =
                MessageBox.Show($"Are you sure you want to delete {selectedPackage.PkgName.Trim()}?",
                                "Confirm Delete", MessageBoxButtons.YesNo,
                                MessageBoxIcon.Question);
            
            //Remove the selected package
            if (result == DialogResult.Yes)
            {
                try
                {
                    //selectedPackage is updated on dgv cell click in the ManagePackage() method
                    PackagesManager.Remove(selectedPackage);
                    DisplayPackages();
                }
                catch (DbUpdateException ex)
                {
                    HandleDatabaseError(ex, "Deleting Package");
                }
                catch (Exception ex)
                {
                    HandleGeneralError(ex, "Deleting Package");
                }
            }
            
        }//DeletePackage

        //******************************************************************************************
        //      Product Database Manipulation
        //******************************************************************************************

        private void AddProduct()
        {
            frmAddModifyProducts secondFrm = new frmAddModifyProducts();
            secondFrm.isAdd = true;
            secondFrm.product = null; //no customer yet

            //display the second form modal and process result when it closes
            DialogResult result = secondFrm.ShowDialog();

            if (result == DialogResult.OK) //second form has customer object with data
            {
                try
                {
                    selectedProduct = secondFrm.product;
                    ProductsManager.Add(selectedProduct);
                    DisplayProducts();
                }
                catch (DbUpdateException ex)
                {
                    HandleDatabaseError(ex, "Adding Product");
                }
                catch (Exception ex)
                {
                    HandleGeneralError(ex, "Adding Product");
                }
            }
        }

        private void ModifyProduct()
        {

            frmAddModifyProducts secondFrm = new frmAddModifyProducts();
            secondFrm.isAdd = false; //is Modify
            secondFrm.product = selectedProduct;

            //display the second form modal and process result when it closes
            DialogResult result = secondFrm.ShowDialog();

            if (result == DialogResult.OK) //second form has product object with data
            {
                try
                {
                    selectedProduct = secondFrm.product;
                    ProductsManager.Modify(selectedProduct);
                    DisplayProducts();
                }
                catch (DbUpdateException ex)
                {
                    HandleDatabaseError(ex, "Modifying Product");
                }
                catch (Exception ex)
                {
                    HandleGeneralError(ex, "Modifying Product");
                }
            }

        }

        private void DeleteProduct()
        {

            DialogResult result = MessageBox.Show($"Delete {selectedProduct.ProdName}?",
                                    "Confirm Delete", MessageBoxButtons.YesNo,
                                    MessageBoxIcon.Question);

            if (result == DialogResult.Yes)
            {
                try
                {
                    //Selected product is set in the ManageProducts method
                    ProductsManager.Remove(selectedProduct);
                    DisplayProducts();
                }
                catch (DbUpdateException ex)
                {
                    HandleDatabaseError(ex, "Deleting Product");
                }
                catch (Exception ex)
                {
                    HandleGeneralError(ex, "Deleting Product");
                }
            }
        }

        //******************************************************************************************
        //      Supplier Database Manipulation
        //******************************************************************************************

        private void AddSupplier()
        {
            frmAddModifySuppliers addSupplier = new frmAddModifySuppliers();
            addSupplier.AddSupplier = true;
            addSupplier.Supplier = null;


            //display the second form model and process result whem it closes
            DialogResult result = addSupplier.ShowDialog();

            if (result == DialogResult.OK)// second form has a package object with data
            {
                
                try
                {
                    selectedSupplier = addSupplier.Supplier;
                    SuppliersManager.Add(selectedSupplier);
                    DisplaySuppliers();
                }
                catch (DbUpdateException ex)
                {
                    HandleDatabaseError(ex, "Adding Supplier");
                }
                catch (Exception ex)
                {
                    HandleGeneralError(ex, "Adding Supplier");
                }
            }
        }

        private void ModifySupplier()
        {
            frmAddModifySuppliers modifySupplier = new frmAddModifySuppliers()
            {
                AddSupplier = false,
                Supplier = selectedSupplier
            };

            DialogResult result = modifySupplier.ShowDialog();

            if (result == DialogResult.OK)
            {
                try
                {
                    selectedSupplier = modifySupplier.Supplier;
                    SuppliersManager.Modify(selectedSupplier);
                    DisplaySuppliers();
                }
                catch (DbUpdateException ex)
                {
                    HandleDatabaseError(ex, "Modifying Supplier");
                }
                catch (Exception ex)
                {
                    HandleGeneralError(ex, "Modifying Supplier");
                }
            }
        }

        private void DeleteSupplier()
        {
            if (selectedSupplier != null)
            {

                List<PackageDTO> supplierPacks = PackagesManager.FindBySupplier(selectedSupplier.SupplierId);

                if (supplierPacks.Count == 0) //suppier is not linked to any packages so delete ok
                {
                    // get confirmation from the user
                    DialogResult answer = MessageBox.Show($"Do you want to delete {selectedSupplier.SupplierId}?",
                                                            "Confirm Delete", MessageBoxButtons.YesNo,
                                                            MessageBoxIcon.Question);

                    if (answer == DialogResult.Yes)// user confirmed 
                    {
                        try
                        {
                            //selectedSupplier set in ManageSupplier method
                            SuppliersManager.Remove(selectedSupplier);
                            DisplaySuppliers();
                        }
                        catch (DbUpdateException ex)
                        {
                            HandleDatabaseError(ex, "Deleting Supplier");
                        }
                        catch (Exception ex)
                        {
                            HandleGeneralError(ex, "Deleting Supplier");
                        }
                    }
                }
                else //supplier is linked to packages and delete is not allowed
                {
                    string msg = $"Supplier {selectedSupplier.SupplierId} must be removed from the following "
                        + "packages before it can be deleted: \n\n";

                    foreach (PackageDTO p in supplierPacks)
                        msg += p.PkgName + "\n";

                    MessageBox.Show(msg, "Supplier Delete Error");
                }
            }
        }//DeleteSupplier


        //******************************************************************************************
        //      Error Handler Methods
        //******************************************************************************************        

        private void HandleDatabaseError(DbUpdateException ex, string errorAction)
        {
            string errorMsg = $"The following error occured while performing the following action: {errorAction}";
            var sqlException = (SqlException)ex.InnerException;
            foreach (SqlError error in sqlException.Errors)
            {
                errorMsg += "ERROR CODE:  " + error.Number + " " +
                                error.Message + "\n";
            }
            MessageBox.Show(errorMsg, ex.GetType().ToString());
        }

        private void HandleGeneralError(Exception ex, string errorAction)
        {
            string msg = $"The following error occured while performing the following action: {errorAction}"
                         + "\n" + $"ERROR: {ex.Message}.";
            MessageBox.Show(msg, ex.GetType().ToString());
           
        }

    }//Class
}//Namespace
