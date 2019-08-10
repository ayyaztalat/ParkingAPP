package com.example.parkingapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.BookingModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingClass extends AppCompatActivity {
    TextView name_icon,title,price,from_date,to_date,text_days,time_picker;
    Button reserve;
    CalendarView calendarView;
    ProgressDialog dialog;
    String parking_reserved_spots,parking_filled_spots,parking_remaining_spots;
    Preferences preferences;
    ConstraintLayout abc;
    Toolbar toolbar;
    String time_from_1,time_to_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_class);

        preferences=new Preferences(this);
        abc=findViewById(R.id.abc);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("RESERVE PARKING");

        dialog=new ProgressDialog(this);
        dialog.setTitle("Booking");
        dialog.setMessage("Please wait we are making a booking");
        dialog.setCancelable(false);


        name_icon=findViewById(R.id.name_icon);
        title=findViewById(R.id.title);
        price=findViewById(R.id.price);
        from_date=findViewById(R.id.from_date);
        to_date=findViewById(R.id.to_date);
        text_days=findViewById(R.id.text_days);
        time_picker=findViewById(R.id.time_picker);

        reserve=findViewById(R.id.reserve);
        calendarView=findViewById(R.id.calendarView);
        time_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.pop_time_picker, null);
                final AlertDialog.Builder alert = new AlertDialog.Builder(BookingClass.this);

                Button btn_save_time=alertLayout.findViewById(R.id.btn_save_time);
                final TimePicker time_pickers=alertLayout.findViewById(R.id.time_picker);

                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(true);
                final AlertDialog dialog = alert.create();
                dialog.show();



                time_pickers.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        time_to_1=convertDate(hourOfDay)+":"+convertDate(minute);
                    }
                });

                btn_save_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(time_to_1)) {
                            time_to_1=convertDate(time_pickers.getHour())+":"+convertDate(time_pickers.getMinute());
                            time_picker.setText(time_to_1);
                            dialog.dismiss();
                        } else {
                            time_picker.setText(time_to_1);
                            dialog.dismiss();
                        }}
                });
            }
        });

        calendarView.setVisibility(View.GONE);

        if (preferences.getSwitchNightMod()){
            abc.setBackgroundColor(getResources().getColor(R.color.black));
           /* name_icon.setTextColor(getResources().getColor(R.color.white));
            title.setTextColor(getResources().getColor(R.color.white));
            price.setTextColor(getResources().getColor(R.color.white));
            from_date.setTextColor(getResources().getColor(R.color.white));
            to_date.setTextColor(getResources().getColor(R.color.white));
            text_days.setTextColor(getResources().getColor(R.color.white));
            time_picker.setTextColor(getResources().getColor(R.color.white));
            calendarView.setBackgroundColor(getResources().getColor(R.color.white));*/
        }else {
            abc.setBackgroundColor(getResources().getColor(R.color.white));
            /*name_icon.setTextColor(getResources().getColor(R.color.black));
            title.setTextColor(getResources().getColor(R.color.black));
            price.setTextColor(getResources().getColor(R.color.black));
            from_date.setTextColor(getResources().getColor(R.color.black));
            to_date.setTextColor(getResources().getColor(R.color.black));
            text_days.setTextColor(getResources().getColor(R.color.black));
            calendarView.setBackgroundColor(getResources().getColor(R.color.white));
            time_picker.setTextColor(getResources().getColor(R.color.black));*/
        }

        /*.putExtra("truck_id",arrayList.get(i).getTruckId())
                .putExtra("parking_id",arrayList.get(i).getParkingId())
                .putExtra("truck_owner_name",arrayList.get(i).getTruckOwnerName())
                .putExtra("parking_owner_name",arrayList.get(i).getParkingOwnerName())
                .putExtra("truck_number",arrayList.get(i).getTruckNumber())
                .putExtra("truck_color",arrayList.get(i).getTruckColor())
                .putExtra("estimated_time",arrayList.get(i).getEstimatedTime())
                .putExtra("from_date",arrayList.get(i).getFromDate())
                .putExtra("to_date",arrayList.get(i).getToDate())
                .putExtra("truck_owner_id",preferences.getUserId())


                putExtra(",",arrayList.get(i).getReservedParkingSpots())
                    .putExtra("parking_filled_spots",arrayList.get(i).getFilledParkingSpots())
                    .putExtra("parking_reserved_spots",arrayList.get(i).getReservedParkingSpots())
                    */



        Intent intent=getIntent();
        if (intent!=null){
          //  name_icon=getIntent().getStringExtra("")
          titles=getIntent().getStringExtra("parking_owner_name");
           truck_name=getIntent().getStringExtra("truck_owner_name");
          parking_owner_name=getIntent().getStringExtra("parking_owner_name");
          parking_id=getIntent().getStringExtra("parking_id");
          truck_number=getIntent().getStringExtra("truck_number");
          truck_color=getIntent().getStringExtra("truck_color");
          estimate_time=getIntent().getStringExtra("estimate_time");
          from_dates=getIntent().getStringExtra("from_date");
          to_dates=getIntent().getStringExtra("to_date");
          truck_owner_id=getIntent().getStringExtra("truck_owner_id");
          parking_owner_id=getIntent().getStringExtra("parking_owner_id");
          amount=getIntent().getStringExtra("amount");
            truck_id=getIntent().getStringExtra("truck_id");
            truck_owner_name=getIntent().getStringExtra("truck_owner_name");

            parking_remaining_spots=getIntent().getStringExtra("parking_remaining_spots");
            parking_filled_spots=getIntent().getStringExtra("parking_filled_spots");
            parking_reserved_spots=getIntent().getStringExtra("parking_reserved_spots");


            name_icon.setText(titles);
            title.setText(titles);

            this.from_date.setText(from_dates);
            this.to_date.setText(to_dates);

        }

       // name_icon.setText();
        title.setText(parking_owner_name);
        price.setText(amount);

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id=preferences.getCustomerID();
                if(id.equalsIgnoreCase("") || TextUtils.isEmpty(id)){
                    callNonce();
                }else{

                    APICall(id);
                }
            }
        });
    }

    String titles,truck_name,parking_owner_name,parking_id,truck_number,truck_color,estimate_time,from_dates,to_dates,truck_owner_id,parking_owner_id
          ,truck_id ,truck_owner_name ,amount;

    private void callNonce() {
        DropInRequest dropInRequest=new DropInRequest().clientToken(preferences.getBrainTreeToker());
        startActivityForResult(dropInRequest.getIntent(this),1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000){
            if (resultCode== Activity.RESULT_OK){
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String paymentNonce=result.getPaymentMethodNonce().getNonce();
                Log.e("nonce", "onActivityResult: "+ paymentNonce);
                Toast.makeText(BookingClass.this, "Successfully Created", Toast.LENGTH_SHORT).show();

                callCardSavingApi(paymentNonce);



            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(BookingClass.this, "User Cancelled The Request", Toast.LENGTH_SHORT).show();
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Toast.makeText(BookingClass.this,"error "+error,Toast.LENGTH_SHORT).show();
                Log.e("error", "onActivityResult: "+error );
            }
        }
    }

    private void callCardSavingApi(String paymentNonce) {

        estimate_time=time_picker.getText().toString().trim();

        if (TextUtils.isEmpty(truck_id)){
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: truck_id"+truck_id );
        }
        else if (TextUtils.isEmpty(parking_id)){
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: parking_id"+parking_id);
        }
        else if (TextUtils.isEmpty(truck_owner_name)){
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: truck_owner_name"+truck_owner_name);
        }
        else if (TextUtils.isEmpty(parking_owner_name)){
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: parking_owner_name"+parking_owner_name);
        }
        else if (TextUtils.isEmpty(truck_number)){
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: truck_number"+truck_number);
        }
        else if (TextUtils.isEmpty(truck_color)){
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: truck_color"+truck_color);
        }
        else if (TextUtils.isEmpty(estimate_time)){
            time_picker.setError("Please enter Estimate Time");
        }
        else if (TextUtils.isEmpty(from_dates)){
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: from_dates"+from_dates);
        }
        else if (TextUtils.isEmpty(to_dates)){
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: to_dates"+to_dates);
        }

        else if (TextUtils.isEmpty(amount)){
            amount="0";
        }
        else if (TextUtils.isEmpty(truck_owner_name)){
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: truck_owner_name"+truck_owner_name);
        }
        else if (TextUtils.isEmpty(amount)){
            amount="0";
        }
       /* else if (TextUtils.isEmpty(parking_filled_spots)){
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: parking_filled_spot"+parking_filled_spots);
        }
        else if (TextUtils.isEmpty(parking_remaining_spots)){
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: parking_remaining_spot"+parking_remaining_spots);
        }*/
        else if (TextUtils.isEmpty(parking_reserved_spots)){
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: parking_reserved_spots"+parking_reserved_spots);
        }else {

            dialog.show();
            APIService service = APIClient.getClient().create(APIService.class);
            Call<BookingModel> modelCall = service.booking(truck_id, parking_id, truck_owner_name, parking_owner_name,
                    truck_number, truck_color, estimate_time, from_dates, to_dates, paymentNonce,
                    amount, "", truck_owner_id, amount
                    , parking_reserved_spots);
            modelCall.enqueue(new Callback<BookingModel>() {
                @Override
                public void onResponse(Call<BookingModel> call, Response<BookingModel> response) {
                    BookingModel model = response.body();
                    dialog.dismiss();
                    if (model.getStatus().equalsIgnoreCase("success")) {
                        Toast.makeText(BookingClass.this, model.getPayment(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finishAffinity();
                    } else {
                        Toast.makeText(BookingClass.this, model.getError(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BookingModel> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(BookingClass.this, "Network error", Toast.LENGTH_SHORT).show();
                    Log.e("error", "onFailure: " + t.getMessage());
                }
            });
        }
    }


    private void APICall(String id) {
        if (TextUtils.isEmpty(truck_id)) {
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: truck_id" + truck_id);
        } else if (TextUtils.isEmpty(parking_id)) {
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: parking_id" + parking_id);
        } else if (TextUtils.isEmpty(truck_owner_name)) {
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: truck_owner_name" + truck_owner_name);
        } else if (TextUtils.isEmpty(parking_owner_name)) {
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: parking_owner_name" + parking_owner_name);
        } else if (TextUtils.isEmpty(truck_number)) {
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: truck_number" + truck_number);
        } else if (TextUtils.isEmpty(truck_color)) {
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: truck_color" + truck_color);
        } else if (TextUtils.isEmpty(estimate_time)) {
           // time_picker.setError("Please enter Estimate Time");
        } else if (TextUtils.isEmpty(from_dates)) {
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: from_dates" + from_dates);
        } else if (TextUtils.isEmpty(to_dates)) {
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: to_dates" + to_dates);
        } else if (TextUtils.isEmpty(amount)) {
            amount = "0";
        } else if (TextUtils.isEmpty(truck_owner_name)) {
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: truck_owner_name" + truck_owner_name);
        } else if (TextUtils.isEmpty(amount)) {
            amount = "0";
        } else if (TextUtils.isEmpty(parking_filled_spots)) {
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: parking_filled_spot" + parking_filled_spots);
        } else if (TextUtils.isEmpty(parking_remaining_spots)) {
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: parking_remaining_spot" + parking_remaining_spots);
        } else if (TextUtils.isEmpty(parking_reserved_spots)) {
            Toast.makeText(this, "Data is missing please contact admin", Toast.LENGTH_SHORT).show();
            Log.e("error", "callCardSavingApi: parking_reserved_spots" + parking_reserved_spots);
        } else {

            dialog.show();
            APIService service = APIClient.getClient().create(APIService.class);
            Call<BookingModel> modelCall = service.booking(truck_id, parking_id, truck_owner_name, parking_owner_name,
                    truck_number, truck_color, estimate_time, from_dates,
                    to_dates, "", amount,
                    id, truck_owner_id, amount,  parking_reserved_spots);
            modelCall.enqueue(new Callback<BookingModel>() {
                @Override
                public void onResponse(Call<BookingModel> call, Response<BookingModel> response) {
                    BookingModel model = response.body();
                    dialog.dismiss();
                    if (model.getStatus().equalsIgnoreCase("success")) {
                        Toast.makeText(BookingClass.this, model.getPayment(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finishAffinity();
                    } else {
                        Toast.makeText(BookingClass.this, model.getError(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BookingModel> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(BookingClass.this, "Network error", Toast.LENGTH_SHORT).show();
                    Log.e("error", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }
}
