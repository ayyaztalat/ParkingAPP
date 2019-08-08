package com.example.parkingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parkingapp.Models.HistoryModel;
import com.example.parkingapp.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    Context context;
    ArrayList<HistoryModel> arrayList;

    public HistoryAdapter(Context context, ArrayList<HistoryModel> arrayList) {
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.history_item_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.availablity.setText(arrayList.get(i).getParkingOwnerName());
        viewHolder.date.setText(arrayList.get(i).getTimeStamp());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        TextView availablity,date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            availablity=itemView.findViewById(R.id.availablity);
            date=itemView.findViewById(R.id.date);

        }
    }
}
