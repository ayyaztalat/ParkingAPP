package com.example.parkingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.Activities.Reservation;
import com.example.parkingapp.Models.ReservationModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;

import java.util.ArrayList;

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
        for (String s : fullname.split(" ")) {
            initials.append(s.charAt(0));
        }
        holder.image_name.setText(initials.toString());

        holder.name_user.setText(preferences.getName());
        holder.time_from.setText(arrayList.get(i).getFromDate());
        holder.time_to.setText(arrayList.get(i).getToDate());

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Not yet active", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView image_name,name_user,time_from,time_to,cancel;

        public Holder(@NonNull View itemView) {
            super(itemView);
            image_name=itemView.findViewById(R.id.image_name);
            name_user=itemView.findViewById(R.id.name_user);
            time_from=itemView.findViewById(R.id.time_from);
            time_to=itemView.findViewById(R.id.time_to);
            cancel=itemView.findViewById(R.id.cancel);
        }
    }
}
