package com.example.parkingapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.parkingapp.Activities.BookingClass;
import com.example.parkingapp.Activities.HomeActivity;
import com.example.parkingapp.Activities.Reservation;
import com.example.parkingapp.Activities.ReserveParking;
import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.CancelReservationModel;
import com.example.parkingapp.Models.ReservationModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReservationAdapter extends RecyclerView.Adapter<MyReservationAdapter.Holder> {
    Context context;
    Preferences preferences;
    ArrayList<ReservationModel> arrayList;
    public MyReservationAdapter(Context context, ArrayList<ReservationModel> arrayList){
        this.arrayList=arrayList;
        this.context=context;
        preferences=new Preferences(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reservation_item_view,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {

        String fullname=preferences.getName();


        StringBuilder initials = new StringBuilder();
        for (String s : fullname.split("")) {
            initials.append(s.charAt(0));
        }
        holder.image_name.setText(initials.toString());

        holder.name_user.setText(preferences.getName());
        holder.time_from.setText(arrayList.get(i).getFromDate());
        holder.time_to.setText(arrayList.get(i).getToDate());
        holder.main_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, BookingClass.class));
            }
        });

        final String reservedParkingId=arrayList.get(i).getReservedParkingId();
        final String parkingID=arrayList.get(i).getParkingId();

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCancelReservationAPI(reservedParkingId,parkingID);
            }
        });
    }

    private void callCancelReservationAPI(final String reservedParkingId, final String parkingID) {

        LayoutInflater inflater = ((HomeActivity)context).getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.pop_cancel_dialogue, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);

        Button confirm=alertLayout.findViewById(R.id.confirm);
        Button  cancel=alertLayout.findViewById(R.id.cancel);

        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(true);
        final AlertDialog dialog = alert.create();
        dialog.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallAPI(reservedParkingId,parkingID);
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });




    }

    private void CallAPI(String reservedParkingId, String parkingID) {
        APIService service= APIClient.getClient().create(APIService.class);
        Call<CancelReservationModel> modelCall=service.cancelReservation(reservedParkingId,parkingID);
        modelCall.enqueue(new Callback<CancelReservationModel>() {
            @Override
            public void onResponse(Call<CancelReservationModel> call, Response<CancelReservationModel> response) {
                CancelReservationModel model=response.body();
                if (model.getStatus().equalsIgnoreCase("success")){
                    Toast.makeText(context, "Reservation Cancelled", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(context, model.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CancelReservationModel> call, Throwable t) {
                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
                Log.e("error", "onFailure: "+t );
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView image_name,name_user,time_from,time_to,cancel;
        LinearLayout main_view;

        public Holder(@NonNull View itemView) {
            super(itemView);
            image_name=itemView.findViewById(R.id.image_name);
            name_user=itemView.findViewById(R.id.name_user);
            time_from=itemView.findViewById(R.id.time_from);
            time_to=itemView.findViewById(R.id.time_to);
            cancel=itemView.findViewById(R.id.cancel);
            main_view=itemView.findViewById(R.id.main_view);
        }
    }
}
