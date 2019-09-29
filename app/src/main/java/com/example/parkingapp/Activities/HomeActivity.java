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
import android.support.v7.app.AppCompatDelegate;
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
import android.widget.Toast;

import com.example.parkingapp.Activities.FragmentActivities.AboutFragment;
import com.example.parkingapp.Activities.FragmentActivities.HistoryFragment;
import com.example.parkingapp.Activities.FragmentActivities.MapFragmentClass;
import com.example.parkingapp.Activities.FragmentActivities.MyBookingClass;
import com.example.parkingapp.Activities.FragmentActivities.MyParking;
import com.example.parkingapp.Activities.FragmentActivities.MyReservation;
import com.example.parkingapp.Activities.FragmentActivities.SupportFragment;
import com.example.parkingapp.BuildConfig;
import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.BrainTreeModel;
import com.example.parkingapp.Models.BrainTreeToken;
import com.example.parkingapp.Models.ProfileModel;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,MapFragmentClass.OnFragmentInteractionListener ,
        MyParking.OnFragmentInteractionListener,AboutFragment.OnFragmentInteractionListener,MyReservation.OnFragmentInteractionListener,
        SupportFragment.OnFragmentInteractionListener,MyBookingClass.OnFragmentInteractionListener,HistoryFragment.OnFragmentInteractionListener
 {

    Preferences preferences;
    String status_value;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
     private SwitchCompat switcher;
     private SwitchCompat switchers;

     NavigationView navigationView;
     String guest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        preferences=new Preferences(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      callToken();

    callProfileLoading();




        guest=preferences.getTypeGuest();
        status_value=preferences.getStatusVaalue();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);


        if (preferences.getSwitchNightMod()){
            drawer.setBackgroundColor(getResources().getColor(R.color.black));
        }else {
            drawer.setBackgroundColor(getResources().getColor(R.color.white));
        }

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
                    startActivity(new Intent(getApplicationContext(),tempClass.class));
                    finish();

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

        MenuItem myParking=menu.findItem(R.id.my_parking);
      //  View actionV=MenuItemCompat.getActionView(myParking);
        MenuItem myReservations=menu.findItem(R.id.my_reservation);
     //   View action=MenuItemCompat.getActionView(myReservations);

        MenuItem history=menu.findItem(R.id.history);
     //   View historyAction=MenuItemCompat.getActionView(history);

        MenuItem myBooking=menu.findItem(R.id.booked_parking);
    //    View bookingAction=MenuItemCompat.getActionView(myBooking);

        if (status_value.equalsIgnoreCase("truck_owner")){
            myParking.setVisible(false);
            myReservations.setVisible(true);
            myBooking.setVisible(false);
            history.setVisible(false);
        }else{
            myParking.setVisible(true);
            myReservations.setVisible(false);
            history.setVisible(true);
            myBooking.setVisible(false);
        }





        switchers = (SwitchCompat) actionViews.findViewById(R.id.drawer_switch);
    //    switchers.setChecked(true);
        switchers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (traffic){
                    switchers.setChecked(false);
                    traffic=false;
                    preferences.setTrafficCheck(false);
                    startActivity(new Intent(getApplicationContext(),tempClass.class));
                    finish();
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

     private void callProfileLoading() {
         APIService service=APIClient.getClient().create(APIService.class);
         Call<ProfileModel> modelCall=service.profile(preferences.getEmail());
         modelCall.enqueue(new Callback<ProfileModel>() {
             @Override
             public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                 ProfileModel model=response.body();
                 if (model.getStatus().equalsIgnoreCase("success")){

                     preferences.setName(model.getUserData().get(0).getName());
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

                 //    Toast.makeText(HomeActivity.this, model.getUserData().get(0).getName(), Toast.LENGTH_SHORT).show();


                 }else{
                     Toast.makeText(HomeActivity.this, model.getError(), Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onFailure(Call<ProfileModel> call, Throwable t) {
                 Toast.makeText(HomeActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                 Log.e("Error", "onFailure: "+t.getMessage() );
             }
         });
     }

     private void callToken() {
        try {
            APIService service = APIClient.getClient().create(APIService.class);
            Call<BrainTreeToken> tokenCall = service.braintree();
            tokenCall.enqueue(new Callback<BrainTreeToken>() {
                @Override
                public void onResponse(Call<BrainTreeToken> call, Response<BrainTreeToken> response) {
                   try {
                       BrainTreeToken treeToken = response.body();
                       assert treeToken != null;
                       if (treeToken.status.equalsIgnoreCase("success")) {
                           preferences.setBraintreeToken(treeToken.getToken());
                       } else {
                     //      Toast.makeText(HomeActivity.this, treeToken.getError(), Toast.LENGTH_SHORT).show();
                           Log.e("error", "onResponse: "+treeToken.getError() );
                       }
                   }catch (Exception e){
                       e.printStackTrace();
               //        Toast.makeText(HomeActivity.this, "Braintree failed", Toast.LENGTH_SHORT).show();
                   }
                }

                @Override
                public void onFailure(Call<BrainTreeToken> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, "No Network", Toast.LENGTH_SHORT).show();
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);


        MenuItem item1=findViewById(R.id.my_parking);
        MenuItem item2=findViewById(R.id.my_reservation);
        if (status_value.equalsIgnoreCase("truck_owner")){
        //    item1.setVisible(true);

        }else if (status_value.equalsIgnoreCase("parking_owner")){
        //    item2.setVisible(true);
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
            if (guest.equalsIgnoreCase("guest")) {
                callToast();
            }else{
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        } else if (id == R.id.my_parking) {
            if (guest.equalsIgnoreCase("guest")) {
                callToast();
            }else {
                fragmentClass = MyParking.class;
                change();
            }
        } else if (id == R.id.my_reservation) {
            if (guest.equalsIgnoreCase("guest")) {
                callToast();
            }else {
                fragmentClass = MyReservation.class;
                change();
            }
        }else if (id==R.id.booked_parking) {
            if (guest.equalsIgnoreCase("guest")) {
                callToast();
            } else {
              startActivity(new Intent(getApplicationContext(),BookingClass.class));
            }
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

        }  else if (id == R.id.history) {
            if (guest.equalsIgnoreCase("guest")) {
                callToast();
            }else {
                fragmentClass= HistoryFragment.class;
                change();
            }

        } else if (id == R.id.invite_friend) {
            startInviteLink();

        } else if (id == R.id.support) {
            fragmentClass = SupportFragment.class;
            change();
        } else if (id == R.id.about) {
            fragmentClass = AboutFragment.class;
            change();


        } else if (id == R.id.sign_out) {
            if (guest.equalsIgnoreCase("guest")) {
                preferences.setTypeGuest("");
                    startActivity(new Intent(getApplicationContext(),LoginClass.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                    finishAffinity();
            }else{
                CallSignOutAPI();
            }

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
             String shareMessage= "\nLet me recommend you this application\n\n";
             shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
             shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

             int walletAmount=preferences.getWalletInvite();

             if (walletAmount==0){
                 walletAmount=5;
                 preferences.putWalletInvite(walletAmount);
             }
             else {
                 walletAmount=walletAmount+5;
                 preferences.putWalletInvite(walletAmount);
             }

             startActivity(Intent.createChooser(shareIntent, "choose one"));
         } catch(Exception e) {
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
         if (traffic){
             switchers.setChecked(false);
             traffic=false;
             preferences.setTrafficCheck(false);
             startActivity(new Intent(getApplicationContext(),tempClass.class));
             finish();
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
             startActivity(new Intent(getApplicationContext(),tempClass.class));
             finish();
         }else{
             switcher.setChecked(true);
             night=true;
             startActivity(new Intent(getApplicationContext(),tempClass.class));
             finish();
             preferences.setSwitchNightMod(true);

         }


    }

     private void CallSignOutAPI() {
        startActivity(new Intent(getApplicationContext(),LoginClass.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        preferences.setSession(false);
        preferences.clear();
        finishAffinity();

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
