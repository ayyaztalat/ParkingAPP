package com.example.parkingapp.Activities.FragmentActivities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.parkingapp.Activities.AddParkingActivity;
import com.example.parkingapp.Activities.HomeActivity;
import com.example.parkingapp.Adapters.MyParkingAdapter;
import com.example.parkingapp.Adapters.MyReservationAdapter;
import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.ParkingModel;
import com.example.parkingapp.Models.ReservationModel;
import com.example.parkingapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyParking extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyParking() {
        // Required empty public constructor
    }

    public static MyParking newInstance(String param1, String param2) {
        MyParking fragment = new MyParking();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private FragmentActivity myFragementActivity;
    RecyclerView recycler_my_parking;
    MyParkingAdapter parkingAdapter;
    ArrayList<ParkingModel> modelArrayList;
    LinearLayoutManager linearLayoutManager;
    ProgressDialog dialog;
    Context context;
    Button add_parking;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_parking, container, false);

        dialog=new ProgressDialog(container.getContext());
        dialog.setMessage("Please wait while we are loading information");
        dialog.setTitle("Loading Parking");
        dialog.setCancelable(false);
        context=container.getContext();
        recycler_my_parking=view.findViewById(R.id.recycler_my_parking);
        recycler_my_parking.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(container.getContext(),LinearLayoutManager.VERTICAL,false);
        add_parking=view.findViewById(R.id.add_parking);
        add_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddParkingDetails();
            }
        });
        modelArrayList=new ArrayList<>();

        recycler_my_parking.setLayoutManager(linearLayoutManager);
        callAPIData(container.getContext());
        return  view;
    }

    private void AddParkingDetails() {
        startActivity(new Intent(context, AddParkingActivity.class));
        ((HomeActivity)context).finish();

    }

    private void callAPIData(final Context context) {
        dialog.show();
        APIService service= APIClient.getClient().create(APIService.class);
        Call<ParkingModel> modelCall=service.MyParkingCall();
        modelCall.enqueue(new Callback<ParkingModel>() {
            @Override
            public void onResponse(Call<ParkingModel> call, Response<ParkingModel> response) {
                ParkingModel model=response.body();
                dialog.dismiss();
                if (model.getStatus().equalsIgnoreCase("success")){
                    modelArrayList=model.getParkingData();
                    parkingAdapter=new MyParkingAdapter(context,modelArrayList);
                    recycler_my_parking.setAdapter(parkingAdapter);
                }else{
                    Toast.makeText(context, model.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ParkingModel> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(context, "No network", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
