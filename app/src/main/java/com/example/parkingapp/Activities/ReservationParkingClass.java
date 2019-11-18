package com.example.parkingapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.parkingapp.Activities.Fragment_parking_info_insider.Map_fragment_parking;
import com.example.parkingapp.Activities.Fragment_parking_info_insider.Parking_info_fragment;
import com.example.parkingapp.Activities.Fragment_parking_info_insider.Photos_parking;
import com.example.parkingapp.Activities.Fragment_parking_info_insider.TabsParking;
import com.example.parkingapp.Adapters.TabsAdapters;
import com.example.parkingapp.Models.CustomizationMapModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.Preferences.ReservationPreferences;
import com.example.parkingapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ReservationParkingClass extends AppCompatActivity implements Map_fragment_parking.OnFragmentInteractionListener, Parking_info_fragment.OnFragmentInteractionListener, Photos_parking.OnFragmentInteractionListener {

  //  String latitude, longitude, parking_owner, parking_name, parking_availablity, parking_id, parking_des, parking_price, parking_owner_id, remaining_parking_spots, filled_parking_spots;
    //   Preferences preferences;
    ReservationPreferences reservationPreferences;
    Preferences preferenceMain;
    ConstraintLayout abc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_parking_class);

        preferenceMain = new Preferences(this);
        abc = findViewById(R.id.abc);
        if (preferenceMain.getSwitchNightMod()) {
            abc.setBackgroundColor(getResources().getColor(R.color.black));
        } else {
            abc.setBackgroundColor(getResources().getColor(R.color.white));
        }
        ArrayList<CustomizationMapModel> arrayList = new ArrayList<>();

        Intent intent = getIntent();
        if (intent != null) {

            String data = intent.getStringExtra("MapData");
            String formatedData = null;
            if (data != null) {
                formatedData = data.toString().
                        replace("[", "").
                        replace("]", "").
                        trim();
            }

            Log.e("error", "onCreate: " + formatedData);
            CustomizationMapModel customizationMapModel = new Gson().fromJson(formatedData, CustomizationMapModel.class);
            arrayList.add(customizationMapModel);

            /*latitude = intent.getStringExtra("latitude");
            longitude = intent.getStringExtra("longitude");
            parking_owner = intent.getStringExtra("parking_owner");
            parking_name = intent.getStringExtra("parking_name");
            parking_availablity = intent.getStringExtra("parking_availability");
            parking_id = intent.getStringExtra("parking_id");
            parking_des = intent.getStringExtra("parking_des");
            parking_price = intent.getStringExtra("price");
            parking_owner_id = intent.getStringExtra("owner_id");
            remaining_parking_spots = intent.getStringExtra("remaining_parking_spots");
            filled_parking_spots = intent.getStringExtra("filled_parking_spots");*/
        }
        //  .putExtra("remaining_parking_spots",remaining_parking_spots).putExtra("filled_parking_spots",filled_parking_spots)


        reservationPreferences = new ReservationPreferences(this);

        reservationPreferences.setLatitude(arrayList.get(0).getLatitude());
        reservationPreferences.setLongitude(arrayList.get(0).getLongitude());
        reservationPreferences.setParkingOwner(arrayList.get(0).getParking_owner_name());
        reservationPreferences.setParkingName(arrayList.get(0).getParkingName());
        reservationPreferences.setParkingAvailablity(arrayList.get(0).getParking_avaibility());
        reservationPreferences.setParkingID(arrayList.get(0).getParking_id());
        reservationPreferences.setParkingDes(arrayList.get(0).getParking_description());
        reservationPreferences.setParkingPrice(arrayList.get(0).getParking_price());
        reservationPreferences.setParkingOwnerId(arrayList.get(0).getParking_owner_id());
        reservationPreferences.setRemainingParkingSpots(arrayList.get(0).getRemaining_parking_spots());
        reservationPreferences.setFilledParkingSpots(arrayList.get(0).getFilled_parking_spots());
        reservationPreferences.setVehicalType(arrayList.get(0).getTypeofVehical());
        reservationPreferences.setOwnerNumber(arrayList.get(0).getOwnerNumber());
        reservationPreferences.setParkingImage1(arrayList.get(0).getParking_image1());
        reservationPreferences.setParkingImage2(arrayList.get(0).getParking_image2());

        Toolbar toolbar = findViewById(R.id.top_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(arrayList.get(0).getParkingName());
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Photos"));
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
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
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        finish();
        super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
