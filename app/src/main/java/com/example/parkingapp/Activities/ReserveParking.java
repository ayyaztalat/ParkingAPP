package com.example.parkingapp.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
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

    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_parking);

        reservationPreferences=new ReservationPreferences(this);
        preferences=new Preferences(this);

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

        main_card=findViewById(R.id.main_card);
        calender=findViewById(R.id.calender);

         calendarView = findViewById(R.id.calendarView);

        title.setText(reservationPreferences.getParkingName());

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callReserveApi();
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

    private void callReserveApi() {

        String estimate_time=time_picker.getText().toString().trim();
        String from_date_final=from_date.getText().toString()+time_from.getText().toString();
        String to_date_final=to_date.getText().toString()+time_to.getText().toString();

        if (TextUtils.isEmpty(estimate_time)){
            time_picker.setError("Please enter estimate time");
        }else if (TextUtils.isEmpty(from_date_final)){
            from_date.setError("please enter date and time");
        }else if (TextUtils.isEmpty(to_date_final)){{
                to_date.setError("Please enter date and time");
        }}else{

            APIService service= APIClient.getClient().create(APIService.class);
            Call<AddReservationModel> call=service.addReservation(preferences.getTruckID(),reservationPreferences.getParkingID(),preferences.getName(),reservationPreferences.getParkingOwner()
                    ,preferences.getTruckNum(),preferences.getTruckColor(),estimate_time,from_date_final,to_date_final,
                    reservationPreferences.getParkingPrice(),preferences.getUserId(),reservationPreferences.getParkingOwnerId());

            call.enqueue(new Callback<AddReservationModel>() {
                @Override
                public void onResponse(Call<AddReservationModel> call, Response<AddReservationModel> response) {
                    AddReservationModel model=response.body();
                    if (model.getStatus().equalsIgnoreCase("success")){
                        Toast.makeText(ReserveParking.this, "Successfully Reserved", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            finishAffinity();

                    }else {
                        Toast.makeText(ReserveParking.this, model.getError(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddReservationModel> call, Throwable t) {
                    Toast.makeText(ReserveParking.this, "Network Error", Toast.LENGTH_SHORT).show();
                    Log.e("error", "onFailure: "+t );
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

 String date_start,date_end;

    private void callToDate() {

        days();
    }

    private void callFromDate() {

        days2();
}

    public void calculate() throws ParseException {
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

        Date date = sdf.parse(date_start);
        cal1.setTime(date);
        date = sdf.parse(date_end);
        cal2.setTime(date);

        text_days.setText(daysBetween(cal1.getTime(),cal2.getTime())+"days");
       // System.out.println("Days= "+daysBetween(cal1.getTime(),cal2.getTime()));
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


                date_start = dayOfMonth + "/" + month + "/"+ year;
               // popUpMethodology(date);
                main_card.setVisibility(View.VISIBLE);
                calender.setVisibility(View.GONE);


            }
        });
    }

    public void days2(){

        final long date= calendarView.getDate();
        calendarView.setMinDate(date);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {


                date_end = dayOfMonth + "/" + month + "/"+ year;
               // popUpMethodology(date);
                main_card.setVisibility(View.VISIBLE);
                calender.setVisibility(View.GONE);

                try {
                    calculate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
