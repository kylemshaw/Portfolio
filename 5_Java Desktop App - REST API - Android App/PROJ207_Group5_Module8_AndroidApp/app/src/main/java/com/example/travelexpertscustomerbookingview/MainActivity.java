//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Adolphus Cox
//     Description: Activity class that handles logic for the booking details on the main view (activity_main.xml)
//----------------------------------------------------------------------------

package com.example.travelexpertscustomerbookingview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lvBookings;
    TextView tvWelcome;
    Button btnLogout, btnUpdate;

    ArrayList<Booking> list;

    // prevents user from accidentally backing out of a session
    @Override
    public void onBackPressed() {
        // do nothing
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvBookings = findViewById(R.id.lvBookings);
        btnLogout = findViewById(R.id.btnLogout);
        btnUpdate = findViewById(R.id.btnUpdate);
        tvWelcome = findViewById(R.id.tvWelcome);

        // customized action bar -----------------------------------------------
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_layout);
        // ---------------------------------------------------------------------

        Customer customer = (Customer) getIntent().getSerializableExtra("customer");
        tvWelcome.setText("Welcome, " + customer.getCustFirstName());

        loadBookings(customer.getCustomerId(), lvBookings);

        lvBookings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("booking", (Booking)lvBookings.getAdapter().getItem(position));
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIntent().removeExtra("customer");
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CustomerProfileActivity.class);
                intent.putExtra("customer", customer);
                startActivity(intent);
            }
        });
    }

    private void loadBookings(int id, ListView lvBookings) {

        TravelExpertsDataService teDataService = new TravelExpertsDataService(MainActivity.this);
        teDataService.getBookingsByCustomer(id, new TravelExpertsDataService.GetBookingsByCustomerListener() {
            @Override
            public void onError(String message) {
                //Alerts.customToast(MainActivity.this, message);
            }

            @Override
            public void onResponse(ArrayList<Booking> custBookings) {

                // store list of current customer bookings
                list = custBookings;

                TravelExpertsDataService _teDataService = new TravelExpertsDataService(MainActivity.this);

                // before display the current bookings in the list view, a package reference must be added
                // to each booking (using the existing package id) in order to display the package name
                for (Booking b : list) {

                    _teDataService.getPackage(b.getPackageId(), new TravelExpertsDataService.GetPackageListener() {
                        @Override
                        public void onError(String message) {
                            Alerts.customToast(MainActivity.this, message);
                        }

                        @Override
                        public void onResponse(Package pack) {
                            // since a nested response must be done for all actions to be synchronized and not throw an
                            // error, the listview needs to be populated and updated as each booking is updated with
                            // a package reference

                            // setting package reference to booking
                            b.setPkg(pack);

                            // listview is reset by setting to null, and re-populated
                            lvBookings.setAdapter(null);

                            ArrayAdapter<Booking> adapter = new ArrayAdapter<Booking>(
                                    getApplicationContext(), android.R.layout.simple_list_item_1, list) {
                                @NonNull
                                @Override
                                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                    TextView item = (TextView) super.getView(position, convertView, parent);
                                    item.setTypeface(ResourcesCompat.getFont(getContext(), R.font.anonymouspro_bold));
                                    return item;
                                }
                            };

                            lvBookings.setAdapter(adapter);
                        }
                    });
                }
            }
        });
    }
}