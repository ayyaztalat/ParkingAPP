package com.example.parkingapp.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
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
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_class);

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

        calendarView.setVisibility(View.GONE);

        preferences=new Preferences(this);

        /*.putExtra("truck_id",arrayList.get(i).getTruckId())
                .putExtra("parking_id",arrayList.get(i).getParkingId())
                .putExtra("truck_owner_name",arrayList.get(i).getTruckOwnerName())
                .putExtra("parking_owner_name",arrayList.get(i).getParkingOwnerName())
                .putExtra("truck_number",arrayList.get(i).getTruckNumber())
                .putExtra("truck_color",arrayList.get(i).getTruckColor())
                .putExtra("estimated_time",arrayList.get(i).getEstimatedTime())
                .putExtra("from_date",arrayList.get(i).getFromDate())
                .putExtra("to_date",arrayList.get(i).getToDate())
                .putExtra("truck_owner_id",preferences.getUserId())*/

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


            name_icon.setText(titles);
            title.setText(titles);

            this.from_date.setText(from_dates);
            this.to_date.setText(to_dates);

        }
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
        dialog.show();
        APIService service=APIClient.getClient().create(APIService.class);
        Call<BookingModel> modelCall=service.booking(truck_id,parking_id,truck_owner_name,parking_owner_name,truck_number,truck_color,estimate_time,from_dates,to_dates,paymentNonce,amount,"",truck_owner_id,amount);
        modelCall.enqueue(new Callback<BookingModel>() {
            @Override
            public void onResponse(Call<BookingModel> call, Response<BookingModel> response) {
                BookingModel model=response.body();
                dialog.dismiss();
                if (model.getStatus().equalsIgnoreCase("success")){
                    Toast.makeText(BookingClass.this, model.getPayment(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    finishAffinity();
                }else{
                    Toast.makeText(BookingClass.this, model.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookingModel> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(BookingClass.this, "Network error", Toast.LENGTH_SHORT).show();
                Log.e("error", "onFailure: "+t.getMessage() );
            }
        });

    }


    private void APICall(String id) {
        dialog.show();
        APIService service= APIClient.getClient().create(APIService.class);
        Call<BookingModel> modelCall=service.booking(truck_id,parking_id,truck_owner_name,parking_owner_name,
                                                        truck_number,truck_color,estimate_time,from_dates,
                                                        to_dates,"",amount,
                                                        id,truck_owner_id,amount);
        modelCall.enqueue(new Callback<BookingModel>() {
            @Override
            public void onResponse(Call<BookingModel> call, Response<BookingModel> response) {
                BookingModel model=response.body();
                dialog.dismiss();
                if (model.getStatus().equalsIgnoreCase("success")){
                    Toast.makeText(BookingClass.this, model.getPayment(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    finishAffinity();
                }else{
                    Toast.makeText(BookingClass.this, model.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookingModel> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(BookingClass.this, "Network error", Toast.LENGTH_SHORT).show();
                Log.e("error", "onFailure: "+t.getMessage() );
            }
        });
    }
}
