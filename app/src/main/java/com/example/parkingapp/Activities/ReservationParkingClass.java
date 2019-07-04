package com.example.parkingapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.parkingapp.Activities.Fragment_parking_info_insider.Map_fragment_parking;
import com.example.parkingapp.Activities.Fragment_parking_info_insider.Parking_info_fragment;
import com.example.parkingapp.Activities.Fragment_parking_info_insider.Photos_parking;
import com.example.parkingapp.Activities.Fragment_parking_info_insider.TabsParking;
import com.example.parkingapp.Adapters.TabsAdapters;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.Preferences.ReservationPreferences;
import com.example.parkingapp.R;

public class ReservationParkingClass extends AppCompatActivity implements Map_fragment_parking.OnFragmentInteractionListener, Parking_info_fragment.OnFragmentInteractionListener, Photos_parking.OnFragmentInteractionListener {

    String latitude,longitude,parking_owner,parking_name,parking_availablity,parking_id,parking_des,parking_price;
 //   Preferences preferences;
    ReservationPreferences reservationPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_parking_class);

        Intent intent=getIntent();
        if (intent!=null){
            latitude=intent.getStringExtra("latitude");
            longitude=intent.getStringExtra("longitude");
            parking_owner=intent.getStringExtra("parking_owner");
            parking_name=intent.getStringExtra("parking_name");
            parking_availablity=intent.getStringExtra("parking_availability");
            parking_id=intent.getStringExtra("parking_id");
            parking_des=intent.getStringExtra("parking_des");
            parking_price=intent.getStringExtra("price");
        }


       reservationPreferences=new ReservationPreferences(this);

        reservationPreferences.setLatitude(latitude);
        reservationPreferences.setLongitude(longitude);
        reservationPreferences.setParkingOwner(parking_owner);
        reservationPreferences.setParkingName(parking_name);
        reservationPreferences.setParkingAvailablity(parking_availablity);
        reservationPreferences.setParkingID(parking_id);
        reservationPreferences.setParkingDes(parking_des);
        reservationPreferences.setParkingPrice(parking_price);

        Toolbar toolbar = findViewById(R.id.top_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(parking_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.addTab(tabLayout.newTab().setText("Photos"));
        tabLayout.addTab(tabLayout.newTab().setText("Info"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager =(ViewPager)findViewById(R.id.viewpager);
        TabsParking tabsAdapter = new TabsParking(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });








        /*.putExtra("latitude",latitude)
              .putExtra("longitude",longitude).putExtra("parking_owner",parking_owner_name).putExtra("parking_name",parking_title)
              .putExtra("parking_availability",parking_avaibility).putExtra("parking_id",parking_id).putExtra("parking_des",parking_description)
              .putExtra("price",parking_price));*/
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
