package com.example.parkingapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.EditParkingModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditParkingClass extends AppCompatActivity {


    Preferences preferences;
    Intent intent;
    String parking_id;

    ImageView back_press;
    EditText parking_name_edti,edit_parking_description,edit_parking_price,parking_vehical_type;
    TextView Owner_name_parking;

    Button monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    String temp,owner_number;
    Button save_details;
    String parkingPrice,type_vehicle,parking_description,parking_Owner,parking_time;
    ProgressDialog progressDialog;
    ConstraintLayout abc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_parking_class);

        preferences=new Preferences(this);

        abc=findViewById(R.id.abc);
    /*    if (preferences.getSwitchNightMod()){
            abc.setBackgroundColor(getResources().getColor(R.color.black));
        }else {
            abc.setBackgroundColor(getResources().getColor(R.color.white));
        }
*/


        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Updating");
        progressDialog.setMessage("Please Wait while we are updating");
        progressDialog.setCancelable(false);



        intent=getIntent();
        if (intent!=null){
            parking_id=intent.getStringExtra("parking_id");
            parkingPrice=intent.getStringExtra("parking_price");
            type_vehicle=intent.getStringExtra("parking_vehical");
            parking_description=intent.getStringExtra("parking_description");
            parking_time=intent.getStringExtra("parking_time");
            parking_Owner=intent.getStringExtra("parking_owner_name");
            owner_number=intent.getStringExtra("owner_number");

        }

        back_press=findViewById(R.id.back_press);
        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save_details=findViewById(R.id.save_details);

        parking_name_edti=findViewById(R.id.parking_name_edti);
        edit_parking_description=findViewById(R.id.edit_parking_description);
        edit_parking_price=findViewById(R.id.edit_parking_price);
        parking_vehical_type=findViewById(R.id.parking_vehical_type);

        Owner_name_parking=findViewById(R.id.Owner_name_parking);

        monday=findViewById(R.id.monday);
        tuesday=findViewById(R.id.Tuesday);
        wednesday=findViewById(R.id.wednesday);
        thursday=findViewById(R.id.thursday);
        friday=findViewById(R.id.friday);
        saturday=findViewById(R.id.saturday);
        sunday=findViewById(R.id.sunday);


        if (preferences.getSwitchNightMod()){
            abc.setBackgroundColor(getResources().getColor(R.color.black));
            parking_vehical_type.setTextColor(getResources().getColor(R.color.white));
            edit_parking_price.setTextColor(getResources().getColor(R.color.white));
            edit_parking_description.setTextColor(getResources().getColor(R.color.white));
            parking_name_edti.setTextColor(getResources().getColor(R.color.white));
            Owner_name_parking.setTextColor(getResources().getColor(R.color.white));
        }else {
            abc.setBackgroundColor(getResources().getColor(R.color.black));
            parking_name_edti.setTextColor(getResources().getColor(R.color.black));
            edit_parking_price.setTextColor(getResources().getColor(R.color.black));
            edit_parking_description.setTextColor(getResources().getColor(R.color.black));
            parking_vehical_type.setTextColor(getResources().getColor(R.color.black));
            Owner_name_parking.setTextColor(getResources().getColor(R.color.black));

        }

        parking_name_edti.setText(parking_Owner);
        parking_vehical_type.setText(type_vehicle);
        edit_parking_description.setText(parking_description);
        edit_parking_price.setText(parkingPrice);

        temp=parking_time;

        try {
            String[] separated = temp.split(",");
            String mon = separated[0];
            String tues = separated[0];
            String wed = separated[0];
            String thurs = separated[0];
            String fri = separated[0];
            String sat = separated[0];
            String sun = separated[0];

          //  Log.e("error", "onCreate: " + startMon);

            if (mon.equalsIgnoreCase("m")){
                monday.setBackgroundColor(getResources().getColor(R.color.green));
            }
            if (tues.equalsIgnoreCase("t")){
                tuesday.setBackgroundColor(getResources().getColor(R.color.green));
            }
            if (wed.equalsIgnoreCase("w")){
                wednesday.setBackgroundColor(getResources().getColor(R.color.green));
            }
            if (thurs.equalsIgnoreCase("th")){
                thursday.setBackgroundColor(getResources().getColor(R.color.green));
            }
            if (fri.equalsIgnoreCase("f")){
                friday.setBackgroundColor(getResources().getColor(R.color.green));
            }
            if (sat.equalsIgnoreCase("sat")){
                saturday.setBackgroundColor(getResources().getColor(R.color.green));
            }
            if (sun.equalsIgnoreCase("s")){
                sunday.setBackgroundColor(getResources().getColor(R.color.green));
            }
        }catch (Exception e){
            e.printStackTrace();
        }





            Log.e("error", "onCreate: "+parking_time);


        save_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAPI();
            }
        });


    }

    private void callAPI() {
        String name=parking_name_edti.getText().toString();
     String vehical=   parking_vehical_type.getText().toString();
        String des  = edit_parking_description.getText().toString();
        String price  = edit_parking_price.getText().toString();
        ;


        if (TextUtils.isEmpty(parking_id)){
            Toast.makeText(this, "No ID Provided", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(owner_number)){
            Toast.makeText(this, "No Owner Number Provided", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(vehical)){
            parking_vehical_type.setError("Give Vehicle type");
        }else if (TextUtils.isEmpty(des)){
            edit_parking_description.setError("No description provided");
        }else if (TextUtils.isEmpty(price)){
            edit_parking_price.setError("No price given");
        }else {
            progressDialog.show();
            APIService service = APIClient.getClient().create(APIService.class);
            Call<EditParkingModel> modelCall = service.editParking(parking_id, name, temp, owner_number, price, des, vehical);
            modelCall.enqueue(new Callback<EditParkingModel>() {
                @Override
                public void onResponse(Call<EditParkingModel> call, Response<EditParkingModel> response) {
                    EditParkingModel model=response.body();
                    progressDialog.dismiss();
                    if (model.getStatus().equalsIgnoreCase("success")){
                        Toast.makeText(EditParkingClass.this, model.getParkingData(), Toast.LENGTH_SHORT).show();
                        finish();
                    }else{

                        Toast.makeText(EditParkingClass.this, "Failed to update", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<EditParkingModel> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(EditParkingClass.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
