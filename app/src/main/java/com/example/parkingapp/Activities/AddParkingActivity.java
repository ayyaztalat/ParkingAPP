package com.example.parkingapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.AddParkingModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;
import com.google.android.gms.maps.model.LatLng;
import com.schibstedspain.leku.LocationPickerActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.schibstedspain.leku.LocationPickerActivityKt.LATITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.LOCATION_ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.LONGITUDE;

public class AddParkingActivity extends AppCompatActivity {
    ImageView back_press;
    Preferences preference;
    TextView location_parking;
    AppCompatEditText edit_parking_name,edit_parking_description,edit_parking_status,edit_parking_car_type,edit_parking_price;
    Button monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    Button save_parking;
    String days="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_parking);

        preference=new Preferences(this);
        location_parking=findViewById(R.id.location_parking);
        edit_parking_price=findViewById(R.id.edit_parking_price);
        edit_parking_car_type=findViewById(R.id.edit_parking_car_type);
        edit_parking_description=findViewById(R.id.edit_parking_description);
        edit_parking_name=findViewById(R.id.edit_parking_name);
        edit_parking_status=findViewById(R.id.edit_parking_status);


        location_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationGettingMechanism();
            }
        });

        monday=findViewById(R.id.monday);
        tuesday=findViewById(R.id.Tuesday);
        wednesday=findViewById(R.id.wednesday);
        thursday=findViewById(R.id.thursday);
        friday=findViewById(R.id.friday);
        saturday=findViewById(R.id.satellite);
        sunday=findViewById(R.id.sunday);

        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days="M";
            }
        });

        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days=days+","+"T";
            }
        });

        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days=days+","+"W";
            }
        });

        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days=days+","+"TH";
            }
        });

        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days=days+","+"F";
            }
        });

        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days=days+","+"SA";
            }
        });

          sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days=days+","+"S";
            }
        });

        save_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallDataGet();
            }
        });

        back_press=findViewById(R.id.back_press);
        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });
    }

    private void LocationGettingMechanism() {
        if (lat!=null && lng!=null) {
            LatLng latLng=new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
            Intent locationPickerIntent = new LocationPickerActivity.Builder()
                    //  .withLocation(40.7128, 74.0060)
                    .withGeolocApiKey("AIzaSyBiWCqUwYcKgZyvusgkFOKfop1vA2dLZnE")
                    .withSearchZone("es_ES")
                    .withLocation(latLng)
//                        .withStreetHidden()
//                        .withCityHidden()
//                        .withZipCodeHidden()
//                        .withSatelliteViewHidden()
                    .withGooglePlacesEnabled()
                    //   .withVoiceSearchHidden()
                    .build(getApplicationContext());

            startActivityForResult(locationPickerIntent, 1);
        }else{
            Intent locationPickerIntent = new LocationPickerActivity.Builder()
                    //  .withLocation(40.7128, 74.0060)
                    .withGeolocApiKey("AIzaSyBiWCqUwYcKgZyvusgkFOKfop1vA2dLZnE")
                    .withSearchZone("es_ES")
//                        .withStreetHidden()
//                        .withCityHidden()
//                        .withZipCodeHidden()
//                        .withSatelliteViewHidden()
                    .withGooglePlacesEnabled()
                    //   .withVoiceSearchHidden()
                    .build(getApplicationContext());

            startActivityForResult(locationPickerIntent, 1);
        }

    }

    private void CallDataGet() {

        String parkingName=edit_parking_name.getText().toString();
        String status=edit_parking_status.getText().toString();
        String carType=edit_parking_car_type.getText().toString();
        String Description=edit_parking_description.getText().toString();
        String locationParking=location_parking.getText().toString();
        String price=edit_parking_price.getText().toString();
        if (TextUtils.isEmpty(parkingName)){
            edit_parking_name.setText("Parking Name is Empty");
        }else if (TextUtils.isEmpty(status)){
            edit_parking_status.setText("Parking status is Empty");
        }else if (TextUtils.isEmpty(carType)){
            edit_parking_car_type.setText("Parking Vehicle Type is Empty");
        }else if (TextUtils.isEmpty(Description)){
            edit_parking_description.setText("Parking Description is Empty");
        }else if (TextUtils.isEmpty(locationParking)){
            edit_parking_description.setText("No Location is provided");
        }else if (TextUtils.isEmpty(days)){
            edit_parking_description.setText("No Days are Seleced");
        }else if (TextUtils.isEmpty(price)){
            edit_parking_price.setText("No price given");
        }else{
            CallAPI(parkingName,status,carType,Description,locationParking,price);
        }

    }

    private void CallAPI(String parkingName, String status, String carType, String description, String locationParking, String price) {

        APIService service= APIClient.getClient().create(APIService.class);
        Call<AddParkingModel> modelCall=service.AddParking(preference.getUserId(),lat,lng,parkingName,days,preference.getName(),preference.getPhone(),status,price,description,carType);
        modelCall.enqueue(new Callback<AddParkingModel>() {
            @Override
            public void onResponse(Call<AddParkingModel> call, Response<AddParkingModel> response) {
                AddParkingModel model=response.body();
                if (model.getStatus().equalsIgnoreCase("success")){
                   // Toast.makeText(AddParkingActivity.this, model.getParkingData(), Toast.LENGTH_SHORT).show();

                    String parking_id=model.getParkingId();

                    startActivity(new Intent(getApplicationContext(),AddPhotosParking.class).putExtra("parking_id",parking_id));
                }else {
                    Toast.makeText(AddParkingActivity.this, model.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddParkingModel> call, Throwable t) {
                Toast.makeText(AddParkingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        finish();
    }

    static String lat,lng;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1) {
            if(resultCode==RESULT_OK) {

                Double latitudes = data.getDoubleExtra(LATITUDE, 0.0);
                Double longitude = data.getDoubleExtra(LONGITUDE, 0.0);
                String Address = data.getStringExtra(LOCATION_ADDRESS);

                location_parking.setText(Address);
                lat = String.valueOf(latitudes);
                lng = String.valueOf(longitude);
                Log.e("error", "onActivityResult: " + lat + lng);

            }

        }

        if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("RESULT****", "CANCELLED");
            Toast.makeText(this, "User Cancelled the request", Toast.LENGTH_SHORT).show();
        }
    }
}
