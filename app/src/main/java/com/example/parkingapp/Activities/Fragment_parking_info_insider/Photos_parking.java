package com.example.parkingapp.Activities.Fragment_parking_info_insider;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.parkingapp.Adapters.ImageParkingAdapter;
import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.ImageParkingModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.Preferences.ReservationPreferences;
import com.example.parkingapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Photos_parking extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Photos_parking() {
        // Required empty public constructor
    }


    public static Photos_parking newInstance(String param1, String param2) {
        Photos_parking fragment = new Photos_parking();
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

    RecyclerView recyclerMain;
    GridLayoutManager gridLayoutManager;
    ArrayList<ImageParkingModel>  models=new ArrayList<>();
    Context context;
    ReservationPreferences preferences;
    ImageParkingAdapter adapter;
    Preferences preferenceMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=container.getContext();
        preferences=new ReservationPreferences(context);


        preferenceMain=new Preferences(context);

        if (preferenceMain.getSwitchNightMod()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        View view= inflater.inflate(R.layout.fragment_photos_parking, container, false);
        recyclerMain=view.findViewById(R.id.recyclerMain);

        gridLayoutManager=new GridLayoutManager(context,3, LinearLayoutManager.VERTICAL,false);
        recyclerMain.setHasFixedSize(true);
        recyclerMain.setLayoutManager(gridLayoutManager);

        callApiRecycler();

        return view;
    }

    private void callApiRecycler() {

        APIService service= APIClient.getClient().create(APIService.class);
        Call<ImageParkingModel> modelCall=service.getImage(preferences.getParkingID());
        modelCall.enqueue(new Callback<ImageParkingModel>() {
            @Override
            public void onResponse(Call<ImageParkingModel> call, Response<ImageParkingModel> response) {
                ImageParkingModel model=response.body();
                if (model.getStatus().equalsIgnoreCase("success")){
                    models=model.getParkingPicData();
                    adapter=new ImageParkingAdapter(context,models);
                    recyclerMain.setAdapter(adapter);

                }else {
                    Toast.makeText(context, model.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageParkingModel> call, Throwable t) {
                Toast.makeText(context, "network error", Toast.LENGTH_SHORT).show();
            }
        });

    }


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
