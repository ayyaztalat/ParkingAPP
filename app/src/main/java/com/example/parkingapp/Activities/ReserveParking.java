package com.example.parkingapp.Activities;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.parkingapp.Preferences.ReservationPreferences;
import com.example.parkingapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ReserveParking extends AppCompatActivity {
    Toolbar toolbar;
    TextView title,from_date,time_from,to_date,time_to,text_days,time_picker;
    Button reserve;
    ReservationPreferences reservationPreferences;
    CardView main_card;
    LinearLayout calender;
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_parking);

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

        main_card=findViewById(R.id.main_card);
        calender=findViewById(R.id.calender);

         calendarView = findViewById(R.id.calendarView);

        title.setText(reservationPreferences.getParkingName());

        from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFromDate();
            }
        });

        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callToDate();
            }
        });

    }

    int years_start,month_start,day_of_month;
    int years_end,month_end,day_of_end;

    private void callToDate() {

        days();
    }

    private void callFromDate() {


    }

    public void calculate() throws ParseException {
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

        Date date = sdf.parse("your first date");
        cal1.setTime(date);
        date = sdf.parse("your second date");
        cal2.setTime(date);

        System.out.println("Days= "+daysBetween(cal1.getTime(),cal2.getTime()));
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

               dat
                String date = dayOfMonth + "/" + month + "/"+ year;
               // popUpMethodology(date);



            }
        });
    }
}
