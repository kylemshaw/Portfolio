//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Adolphus Cox
//     Description: Activity class that handles logic for the Log In view (activity_login.xml)
//----------------------------------------------------------------------------

package com.example.travelexpertscustomerbookingview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail;
    EditText etPassword;
    Button btnLogin;
    Customer customer;

    boolean isAuthenticated = false;

    // prevents user from going back into a session once already logged out
    @Override
    public void onBackPressed() {
        // do nothing
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // customized action bar -----------------------------------------------
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_layout);
        // ---------------------------------------------------------------------

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TravelExpertsDataService teDataService = new TravelExpertsDataService(LoginActivity.this);
                teDataService.getAllCustomers(new TravelExpertsDataService.GetAllCustomersListener() {
                    @Override
                    public void onError(String message) {
                        Alerts.customToast(LoginActivity.this, message);
                    }

                    @Override
                    public void onResponse(ArrayList<Customer> custList) {
                        boolean isValid = true;

                        // retrieving all customer entries from the database
                        ArrayList<Customer> list = custList;

                        // checking for blank entries in the email and password fields
                        if (etEmail.getText().toString().matches("")) {
                            Alerts.customToast(getApplicationContext(),"Fill Email");
                            isValid = false;
                        }

                        if (etPassword.getText().toString().matches("") && isValid) {
                            Alerts.customToast(getApplicationContext(),"Fill Password");
                            isValid = false;
                        }

                        // if validation passes, loop through all customers to check for an email
                        // and password that matches the user input
                        // if there's a match, proceed to main activity and pass current customer
                        // to that activity
                        if (isValid) {
                            for (Customer c : list) {
                                if (c.getCustEmail().equals(etEmail.getText().toString())
                                        && c.getCustPassword().equals(etPassword.getText().toString())) {
                                    isAuthenticated = true;
                                    customer = c;
                                }
                            }

                            if (isAuthenticated) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("customer", customer);
                                startActivity(intent);
                            } else {
                                Alerts.customToast(getApplicationContext(),"The password or email is incorrect.");
                            }
                        }
                    }
                });
            }
        });
    }
}