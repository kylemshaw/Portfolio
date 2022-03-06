//-----------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Snow Tran and Adolphus Cox
//     Description: Displays booking details when one is chosen from the
//                  main booking list.
//
//-----------------------------------------------------------------------

package com.example.travelexpertscustomerbookingview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    TextView tvBookingId, tvBookingDate, tvBookingNo, tvTravelCount, tvPackage, tvAgent;
    Button btnBack, btnContact;
    Agent agent = new Agent();
    Package pkg = new Package();

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvBookingId = findViewById(R.id.tvBookingId);
        tvBookingDate = findViewById(R.id.tvBookingDate);
        tvBookingNo = findViewById(R.id.tvBookingNo);
        tvTravelCount = findViewById(R.id.tvTravelerCount);
        tvPackage = findViewById(R.id.tvPackage);
        tvAgent = findViewById(R.id.tvAgent);
        btnBack = findViewById(R.id.btnBack2);
        btnContact = findViewById(R.id.btnContact);

        // customized action bar -----------------------------------------------
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_layout);
        // ---------------------------------------------------------------------

        Booking booking = (Booking) getIntent().getSerializableExtra("booking");

        TravelExpertsDataService teDataService = new TravelExpertsDataService(DetailActivity.this);
        teDataService.getAgent(booking.getAgentId(), new TravelExpertsDataService.GetAgentListener() {
            @Override
            public void onError(String message) {
                Alerts.customToast(DetailActivity.this, message);
            }

            @Override
            public void onResponse(Agent a) {
                agent = a;
                tvAgent.setText(agent.toString());
            }
        });

        teDataService.getPackage(booking.getPackageId(), new TravelExpertsDataService.GetPackageListener() {
            @Override
            public void onError(String message) {
                Alerts.customToast(DetailActivity.this, message);
            }

            @Override
            public void onResponse(Package p) {
                pkg = p;
                tvPackage.setText(pkg.getPkgName());
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.CANADA);

        tvBookingId.setText(booking.getBookingId() + "");
        tvBookingDate.setText(sdf.format(booking.getBookingDate()));
        tvBookingNo.setText(booking.getBookingNo());
        tvTravelCount.setText((int)booking.getTravelerCount() + "");

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AgentContactActivity.class);
                intent.putExtra("agent", agent);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}