package com.example.parkingapp.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.SignupModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {


    AppCompatEditText edit_text_name, edit_text_email, edit_text_password, edit_text_number;
    Button i_have_truck, i_have_parking;
    String status_value;
    ProgressDialog progressDialog;
    TextView signin;
    Button signup;
    ImageView google_signup, facebook_signup, twitter_signup;
    private String latitude;
    private String longitude;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    Preferences preferences;

    LocationRequest request;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_class);
        preferences = new Preferences(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Signing up");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        if (checkLocationPermission()) {

            request = new LocationRequest();
            request.setInterval(10000);
            request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
            int permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            if (permission == PackageManager.PERMISSION_GRANTED) {
                client.requestLocationUpdates(request, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        Location location = locationResult.getLastLocation();
                        if (location != null) {
                            //  Log.d(TAG, "location update " + location);
                            longitude = String.valueOf(location.getLongitude());
                            latitude = String.valueOf(location.getLatitude());
                        }
                    }


                }, null);
            }


        } else {
            checkLocationPermission();
        }

        edit_text_email = findViewById(R.id.edit_text_email);
        edit_text_name = findViewById(R.id.edit_text_name);
        edit_text_number = findViewById(R.id.edit_text_number);
        edit_text_password = findViewById(R.id.edit_text_password);

        i_have_truck = findViewById(R.id.i_have_truck);
        i_have_parking = findViewById(R.id.i_have_parking);

        status_value = "I have Truck";

        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        google_signup = findViewById(R.id.google_signup);
        facebook_signup = findViewById(R.id.facebook_signup);
        twitter_signup = findViewById(R.id.twitter_signup);

        callInit();
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    private void callInit() {


        i_have_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status_value = "parking_owner";

                i_have_parking.setBackground(getResources().getDrawable(R.drawable.button_background));
                i_have_parking.setTextColor(getResources().getColor(R.color.white));

                i_have_truck.setBackground(getResources().getDrawable(R.drawable.button_backgrounds));
                i_have_truck.setTextColor(getResources().getColor(R.color.black));
            }
        });

        i_have_truck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status_value = "truck_owner";

                i_have_truck.setBackground(getResources().getDrawable(R.drawable.button_background));
                i_have_truck.setTextColor(getResources().getColor(R.color.white));

                i_have_parking.setBackground(getResources().getDrawable(R.drawable.button_backgrounds));
                i_have_parking.setTextColor(getResources().getColor(R.color.black));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginClass.class));
                finish();
            }


        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

        google_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Signup.this, "Pending Second week", Toast.LENGTH_SHORT).show();
            }
        });
        facebook_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Signup.this, "Pending Second Week", Toast.LENGTH_SHORT).show();
            }
        });

        twitter_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Signup.this, "Pending Second Week", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void validation() {

        String email = edit_text_email.getText().toString();
        String password = edit_text_password.getText().toString();
        String name = edit_text_name.getText().toString();
        String phone = edit_text_number.getText().toString();


        if (TextUtils.isEmpty(email)) {
            edit_text_email.setError("Please Enter Email");
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edit_text_email.setError("Enter valid Email");
//            Toast.makeText(this, "Enter valid Email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            edit_text_email.setError("Please Enter Password");
        } else if (TextUtils.isEmpty(name)) {
            edit_text_name.setError("Please Enter name");
        } else if (TextUtils.isEmpty(phone)) {
            edit_text_number.setError("Please Enter phone");
        } else if (TextUtils.isEmpty(status_value)) {
            Toast.makeText(this, "Please Select type of client", Toast.LENGTH_SHORT).show();
            ;
        } else if (TextUtils.isEmpty(latitude)) {
            Toast.makeText(this, "Location Missing Please provide permission and try again", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(longitude)) {
            Toast.makeText(this, "Location Missing Please provide permission and try again", Toast.LENGTH_SHORT).show();
        } else {
            CallApiServiceSignup(email, password, name, phone);
        }
    }

    private void CallApiServiceSignup(String email, String password, String name, String phone) {
        progressDialog.show();
        APIService service = APIClient.getClient().create(APIService.class);
        Call<SignupModel> modelCall = service.signup(name, email, password, phone, status_value, latitude, longitude, preferences.getFcmToken());
        Log.e("data", "CallApiServiceSignup: "+name+email+password+phone+status_value+latitude+longitude+preferences.getFcmToken() );
        modelCall.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                SignupModel model = response.body();
                progressDialog.dismiss();
                try {


                    if (model.getStatus().equalsIgnoreCase("success")) {
                        preferences.setName(model.getUserData().get(0).getName());
                        preferences.setPhone(model.getUserData().get(0).getPhone());
                        preferences.setPassword(model.getUserData().get(0).getPassword());
                        preferences.setEmail(model.getUserData().get(0).getEmail());
                        preferences.setTime(model.getUserData().get(0).getTimestamp());
                        preferences.setUserId(model.getUserData().get(0).getUserId());
                        preferences.setStatusValue(model.getUserData().get(0).getStatus_value());
                        preferences.setLatitude(model.getUserData().get(0).getLatitude());
                        preferences.setLongitude(model.getUserData().get(0).getLongitude());


                        preferences.setSession(true);
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                    } else {

                        Toast.makeText(Signup.this, model.getError(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.fillInStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Signup.this, "No network available", Toast.LENGTH_SHORT).show();
                Log.e("error", "onFailure: " + t.getMessage());
            }
        });


    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Signup.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        request = new LocationRequest();
                        request.setInterval(10000);
                        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
                        int permission = ContextCompat.checkSelfPermission(this,
                                Manifest.permission.ACCESS_FINE_LOCATION);
                        if (permission == PackageManager.PERMISSION_GRANTED) {
                            client.requestLocationUpdates(request, new LocationCallback() {
                                @Override
                                public void onLocationResult(LocationResult locationResult) {
                                    super.onLocationResult(locationResult);
                                    Location location = locationResult.getLastLocation();
                                    if (location != null) {
                                        //  Log.d(TAG, "location update " + location);
                                        longitude = String.valueOf(location.getLongitude());
                                        latitude = String.valueOf(location.getLatitude());
                                    }
                                }


                            }, null);
                        }
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }

    }


}
