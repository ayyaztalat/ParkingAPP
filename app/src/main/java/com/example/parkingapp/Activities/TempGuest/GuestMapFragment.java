package com.example.parkingapp.Activities.TempGuest;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.parkingapp.Activities.FragmentActivities.AboutFragment;
import com.example.parkingapp.Activities.FragmentActivities.MapFragmentClass;
import com.example.parkingapp.Activities.FragmentActivities.MyBookingClass;
import com.example.parkingapp.Activities.FragmentActivities.MyParking;
import com.example.parkingapp.Activities.FragmentActivities.MyReservation;
import com.example.parkingapp.Activities.FragmentActivities.SupportFragment;
import com.example.parkingapp.Activities.HomeActivity;
import com.example.parkingapp.Activities.LoginClass;
import com.example.parkingapp.Activities.ProfileActivity;
import com.example.parkingapp.Activities.tempClass;
import com.example.parkingapp.BuildConfig;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;

public class GuestMapFragment extends AppCompatActivity   implements NavigationView.OnNavigationItemSelectedListener,
        SupportFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener, MapFragmentClass.OnFragmentInteractionListener{
    private SwitchCompat switcher;
    private SwitchCompat switchers;
    boolean night = true;
    boolean traffic = true;
    NavigationView navigationView;
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_map_fragment);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = new Preferences(this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
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

        night = preferences.getSwitchNightMod();
        traffic = preferences.getTrafficCheck();


        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.night_mood);
        View actionView = MenuItemCompat.getActionView(menuItem);

        switcher = (SwitchCompat) actionView.findViewById(R.id.drawer_switch);
        //  switcher.setChecked(true);
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (night) {
                    switcher.setChecked(false);
                    night = false;
                    preferences.setSwitchNightMod(false);
                    startActivity(new Intent(getApplicationContext(), tempClass.class));
                    finish();

                } else {
                    switcher.setChecked(true);
                    night = true;
                    preferences.setSwitchNightMod(true);
                    startActivity(new Intent(getApplicationContext(), tempClass.class));
                    finish();
                }
            }


        });

        MenuItem menuItems = menu.findItem(R.id.traffic_text);
        View actionViews = MenuItemCompat.getActionView(menuItems);

        MenuItem myParking = menu.findItem(R.id.my_parking);

        switchers = (SwitchCompat) actionViews.findViewById(R.id.drawer_switch);
        //    switchers.setChecked(true);
        switchers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (traffic) {
                    switchers.setChecked(false);
                    traffic = false;
                    preferences.setTrafficCheck(false);
                    startActivity(new Intent(getApplicationContext(), tempClass.class));
                    finish();
                } else {
                    switchers.setChecked(true);
                    traffic = true;
                    preferences.setTrafficCheck(true);
                    startActivity(new Intent(getApplicationContext(), tempClass.class));
                    finish();
                }
            }

        });

        if (night) {

            //    night=false;
            preferences.setSwitchNightMod(true);
            switcher.setChecked(true);
        } else {
            switcher.setChecked(false);
            //  night=true;
            preferences.setSwitchNightMod(false);

        }

        if (traffic) {

            //    traffic=false;
            //  preferences.setTrafficCheck(false);
            switchers.setChecked(true);
        } else {
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
            preferences.setTypeGuest("");
            super.onBackPressed();
        }
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.Account) {

                callToast();

        } else if (id == R.id.my_parking) {
            callToast();
        } else if (id == R.id.my_reservation) {
            callToast();
        } else if (id == R.id.booked_parking) {
            callToast();
        } else if (id == R.id.night_mood) {
            changeWork();
        } else if (id == R.id.traffic_text) {
            trafficChange();
        } else if (id == R.id.map) {

            fragmentClass = MapFragmentClass.class;
            change();

        } else if (id == R.id.payments) {
            callToast();

        } else if (id == R.id.invite_friend) {
            startInviteLink();

        } else if (id == R.id.support) {
            fragmentClass = SupportFragment.class;
            change();
        } else if (id == R.id.about) {
            fragmentClass = AboutFragment.class;
            change();


        } else if (id == R.id.sign_out) {

                preferences.setTypeGuest("");
                startActivity(new Intent(getApplicationContext(), LoginClass.class));
                finishAffinity();


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startInviteLink() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

            int walletAmount = preferences.getWalletInvite();

            if (walletAmount == 0) {
                walletAmount = 5;
                preferences.putWalletInvite(walletAmount);
            } else {
                walletAmount = walletAmount + 5;
                preferences.putWalletInvite(walletAmount);
            }

            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    private void callToast() {
        Toast.makeText(this, "Please login to view these options", Toast.LENGTH_LONG).show();
    }

    private void trafficChange() {
        Menu menu = navigationView.getMenu();
        MenuItem menuItems = menu.findItem(R.id.traffic_text);
        View actionViews = MenuItemCompat.getActionView(menuItems);

        switchers = (SwitchCompat) actionViews.findViewById(R.id.drawer_switch);
        // switchers.setChecked(true);
        if (traffic) {
            switchers.setChecked(false);
            traffic = false;
            preferences.setTrafficCheck(false);
            startActivity(new Intent(getApplicationContext(), tempClass.class));
            finish();
        } else {
            switchers.setChecked(true);
            traffic = true;
            startActivity(new Intent(getApplicationContext(), tempClass.class));
            finish();
            preferences.setTrafficCheck(true);

        }

    }

    private void changeWork() {

        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.night_mood);
        View actionView = MenuItemCompat.getActionView(menuItem);

        final SwitchCompat switcher = (SwitchCompat) actionView.findViewById(R.id.drawer_switch);
        //   switcher.setChecked(true);

        if (night) {
            switcher.setChecked(false);
            night = false;
            preferences.setSwitchNightMod(false);
            startActivity(new Intent(getApplicationContext(), tempClass.class));
            finish();
        } else {
            switcher.setChecked(true);
            night = true;
            startActivity(new Intent(getApplicationContext(), tempClass.class));
            finish();
            preferences.setSwitchNightMod(true);

        }


    }

    private void CallSignOutAPI() {
        startActivity(new Intent(getApplicationContext(), LoginClass.class));
        preferences.setSession(false);
        preferences.clear();
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
