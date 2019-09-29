package com.example.parkingapp.Activities.Fragment_parking_info_insider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.support.annotation.DrawableRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.Preferences.ReservationPreferences;
import com.example.parkingapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.LOCATION_SERVICE;


public class Map_fragment_parking extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Map_fragment_parking() {
        // Required empty public constructor
    }


    public static Map_fragment_parking newInstance(String param1, String param2) {
        Map_fragment_parking fragment = new Map_fragment_parking();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Context context;
    MapView mMapView;
    ReservationPreferences preferences;
    private GoogleMap googleMap;
    double latitude,longitude;
    Preferences preferenceMain;

    private LocationManager mLocationManager;
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {

         double latitude =location.getLatitude();
          double  longitude= location.getLongitude();

            Log.e("error", "onLocationChanged: "+latitude+longitude );
            onMapWork(latitude,longitude);
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

    private void onMapWork(final double latitude, final double longitude) {



        GoogleDirection.withServerKey("AIzaSyBX0vQh9fJWUTTLfZ6bmVMYQTg9beo-vlQ")
                .from(new LatLng(latitude, longitude))
                .to(new LatLng(this.latitude, this.longitude))

                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (direction.isOK()) {
                            Route route = direction.getRouteList().get(0);
                            ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
                            googleMap.addPolyline(DirectionConverter.createPolyline(context, directionPositionList, 5, Color.RED));
                            setCameraWithCoordinationBounds(route);
                        } else {
                            //  Toast.makeText(context, "Network issue", Toast.LENGTH_SHORT).show();
                            try {
                                onMapWork(latitude, longitude);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                        Toast.makeText(context, "error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

String type_of_vehical;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context = container.getContext();
        preferenceMain=new Preferences(context);

        if (preferenceMain.getSwitchNightMod()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
            View view = inflater.inflate(R.layout.fragment_map_fragment_parking, container, false);
       //  LayoutInflater.from(container.getContext()).inflate(R.layout.content_home, container, false);
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMaps) {
                googleMap = googleMaps;
            }
        });

        preferences=new ReservationPreferences(context);
         latitude=Double.parseDouble(preferences.getLatitude());
         longitude=Double.parseDouble(preferences.getLongitude());
         type_of_vehical=preferences.getVehicalType();
        final String parkingName=preferences.getParkingName();




       /* mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        try {
            MapsInitializer.initialize(Objects.requireNonNull(getActivity()).getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMaps) {
                googleMap = googleMaps;

           //     googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setRotateGesturesEnabled(true);
                googleMap.getUiSettings().setScrollGesturesEnabled(true);
                googleMap.getUiSettings().setTiltGesturesEnabled(true);

            }
        });

new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        try {
            LatLng latLng=new LatLng(latitude,longitude);

            if (!type_of_vehical.equalsIgnoreCase("")|| type_of_vehical.equalsIgnoreCase(null)) {

                if (type_of_vehical.equalsIgnoreCase("truck_stops")) {
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(parkingName).icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context, R.drawable.green_truck_area))).draggable(false).visible(true));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                } else if (type_of_vehical.equalsIgnoreCase("rest_areas")) {
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(parkingName).icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context, R.drawable.rest_area))).draggable(false).visible(true));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                } else if (type_of_vehical.equalsIgnoreCase("weight_station")) {
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(parkingName).icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context, R.drawable.weight_station))).draggable(false).visible(true));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                } else {
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(parkingName).icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context, R.drawable.free_parking))).draggable(false).visible(true));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                }
            }else {
                googleMap.addMarker(new MarkerOptions().position(latLng).title(parkingName).icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context, R.drawable.free_parking))).draggable(false).visible(true));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
},5000);

        mMapView.onResume();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
