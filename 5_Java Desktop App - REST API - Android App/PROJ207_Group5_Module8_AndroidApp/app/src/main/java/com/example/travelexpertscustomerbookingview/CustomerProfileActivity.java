//-----------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Snow Tran and Adolphus Cox
//     Description: Displays agent information and in app calling with
//                  permission request.
//
//-----------------------------------------------------------------------


package com.example.travelexpertscustomerbookingview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerProfileActivity extends AppCompatActivity {

    EditText etCustFirstName, etCustLastName, etCustAddress, etCustCity, etCustPostal,
            etCustHomePhone, etCustBusPhone, etCustEmail, etCustPassword, etCustPasswordConfirm;
    Spinner spnCustProv;
    Button btnSave, btnCancel;
    TextView tvConfirmPassword;
    boolean isPasswordChanged = false;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        // set view references
        etCustFirstName = findViewById(R.id.etCustFirstName);
        etCustLastName = findViewById(R.id.etCustLastName);
        etCustAddress = findViewById(R.id.etCustAddress);
        etCustCity = findViewById(R.id.etCustCity);
        etCustPostal = findViewById(R.id.etCustPostal);
        etCustHomePhone = findViewById(R.id.etCustHomePhone);
        etCustBusPhone = findViewById(R.id.etCustBusPhone);
        etCustEmail = findViewById(R.id.etCustEmail);
        etCustPassword = findViewById(R.id.etCustPassword);
        btnSave = findViewById(R.id.btnSave2);
        btnCancel = findViewById(R.id.btnCancel2);
        spnCustProv = findViewById(R.id.spnCustProv);
        etCustPasswordConfirm = findViewById(R.id.etCustPasswordConfirm);
        tvConfirmPassword = findViewById(R.id.tvConfirmPassword);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_layout);

        //get customer from intent and display
        Customer currentCustomer = (Customer) getIntent().getSerializableExtra("customer");
        displayCustomerData(currentCustomer);

        // add listener of password field, if changed, display password confirmation field
        etCustPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                tvConfirmPassword.setVisibility(1);
                etCustPasswordConfirm.setVisibility(1);
                isPasswordChanged = true;
            }
        });

        // when click save, validate password confirmation if password was changed, and update customer info
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordChanged && !etCustPassword.getText().toString().equals(etCustPasswordConfirm.getText().toString())) {
                    Alerts.customToast_s(getApplicationContext(), "Password confirmation does not match");
                } else {

                    Customer updatedCustomer = new Customer(
                            currentCustomer.getCustomerId(),
                            etCustFirstName.getText().toString(),
                            etCustLastName.getText().toString(),
                            etCustAddress.getText().toString(),
                            etCustCity.getText().toString(),
                            spnCustProv.getSelectedItem().toString(),
                            etCustPostal.getText().toString(),
                            currentCustomer.getCustCountry(),
                            etCustHomePhone.getText().toString(),
                            etCustBusPhone.getText().toString(),
                            etCustEmail.getText().toString(),
                            etCustPassword.getText().toString(),
                            currentCustomer.getAgentId());

                    TravelExpertsDataService teDataService = new TravelExpertsDataService(CustomerProfileActivity.this);
                    teDataService.updateCustomer(updatedCustomer, new TravelExpertsDataService.GetUpdateCustomerListener() {
                        @Override
                        public void onError(String message) {
                            // make short?
                            Alerts.customToast_s(CustomerProfileActivity.this, message);
                        }

                        @Override
                        public void onResponse(String message) {
                            // make short?
                            Alerts.customToast_s(CustomerProfileActivity.this, message);

                            //customerDB.updateCustomer(updateCustomer);
                            //Toast.makeText(getApplicationContext(), "Your information has been updated", Toast.LENGTH_SHORT).show();

                            //change the logged in customer's (currentCustomer) information to the new values
                            currentCustomer.setCustFirstName(updatedCustomer.getCustFirstName());
                            currentCustomer.setCustLastName(updatedCustomer.getCustLastName());
                            currentCustomer.setCustAddress(updatedCustomer.getCustAddress());
                            currentCustomer.setCustCity(updatedCustomer.getCustCity());
                            currentCustomer.setCustProv(updatedCustomer.getCustProv());
                            currentCustomer.setCustPostal(updatedCustomer.getCustPostal());
                            currentCustomer.setCustBusPhone(updatedCustomer.getCustBusPhone());
                            currentCustomer.setCustHomePhone(updatedCustomer.getCustHomePhone());
                            currentCustomer.setCustEmail(updatedCustomer.getCustEmail());
                            currentCustomer.setCustPassword(updatedCustomer.getCustPassword());

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("customer", currentCustomer);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

        // when Cancel button is click, dismiss the current activity
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // set text of all the fields with a customer object as an argument
    private void displayCustomerData(Customer customer) {
        etCustFirstName.setText(customer.getCustFirstName());
        etCustLastName.setText(customer.getCustLastName());
        etCustAddress.setText(customer.getCustAddress());
        etCustCity.setText(customer.getCustCity());
        etCustPostal.setText(customer.getCustPostal());
        etCustHomePhone.setText(customer.getCustHomePhone());
        etCustBusPhone.setText(customer.getCustBusPhone());
        etCustEmail.setText(customer.getCustEmail());
        etCustPassword.setText(customer.getCustPassword());

        // populate the dropdownlist of provinces and set the customer's current province as default
        String[] provinces = new String[]{"AB", "BC", "BM", "NB", "NL", "NT", "NS", "NU", "ON", "PE", "QC", "SK", "YT"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, provinces);
        spnCustProv.setAdapter(adapter);
        spnCustProv.setSelection(adapter.getPosition(customer.getCustProv()));
    }
}