package com.example.parkingapp.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.SignupModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.schibstedspain.leku.LocationPickerActivity;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.schibstedspain.leku.LocationPickerActivityKt.LATITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.LOCATION_ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.LONGITUDE;

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
    TextView add_location;
    LocationRequest request;
    private CallbackManager callbackManager;
    TwitterAuthClient mTwitterAuthClient;
    private GoogleSignInClient googleSignInClient;
    private ConstraintLayout abc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = new Preferences(this);
        setContentView(R.layout.activity_signup_class);

        abc=findViewById(R.id.abc);

       /* if (preferences.getSwitchNightMod()){
            abc.setBackgroundColor(getResources().getColor(R.color.black));
        }else {
            abc.setBackgroundColor(getResources().getColor(R.color.white));
        }*/

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Signing up");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        checkLocationEnsblle();
        callbackManager = CallbackManager.Factory.create();

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))//enable logging when app is in debug mode
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))//pass the created app Consumer KEY and Secret also called API Key and Secret
                .debug(true)//enable debug mode
                .build();

        //finally initialize twitter_shape with created configs
        Twitter.initialize(config);
        mTwitterAuthClient  = new TwitterAuthClient();



   /*      if (checkLocationPermission()) {

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
*/
        add_location=findViewById(R.id.add_location);
        add_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLocationPermission()){
                    LocationGettingMechanism();
                }else {
                    checkLocationPermission();
                }
            }
        });

        edit_text_email = findViewById(R.id.edit_text_email);
        edit_text_name = findViewById(R.id.edit_text_name);
        edit_text_number = findViewById(R.id.edit_text_number);
        edit_text_password = findViewById(R.id.edit_text_password);

        i_have_truck = findViewById(R.id.i_have_truck);
        i_have_parking = findViewById(R.id.i_have_parking);

        status_value = "truck_owner";

        if (preferences.getSwitchNightMod()){
            abc.setBackgroundColor(getResources().getColor(R.color.black));
            edit_text_email.setTextColor(getResources().getColor(R.color.white));
            edit_text_password.setTextColor(getResources().getColor(R.color.white));
            edit_text_name.setTextColor(getResources().getColor(R.color.white));
            edit_text_number.setTextColor(getResources().getColor(R.color.white));
            add_location.setTextColor(getResources().getColor(R.color.white));
        }else {
            abc.setBackgroundColor(getResources().getColor(R.color.white));
            edit_text_email.setTextColor(getResources().getColor(R.color.black));
            edit_text_password.setTextColor(getResources().getColor(R.color.black));
            edit_text_name.setTextColor(getResources().getColor(R.color.black));
            edit_text_number.setTextColor(getResources().getColor(R.color.black));
            add_location.setTextColor(getResources().getColor(R.color.black));
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(Signup.this, gso);



        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        google_signup = findViewById(R.id.google_signup);
        facebook_signup = findViewById(R.id.facebook_signup);
        twitter_signup = findViewById(R.id.twitter_signup);

        callInit();
    }


    private void LocationGettingMechanism() {
        if (latitude!=null && longitude!=null) {
            LatLng latLng=new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
            Intent locationPickerIntent = new LocationPickerActivity.Builder()
                    //  .withLocation(40.7128, 74.0060)
                    .withGeolocApiKey("AIzaSyBiWCqUwYcKgZyvusgkFOKfop1vA2dLZnE")
                    .withSearchZone("es_ES")
                    .withLocation(latLng)
//                        .withStreetHidden()
//                        .withCityHidden()
//                        .withZipCodeHidden()
//                        .withSatelliteViewHidden()
                    .withGooglePlacesEnabled()
                    //   .withVoiceSearchHidden()
                    .build(getApplicationContext());

            startActivityForResult(locationPickerIntent, 1);
        }else{
            Intent locationPickerIntent = new LocationPickerActivity.Builder()
                    //  .withLocation(40.7128, 74.0060)
                    .withGeolocApiKey("AIzaSyBiWCqUwYcKgZyvusgkFOKfop1vA2dLZnE")
                    .withSearchZone("es_ES")
//                        .withStreetHidden()
//                        .withCityHidden()
//                        .withZipCodeHidden()
//                        .withSatelliteViewHidden()
                    .withGooglePlacesEnabled()
                    //   .withVoiceSearchHidden()
                    .build(getApplicationContext());

            startActivityForResult(locationPickerIntent, 1);
        }

    }

    private void checkLocationEnsblle() {

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setMessage("Please Provide location access ")
                    .setPositiveButton("Open Location", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    })
                    .show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1) {
            if(resultCode==RESULT_OK) {

                Double latitudes = data.getDoubleExtra(LATITUDE, 0.0);
                Double longitudes = data.getDoubleExtra(LONGITUDE, 0.0);
                String Address = data.getStringExtra(LOCATION_ADDRESS);

                add_location.setText(Address);

             //   location_parking.setText(Address);
                latitude = String.valueOf(latitudes);
                longitude = String.valueOf(longitudes);
                Log.e("error", "onActivityResult: " + latitude + longitude);

            }

        }

        if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("RESULT****", "CANCELLED");
            Toast.makeText(this, "User Cancelled the request", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == 101) {
            try {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                signUpMethod(account);

                // onLoggedIn(account);
            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                Log.w("error", "signInResult:failed code=" + e.getStatusCode());
                Toast.makeText(this, "Couldnot login try again", Toast.LENGTH_SHORT).show();
            }
        }
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
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });

        facebook_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(Signup.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        AccessToken accessToken = loginResult.getAccessToken();
                        useLoginInformation(accessToken);
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
            }
        });

        twitter_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTwitterAuthClient.authorize(Signup.this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> result) {
                        TwitterSession twitterSession = result.data;

                        //call fetch email only when permission is granted
                        fetchTwitterEmail(twitterSession);
                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                });
            }
        });


    }

    private TwitterAuthClient client;

    public void fetchTwitterEmail(final TwitterSession twitterSession) {
        client.requestEmail(twitterSession, new com.twitter.sdk.android.core.Callback<String>() {
            @Override
            public void success(Result<String> result) {
                //here it will give u only email and rest of other information u can get from TwitterSession
                //    userDetailsLabel.setText("User Id : " + twitterSession.getUserId() + "\nScreen Name : " + twitterSession.getUserName() + "\nEmail Id : " + result.data);

                String username=twitterSession.getUserName();
                String email=result.data;
              //  openDialogue(username,email);

                Toast.makeText(Signup.this, "Data fetched", Toast.LENGTH_SHORT).show();
                edit_text_email.setText(email);
                edit_text_name.setText(username);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(Signup.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void useLoginInformation(AccessToken accessToken) {

        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name = object.getString("name");
                    String email = object.getString("email");
                    String image = object.getJSONObject("picture").getJSONObject("data").getString("url");

                  //  openDialogue(name,email);
                    edit_text_email.setText(email);
                    edit_text_name.setText(name);
                    Toast.makeText(Signup.this, "Data fetched", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
    }

    private void validation() {

        String email = edit_text_email.getText().toString();
        String password = edit_text_password.getText().toString();
        String name = edit_text_name.getText().toString();
        String phone = edit_text_number.getText().toString();
        String address=add_location.getText().toString().trim();


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

        } else if (TextUtils.isEmpty(address)){
            add_location.setError("Please provide proper Address");
        }else if (TextUtils.isEmpty(latitude)) {
            Toast.makeText(this, "Please Provide Address", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(longitude)) {
            Toast.makeText(this, "Please Provide Address", Toast.LENGTH_SHORT).show();
        } else {

            if (status_value.equalsIgnoreCase("parking_owner")){
                popupOwnerRemainingDetails(email, password, name, phone,address);
            }else{
                CallApiServiceSignup(email, password, name, phone,address, "", "", "", "");
            }


        }
    }

    private void popupOwnerRemainingDetails(final String email, final String password, final String name, final String phone, final String address) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.popup_signup_remaining_part, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(Signup.this);

        final EditText city=alertLayout.findViewById(R.id.city);
        final EditText state=alertLayout.findViewById(R.id.state);
        final EditText company_name=alertLayout.findViewById(R.id.company_name);
        final Button signupsss=alertLayout.findViewById(R.id.signupsss);
        final EditText tac_id=alertLayout.findViewById(R.id.tac_id);

        //  etcode = alertLayout.findViewById(R.id.et_password);

        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        final AlertDialog dialog = alert.create();
        dialog.show();
        signupsss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String citys=city.getText().toString();
                String states=state.getText().toString();
                String company_names=company_name.getText().toString();
                String tacID=tac_id.getText().toString();

                if (TextUtils.isEmpty(citys)) {
                    city.setError("enter city");

                }else if (TextUtils.isEmpty(states)){
                    state.setError("Please enter state");

                }else if (TextUtils.isEmpty(company_names)){
                   company_name.setError("please enter company name");
                }else if (TextUtils.isEmpty(tacID)){
                    tac_id.setError("please enter tac id");
                }else{

                    dialog.dismiss();
                    progressDialog.show();

                    CallApiServiceSignup(email, password, name, phone,address,citys,states,company_names,tacID);
                    //  apiServiceCall();
                    // hide virtual keyboard
                }


            }
        });


    }

    private void CallApiServiceSignup(String email, String password, String name, String phone, String address, String citys, String states, String company_names, String tacID) {
        progressDialog.show();
        APIService service = APIClient.getClient().create(APIService.class);
        Call<SignupModel> modelCall = service.signup(name, email, password, phone, status_value, latitude, longitude, preferences.getFcmToken(),address,citys,states,company_names,tacID);
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
                        preferences.setAddress(model.getUserData().get(0).getAddress());


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



    private void signUpMethod(GoogleSignInAccount account) {
        String name=account.getDisplayName();
        String email=account.getEmail();

        Toast.makeText(this, "Data fetched", Toast.LENGTH_SHORT).show();
        edit_text_name.setText(name);
        edit_text_name.setText(email);

      //  openDialogue(name,email);


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

                       /* request = new LocationRequest();
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
                    }*/
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
