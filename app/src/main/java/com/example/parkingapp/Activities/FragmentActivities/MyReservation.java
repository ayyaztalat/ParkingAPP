package com.example.parkingapp.Activities.FragmentActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.parkingapp.Adapters.MyReservationAdapter;
import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.ReservationModel;
import com.example.parkingapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReservation extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyReservation() {
        // Required empty public constructor
    }

    public static MyReservation newInstance(String param1, String param2) {
        MyReservation fragment = new MyReservation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    RecyclerView recycler_my_reservation;
    MyReservationAdapter reservationAdapter;
    ArrayList<ReservationModel> modelArrayList;
    LinearLayoutManager linearLayoutManager;
    ProgressDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_reservation, container, false);
        dialog=new ProgressDialog(container.getContext());
        dialog.setMessage("Please wait while we are loading information");
        dialog.setTitle("Loading Parking");
        dialog.setCancelable(false);

        recycler_my_reservation=view.findViewById(R.id.recycler_my_reservation);
        recycler_my_reservation.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(container.getContext(),LinearLayoutManager.VERTICAL,false);

        modelArrayList=new ArrayList<>();

        recycler_my_reservation.setLayoutManager(linearLayoutManager);
        callAPIData(container.getContext());
        return view;
    }

    private void callAPIData(final Context context) {
        dialog.show();
        APIService service= APIClient.getClient().create(APIService.class);
        Call<ReservationModel> modelCall=service.MyReservationCall();
        modelCall.enqueue(new Callback<ReservationModel>() {
            @Override
            public void onResponse(Call<ReservationModel> call, Response<ReservationModel> response) {
                ReservationModel model=response.body();
                dialog.dismiss();

                if (model.getStatus().equalsIgnoreCase("success")){

                    modelArrayList=model.getReservedData();
                    reservationAdapter=new MyReservationAdapter(context,modelArrayList);
                    recycler_my_reservation.setAdapter(reservationAdapter);
                }else{
                    Toast.makeText(context, model.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReservationModel> call, Throwable t) {
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
