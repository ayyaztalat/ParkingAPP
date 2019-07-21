package com.example.parkingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.parkingapp.Models.ImageParkingModel;
import com.example.parkingapp.R;

import java.util.ArrayList;

import static com.example.parkingapp.Intefaces.APIClient.BASE_IMG;
import static com.example.parkingapp.Intefaces.APIClient.BASE_URL;

public class ImageParkingAdapter extends RecyclerView.Adapter<ImageParkingAdapter.Holder> {
    Context context;
    ArrayList<ImageParkingModel> arrayList;

    public ImageParkingAdapter(Context context, ArrayList<ImageParkingModel> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_recycler_parking_item,viewGroup,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        Glide.with(context).load(BASE_IMG+arrayList.get(i).getPicture()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_item);
        }
    }
}
