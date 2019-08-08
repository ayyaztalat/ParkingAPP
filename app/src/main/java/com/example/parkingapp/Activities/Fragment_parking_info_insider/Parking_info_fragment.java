package com.example.parkingapp.Activities.Fragment_parking_info_insider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.Activities.ReserveParking;
import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.AvailabilityTypeModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.Preferences.ReservationPreferences;
import com.example.parkingapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Parking_info_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Parking_info_fragment() {
        // Required empty public constructor
    }


    public static Parking_info_fragment newInstance(String param1, String param2) {
        Parking_info_fragment fragment = new Parking_info_fragment();
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

    TextView Available_time,parking_name,parking_number,Owner_name,parking_spots;
    Button next;
    Context context;
    ReservationPreferences preferences;
    Preferences preferenceMain;

    LinearLayout availablity_type;
    TextView high,medium,low;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();
        preferenceMain=new Preferences(context);
/*

        if (preferenceMain.getSwitchNightMod()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
*/


        preferences=new ReservationPreferences(context);
        View view= inflater.inflate(R.layout.fragment_parking_info_fragment, container, false);
        Available_time=view.findViewById(R.id.Available_time);
        parking_name=view.findViewById(R.id.parking_name);
        parking_number=view.findViewById(R.id.parking_number);
        Owner_name=view.findViewById(R.id.Owner_name);
        parking_spots=view.findViewById(R.id.parking_spots);

        String parking_price= preferences.getParkingPrice();

        next=view.findViewById(R.id.next);
        Available_time.setText(preferences.getParkingAvailablity());
        parking_number.setText(preferences.getParkingDes());
        parking_name.setText(preferences.getParkingName());
        Owner_name.setText(preferences.getParkingOwner());
        parking_spots.setText(preferences.getRemainingParkingSpots());

        availablity_type=view.findViewById(R.id.availablity_type);
        high=view.findViewById(R.id.high);
        medium=view.findViewById(R.id.Medium);
        low=view.findViewById(R.id.low);


        if (parking_price.equalsIgnoreCase("0")|| parking_price.equalsIgnoreCase("free")){
            availablity_type.setVisibility(View.GONE);
        }else {
            availablity_type.setVisibility(View.VISIBLE);
        }

        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAvailablityType(preferences.getParkingID(),"high");
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAvailablityType(preferences.getParkingID(),"medium");
            }
        });

        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAvailablityType(preferences.getParkingID(),"low");
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ReserveParking.class));
            }
        });



        return view;
    }

    private void checkAvailablityType(String parkingID, String high) {
        APIService service= APIClient.getClient().create(APIService.class);
        final Call<AvailabilityTypeModel> modelCall=service.availableType(parkingID,high);
        modelCall.enqueue(new Callback<AvailabilityTypeModel>() {
            @Override
            public void onResponse(Call<AvailabilityTypeModel> call, Response<AvailabilityTypeModel> response) {
                AvailabilityTypeModel model=response.body();
                if (model.getStatus().equalsIgnoreCase("success")){
                    Toast.makeText(context, model.getParkingData(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, model.getError(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AvailabilityTypeModel> call, Throwable t) {
                Toast.makeText(context, "network error", Toast.LENGTH_SHORT).show();
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
