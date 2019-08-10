package com.example.parkingapp.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.AddReservationModel;
import com.example.parkingapp.Models.ReservationModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.Preferences.ReservationPreferences;
import com.example.parkingapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReserveParking extends AppCompatActivity {
    Toolbar toolbar;
    TextView title,from_date,time_from,to_date,time_to,text_days,time_picker;
    Button reserve;
    ReservationPreferences reservationPreferences;
    CardView main_card;
    LinearLayout calender;
    CalendarView calendarView;
    String time_from_1,time_to_1;
    TextView price;
    EditText Spots;
    ConstraintLayout abc;
    Preferences preferences;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_parking);
        preferences=new Preferences(this);
        main_card=findViewById(R.id.main_card);
        calender=findViewById(R.id.calender);
        calender.setVisibility(View.GONE);
        calendarView = findViewById(R.id.calendarView);


        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Reserving");
        progressDialog.setMessage("Please wait for a minute");
        progressDialog.setCancelable(false);

        abc=findViewById(R.id.abc);
     /*   if (preferences.getSwitchNightMod()){
            abc.setBackgroundColor(getResources().getColor(R.color.black));
        }else {
            abc.setBackgroundColor(getResources().getColor(R.color.white));
        }*/



        reservationPreferences=new ReservationPreferences(this);


        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("RESERVE PARKING");


        title=findViewById(R.id.title);
        from_date=findViewById(R.id.from_date);
        time_from=findViewById(R.id.time_from);
        to_date=findViewById(R.id.to_date);
        time_to=findViewById(R.id.time_to);
        text_days=findViewById(R.id.text_days);
        time_picker=findViewById(R.id.time_picker);
        reserve=findViewById(R.id.reserve);
        price=findViewById(R.id.price);
        Spots=findViewById(R.id.Spots);

        if (preferences.getSwitchNightMod()){
            abc.setBackgroundColor(getResources().getColor(R.color.black));
          /*  title.setTextColor(getResources().getColor(R.color.white));
            from_date.setTextColor(getResources().getColor(R.color.white));
            to_date.setTextColor(getResources().getColor(R.color.white));
            time_from.setTextColor(getResources().getColor(R.color.white));
            time_to.setTextColor(getResources().getColor(R.color.white));
            text_days.setTextColor(getResources().getColor(R.color.white));
            time_picker.setTextColor(getResources().getColor(R.color.white));
            price.setTextColor(getResources().getColor(R.color.white));
            Spots.setTextColor(getResources().getColor(R.color.white));*/
          calender.setBackgroundColor(getResources().getColor(R.color.white));
        }else {
            abc.setBackgroundColor(getResources().getColor(R.color.white));
          /*  title.setTextColor(getResources().getColor(R.color.black));
            from_date.setTextColor(getResources().getColor(R.color.black));
            to_date.setTextColor(getResources().getColor(R.color.black));
            time_from.setTextColor(getResources().getColor(R.color.black));
            time_to.setTextColor(getResources().getColor(R.color.black));
            text_days.setTextColor(getResources().getColor(R.color.black));
            time_picker.setTextColor(getResources().getColor(R.color.black));
            price.setTextColor(getResources().getColor(R.color.black));
            Spots.setTextColor(getResources().getColor(R.color.black));*/
            calender.setBackgroundColor(getResources().getColor(R.color.white));
        }



        title.setText(reservationPreferences.getParkingName());
        price.setText(reservationPreferences.getParkingPrice()+" $");
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    calculate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_card.setVisibility(View.GONE);
                calender.setVisibility(View.VISIBLE);
                callFromDate();
            }
        });

        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callToDate();
                main_card.setVisibility(View.GONE);
                calender.setVisibility(View.VISIBLE);
            }
        });

        time_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.pop_time_picker, null);
                final AlertDialog.Builder alert = new AlertDialog.Builder(ReserveParking.this);

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



        time_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.pop_time_picker, null);
                final AlertDialog.Builder alert = new AlertDialog.Builder(ReserveParking.this);

                Button btn_save_time=alertLayout.findViewById(R.id.btn_save_time);
                final TimePicker time_picker=alertLayout.findViewById(R.id.time_picker);

                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(true);
                final AlertDialog dialog = alert.create();
                dialog.show();



                time_picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        time_from_1=convertDate(hourOfDay)+":"+convertDate(minute);
                    }
                });

                btn_save_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(time_from_1)) {
                            time_from_1=convertDate(time_picker.getHour())+":"+convertDate(time_picker.getMinute());
                            time_from.setText(time_from_1);
                            dialog.dismiss();
                        } else {
                            time_from.setText(time_from_1);
                            dialog.dismiss();
                        }}
                });
            }
        });




        time_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.pop_time_picker, null);
                final AlertDialog.Builder alert = new AlertDialog.Builder(ReserveParking.this);

                Button btn_save_time=alertLayout.findViewById(R.id.btn_save_time);
                final TimePicker time_picker=alertLayout.findViewById(R.id.time_picker);

                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(true);
                final AlertDialog dialog = alert.create();
                dialog.show();



                time_picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        time_to_1=convertDate(hourOfDay)+":"+convertDate(minute);
                    }
                });

                btn_save_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(time_to_1)) {
                            time_to_1=convertDate(time_picker.getHour())+":"+convertDate(time_picker.getMinute());
                            time_to.setText(time_to_1);
                            dialog.dismiss();
                        } else {
                            time_to.setText(time_to_1);
                            dialog.dismiss();
                        }}
                });
            }
        });
    }
    int remaining_parking_value,filled_parking_value;
    private void callReserveApi() {

        String estimate_time=time_picker.getText().toString().trim();
        String from_date_final=from_date.getText().toString()+time_from.getText().toString();
        String to_date_final=to_date.getText().toString()+time_to.getText().toString();
        String Spotss=Spots.getText().toString();

        String remaining_parking=reservationPreferences.getRemainingParkingSpots();
        String filled_spots=reservationPreferences.getFilled_parking_spots();


        if (TextUtils.isEmpty(estimate_time)){
            time_picker.setError("Please enter estimate time");
        }else if (TextUtils.isEmpty(from_date_final)){
            from_date.setError("please enter date and time");
        }else if (TextUtils.isEmpty(Spotss)){
            Spots.setError("please enter Spots");
        }else if (TextUtils.isEmpty(to_date_final)){{
                to_date.setError("Please enter date and time");
        }}
        else if (preferences.getTruckID().equalsIgnoreCase("")){
            Toast.makeText(this, "Please Add truck from profile first", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(remaining_parking)){
            Toast.makeText(this, "Remaining spots are empty", Toast.LENGTH_SHORT).show();
        }else if (filled_spots.equalsIgnoreCase(remaining_parking)){
            Toast.makeText(this, "All parking Spots are filled Can't Reserve", Toast.LENGTH_LONG).show();
        }
        else {
            if (filled_spots == null || filled_spots == "") {
                filled_spots = "0";
            }

            int rem_park = Integer.parseInt(remaining_parking);
            int filled = Integer.parseInt(filled_spots);
            int spot = Integer.parseInt(Spotss);

            remaining_parking_value = rem_park - spot;

            filled_parking_value = filled + spot;

            APIService service = APIClient.getClient().create(APIService.class);
            String truck_id = preferences.getTruckID();
            String parking_id = reservationPreferences.getParkingID();
            String name = preferences.getName();
            String parking_owner = reservationPreferences.getParkingOwner();
            String truckNum = preferences.getTruckNum();
            String truckColor = preferences.getTruckColor();
            String parkingprice = reservationPreferences.getParkingPrice();
            String userid = preferences.getUserId();
            String parking_owner_id = reservationPreferences.getParkingOwnerId();
            String estimate = estimate_time;
            String from_date = from_date_final;
            String to_date_finals = to_date_final;
            String remaining = String.valueOf(remaining_parking_value);
            String fill = String.valueOf(filled_parking_value);
            String spotaa = Spotss;

            if (TextUtils.isEmpty(truck_id)) {
                Toast.makeText(this, "Parking detail missing please contact admin", Toast.LENGTH_SHORT).show();
                Log.e("error", "callReserveApi: truck_id"+ truck_id );
            } else if (TextUtils.isEmpty(parking_id)) {
                Toast.makeText(this, "Parking details missing please contact admin", Toast.LENGTH_SHORT).show();
                Log.e("error", "callReserveApi: parking_id"+ parking_id );
            } else if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, "Parking details missing please contact admin", Toast.LENGTH_SHORT).show();
                Log.e("error", "callReserveApi:name "+ name );
            } else if (TextUtils.isEmpty(parking_owner)) {
                Toast.makeText(this, "Parking details missing please contact admin", Toast.LENGTH_SHORT).show();
                Log.e("error", "callReserveApi:parking_owner "+ parking_owner );
            } else if (TextUtils.isEmpty(truckNum)) {
                Toast.makeText(this, "Parking details missing please contact admin", Toast.LENGTH_SHORT).show();
                Log.e("error", "callReserveApi:truckNum "+ truckNum );
            } else if (TextUtils.isEmpty(truckColor)) {
                Toast.makeText(this, "Parking details missing please contact admin", Toast.LENGTH_SHORT).show();
                Log.e("error", "callReserveApi: truckColor"+ truckColor );
            } else if (TextUtils.isEmpty(parkingprice)) {
                Toast.makeText(this, "Parking details missing please contact admin", Toast.LENGTH_SHORT).show();
                Log.e("error", "callReserveApi: parkingprice"+ parkingprice );
            } else if (TextUtils.isEmpty(userid)) {
                Toast.makeText(this, "Parking details missing please contact admin", Toast.LENGTH_SHORT).show();
                Log.e("error", "callReserveApi: userid"+ userid );
            } else if (TextUtils.isEmpty(parking_owner_id)) {
                Toast.makeText(this, "Parking details missing please contact admin", Toast.LENGTH_SHORT).show();
                Log.e("error", "callReserveApi: parking_owner_id"+ parking_owner_id );
            } else if (TextUtils.isEmpty(estimate)) {
                Toast.makeText(this, "Parking details missing please contact admin", Toast.LENGTH_SHORT).show();
                Log.e("error", "callReserveApi:estimate "+ estimate );
            } else if (TextUtils.isEmpty(from_date)) {
                Toast.makeText(this, "Parking details missing please contact admin", Toast.LENGTH_SHORT).show();
                Log.e("error", "callReserveApi: from_date"+ from_date );
            } else if (TextUtils.isEmpty(to_date_finals)) {
                Toast.makeText(this, "Parking details missing please contact admin", Toast.LENGTH_SHORT).show();
                Log.e("error", "callReserveApi: to_date_finals"+ to_date_finals );
            } else if (TextUtils.isEmpty(remaining)) {
                Toast.makeText(this, "Parking details missing please contact admin", Toast.LENGTH_SHORT).show();
                Log.e("error", "callReserveApi: remaining"+ remaining );
            } else if (TextUtils.isEmpty(fill)) {
                Toast.makeText(this, "Parking details missing please contact admin", Toast.LENGTH_SHORT).show();
                Log.e("error", "callReserveApi: fill"+ fill );
            } else if (TextUtils.isEmpty(spotaa)) {
                Toast.makeText(this, "Parking details missing please contact admin", Toast.LENGTH_SHORT).show();
                Log.e("error", "callReserveApi: spotaa"+ spotaa );
            } else {
                progressDialog.show();

                Call<AddReservationModel> call = service.addReservation(truck_id,parking_id,name,parking_owner,truckNum,truckColor,
                        estimate_time,from_date,to_date_final,parkingprice,userid,parking_owner_id,remaining,fill,spotaa);

                call.enqueue(new Callback<AddReservationModel>() {
                    @Override
                    public void onResponse(Call<AddReservationModel> call, Response<AddReservationModel> response) {
                        AddReservationModel model = response.body();
                        progressDialog.dismiss();
                        if (model.getStatus().equalsIgnoreCase("success")) {
                            Toast.makeText(ReserveParking.this, "Successfully Reserved", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finishAffinity();

                        } else {
                            Toast.makeText(ReserveParking.this, model.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddReservationModel> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(ReserveParking.this, "Network Error", Toast.LENGTH_SHORT).show();
                        Log.e("error", "onFailure: " + t);
                    }
                });

            }

        }

    }



    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }

 String date_start,date_end;

    private void callToDate() {

        days2();
    }

    private void callFromDate() {

        days();
}

    public void calculate() throws ParseException {
      /*  Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();*/

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
        //    Date date = sdf.parse(date_start);
            to_date.setText(date_end);
            from_date.setText(date_start);

            Date date1=sdf.parse(date_start);
            Date date2=sdf.parse(date_end);

            text_days.setText(daysBetween(date1, date2) + "days");

            callReserveApi();

            // System.out.println("Days= "+daysBetween(cal1.getTime(),cal2.getTime()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }


    public void days(){

        final long date= calendarView.getDate();
        calendarView.setMinDate(date);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {


                date_start = dayOfMonth + "-" + month + "-"+ year;
               // popUpMethodology(date);
                main_card.setVisibility(View.VISIBLE);
                calender.setVisibility(View.GONE);
                from_date.setText(date_start);


            }
        });
    }

    public void days2(){

        final long date= calendarView.getDate();
        calendarView.setMinDate(date);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {


                date_end = dayOfMonth + "-" + month + "-"+ year;
               // popUpMethodology(date);
                main_card.setVisibility(View.VISIBLE);
                calender.setVisibility(View.GONE);
                to_date.setText(date_end);
                /*try {
                    calculate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
*/
            }
        });
    }
}
