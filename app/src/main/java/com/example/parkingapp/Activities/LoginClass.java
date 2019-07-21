package com.example.parkingapp.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.LoginModel;
import com.example.parkingapp.Models.SignupModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
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

import static com.example.parkingapp.Activities.Signup.MY_PERMISSIONS_REQUEST_LOCATION;

public class LoginClass extends AppCompatActivity {

  AppCompatEditText edit_text_email,edit_text_password;
  TextView forgot_password,signup;
  private GoogleSignInClient googleSignInClient;
  Button signin;
  ImageView google_signup,facebook_signup,twitter_signup;
  Preferences preferences;
  ProgressDialog progressDialog;
  TextView continue_as_guest;
  String latitude,longitude;
  LoginButton login_button;
    private CallbackManager callbackManager;
    TwitterAuthClient mTwitterAuthClient;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_class);

    preferences=new Preferences(this);
    progressDialog=new ProgressDialog(this);
    progressDialog.setMessage("Please Wait while we are loading");
    progressDialog.setTitle("Signing In");
    progressDialog.setCancelable(false);

      callbackManager = CallbackManager.Factory.create();

    edit_text_email=findViewById(R.id.edit_text_email);
    edit_text_password=findViewById(R.id.edit_text_password);
    forgot_password=findViewById(R.id.forgot_password);
    signup=findViewById(R.id.signup);
    continue_as_guest=findViewById(R.id.continue_as_guest);
    signin=findViewById(R.id.signin);
    google_signup=findViewById(R.id.google_signup);
    facebook_signup=findViewById(R.id.facebook_signup);
    twitter_signup=findViewById(R.id.twitter_signup);
    login_button=findViewById(R.id.login_button);
      client = new TwitterAuthClient();

    TwitterConfig config = new TwitterConfig.Builder(this)
            .logger(new DefaultLogger(Log.DEBUG))//enable logging when app is in debug mode
            .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))//pass the created app Consumer KEY and Secret also called API Key and Secret
            .debug(true)//enable debug mode
            .build();

    //finally initialize twitter_shape with created configs
    Twitter.initialize(config);
    mTwitterAuthClient  = new TwitterAuthClient();



      login_button.setReadPermissions(Arrays.asList("email", "public_profile"));
      login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
          @Override
          public void onSuccess(LoginResult loginResult) {
              // Retrieving access token using the LoginResult
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

    signin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        validatedata();
      }
    });

    continue_as_guest.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        preferences.setTypeGuest("guest");
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
      }
    });

    forgot_password.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          openRestorePassword();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    signup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(),Signup.class));
        finish();
      }
    });

    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();
    googleSignInClient = GoogleSignIn.getClient(LoginClass.this, gso);


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
          LoginManager.getInstance().logInWithReadPermissions(LoginClass.this,Arrays.asList("email", "public_profile"));
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
            mTwitterAuthClient.authorize(LoginClass.this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {
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


    if (checkLocationPermission()){
      LocationRequest request = new LocationRequest();
      request.setInterval(10000);
      request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
      FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
      int permission = ContextCompat.checkSelfPermission(this,
              Manifest.permission.ACCESS_FINE_LOCATION);
      if (permission == PackageManager.PERMISSION_GRANTED) {
        client.requestLocationUpdates(request,new LocationCallback(){
          @Override
          public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location location = locationResult.getLastLocation();
            if (location != null) {
              //  Log.d(TAG, "location update " + location);
              longitude=String.valueOf(location.getLongitude());
              latitude=String.valueOf(location.getLatitude());

              preferences.setLongitude(longitude);
              preferences.setLatitude(latitude);
            }
          }



        },null);
      }
    }



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
                openDialogue(username,email);
            }

            @Override
            public void failure(TwitterException exception) {
              Toast.makeText(LoginClass.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
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

                    openDialogue(name,email);
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
                .setTitle("Permission")
                .setMessage("for location")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                    //Prompt the user once explanation has been shown
                    ActivityCompat.requestPermissions(LoginClass.this,
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
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK)
      switch (requestCode) {
        case 101:
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
          break;


      }

  }
  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         String permissions[], int[] grantResults) {

    if(requestCode==MY_PERMISSIONS_REQUEST_LOCATION){
      // If request is cancelled, the result arrays are empty.
      if (grantResults.length > 0
              && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        // permission was granted, yay! Do the
        // location-related task you need to do.
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
          LocationRequest request = new LocationRequest();
          request.setInterval(1000);
          request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
          FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
          int permission = ContextCompat.checkSelfPermission(this,
                  Manifest.permission.ACCESS_FINE_LOCATION);
          if (permission == PackageManager.PERMISSION_GRANTED) {
            client.requestLocationUpdates(request,new LocationCallback(){
              @Override
              public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                if (location != null) {
                  //  Log.d(TAG, "location update " + location);
                  longitude=String.valueOf(location.getLongitude());
                  latitude=String.valueOf(location.getLatitude());

                  preferences.setLongitude(longitude);
                  preferences.setLatitude(latitude);
                }
              }



            },null);
          }

        }

      } else {
        checkLocationPermission();
        // permission denied, boo! Disable the
        // functionality that depends on this permission.

      }
      return;
    }


  }

  private void signUpMethod(GoogleSignInAccount account) {
      String name=account.getDisplayName();
      String email=account.getEmail();
      openDialogue(name,email);


  }

  private void openDialogue(final String name, final String email) {

    LayoutInflater inflater = getLayoutInflater();
    View alertLayout = inflater.inflate(R.layout.popup_signup, null);
    final AlertDialog.Builder alert = new AlertDialog.Builder(LoginClass.this);

    final EditText et_password=alertLayout.findViewById(R.id.et_password);
    final EditText number=alertLayout.findViewById(R.id.number);
    final Spinner gender_spinner=alertLayout.findViewById(R.id.gender_spinner);
    final Button signup=alertLayout.findViewById(R.id.signupsss);

  //  etcode = alertLayout.findViewById(R.id.et_password);

    // this is set the view from XML inside AlertDialog
    alert.setView(alertLayout);
    // disallow cancel of AlertDialog on click of back button and outside touch
    alert.setCancelable(false);
    final AlertDialog dialog = alert.create();
    dialog.show();
    signup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        String password=et_password.getText().toString();
        String numbers=number.getText().toString();

        String spinner=gender_spinner.getSelectedItem().toString();

        if (TextUtils.isEmpty(password)) {
          et_password.setError("enter password");

        }else if (TextUtils.isEmpty(numbers)){
          number.setError("Please enter number");

        }else if (TextUtils.isEmpty(spinner)){
          Toast.makeText(LoginClass.this, "please enter type", Toast.LENGTH_SHORT).show();
        }else{

          dialog.dismiss();
          progressDialog.show();

          apiReferalCodeCall(name,email,password,numbers,spinner);
          //  apiServiceCall();
          // hide virtual keyboard
        }


      }
    });



  }

  private void apiReferalCodeCall(String name, String email, String password, String numbers, String spinner) {
    APIService service=APIClient.getClient().create(APIService.class);
    Call<SignupModel> modelCall=service.signup(name,email,password,numbers,spinner,latitude,longitude,preferences.getFcmToken());
    modelCall.enqueue(new Callback<SignupModel>() {
      @Override
      public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
        SignupModel model=response.body();
        try {
      progressDialog.dismiss();

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

            Toast.makeText(LoginClass.this, model.getError(), Toast.LENGTH_SHORT).show();
          }
        }catch (Exception e){
          progressDialog.dismiss();
          e.fillInStackTrace();
        }
      }

      @Override
      public void onFailure(Call<SignupModel> call, Throwable t) {
        progressDialog.dismiss();
        Toast.makeText(LoginClass.this, "Network error signup failed", Toast.LENGTH_SHORT).show();
        Log.e("error", "onFailure: "+t.getMessage());
      }
    });
  }

  private void openRestorePassword() throws Exception {
    LayoutInflater inflater = getLayoutInflater();
    View alertLayout = inflater.inflate(R.layout.restore_layout_view, null);
    final AlertDialog.Builder alert = new AlertDialog.Builder(LoginClass.this);

    final AppCompatEditText email = alertLayout.findViewById(R.id.edit_text_email);
    final Button cancel = alertLayout.findViewById(R.id.send);


    // this is set the view from XML inside AlertDialog
    alert.setView(alertLayout);
    // disallow cancel of AlertDialog on click of back button and outside touch
    alert.setCancelable(false);
    final AlertDialog dialog = alert.create();



    cancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        String emails=email.getText().toString();
        if (TextUtils.isEmpty(emails)){
          email.setError("Please Enter Email Address");
        }else{
          dialog.dismiss();
          Toast.makeText(LoginClass.this, "Email Sent", Toast.LENGTH_SHORT).show();
        }
        //  apiServiceCall();


      }
    });
    dialog.show();
  }

  private void validatedata() {
    String email=edit_text_email.getText().toString();
    String password=edit_text_password.getText().toString();

    if (TextUtils.isEmpty(email)){
      edit_text_email.setError("Please Enter Email");
    }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      edit_text_email.setError("Enter valid Email");
//            Toast.makeText(this, "Enter valid Email", Toast.LENGTH_SHORT).show();
    }

    else if (TextUtils.isEmpty(password)){
      edit_text_email.setError("Please Enter Password");
    }else{
      CallApiServiceLogin(email,password);
    }
  }

  private void CallApiServiceLogin(String email, String password) {
    progressDialog.show();
    APIService service= APIClient.getClient().create(APIService.class);
    String token=preferences.getFcmToken();
    final Call<LoginModel> modelCall=service.login(email,password,token);
    modelCall.enqueue(new Callback<LoginModel>() {
      @Override
      public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
        LoginModel model=response.body();
        progressDialog.dismiss();
        if (model.getStatus().equalsIgnoreCase("success")){
          preferences.setName(model.getUserData().get(0).getName());
          preferences.setSession(true);
          preferences.setPhone(model.getUserData().get(0).getPhone());
          preferences.setPassword(model.getUserData().get(0).getPassword());
          preferences.setEmail(model.getUserData().get(0).getEmail());
          preferences.setTime(model.getUserData().get(0).getTimeStamp());
          preferences.setUserId(model.getUserData().get(0).getId());
          preferences.setStatusValue(model.getUserData().get(0).getStatusValue());
          preferences.setLatitude(model.getUserData().get(0).getLatitude());
          preferences.setLongitude(model.getUserData().get(0).getLongitude());

          preferences.setCardToken(model.getUserData().get(0).getCardToken());
          preferences.setCardBin(model.getUserData().get(0).getCardBin());
          preferences.setCardLastDigit(model.getUserData().get(0).getCardLast4());
          preferences.setCardType(model.getUserData().get(0).getCardType());
          preferences.setCardHolderName(model.getUserData().get(0).getCardCardholderName());
          preferences.setCardExpirationDate(model.getUserData().get(0).getCardExpirationDate());
          preferences.setCardCustomerLocation(model.getUserData().get(0).getCardCustomerLocation());
          preferences.setCustomerID(model.getUserData().get(0).getCustomerId());

          startActivity(new Intent(getApplicationContext(),HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
          preferences.setTypeGuest("");
          finish();
        }else{
          Toast.makeText(LoginClass.this, model.getError(), Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<LoginModel> call, Throwable t) {
        progressDialog.dismiss();
        Toast.makeText(LoginClass.this, "Network Error", Toast.LENGTH_SHORT).show();
        Log.e("error", "onFailure: "+t.getMessage() );
      }
    });
  }
}
