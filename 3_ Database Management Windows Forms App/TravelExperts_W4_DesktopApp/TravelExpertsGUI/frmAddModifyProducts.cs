using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using TechSupportGUI;
using TravelExpertsData.Models;

namespace TravelExpertsGUI
{
    public partial class frmAddModifyProducts : Form
    {
        //public fields (accessible from main form)
        public bool isAdd; //main form sets it; to distinguish Add from Modify
        //this is funky, working, but awkward
        public Products product; //main form sets it for Modify; current product
        public bool AddProduct { get; set; }

        public frmAddModifyProducts()
        {
            InitializeComponent();
        }

        private void frmAddModifyProducts_Load(object sender, EventArgs e)
        {
            if (this.isAdd) //add new product
            {
                this.Text = "Add Product";
                txtProductId.Enabled = false;
            }

            else //Modify
            {
                this.Text = "Modify Products";
                txtProductId.Enabled = false;


                //display current product data
                if (product == null) //just to be on the safe side
                {
                    MessageBox.Show("There is no current product", "Modify Error");
                    this.Close();

                }
                LoadProductData();

            }

        }

        private void btnAccept_Click(object sender, EventArgs e)
        {
            //add in validation!!!!
            if (Validator.IsPresent(txtProdName))
            {
                if (isAdd) //need to make new product object
                {
                    product = new Products();
                }
                //if Modify product object is already there

                //load data from form controls into product object
                ShowProduct();


                this.DialogResult = DialogResult.OK;
            }
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void ShowProduct()
        {
            //product.ProductId = Convert.ToInt32(txtProductId.Text); //don't need this, auto-generated
            product.ProdName = txtProdName.Text;
        }

        private void LoadProductData()
        {
            txtProductId.Text = product.ProductId.ToString();
            txtProdName.Text = product.ProdName;
        }


    }
}
