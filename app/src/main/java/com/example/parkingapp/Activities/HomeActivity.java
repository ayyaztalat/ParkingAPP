package com.example.parkingapp.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.CompoundButton;

import com.example.parkingapp.Activities.FragmentActivities.AboutFragment;
import com.example.parkingapp.Activities.FragmentActivities.MapFragmentClass;
import com.example.parkingapp.Activities.FragmentActivities.MyBookingClass;
import com.example.parkingapp.Activities.FragmentActivities.MyParking;
import com.example.parkingapp.Activities.FragmentActivities.MyReservation;
import com.example.parkingapp.Activities.FragmentActivities.SupportFragment;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,MapFragmentClass.OnFragmentInteractionListener ,
        MyParking.OnFragmentInteractionListener,AboutFragment.OnFragmentInteractionListener,MyReservation.OnFragmentInteractionListener,
        SupportFragment.OnFragmentInteractionListener,MyBookingClass.OnFragmentInteractionListener
 {

    Preferences preferences;
    String status_value;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
     private SwitchCompat switcher;
     private SwitchCompat switchers;

     NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        preferences=new Preferences(this);
        status_value=preferences.getStatusVaalue();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
         navigationView= findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = null;
        Class fragmentClass = MapFragmentClass.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

      night=  preferences.getSwitchNightMod();
        traffic=preferences.getTrafficCheck();



        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.night_mood);
        View actionView = MenuItemCompat.getActionView(menuItem);

        switcher = (SwitchCompat) actionView.findViewById(R.id.drawer_switch);
      //  switcher.setChecked(true);
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (night){
                    switcher.setChecked(false);
                    night=false;
                    preferences.setSwitchNightMod(false);

                }else{
                    switcher.setChecked(true);
                    night=true;
                    preferences.setSwitchNightMod(true);
                    startActivity(new Intent(getApplicationContext(),tempClass.class));
                    finish();
                }
            }


        });

        MenuItem menuItems = menu.findItem(R.id.traffic_text);
        View actionViews = MenuItemCompat.getActionView(menuItems);

        switchers = (SwitchCompat) actionViews.findViewById(R.id.drawer_switch);
    //    switchers.setChecked(true);
        switchers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (traffic){
                    switchers.setChecked(false);
                    traffic=false;
                    preferences.setTrafficCheck(false);
                }else{
                    switchers.setChecked(true);
                    traffic=true;
                    preferences.setTrafficCheck(true);
                    startActivity(new Intent(getApplicationContext(),tempClass.class));
                    finish();
                }
            }

            });

        if (night){

        //    night=false;
            preferences.setSwitchNightMod(true);
            switcher.setChecked(true);
        }else{
            switcher.setChecked(false);
          //  night=true;
            preferences.setSwitchNightMod(false);

        }

        if (traffic){

        //    traffic=false;
          //  preferences.setTrafficCheck(false);
            switchers.setChecked(true);
        }else{
            switchers.setChecked(false);

           // traffic=true;
           // preferences.setTrafficCheck(true);

        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);


        MenuItem item1=findViewById(R.id.my_parking);
        MenuItem item2=findViewById(R.id.my_reservation);
        if (status_value.equalsIgnoreCase("truck_owner")){
            item1.setVisible(true);

        }else if (status_value.equalsIgnoreCase("parking_owner")){
            item2.setVisible(true);
        }
        return true;
    }







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    boolean night=true; boolean traffic=true;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.Account) {
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        } else if (id == R.id.my_parking) {
            fragmentClass = MyParking.class;
            change();
        } else if (id == R.id.my_reservation) {
            fragmentClass = MyReservation.class;
            change();

        }else if (id==R.id.booked_parking){
            fragmentClass= MyBookingClass.class;
            change();
        }
        else if (id==R.id.night_mood){
            changeWork();
        }
        else if (id==R.id.traffic_text){
            trafficChange();
        }

        else if (id == R.id.map) {
            fragmentClass = MapFragmentClass.class;
            change();

        }  else if (id == R.id.payments) {

        } else if (id == R.id.invite_friend) {

        } else if (id == R.id.support) {
            fragmentClass = SupportFragment.class;
            change();
        } else if (id == R.id.about) {
            fragmentClass = AboutFragment.class;
            change();


        } else if (id == R.id.sign_out) {
            CallSignOutAPI();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

     private void trafficChange() {
         Menu menu = navigationView.getMenu();
         MenuItem menuItems = menu.findItem(R.id.traffic_text);
         View actionViews = MenuItemCompat.getActionView(menuItems);

         switchers = (SwitchCompat) actionViews.findViewById(R.id.drawer_switch);
        // switchers.setChecked(true);
         if (traffic){
             switchers.setChecked(false);
             traffic=false;
             preferences.setTrafficCheck(false);
         }else{
             switchers.setChecked(true);
             traffic=true;
             startActivity(new Intent(getApplicationContext(),tempClass.class));
             finish();
             preferences.setTrafficCheck(true);

         }

     }

     private void changeWork() {

         Menu menu = navigationView.getMenu();
         MenuItem menuItem = menu.findItem(R.id.night_mood);
         View actionView = MenuItemCompat.getActionView(menuItem);

       final SwitchCompat  switcher = (SwitchCompat) actionView.findViewById(R.id.drawer_switch);
      //   switcher.setChecked(true);

         if (night){
             switcher.setChecked(false);
             night=false;
             preferences.setSwitchNightMod(false);
         }else{
             switcher.setChecked(true);
             night=true;
             startActivity(new Intent(getApplicationContext(),tempClass.class));
            finish();
             preferences.setSwitchNightMod(true);

         }


    }

     private void CallSignOutAPI() {
        startActivity(new Intent(getApplicationContext(),LoginClass.class));
        preferences.setSession(false);
        finish();

    }

    Fragment fragment = null;
    Class fragmentClass = null;

    private void change() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragmentManager.beginTransaction().remove(fragment);
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

     //   fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(HomeActivity.class.getName()).commit();

      //  title.setText(item.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }




    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
