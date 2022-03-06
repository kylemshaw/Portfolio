//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Kyle Shaw
//     Description: Centralized validation class to validate input fields
//----------------------------------------------------------------------------

package com.te.dbmanager.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    /**
     * Checks if TextField is empty
     * @param tfInput
     * @return false if empty, true if contains 1 or more characters
     */
    public static Boolean IsPresent(TextField tfInput){
        Boolean isValid = true;

        if(tfInput.getText().equals("")){
            DisplayAlert.showWarning(tfInput.getTooltip().getText() + " is required.");
            tfInput.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    /**
     * Checks if ComboBox has no value
     * @param cboInput
     * @return false if empty, true if value != null
     */
    public static Boolean IsPresent(ComboBox cboInput){
        Boolean isValid = true;

        if(cboInput.getValue() == null){
            DisplayAlert.showWarning("A value must be selected for " + cboInput.getTooltip().getText());
            cboInput.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    /**
     * Checks for the following pattern: "^[a-zA-Z][0-9][a-zA-Z] ?[0-9][a-zA-Z][0-9]$"
     * @param tfInput
     * @return true on pattern match
     */
    public static Boolean IsValidPostal(TextField tfInput){
        Boolean isValid = true;

        String regex = "^[a-zA-Z][0-9][a-zA-Z] ?[0-9][a-zA-Z][0-9]$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(tfInput.getText().toString());

        if(!matcher.find()){
            Alert a = new Alert(Alert.AlertType.ERROR, "Postal code is not valid.");
            a.show();
            tfInput.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    /**
     * Checks for the following pattern: "^(\\w+\\.)*(\\w+)@(\\w+\\.)+[a-z]{2,}"
     * @param tfInput
     * @return true on pattern match
     */
    public static Boolean IsValidEmail(TextField tfInput){
        Boolean isValid = true;

        // [zero or many word objects ending with a .][word object][mandatory @][one or more word objects ending in.][at least two letters][end of string]
        String regex = "^(\\w+\\.)*(\\w+)@(\\w+\\.)+[a-z]{2,}";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(tfInput.getText().toString());

        if(!matcher.find()){
            DisplayAlert.showWarning("Email is not valid.");
            tfInput.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    /**
     * Assumes empty string is valid. If not empty checks this pattern: "^([0-9]-?){10}$"
     * @param tfInput
     * @return true if empty or on pattern match
     */
    public static Boolean IsValidPhone(TextField tfInput){
        Boolean isValid = true;

        //if empty then assume its valid
        if(tfInput.getText().equals(""))
            return isValid;

        // 10 digits with the option of a single "-" after each digit
        String regex = "^([0-9]-?){10}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(tfInput.getText());

        if(!matcher.find()){
            DisplayAlert.showWarning( tfInput.getTooltip().getText() +
                    " must contain 10 digits \n(Single '-' are allowed i.e. 555-555-5555)");
            tfInput.requestFocus();
            isValid = false;
        }

        return isValid;
    }
}
