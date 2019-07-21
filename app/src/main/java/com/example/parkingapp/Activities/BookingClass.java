package com.example.parkingapp.Activities;

import android.app.Activity;
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
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_class);

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
          String  titles=getIntent().getStringExtra("parking_owner_name");
          String truck_name=getIntent().getStringExtra("truck_owner_name");
          String parking_owner_name=getIntent().getStringExtra("parking_owner_name");
          String parking_id=getIntent().getStringExtra("parking_id");
          String truck_number=getIntent().getStringExtra("truck_number");
          String truck_color=getIntent().getStringExtra("truck_color");
          String estimate_time=getIntent().getStringExtra("estimate_time");
          String from_date=getIntent().getStringExtra("from_date");
          String to_date=getIntent().getStringExtra("to_date");
          String truck_owner_id=getIntent().getStringExtra("truck_owner_id");


            name_icon.setText(titles);
            title.setText(titles);

            this.from_date.setText(from_date);
            this.to_date.setText(to_date);



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
        Toast.makeText(this, "API pending", Toast.LENGTH_SHORT).show();
    }


    private void APICall(String id) {
        /*APIService service= APIClient.getClient().create(APIService.class);
        Call<BookingModel> modelCall=service.booking(truck_id,parking_id,truck_owner_name,parking_owner_name,
                                                        truck_number,truck_color,estimate_time,from_dates,
                                                        to_dates,paymonth_nonce,amount,
                                                        customer_id,truck_owner,total_price);
        modelCall.enqueue(new Callback<BookingModel>() {
            @Override
            public void onResponse(Call<BookingModel> call, Response<BookingModel> response) {
                BookingModel model=response.body();
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
                Toast.makeText(BookingClass.this, "Network error", Toast.LENGTH_SHORT).show();
                Log.e("error", "onFailure: "+t.getMessage() );
            }
        });*/
    }
}
