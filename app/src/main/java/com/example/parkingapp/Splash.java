package com.example.parkingapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.parkingapp.Activities.HomeActivity;
import com.example.parkingapp.Activities.LoginClass;
import com.example.parkingapp.FCM.firebaseClass;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseClass firebaseClass=new firebaseClass();
        firebaseClass.onTokenRefresh(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginClass.class));
                finish();
            }
        },5000);
    }
}
