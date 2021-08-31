using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TechSupportGUI
{
    /// <summary>
    /// A repository of validation methods
    /// </summary>
    public static class Validator
    {
        /// <summary>
        /// Checks if a textbox has a value (not empty).
        /// </summary>
        /// <param name="txtInput"> text box to validate
        /// (must have Tag set to meaningful name for error message)</param>
        /// <returns>false if blank, true if not</returns>
        public static bool IsPresent(TextBox txtInput)
        {
            //if test data is invlaid set to false (assume its valid to start)
            bool isValid = true; 

            if(txtInput.Text == "")//if text is an empty string
            {
                MessageBox.Show(txtInput.Tag + " must be provided", "Input Error");
                txtInput.Focus();//put focus on the text box
                isValid = false; //date is invalid
            }

            return isValid;
        }

        /// <summary>
        /// checks for a positive integer
        /// </summary>
        /// <param name="txtInput"> text box to validate
        /// (must have Tag set to meaningful name for error message)</param>
        /// <returns>true if positive int, false otherwise</returns>
        public static bool IsNonNegativeInt(TextBox txtInput)
        {
            bool isValid = true; //if test data is invlaid set to false (assume its valid to start)
            int value; //value stored in text box

            //try to parce text box value as an int, if successfull (its an int) then store result in value
            if (!Int32.TryParse(txtInput.Text, out value))
            {
                //not successfull so show error
                MessageBox.Show(txtInput.Tag + " should be a whole number", "Input Error");
                txtInput.SelectAll();
                txtInput.Focus();
                isValid = false;
            }
            else //test the parsed int to see if it is negative 
            {
                if (value < 0)
                {
                    //value is invlaid (negative) so show error
                    MessageBox.Show(txtInput.Tag + " should be greater than or equal to zero", "Input Error");
                    txtInput.SelectAll();
                    txtInput.Focus();
                    isValid = false;
                }
            }

            return isValid;
        }

        /// <summary>
        /// checks for a positive number (decimals ok)
        /// </summary>
        /// <param name="txtInput"> text box to validate
        /// (must have Tag set to meaningful name for error message)</param>
        /// <returns>true if positive number, false otherwise</returns>
        public static bool IsNonNegativeDouble(TextBox txtInput)
        {
            bool isValid = true;
            double value;

            //try to parce text box value as a double, if successfull (its a double) then store result in value
            if (!Double.TryParse(txtInput.Text, out value))
            {
                //not successfull so show error
                MessageBox.Show(txtInput.Tag + " should be a number", "Input Error");
                txtInput.SelectAll();
                txtInput.Focus();
                isValid = false;
            }
            else //test the parsed double to see if it is negative 
            {
                if (value < 0)
                {
                    //value is invlaid (negative) so show error
                    MessageBox.Show(txtInput.Tag + " should be greater than or equal to zero", "Input Error");
                    txtInput.SelectAll();
                    txtInput.Focus();
                    isValid = false;
                }
            }

            return isValid;
        }

        /// <summary>
        /// checks for a positive number (decimals ok)
        /// </summary>
        /// <param name="txtInput"> text box to validate
        /// (must have Tag set to meaningful name for error message)</param>
        /// <returns>true if positive number, false otherwise</returns>
        public static bool IsNonNegativeDecimal(TextBox txtInput)
        {
            bool isValid = true;
            decimal value;

            //try to parce text box value as a decimal , if successfull (its a decimal) then store result in value
            if (!Decimal.TryParse(txtInput.Text, out value))
            {
                //not successfull so show error
                MessageBox.Show(txtInput.Tag + " should be a number", "Input Error");
                txtInput.SelectAll();
                txtInput.Focus();
                isValid = false;
            }
            else //test the parsed decimal to see if it is negative 
            {
                if (value < 0)
                {
                    //value is invlaid (negative) so show error
                    MessageBox.Show(txtInput.Tag + " should be greater than or equal to zero", "Input Error");
                    txtInput.SelectAll();
                    txtInput.Focus();
                    isValid = false;
                }
            }

            return isValid;
        }

        /// <summary>
        /// checks for a double x such that min <= x <= max
        /// </summary>
        /// <param name="txtInput"> text box to validate
        /// (must have Tag set to meaningful name for error message)</param>
        /// <param name="max"> Maximum allowed value</param>
        /// <param name="min"> Minimum value allowed</param>
        /// <returns>true if double in specified range, false otherwise</returns>
        public static bool IsDoubleInRange(TextBox txtInput, double min, double max)
        {
            bool isValid = true;
            double value;

            //try to parce text box value as a double, if successfull (its a double) then store result in value
            if (!Double.TryParse(txtInput.Text, out value))
            {
                //not successfull so show error
                MessageBox.Show($"{txtInput.Tag} should be a number.", "Input Error");
                txtInput.SelectAll();
                txtInput.Focus();
                isValid = false;
            }
            else //test the double to see if it is within the specified range
            {
                if (value < min || value > max)
                {
                    //value is invlaid (not in range) so show error
                    MessageBox.Show($"{txtInput.Tag} should be between {min} and {max}.", "Input Error");
                    txtInput.SelectAll();
                    txtInput.Focus();
                    isValid = false;
                }
            }

            return isValid;
        }

        /// <summary>
        /// checks if combo box has a value selected
        /// </summary>
        /// <param name="cboInput"> combo box to validate
        /// (must have Tag set to meaningful name for error message)</param>
        /// <returns>true if a value has been selected, false otherwise</returns>
        public static bool IsSelected(ComboBox cboInput)
        {
            bool isValid = true;

            if(cboInput.SelectedIndex == -1) //not selected
            {
                MessageBox.Show($"{cboInput.Tag} should be a number.", "Input Error");
                cboInput.Focus();
                isValid = false;
            }

            return isValid;
        }

        /// <summary>
        /// Tests if the decimal entered in txtSmaller is less than the 
        /// decimal in txtLarger.
        /// </summary>
        /// <param name="txtSmaller">Textbox containing smaller decimal</param>
        /// <param name="txtLarger">Textbox containing larger decimal</param>
        /// <returns>Returns true if larger is greater than smaller, false otherwise</returns>

        public static bool IsDecimalLessThan(TextBox txtSmaller, TextBox txtLarger)
        {
            bool isValid = true;

            if(txtSmaller.Text != "") // if small is not provided then true
            {
                //make sure text boxes have decimals
                if (IsNonNegativeDecimal(txtSmaller) &&
                    IsNonNegativeDecimal(txtLarger)
                    )
                {
                    //get decimal values from text box
                    decimal smaller = Convert.ToDecimal(txtSmaller.Text);
                    decimal larger = Convert.ToDecimal(txtLarger.Text);

                    //if smaller is larger then provide an error message and return false
                    if (smaller > larger)
                    {
                        MessageBox.Show($"{txtSmaller.Tag} must be less than {txtLarger.Tag}.", "Input Error");
                        txtSmaller.SelectAll();
                        txtSmaller.Focus();
                        isValid = false;
                    }
                }
                else //text boxes are not decimals then can't compare and result is false
                {
                    isValid = false;
                }
            }           
            
            return isValid;
        }

        /// <summary>
        /// Checks if the value set for dtpEarly is set to a date that is before the date
        /// set for dtpLate
        /// </summary>
        /// <param name="dtpEarly">dtp with the earliest date</param>
        /// <param name="dtpLate">dtp with the latest date</param>
        /// <returns>true if early is before late, false otherwise</returns>
        public static bool IsDateAfter(DateTimePicker dtpEarly, DateTimePicker dtpLate)
        {
            bool isValid = true;
           
            //if the date in dtpEarly comes after the date in dtpLate then show error and return false
            if (DateTime.Compare(dtpEarly.Value, dtpLate.Value) > 0) //>0 if d1 is later than d2
            {
                MessageBox.Show($"{dtpLate.Tag} must be come after {dtpEarly.Tag}.", "Input Error");
                dtpLate.Select();
                dtpLate.Focus();
                isValid = false;
            }
            
            return isValid;
        }


    }
}
