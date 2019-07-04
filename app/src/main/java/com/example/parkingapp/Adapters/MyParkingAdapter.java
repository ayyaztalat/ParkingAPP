package com.example.parkingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.Activities.EditParkingClass;
import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.DeleteParkingModel;
import com.example.parkingapp.Models.ParkingModel;
import com.example.parkingapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        String fullname=modelArrayList.get(i).getParkingOwnerName();


        StringBuilder initials = new StringBuilder();
        for (String s : fullname.split(" ")) {
            initials.append(s.charAt(0));
        }
        holder.image_name.setText(initials.toString());

        holder.location.setText(modelArrayList.get(i).getParkingName());
        holder.location.setText(modelArrayList.get(i).getParkingLatitude());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, EditParkingClass.class)
                        .putExtra("parking_id",modelArrayList.get(i).getParkingId())
                        .putExtra("parking_price",modelArrayList.get(i).getParkingPrice())
                        .putExtra("parking_vehical",modelArrayList.get(i).getTypeOfVehicle())
                        .putExtra("parking_time",modelArrayList.get(i).getParkingTime())
                        .putExtra("owner_number",modelArrayList.get(i).getParkingOwnerNumber())
                        .putExtra("parking_description",modelArrayList.get(i).getParkingDescription())
                        .putExtra("parking_owner_name",modelArrayList.get(i).getParkingOwnerName()));
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDeleteImageAPI(modelArrayList.get(i).getParkingId(),i);
            }
        });
    }

    private void callDeleteImageAPI(String parkingId, final int parkingModel) {
        APIService service= APIClient.getClient().create(APIService.class);
        Call<DeleteParkingModel> modelCall=service.Delete(parkingId);
        modelCall.enqueue(new Callback<DeleteParkingModel>() {
            @Override
            public void onResponse(Call<DeleteParkingModel> call, Response<DeleteParkingModel> response) {
                DeleteParkingModel model=response.body();
                if (model.getStatus().equalsIgnoreCase("success")){
                    Toast.makeText(context, model.getParkingData(), Toast.LENGTH_SHORT).show();
                    modelArrayList.remove(parkingModel);
                    notifyDataSetChanged();

                }else{
                    Toast.makeText(context, model.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteParkingModel> call, Throwable t) {
                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();

            }
        });

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
