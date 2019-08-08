package com.example.parkingapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.Activities.BookingClass;
import com.example.parkingapp.Activities.HomeActivity;
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
    public void onBindViewHolder(@NonNull Holder holder, final int i) {

        String fullname=preferences.getName();

try {


   /* StringBuilder initials = new StringBuilder();
    for (String s : fullname.split("")) {
        initials.append(s.charAt(0));
    }*/
  //  holder.image_name.setText(initials.toString());

    holder.name_user.setText(preferences.getName());
    holder.time_from.setText(arrayList.get(i).getFromDate());
    holder.time_to.setText(arrayList.get(i).getToDate());


    final String reservedParkingId = arrayList.get(i).getReservedParkingId();
    final String parkingID = arrayList.get(i).getParkingId();
    holder.main_view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context, BookingClass.class)
                    .putExtra("truck_id", arrayList.get(i).getTruckId())
                    .putExtra("parking_id", arrayList.get(i).getParkingId())
                    .putExtra("truck_owner_name", arrayList.get(i).getTruckOwnerName())
                    .putExtra("parking_owner_name", arrayList.get(i).getParkingOwnerName())
                    .putExtra("truck_number", arrayList.get(i).getTruckNumber())
                    .putExtra("truck_color", arrayList.get(i).getTruckColor())
                    .putExtra("estimated_time", arrayList.get(i).getEstimatedTime())
                    .putExtra("from_date", arrayList.get(i).getFromDate())
                    .putExtra("to_date", arrayList.get(i).getToDate())
                    .putExtra("truck_owner_id", arrayList.get(i).getTruckOwnerId())
                    .putExtra("amount", arrayList.get(i).getAmount())
                    .putExtra("parking_owner_id", arrayList.get(i).getParkingOwnerId())
                    .putExtra("parking_remaining_spots",arrayList.get(i).getReservedParkingSpots())
                    .putExtra("parking_filled_spots",arrayList.get(i).getFilledParkingSpots())
                    .putExtra("parking_reserved_spots",arrayList.get(i).getReservedParkingSpots())
            );
        }
    });


    holder.cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            callCancelReservationAPI(reservedParkingId, parkingID,arrayList.get(i).getReservedParkingSpots(),arrayList.get(i).getFilledParkingSpots(),arrayList.get(i).getRemainingParkingSpots());
        }
    });
}catch (Exception e){
    e.printStackTrace();
}
    }

    private void callCancelReservationAPI(final String reservedParkingId, final String parkingID,
                                          final String reservedParkingSpots, final String filledParkingSpots,
                                          final String remainingParkingSpots) {

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
                CallAPI(reservedParkingId,parkingID,reservedParkingSpots,filledParkingSpots,remainingParkingSpots);
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

    private void CallAPI(String reservedParkingId, String parkingID,
                         String reservedParkingSpots, String filledParkingSpots,
                         String remainingParkingSpots) {

        int reserved_parking= Integer.parseInt(reservedParkingSpots);
        int remaining_parking= Integer.parseInt(remainingParkingSpots);
        int filled_parking=Integer.parseInt(filledParkingSpots);

        remaining_parking=remaining_parking+1;
        filled_parking=filled_parking-1;


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
        LinearLayout main_view,second_View;

        public Holder(@NonNull View itemView) {
            super(itemView);
            image_name=itemView.findViewById(R.id.image_name);
            name_user=itemView.findViewById(R.id.name_user);
            time_from=itemView.findViewById(R.id.time_from);
            time_to=itemView.findViewById(R.id.time_to);
            cancel=itemView.findViewById(R.id.cancel);
            main_view=itemView.findViewById(R.id.main_view);
            second_View=itemView.findViewById(R.id.second_View);
        }
    }
}
