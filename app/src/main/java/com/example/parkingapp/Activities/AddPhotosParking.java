package com.example.parkingapp.Activities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parkingapp.Adapters.ImageParkingAdapter;
import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.ImageParkingModel;
import com.example.parkingapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPhotosParking extends AppCompatActivity {

    String parkingId;
    Button add_image;
    ImageView backPress;

    RecyclerView imageRecyclerView;
    ArrayList<ImageParkingModel> modelArrayList=new ArrayList<>();
    GridLayoutManager layoutManager;
    ImageParkingAdapter imageParkingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photos_parking);

        Intent intent=getIntent();
        if (intent!=null){
            parkingId=intent.getStringExtra("parking_id");
        }

        imageRecyclerView=findViewById(R.id.image_recycler_view);
        add_image=findViewById(R.id.add_image);
        backPress=findViewById(R.id.back_press);


        layoutManager=new GridLayoutManager(this,3, LinearLayoutManager.VERTICAL,false);

        callImageHistory();


    }

    private void callImageHistory() {
        APIService service= APIClient.getClient().create(APIService.class);
        Call<ImageParkingModel> callModel=service.getImage();
        callModel.enqueue(new Callback<ImageParkingModel>() {
            @Override
            public void onResponse(Call<ImageParkingModel> call, Response<ImageParkingModel> response) {
                ImageParkingModel model=response.body();
            }

            @Override
            public void onFailure(Call<ImageParkingModel> call, Throwable t) {
                Toast.makeText(AddPhotosParking.this, "network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
