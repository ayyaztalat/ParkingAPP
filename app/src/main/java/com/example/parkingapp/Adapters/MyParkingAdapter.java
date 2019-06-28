package com.example.parkingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parkingapp.Models.ParkingModel;
import com.example.parkingapp.R;

import java.util.ArrayList;

public class MyParkingAdapter extends RecyclerView.Adapter<MyParkingAdapter.Holder> {
    Context context;
    ArrayList<ParkingModel> modelArrayList;

    public MyParkingAdapter(Context context, ArrayList<ParkingModel> modelArrayList) {
        this.context=context;
        this.modelArrayList=modelArrayList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.my_parking_item_view,viewGroup,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        String fullname=modelArrayList.get(i).getParkingOwnerName();


        StringBuilder initials = new StringBuilder();
        for (String s : fullname.split(" ")) {
            initials.append(s.charAt(0));
        }
        holder.image_name.setText(initials.toString());

        holder.location.setText(modelArrayList.get(i).getParkingName());
        holder.location.setText(modelArrayList.get(i).getParkingLatitude());
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView image_name,name_user,location;
        ImageView edit,delete;
        public Holder(@NonNull View itemView) {
            super(itemView);
            image_name=itemView.findViewById(R.id.image_name);
            name_user=itemView.findViewById(R.id.name_user);
            location=itemView.findViewById(R.id.location);
            edit=itemView.findViewById(R.id.edit);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
