package com.example.parkingapp.Activities.FragmentActivities;

import android.Manifest;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.parkingapp.Activities.HomeActivity;
import com.example.parkingapp.Activities.ReservationParkingClass;
import com.example.parkingapp.Activities.Signup;
import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.ParkingModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;
import android.location.LocationListener;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;
import static com.example.parkingapp.Activities.Signup.MY_PERMISSIONS_REQUEST_LOCATION;


public class MapFragmentClass extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  private static final float LOCATION_REFRESH_DISTANCE = 100;

  double longitude;
  double latitude;
  AlertDialog dialog;

  private String mParam1;
  private String mParam2;

  private OnFragmentInteractionListener mListener;
  private LocationManager mLocationManager;

  public MapFragmentClass() {
    // Required empty public constructor
  }

  private final LocationListener mLocationListener = new LocationListener() {
    @Override
    public void onLocationChanged(final Location location) {
    //googleMap.clear();
    /*  latitude=location.getLatitude();
      longitude= location.getLongitude();

      if (preference.getTrafficCheck()){
        googleMap.setTrafficEnabled(true);
      }

      Log.e(TAG, "onLocationChanged: "+latitude+longitude );
      onMapWork();*/
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
  };
private String TAG="error";

  public static MapFragmentClass newInstance(String param1, String param2) {
    MapFragmentClass fragment = new MapFragmentClass();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  MapView mMapView;
  private GoogleMap googleMap;
  Context context;
    Preferences preference;


  FloatingActionButton fab;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  Boolean fabCLicked=false;
  LocationRequest request;

  String guest;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    context = container.getContext();
    View view = LayoutInflater.from(container.getContext()).inflate(R.layout.content_home, container, false);
    mMapView = (MapView) view.findViewById(R.id.mapView);
    mMapView.onCreate(savedInstanceState);

    preference=new Preferences(context);
    guest=preference.getTypeGuest();

    checkLocationEnsblle();

    fab=view.findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (fabCLicked){
          fabCLicked=false;
          fab.setBackground(context.getResources().getDrawable(R.drawable.leku_ic_satellite_on));
          googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        }else {
          fabCLicked=true;
          fab.setBackground(context.getResources().getDrawable(R.drawable.leku_ic_satellite_off));
          googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
      }
    });

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setCancelable(false); // if you want user to wait for some process to finish,
    builder.setView(R.layout.layout_progress_dialogue);
     dialog= builder.create();

    mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

  /*  if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
    }else {
      mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,
              1000, mLocationListener);
    }*/

    if (checkLocationPermission()) {

      request = new LocationRequest();
      request.setInterval(50000);
      request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
      FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(context);
      int permission = ContextCompat.checkSelfPermission(context,
              Manifest.permission.ACCESS_FINE_LOCATION);
      if (permission == PackageManager.PERMISSION_GRANTED) {
        client.requestLocationUpdates(request, new LocationCallback() {
          @Override
          public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location location = locationResult.getLastLocation();
            if (location != null) {
              //  Log.d(TAG, "location update " + location);
              longitude = location.getLongitude();
              latitude = location.getLatitude();
              onMapWork();
            }
          }


        }, null);
      }




    } else {
      checkLocationPermission();
    }

    try {
      MapsInitializer.initialize(getActivity().getApplicationContext());
    } catch (Exception e) {
      e.printStackTrace();
    }


    mMapView.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(GoogleMap googleMaps) {
        googleMap = googleMaps;

        if (preference.getSwitchNightMod()){
          googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.map_in_night));

        }

        if (preference.getTrafficCheck()){
          googleMap.setTrafficEnabled(true);
        }


        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);

      }
    });



    mMapView.onResume();
    return view;
  }

    private void checkLocationEnsblle() {

        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
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
            new AlertDialog.Builder(context)
                    .setMessage("Please Provide location access ")
                    .setPositiveButton("Open Location", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((HomeActivity)context).finishAffinity();
                                }
                            })
                            .show();
        }
  }

    public boolean checkLocationPermission() {
    if (ContextCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

      // Should we show an explanation?
      if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
              Manifest.permission.ACCESS_FINE_LOCATION)) {

        // Show an explanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.
        new android.app.AlertDialog.Builder(context)
                .setTitle(R.string.title_location_permission)
                .setMessage(R.string.text_location_permission)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                    //Prompt the user once explanation has been shown
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                  }
                })
                .create()
                .show();


      } else {
        // No explanation needed, we can request the permission.
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);
      }
      return false;
    } else {
      return true;
    }
  }


  private void onMapWork() {
   // mMapView.removeAllViews();

    googleMap.clear();

    try {
      Log.e(TAG, "onMapReady: "+latitude+longitude);
      LatLng latLng=new LatLng(latitude,longitude);
      googleMap.addMarker(new MarkerOptions().position(latLng).title(preference.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).draggable(false).visible(true));
      googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
    }catch (Exception e){
      Toast.makeText(context, "Failed to get Location Update", Toast.LENGTH_SHORT).show();
    }

    if (preference.getStatusVaalue().equalsIgnoreCase("parking_owner")){
      Log.e(TAG, "onMapWork: no Parking" );
    }else {
      callAPIDataFetching();
    }


  }

  ArrayList<ParkingModel> arrayList=new ArrayList<>();

  private void callAPIDataFetching() {
   // dialog.show();
    APIService apiService= APIClient.getClient().create(APIService.class);
    Call<ParkingModel> call=apiService.MyParkingCall();
    call.enqueue(new Callback<ParkingModel>() {
      @Override
      public void onResponse(Call<ParkingModel> call, Response<ParkingModel> response) {
        ParkingModel parkingModel=response.body();
     //   dialog.dismiss();
        if (parkingModel.getStatus().equalsIgnoreCase("success")){

          arrayList=parkingModel.getParkingData();

          callMapFetchMethod(arrayList);

        }else {
          Toast.makeText(context, parkingModel.getError(), Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<ParkingModel> call, Throwable t) {
        dialog.dismiss();
        Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void callMapFetchMethod(ArrayList<ParkingModel> arrayList) {

    for (int i=0;i<arrayList.size();i++){

      final String latitude=arrayList.get(i).getParkingLatitude();
      final String longitude=arrayList.get(i).getParkingLongitude();
      final String parking_title=arrayList.get(i).getParkingName();
      final String parking_owner_name=arrayList.get(i).getParkingOwnerName();
      final String parking_avaibility=arrayList.get(i).getParkingTime();
      final String parking_id=arrayList.get(i).getParkingId();
      final String parking_description=arrayList.get(i).getParkingDescription();
      final String parking_price=arrayList.get(i).getParkingPrice();
      final String parking_owner_id=arrayList.get(i).getParkingOwnerId();

      try{
        googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude)))
        .title(parking_title).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
          @Override
          public boolean onMarkerClick(Marker marker) {
              startActivity(new Intent(context, ReservationParkingClass.class).putExtra("latitude",latitude)
              .putExtra("longitude",longitude).putExtra("parking_owner",parking_owner_name).putExtra("parking_name",parking_title)
              .putExtra("parking_availability",parking_avaibility).putExtra("parking_id",parking_id).putExtra("parking_des",parking_description)
              .putExtra("price",parking_price).putExtra("owner_id",parking_owner_id));
            return true;
          }
        });
      }catch (Exception e){
e.printStackTrace();
      }


    }
  }


  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
              + " must implement OnFragmentInteractionListener");
    }
  }


  @Override
  public void onResume() {
    super.onResume();
    mMapView.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    mMapView.onPause();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mMapView.onDestroy();
    mLocationManager.removeUpdates(mLocationListener);
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mMapView.onLowMemory();
  }


  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);
  }
}
