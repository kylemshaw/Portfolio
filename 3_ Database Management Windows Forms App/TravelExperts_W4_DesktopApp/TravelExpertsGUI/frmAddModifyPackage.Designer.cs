
namespace TravelExpertsGUI
{
    partial class frmAddModifyPackage
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.lblProducts = new System.Windows.Forms.Label();
            this.btnEditProducts = new System.Windows.Forms.Button();
            this.lstProducts = new System.Windows.Forms.ListBox();
            this.btnCancel = new System.Windows.Forms.Button();
            this.btnConfirm = new System.Windows.Forms.Button();
            this.txtAgencyCommission = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.txtPackageBasePrice = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.txtPackageDescription = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.txtPackageName = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.dtpStartDate = new System.Windows.Forms.DateTimePicker();
            this.dtpEndDate = new System.Windows.Forms.DateTimePicker();
            this.chkStartDate = new System.Windows.Forms.CheckBox();
            this.chkEndDate = new System.Windows.Forms.CheckBox();
            this.SuspendLayout();
            // 
            // lblProducts
            // 
            this.lblProducts.AutoSize = true;
            this.lblProducts.Location = new System.Drawing.Point(16, 191);
            this.lblProducts.Name = "lblProducts";
            this.lblProducts.Size = new System.Drawing.Size(104, 15);
            this.lblProducts.TabIndex = 46;
            this.lblProducts.Text = "Selected Products:";
            // 
            // btnEditProducts
            // 
            this.btnEditProducts.Location = new System.Drawing.Point(384, 209);
            this.btnEditProducts.Name = "btnEditProducts";
            this.btnEditProducts.Size = new System.Drawing.Size(75, 23);
            this.btnEditProducts.TabIndex = 45;
            this.btnEditProducts.Text = "&Edit Products";
            this.btnEditProducts.UseVisualStyleBackColor = true;
            this.btnEditProducts.Click += new System.EventHandler(this.btnEditProducts_Click);
            // 
            // lstProducts
            // 
            this.lstProducts.BackColor = System.Drawing.SystemColors.ControlLight;
            this.lstProducts.Font = new System.Drawing.Font("Consolas", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.lstProducts.FormattingEnabled = true;
            this.lstProducts.ItemHeight = 14;
            this.lstProducts.Location = new System.Drawing.Point(21, 209);
            this.lstProducts.Name = "lstProducts";
            this.lstProducts.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
            this.lstProducts.Size = new System.Drawing.Size(357, 88);
            this.lstProducts.TabIndex = 44;
            // 
            // btnCancel
            // 
            this.btnCancel.Location = new System.Drawing.Point(277, 331);
            this.btnCancel.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btnCancel.Name = "btnCancel";
            this.btnCancel.Size = new System.Drawing.Size(82, 22);
            this.btnCancel.TabIndex = 43;
            this.btnCancel.Text = "C&ancel";
            this.btnCancel.UseVisualStyleBackColor = true;
            this.btnCancel.Click += new System.EventHandler(this.btnCancel_Click);
            // 
            // btnConfirm
            // 
            this.btnConfirm.Location = new System.Drawing.Point(149, 331);
            this.btnConfirm.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btnConfirm.Name = "btnConfirm";
            this.btnConfirm.Size = new System.Drawing.Size(82, 22);
            this.btnConfirm.TabIndex = 42;
            this.btnConfirm.Text = "&Confirm";
            this.btnConfirm.UseVisualStyleBackColor = true;
            this.btnConfirm.Click += new System.EventHandler(this.btnConfirm_Click);
            // 
            // txtAgencyCommission
            // 
            this.txtAgencyCommission.Location = new System.Drawing.Point(149, 155);
            this.txtAgencyCommission.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.txtAgencyCommission.Name = "txtAgencyCommission";
            this.txtAgencyCommission.Size = new System.Drawing.Size(110, 23);
            this.txtAgencyCommission.TabIndex = 41;
            this.txtAgencyCommission.Tag = "Agency Commission";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(16, 157);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(120, 15);
            this.label7.TabIndex = 40;
            this.label7.Text = "Agency Commission:";
            // 
            // txtPackageBasePrice
            // 
            this.txtPackageBasePrice.Location = new System.Drawing.Point(149, 130);
            this.txtPackageBasePrice.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.txtPackageBasePrice.Name = "txtPackageBasePrice";
            this.txtPackageBasePrice.Size = new System.Drawing.Size(110, 23);
            this.txtPackageBasePrice.TabIndex = 39;
            this.txtPackageBasePrice.Tag = "Base Price";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(24, 132);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(110, 15);
            this.label6.TabIndex = 38;
            this.label6.Text = "Package Base Price:";
            // 
            // txtPackageDescription
            // 
            this.txtPackageDescription.Location = new System.Drawing.Point(149, 103);
            this.txtPackageDescription.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.txtPackageDescription.MaxLength = 50;
            this.txtPackageDescription.Name = "txtPackageDescription";
            this.txtPackageDescription.Size = new System.Drawing.Size(339, 23);
            this.txtPackageDescription.TabIndex = 37;
            this.txtPackageDescription.Tag = "Package Description";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(16, 105);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(117, 15);
            this.label5.TabIndex = 36;
            this.label5.Text = "Package Description:";
            // 
            // txtPackageName
            // 
            this.txtPackageName.Location = new System.Drawing.Point(145, 21);
            this.txtPackageName.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.txtPackageName.MaxLength = 50;
            this.txtPackageName.Name = "txtPackageName";
            this.txtPackageName.Size = new System.Drawing.Size(339, 23);
            this.txtPackageName.TabIndex = 31;
            this.txtPackageName.Tag = "Package Name";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(47, 27);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(89, 15);
            this.label2.TabIndex = 30;
            this.label2.Text = "Package Name:";
            // 
            // dtpStartDate
            // 
            this.dtpStartDate.Location = new System.Drawing.Point(166, 49);
            this.dtpStartDate.Name = "dtpStartDate";
            this.dtpStartDate.Size = new System.Drawing.Size(200, 23);
            this.dtpStartDate.TabIndex = 47;
            this.dtpStartDate.Tag = "Start Date";
            // 
            // dtpEndDate
            // 
            this.dtpEndDate.Location = new System.Drawing.Point(166, 74);
            this.dtpEndDate.Name = "dtpEndDate";
            this.dtpEndDate.Size = new System.Drawing.Size(200, 23);
            this.dtpEndDate.TabIndex = 48;
            this.dtpEndDate.Tag = "End Date";
            // 
            // chkStartDate
            // 
            this.chkStartDate.AutoSize = true;
            this.chkStartDate.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.chkStartDate.Location = new System.Drawing.Point(24, 54);
            this.chkStartDate.Name = "chkStartDate";
            this.chkStartDate.Size = new System.Drawing.Size(136, 19);
            this.chkStartDate.TabIndex = 49;
            this.chkStartDate.Text = "Package Start Date:   \r\n";
            this.chkStartDate.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.chkStartDate.UseVisualStyleBackColor = true;
            this.chkStartDate.CheckedChanged += new System.EventHandler(this.chkStartDate_CheckedChanged);
            // 
            // chkEndDate
            // 
            this.chkEndDate.AutoSize = true;
            this.chkEndDate.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.chkEndDate.Location = new System.Drawing.Point(28, 79);
            this.chkEndDate.Name = "chkEndDate";
            this.chkEndDate.Size = new System.Drawing.Size(132, 19);
            this.chkEndDate.TabIndex = 51;
            this.chkEndDate.Text = "Package End Date:   ";
            this.chkEndDate.UseVisualStyleBackColor = true;
            this.chkEndDate.CheckedChanged += new System.EventHandler(this.chkEndDate_CheckedChanged);
            // 
            // frmAddModifyPackage
            // 
            this.AcceptButton = this.btnConfirm;
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.CancelButton = this.btnCancel;
            this.ClientSize = new System.Drawing.Size(504, 385);
            this.Controls.Add(this.chkEndDate);
            this.Controls.Add(this.chkStartDate);
            this.Controls.Add(this.dtpEndDate);
            this.Controls.Add(this.dtpStartDate);
            this.Controls.Add(this.lblProducts);
            this.Controls.Add(this.btnEditProducts);
            this.Controls.Add(this.lstProducts);
            this.Controls.Add(this.btnCancel);
            this.Controls.Add(this.btnConfirm);
            this.Controls.Add(this.txtAgencyCommission);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.txtPackageBasePrice);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.txtPackageDescription);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.txtPackageName);
            this.Controls.Add(this.label2);
            this.Name = "frmAddModifyPackage";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "frmAddModifyPackage";
            this.Load += new System.EventHandler(this.frmAddModifyPackage_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lblProducts;
        private System.Windows.Forms.Button btnEditProducts;
        private System.Windows.Forms.ListBox lstProducts;
        private System.Windows.Forms.Button btnCancel;
        private System.Windows.Forms.Button btnConfirm;
        private System.Windows.Forms.TextBox txtAgencyCommission;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.TextBox txtPackageBasePrice;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox txtPackageDescription;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox txtPackageName;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.DateTimePicker dtpStartDate;
        private System.Windows.Forms.DateTimePicker dtpEndDate;
        private System.Windows.Forms.CheckBox chkStartDate;
        private System.Windows.Forms.CheckBox chkEndDate;
    }
}