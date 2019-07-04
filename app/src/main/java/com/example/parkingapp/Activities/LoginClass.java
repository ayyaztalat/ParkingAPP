package com.example.parkingapp.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.LoginModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginClass extends AppCompatActivity {

  AppCompatEditText edit_text_email,edit_text_password;
  TextView forgot_password,signup;
  Button signin;
  ImageView google_signup,facebook_signup,twitter_signup;
  Preferences preferences;
  ProgressDialog progressDialog;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_class);

    preferences=new Preferences(this);
    progressDialog=new ProgressDialog(this);
    progressDialog.setMessage("Please Wait while we are loading");
    progressDialog.setTitle("Signing In");
    progressDialog.setCancelable(false);

    edit_text_email=findViewById(R.id.edit_text_email);
    edit_text_password=findViewById(R.id.edit_text_password);
    forgot_password=findViewById(R.id.forgot_password);
    signup=findViewById(R.id.signup);

    signin=findViewById(R.id.signin);
    google_signup=findViewById(R.id.google_signup);
    facebook_signup=findViewById(R.id.facebook_signup);
    twitter_signup=findViewById(R.id.twitter_signup);

    signin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        validatedata();
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
      }
    });

    google_signup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(LoginClass.this, "Pending Second week", Toast.LENGTH_SHORT).show();
      }
    });
    facebook_signup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(LoginClass.this, "Pending Second Week", Toast.LENGTH_SHORT).show();
      }
    });

    twitter_signup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(LoginClass.this, "Pending Second Week", Toast.LENGTH_SHORT).show();
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
    final Call<LoginModel> modelCall=service.login(email,password,preferences.getFcmToken());
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
          preferences.setStatusValue(model.getUserData().get(0).getStatus_value());
          preferences.setLatitude(model.getUserData().get(0).getLatitude());
          preferences.setLongitude(model.getUserData().get(0).getLongitude());

          startActivity(new Intent(getApplicationContext(),HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
