//-----------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Kyle Shaw
//     Description: Displays agent information and in app calling with
//                  permission request.
//
//-----------------------------------------------------------------------

package com.example.travelexpertscustomerbookingview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

public class AgentContactActivity extends AppCompatActivity {

    private final int DEFAULT_AGENT = R.drawable.profilepic_transparent; //default agent pic stored in app
    Intent callIntent;
    Activity thisActivity = this;
    Agency agency = new Agency();
    ConstraintLayout clAgentContact;
    ImageView ivAgentPhoto;
    TextView tvAgentName, tvAgtBusPhone, tvAgtEmail, tvAgencyCity, tvAgencyAddress, tvAgencyPhone;
    Button btnBack;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_contact);

        //set view references
        clAgentContact = findViewById(R.id.clAgentContact);
        ivAgentPhoto = findViewById(R.id.ivAgentPhoto);
        tvAgentName = findViewById(R.id.tvAgentName);
        tvAgtBusPhone = findViewById(R.id.tvAgtBusPhone);
        tvAgtEmail = findViewById(R.id.tvAgtEmail);
        tvAgencyCity = findViewById(R.id.tvAgencyCity);
        tvAgencyAddress = findViewById(R.id.tvAgencyAddress);
        tvAgencyPhone = findViewById(R.id.tvAgencyPhone);
        btnBack = findViewById(R.id.btnBack);

        //setup underlines for phone numbers
        tvAgtBusPhone.setPaintFlags(tvAgtBusPhone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvAgencyPhone.setPaintFlags(tvAgencyPhone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_layout);

        //Get agent from intent and display
        Agent agent = (Agent) getIntent().getSerializableExtra("agent");
        tvAgentName.setText(agent.toString());
        tvAgtBusPhone.setText(formatPhone(agent.getAgtBusPhone()));
        tvAgtEmail.setText(agent.getAgtEmail());

        //get agent image from REST API and display
        setAgentImage(ivAgentPhoto, agent);

        //get agent's agency from REST API and display
        TravelExpertsDataService teDataService = new TravelExpertsDataService(AgentContactActivity.this);
        teDataService.getAgency(agent.getAgencyId(), new TravelExpertsDataService.GetAgencyListener() {
            @Override
            public void onError(String message) {
                Alerts.customToast(AgentContactActivity.this, message);
            }

            @Override
            public void onResponse(Agency agncy) {
                agency = agncy;
                tvAgencyCity.setText(agency.toString());
                tvAgencyAddress.setText(agency.getAgencyAddress());
                tvAgencyPhone.setText(formatPhone(agency.getAgencyPhone()));
            }

        });

        //close activity to return to booking detail
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //call agent phone on click/touch
        tvAgtBusPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "tel: " + tvAgtBusPhone.getText().toString();
                callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(number));
                startCall(callIntent);
            }
        });

        //call agency phone on click/touch
        tvAgencyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "tel: " + tvAgencyPhone.getText().toString();
                callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(number));
                startCall(callIntent);
            }
        });

    }//end_Initialize

    //Event triggered after user has denied or allowed a system permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //phone permission request code  == 0
        if(requestCode == 0){
            //if a positive result has been returned start call
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startActivity(callIntent);
            }
            else{
                //provide user with feedback on why call failed
                Snackbar.make(clAgentContact, "Phone call denied. Change app permissions " +
                        "to make a call in the app.", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void startCall(Intent callIntent) {
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            //permission is granted so start activity
            startActivity(callIntent);
        }
        else if(ActivityCompat.shouldShowRequestPermissionRationale(thisActivity, Manifest.permission.CALL_PHONE)){
            // True if the app has requested this permission previously and the user denied the
            // request show message explaining why the permission is needed then send user to the
            // system requestPermissions dialog for a second time
            callPermissionRational().show();
        }
        else{
            //Ask for permission the first time.
            //After user has denied system permission request twice (once here, once in else if) requestPermissions
            //is called from here but is automatically set to deny (user has to change permission in settings)
            ActivityCompat.requestPermissions(thisActivity, new String[]{Manifest.permission.CALL_PHONE}, 0);
        }
    }

    //explains to the user why the permission is required
    public AlertDialog callPermissionRational(){
        AlertDialog dialogBox = new AlertDialog.Builder(thisActivity)
                .setTitle("Allow in-app calls?")
                .setMessage("Phone permission is required to call an agent or agency from the app.")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //ask for permission again
                        ActivityCompat.requestPermissions(thisActivity, new String[]{Manifest.permission.CALL_PHONE}, 0);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return dialogBox;
    }


    // Formats phone numbers for consistency
    // If phone number does not contain 10 digits, output: "---"
    // This prevents invalid numbers from being displayed to the
    // customer in the app.
    public String formatPhone(String phone)  {
        //extract numbers to remove existing formatting
        StringBuilder sbPhone = new StringBuilder();
        for (int i = 0; i < phone.length(); i++) {
            if(Character.isDigit(phone.charAt(i))){
                sbPhone.append(phone.charAt(i));
            }
        }

        //check for valid length then format
        if(sbPhone.length() != 10){
            //throw new IllegalArgumentException("Invalid phone number."); //add throws to method declaration if throw error instead.
            sbPhone = new StringBuilder("---");
        }
        else{
            sbPhone.insert(0,'(');
            sbPhone.insert(4,')');
            sbPhone.insert(5,'-');
            sbPhone.insert(9,'-');
        }
        return sbPhone.toString();
    }

    //Get and set agent image using REST API with volley.
    //TODO: convert to asynch callback and move to TravelExpertsDataService
    private void setAgentImage(ImageView iv, Agent agent) {
        String url = "http://10.0.2.2:8080/TravelExpertsREST-1.0-SNAPSHOT/api/agent/getagentimg/" + agent.getAgtFirstName() + ".jpg";
        ImageRequest imgRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                iv.setImageBitmap(response);
                Log.d("kyle", "setAgentImage success: " + url);

            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                iv.setImageResource(DEFAULT_AGENT);
                Log.d("kyle", "setAgentImage error: " + error);
                //Alerts.customToast(getApplicationContext(),"Agent image failed to download.");
            }
        });

        //send request to volley with 1sec timeout so if API access fails we load the default image quickly
        imgRequest.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(AgentContactActivity.this);
        requestQueue.add(imgRequest);
    }
}