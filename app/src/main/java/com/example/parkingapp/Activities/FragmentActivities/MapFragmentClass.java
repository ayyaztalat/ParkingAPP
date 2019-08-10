package com.example.parkingapp.Activities.FragmentActivities;

import android.Manifest;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parkingapp.Activities.HomeActivity;
import com.example.parkingapp.Activities.ReservationParkingClass;
import com.example.parkingapp.Activities.tempClass;
import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.CustomizationMapModel;
import com.example.parkingapp.Models.FilterModel;
import com.example.parkingapp.Models.FreeParkingModel;
import com.example.parkingapp.Models.ParkingModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;
import android.location.LocationListener;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.ArrayList;

import afu.org.checkerframework.checker.nullness.qual.NonNull;
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

  Boolean filters=false;

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
  private String TAG = "error";

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


  FloatingActionButton fab, brightness, filter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  Boolean fabCLicked = false;
  LocationRequest request;

  String guest;
  Preferences preferenceMain;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    //  context = container.getContext();

    context = container.getContext();
    preferenceMain = new Preferences(context);
    filterClicked=false;

    View view = LayoutInflater.from(container.getContext()).inflate(R.layout.content_home, container, false);
    mMapView = (MapView) view.findViewById(R.id.mapView);
    mMapView.onCreate(savedInstanceState);

    preference = new Preferences(context);
    guest = preference.getTypeGuest();

    checkLocationEnsblle();

    fab = view.findViewById(R.id.fab);
    brightness = view.findViewById(R.id.brightness);
    filter = view.findViewById(R.id.filter);

    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        getDeviceLocation();

      }
    });

    brightness.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        changeBrightness();
      }
    });

    filter.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openDialogue();
      }
    });


    mFusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(getActivity());
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setCancelable(false); // if you want user to wait for some process to finish,
    builder.setView(R.layout.layout_progress_dialogue);
    dialog = builder.create();

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

        if (preference.getSwitchNightMod()) {
          googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.map_in_night));

        }

        if (preference.getTrafficCheck()) {
          googleMap.setTrafficEnabled(true);
        }


        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);

      }
    });



    mMapView.onResume();
    return view;
  }

  private void openDialogue() {

    LayoutInflater inflater = getLayoutInflater();
    View alertLayout = inflater.inflate(R.layout.pop_map_filter, null);
    final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(context);

    final CheckBox truck_stops=alertLayout.findViewById(R.id.truck_stop);
    final CheckBox weight_stations=alertLayout.findViewById(R.id.weight_station);
    final CheckBox parking_areas=alertLayout.findViewById(R.id.parking_areas);
    final Button update=alertLayout.findViewById(R.id.update);
    final CheckBox rest_parking=alertLayout.findViewById(R.id.rest_parking);
    final CheckBox dont_show_free_parking=alertLayout.findViewById(R.id.dont_show_free_parking);

    //  etcode = alertLayout.findViewById(R.id.et_password);

    truck_stops.setChecked(preference.getTruckCheck());
    weight_stations.setChecked(preference.getWeightCheck());
    parking_areas.setChecked(preference.getPrkingArea());
    rest_parking.setChecked(preference.getRestCheck());

    truck_stops.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
          preference.setTruckCheck(isChecked);
      }
    });

    weight_stations.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        preference.setWeightStop(isChecked);
      }
    });

    parking_areas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        preference.setParkingArea(isChecked);
      }
    });

    rest_parking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        preference.setRestParking(isChecked);
      }
    });
      dont_show_free_parking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              preference.setDontShowParking(isChecked);
          }
      });



    dont_show_free_parking.setChecked(preference.getDontShowParking());
    if (preference.getDontShowParking()){
        truck_stops.setChecked(false);
        weight_stations.setChecked(false);
        parking_areas.setChecked(false);
        rest_parking.setChecked(false);
    }


    // this is set the view from XML inside AlertDialog
    alert.setView(alertLayout);
    // disallow cancel of AlertDialog on click of back button and outside touch
    alert.setCancelable(false);
    final android.app.AlertDialog dialog = alert.create();
    dialog.show();
    update.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          String truck, weight, parking_area, rest_parkings;

          if (truck_stops.isChecked()) {
              truck = "truck_stops";
            //  preference.setTruckCheck(true);
          } else {
              truck = "";
         //   preference.setTruckCheck(false);
          }

          if (weight_stations.isChecked()) {
              weight = "weight_station";
         //   preference.setWeightStop(true);
          } else {
              weight = "";
         //   preference.setWeightStop(false);
          }

          if (parking_areas.isChecked()) {
              parking_area = "parking_areas";
         //   preference.setParkingArea(true);
          } else {
              parking_area = "";
        //    preference.setParkingArea(false);
          }
          if (rest_parking.isChecked()) {
              rest_parkings = "rest_areas";
         //   preference.setRestParking(true);
          } else {
              rest_parkings = "";
          //  preference.setRestParking(false);
          }

          dialog.dismiss();

          if (preference.getDontShowParking()) {
              Toast.makeText(context, "Free Parking Closed", Toast.LENGTH_SHORT).show();
              googleMap.clear();

              filterClicked=false;
              preference.setFilterClicked(filterClicked);
              callAPIDataFetching();

          } else {
            filterClicked=true;
            preference.setFilterClicked(filterClicked);
            if (TextUtils.isEmpty(truck)&&TextUtils.isEmpty(weight)&&TextUtils.isEmpty(parking_area)&&TextUtils.isEmpty(rest_parkings)){

              callSecondFreeAPI();
            }else {
              googleMap.clear();
              callAPIDataFetching();
              apiCallFilter(truck, weight, parking_area, rest_parkings);

            }//  apiServiceCall();
              // hide virtual keyboard
          }
      }
    });
  }

  ArrayList<FilterModel> arrayListModel=new ArrayList<>();
  Boolean filterClicked=false;

  private void apiCallFilter(String truck, String weight, String parking_area, String rest_parkings) {
    APIService service=APIClient.getClient().create(APIService.class);
    Call<FilterModel> modelCall=service.Filter(truck,weight,parking_area,rest_parkings);
    modelCall.enqueue(new Callback<FilterModel>() {
      @Override
      public void onResponse(Call<FilterModel> call, Response<FilterModel> response) {
        final FilterModel model=response.body();
        if (model.getStatus().equalsIgnoreCase("success")){
            try {
          arrayListModel=model.getParkingData();

          for (int i=0;i<arrayListModel.size();i++) {

            final String latitude = arrayListModel.get(i).getParkingLatitude();
            final String longitude = arrayListModel.get(i).getParkingLongitude();
            final String parking_title = arrayListModel.get(i).getParkingName();
            final String parking_owner_name = arrayListModel.get(i).getParkingOwnerName();
            final String parking_avaibility = arrayListModel.get(i).getParkingTime();
            final String parking_id = arrayListModel.get(i).getParkingId();
            final String parking_description = arrayListModel.get(i).getParkingDescription();
            final String parking_price = arrayListModel.get(i).getParkingPrice();
            final String parking_owner_id = arrayListModel.get(i).getParkingOwnerId();
            final String remaining_parking_spots = arrayListModel.get(i).getRemainingParkingSpots();
            final String filled_parking_spots = arrayListModel.get(i).getFilledParkingSpots();
            final String typeofVehical=arrayListModel.get(i).getTypeOfVehicle();

            LatLng latLng=new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));


           /*   googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)))
                      .title(parking_title).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
*/

                if (typeofVehical.equalsIgnoreCase("truck_stops")){
                    googleMap.addMarker(new MarkerOptions().position(latLng)
                           .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context,R.drawable.green_truck_area))).title(""+i));

                }else if (typeofVehical.equalsIgnoreCase("rest_areas")){
                    googleMap.addMarker(new MarkerOptions().position(latLng)
                           .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context,R.drawable.rest_area))).title(""+i));

                }else if (typeofVehical.equalsIgnoreCase("weight_station")){
                    googleMap.addMarker(new MarkerOptions().position(latLng)
                           .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context,R.drawable.weight_station))).title(""+i));

                }else {
                    googleMap.addMarker(new MarkerOptions().position(latLng)
                           .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context,R.drawable.free_parking))).title(""+i));

                }


            //  callSecondFreeAPI();

              googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                  ArrayList<CustomizationMapModel> modelForMaps=new ArrayList<>();
                  ArrayList<FilterModel> models=new ArrayList<>();

                  try {
                    if (marker.getTitle() != null) {
                      models.add(arrayListModel.get(Integer.parseInt(marker.getTitle())));
                      modelForMaps.add(new CustomizationMapModel(models.get(0).getParkingLatitude(), models.get(0).getParkingLongitude(), models.get(0).getParkingName(), models.get(0).getParkingOwnerName(), models.get(0).getParkingTime(),
                              models.get(0).getParkingId(), models.get(0).getParkingDescription(), models.get(0).getParkingPrice(),
                              models.get(0).getParkingOwnerId(), models.get(0).getRemainingParkingSpots(), models.get(0).getFilledParkingSpots(), models.get(0).getTypeOfVehicle(), models.get(0).getParkingName(),
                              models.get(0).getParkingOwnerNumber(), models.get(0).getParking_image1(), models.get(0).getParking_image2()));

                      Intent intent = new Intent(context, ReservationParkingClass.class);
                      intent.putExtra("MapData", new Gson().toJson(modelForMaps));
                      startActivity(intent);
                      getActivity().finish();
                    }
                  }catch (Exception e){
                    e.printStackTrace();
                  }

            /*      startActivity(new Intent(context, ReservationParkingClass.class).putExtra("latitude",arrayListModel.get(i).getParkingLatitude())
                          .putExtra("longitude",arrayListModel.get(i).getParkingLongitude()).putExtra("parking_owner",arrayListModel.get(i).getParkingOwnerName()).putExtra("parking_name",arrayListModel.get(i).getParkingName())
                          .putExtra("parking_availability",arrayListModel.get(i).getParkingTime()).putExtra("parking_id",arrayListModel.get(i).getParkingId()).putExtra("parking_des",arrayListModel.get(i).getParkingDescription())
                          .putExtra("price",arrayListModel.get(i).getParkingPrice()).putExtra("owner_id",arrayListModel.get(i).getParkingOwnerId())
                          .putExtra("remaining_parking_spots",arrayListModel.get(i).getRemainingParkingSpots()).putExtra("filled_parking_spots",arrayListModel.get(i).getFilledParkingSpots()));
                  */return true;
                }
              });
            }
          }catch (Exception e) {
              e.printStackTrace();
            }

          }else {
          Toast.makeText(context, model.getError(), Toast.LENGTH_SHORT).show();
        }

      }

      @Override
      public void onFailure(Call<FilterModel> call, Throwable t) {

      }
    });
  }

  boolean night;
  private void changeBrightness() {
   night= preference.getSwitchNightMod();

    if(night)

  {

    night = false;
    preference.setSwitchNightMod(false);
    startActivity(new Intent(context, tempClass.class));

  }else

  {

    night = true;
    preference.setSwitchNightMod(true);
    startActivity(new Intent(context, tempClass.class));


  }
}

  private FusedLocationProviderClient mFusedLocationProviderClient;
  private void getDeviceLocation() {
    try {

      Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
      locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
        @Override
        public void onComplete(@NonNull Task<Location> task) {
          if (task.isSuccessful()) {
            // Set the map's camera position to the current location of the device.
            Location location = task.getResult();
            LatLng currentLatLng = new LatLng(location.getLatitude(),
                    location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLatLng,
                    14.0f);
            googleMap.moveCamera(update);
          }
        }
      });

    } catch (SecurityException e) {
      Log.e("Exception: %s", e.getMessage());
    }
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
      try {
        if (preference.getFilterClicked()) {
          String truck, weight, parking_area, rest_parkings;

          if (preference.getTruckCheck()) {
            truck = "truck_stops";
            //  preference.setTruckCheck(true);
          } else {
            truck = "";
            //   preference.setTruckCheck(false);
          }

          if (preference.getWeightCheck()) {
            weight = "weight_station";
            //   preference.setWeightStop(true);
          } else {
            weight = "";
            //   preference.setWeightStop(false);
          }

          if (preference.getPrkingArea()) {
            parking_area = "parking_areas";
            //  preference.setParkingArea(true);
          } else {
            parking_area = "";
            //   preference.setParkingArea(false);
          }
          if (preference.getRestCheck()) {
            rest_parkings = "rest_areas";
            // preference.setRestParking(true);
          } else {
            rest_parkings = "";
            //  preference.setRestParking(false);
          }
          if (TextUtils.isEmpty(truck) && TextUtils.isEmpty(weight) && TextUtils.isEmpty(parking_area) && TextUtils.isEmpty(rest_parkings)) {
           preference.setFilterClicked(false);
           callAPIDataFetching();
            callSecondFreeAPI();
          } else {
         //   googleMap.clear();
            callAPIDataFetching();
            apiCallFilter(truck, weight, parking_area, rest_parkings);
          }
        }else {
          callAPIDataFetching();
          callSecondFreeAPI();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
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
        ParkingModel parkingModel = response.body();
        //   dialog.dismiss();
        try {


          if (parkingModel.getStatus().equalsIgnoreCase("success")) {

            arrayList = parkingModel.getParkingData();

            callMapFetchMethod(arrayList);

          } else {
            Toast.makeText(context, parkingModel.getError(), Toast.LENGTH_SHORT).show();
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      @Override
      public void onFailure(Call<ParkingModel> call, Throwable t) {
        dialog.dismiss();
        Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void callMapFetchMethod(final ArrayList<ParkingModel> arrayList) {

    for ( int i=0;i<arrayList.size();i++){

      final String latitude=arrayList.get(i).getParkingLatitude();
      final String longitude=arrayList.get(i).getParkingLongitude();
      final String parking_title=arrayList.get(i).getParkingName();
      final String parking_owner_name=arrayList.get(i).getParkingOwnerName();
      final String parking_avaibility=arrayList.get(i).getParkingTime();
      final String parking_id=arrayList.get(i).getParkingId();
      final String parking_description=arrayList.get(i).getParkingDescription();
      final String parking_price=arrayList.get(i).getParkingPrice();
      final String parking_owner_id=arrayList.get(i).getParkingOwnerId();
      final String remaining_parking_spots=arrayList.get(i).getRemainingParkingSpots();
      final String filled_parking_spots=arrayList.get(i).getFilledParkingSpots();
      final String typeofVehical=arrayList.get(i).getTypeOfVehicle();


       boolean truckss=preference.getTruckCheck();
     boolean   weight_stations=preference.getWeightCheck();
     boolean   parking_areas=preference.getPrkingArea();
     boolean   rest_parking=preference.getPrkingArea();


        String truck,weight,parking_area,rest_parkings;

        if (truckss){
            truck="truck_stops";
        }else{
            truck="";
        }

        if (weight_stations){
            weight="weight_station";
        }else {
            weight="";
        }

        if (parking_areas){
            parking_area="parking_areas";
        }
        else {
            parking_area="";
        }
        if (rest_parking){
            rest_parkings="rest_areas";
        }else {
            rest_parkings="";
        }


      LatLng latLng=new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
    //  Toast.makeText(context, "latlng "+latLng.latitude+latLng.longitude, Toast.LENGTH_SHORT).show();
      try{

          if (typeofVehical.equalsIgnoreCase("truck_stops")){
              googleMap.addMarker(new MarkerOptions().position(latLng)
                      .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context,R.drawable.green_truck_area))).title(""+i));

          }else if (typeofVehical.equalsIgnoreCase("rest_areas")){
              googleMap.addMarker(new MarkerOptions().position(latLng)
                      .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context,R.drawable.rest_area))).title(""+i));

          }else if (typeofVehical.equalsIgnoreCase("weight_station")){
              googleMap.addMarker(new MarkerOptions().position(latLng)
                      .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context,R.drawable.weight_station))).title(""+i));

          }else {
              googleMap.addMarker(new MarkerOptions().position(latLng)
                      .title(parking_title).icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context,R.drawable.free_parking))).title(""+i));

          }

          if (preference.getDontShowParking()){
              Toast.makeText(context, "Free parking Closed", Toast.LENGTH_SHORT).show();
          }else {

              if (filterClicked) {
             //   googleMap.clear();
                   callAPIDataFetching();
                  apiCallFilter(truck, weight, parking_area, rest_parkings);
              } else {
                  callSecondFreeAPI();
              }
          }
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
          @Override
          public boolean onMarkerClick(Marker marker) {

            ArrayList<CustomizationMapModel> modelForMaps=new ArrayList<>();
            ArrayList<ParkingModel> models=new ArrayList<>();

            if(marker.getTitle()!=null){
              models.add(arrayList.get(Integer.parseInt(marker.getTitle())));
              modelForMaps.add(new CustomizationMapModel(models.get(0).getParkingLatitude(),models.get(0).getParkingLongitude(),models.get(0).getParkingName(),models.get(0).getParkingOwnerName(),models.get(0).getParkingTime(),
                      models.get(0).getParkingId(),models.get(0).getParkingDescription(),models.get(0).getParkingPrice(),
                      models.get(0).getParkingOwnerId(),models.get(0).getRemainingParkingSpots(),models.get(0).getFilledParkingSpots(),models.get(0).getTypeOfVehicle(), models.get(0).getParkingName()
                      ,models.get(0).getParkingOwnerNumber(),models.get(0).getParking_image1(),models.get(0).getParking_image2()));

              Intent intent=new Intent(context,ReservationParkingClass.class);
              intent.putExtra("MapData",new Gson().toJson(modelForMaps));
              startActivity(intent);
              getActivity().finish();
            }


        /*      startActivity(new Intent(context, ReservationParkingClass.class).putExtra("latitude",arrayList.get(i).getParkingLatitude())
              .putExtra("longitude",arrayList.get(i).getParkingLongitude()).putExtra("parking_owner",arrayList.get(i).getParkingOwnerName()).putExtra("parking_name",arrayList.get(i).getParkingName())
              .putExtra("parking_availability",arrayList.get(i).getParkingTime()).putExtra("parking_id",arrayList.get(i).getParkingId()).putExtra("parking_des",arrayList.get(i).getParkingDescription())
              .putExtra("price",arrayList.get(i).getParkingPrice()).putExtra("owner_id",arrayList.get(i).getParkingOwnerId())
              .putExtra("remaining_parking_spots",arrayList.get(i).getRemainingParkingSpots()).putExtra("filled_parking_spots",arrayList.get(i).getFilledParkingSpots()));
           */ return true;
          }
        });
      }catch (Exception e){
            e.printStackTrace();
      }


    }
  }

  private void callSecondFreeAPI() {

    APIService service=APIClient.getClient().create(APIService.class);
    Call<FreeParkingModel> modelCallback=service.FREE_PARKING_MODEL_CALL();
    modelCallback.enqueue(new Callback<FreeParkingModel>() {
      @Override
      public void onResponse(Call<FreeParkingModel> call, Response<FreeParkingModel> response) {
        try {
          FreeParkingModel model = response.body();
          if (model.getStatus().equalsIgnoreCase("success")) {
            if (preference.getDontShowParking()){
              Toast.makeText(context, "Free Parking Closed", Toast.LENGTH_SHORT).show();
            }else {
            callSecondFetching(model.getParkingData());
               }
          } else {
            Toast.makeText(context, model.getError(), Toast.LENGTH_SHORT).show();
          }

        }catch (Exception e){
          e.printStackTrace();
        }
      }

      @Override
      public void onFailure(Call<FreeParkingModel> call, Throwable t) {
        Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
      }
    });
  }

  ArrayList<FreeParkingModel> freeParkingModels=new ArrayList<>();

  private void callSecondFetching(ArrayList<FreeParkingModel> parkingData) {

    freeParkingModels=parkingData;

    for (int i=0;i<freeParkingModels.size();i++) {

      final String latitude = freeParkingModels.get(i).getParkingLatitude();
      final String longitude = freeParkingModels.get(i).getParkingLongitude();
      final String parking_title = freeParkingModels.get(i).getParkingName();
      final String parking_owner_name = freeParkingModels.get(i).getParkingOwnerName();
      final String parking_avaibility = freeParkingModels.get(i).getParkingTime();
      final String parking_id = freeParkingModels.get(i).getParkingId();
      final String parking_description = freeParkingModels.get(i).getParkingDescription();
      final String parking_price = freeParkingModels.get(i).getParkingPrice();
      final String parking_owner_id = freeParkingModels.get(i).getParkingOwnerId();
      final String remaining_parking_spots = freeParkingModels.get(i).getRemainingParkingSpots();
      final String filled_parking_spots = freeParkingModels.get(i).getFilledParkingSpots();
      String typeofVehical=freeParkingModels.get(i).getTypeOfVehicle();
      LatLng latLng=new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
   //   Toast.makeText(context, "latlng "+latLng.latitude+latLng.longitude, Toast.LENGTH_SHORT).show();
      try {

        if (typeofVehical.equalsIgnoreCase("truck_stops")){
          googleMap.addMarker(new MarkerOptions().position(latLng)
                  .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context,R.drawable.green_truck_area))).title(""+i));

        }else if (typeofVehical.equalsIgnoreCase("rest_areas")){
          googleMap.addMarker(new MarkerOptions().position(latLng)
                  .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context,R.drawable.rest_area))).title(""+i));

        }else if (typeofVehical.equalsIgnoreCase("weight_station")){
          googleMap.addMarker(new MarkerOptions().position(latLng)
                 .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context,R.drawable.weight_station))).title(""+i));

        }else {
          googleMap.addMarker(new MarkerOptions().position(latLng)
                  .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context,R.drawable.free_parking))).title(""+i));

        }


     //   callSecondFreeAPI();

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
          @Override
          public boolean onMarkerClick(Marker marker) {

            ArrayList<CustomizationMapModel> modelForMaps=new ArrayList<>();
            ArrayList<FreeParkingModel> models=new ArrayList<>();

            if(marker.getTitle()!=null) {
              try {
                models.add(freeParkingModels.get(Integer.parseInt(marker.getTitle())));
                modelForMaps.add(new CustomizationMapModel(models.get(0).getParkingLatitude(), models.get(0).getParkingLongitude(), models.get(0).getParkingName(), models.get(0).getParkingOwnerName(), models.get(0).getParkingTime(),
                        models.get(0).getParkingId(), models.get(0).getParkingDescription(), models.get(0).getParkingPrice(),
                        models.get(0).getParkingOwnerId(), models.get(0).getRemainingParkingSpots(),
                        models.get(0).getFilledParkingSpots(), models.get(0).getTypeOfVehicle(), models.get(0).getParkingName(),
                        models.get(0).getParkingOwnerNumber(),models.get(0).getParking_image1(),models.get(0).getParking_image2()));

                Intent intent = new Intent(context, ReservationParkingClass.class);
                intent.putExtra("MapData", new Gson().toJson(modelForMaps));
                startActivity(intent);
                getActivity().finish();
              }catch (Exception e){
                e.printStackTrace();
              }
            }

      /*      startActivity(new Intent(context, ReservationParkingClass.class).putExtra("latitude", freeParkingModels.get(i).getParkingLatitude())
                    .putExtra("longitude", freeParkingModels.get(i).getParkingLongitude()).putExtra("parking_owner", freeParkingModels.get(i).getParkingOwnerName()).putExtra("parking_name", freeParkingModels.get(i).getParkingName())
                    .putExtra("parking_availability", freeParkingModels.get(i).getParkingTime()).putExtra("parking_id", freeParkingModels.get(i).getParkingId()).putExtra("parking_des", freeParkingModels.get(i).getParkingDescription())
                    .putExtra("price", freeParkingModels.get(i).getParkingPrice()).putExtra("owner_id", freeParkingModels.get(i).getParkingOwnerId())
                    .putExtra("remaining_parking_spots", freeParkingModels.get(i).getRemainingParkingSpots()).putExtra("filled_parking_spots", freeParkingModels.get(i).getFilledParkingSpots()));
          */  return true;
          }
        });
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
    }


  public static Bitmap createCustomMarker(Context context, @DrawableRes int resource) {

    View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

    ImageView markerImage =  marker.findViewById(R.id.user_dp);
    markerImage.setImageResource(resource);


    DisplayMetrics displayMetrics = new DisplayMetrics();
    ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
    marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
    marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
    marker.buildDrawingCache();
    Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    marker.draw(canvas);

    return bitmap;
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
