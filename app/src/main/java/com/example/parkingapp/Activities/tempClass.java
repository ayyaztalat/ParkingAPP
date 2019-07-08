package com.example.parkingapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class tempClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this, "Theme Updated", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
    }
}
