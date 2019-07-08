package com.example.parkingapp.Activities.Fragment_parking_info_insider;

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

import com.example.parkingapp.Activities.ReserveParking;
import com.example.parkingapp.Preferences.ReservationPreferences;
import com.example.parkingapp.R;

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

    TextView Available_time,parking_name,parking_number,Owner_name;
    Button next;
    Context context;
    ReservationPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=container.getContext();
        View view= inflater.inflate(R.layout.fragment_parking_info_fragment, container, false);

        preferences=new ReservationPreferences(context);

        Available_time=view.findViewById(R.id.Available_time);
        parking_name=view.findViewById(R.id.parking_name);
        parking_number=view.findViewById(R.id.parking_number);
        Owner_name=view.findViewById(R.id.Owner_name);

        next=view.findViewById(R.id.next);
        Available_time.setText(preferences.getParkingAvailablity());
        parking_number.setText(preferences.getParkingDes());
        parking_name.setText(preferences.getParkingName());
        Owner_name.setText(preferences.getParkingOwner());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ReserveParking.class));
            }
        });



        return view;
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
