package com.example.parkingapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.parkingapp.Activities.ProfileActivity;
import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.EditModel;
import com.example.parkingapp.Models.TruckModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;


public class PersonalFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PersonalFragment() {
        // Required empty public constructor
    }

    AppCompatEditText edit_text_name,edit_text_Email,edit_text_Number,edit_text_truck_number,edit_text_truck_make,edit_text_truck_color;
    Context context;
    Preferences preferences;
    Button save,cancel;
    LinearLayout item,item2;
    ProgressDialog dialog;
    public static PersonalFragment newInstance(String param1, String param2) {
        PersonalFragment fragment = new PersonalFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_personal, container, false);
        context=container.getContext();

        preferences=new Preferences(context);
        save=view.findViewById(R.id.save);
        cancel=view.findViewById(R.id.cancel);
        edit_text_Email=view.findViewById(R.id.edit_text_Email);
        edit_text_name=view.findViewById(R.id.edit_text_name);
        edit_text_Number=view.findViewById(R.id.edit_text_Number);


        edit_text_truck_number=view.findViewById(R.id.edit_text_truck_number);
        edit_text_truck_make=view.findViewById(R.id.edit_text_truck_make);
        edit_text_truck_color=view.findViewById(R.id.edit_text_truck_color);

        edit_text_truck_number.setText(preferences.getTruckNum());
        edit_text_truck_color.setText(preferences.getTruckColor());
        edit_text_truck_color.setText(preferences.getTruckName());

        item=view.findViewById(R.id.item);
        item2=view.findViewById(R.id.item2);

        dialog=new ProgressDialog(context);
        dialog.setTitle("Updating");
        dialog.setMessage("Please wait.This will take a minute or so");
        dialog.setCancelable(false);

        edit_text_Email.setText(preferences.getEmail());
        edit_text_name.setText(preferences.getName());
        edit_text_Number.setText(preferences.getPhone());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProfileActivity)context).finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEditProfileAPI();
            }
        });

        Button save_truck=view.findViewById(R.id.save_truck);
        save_truck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTrackAddAPI();
            }
        });

        return view;
    }

    private void callTrackAddAPI() {

        String truckNum=edit_text_truck_number.getText().toString();
        String truckName=edit_text_truck_make.getText().toString();
        String truckColor=edit_text_truck_color.getText().toString();

        if (TextUtils.isEmpty(truckName)){
            edit_text_truck_make.setError("Please enter Manufacture");
        }else if (TextUtils.isEmpty(truckColor)){
            edit_text_truck_color.setError("Please enter truck color");
        }else if (TextUtils.isEmpty(truckNum)){
            edit_text_truck_number.setError("Please enter truck number");
        }else{
            callAPITruck(truckNum,truckColor,truckName);
        }

    }
    String pictureMainPath;
    private void callAPITruck(String truckNum, String truckColor, String truckName) {


        dialog.setTitle("updating truck data");
        dialog.setMessage("Please wait we are uploading information");
        dialog.show();

        preferences.setTruckNum(truckNum);
        preferences.setTruckColor(truckColor);
        preferences.setTruckName(truckName);


        MultipartBody.Part picBody = null;
        MultipartBody.Part picBody2 = null;
        try {
            File file = new File(pictureMainPath);
            if (file == null) {
                Toast.makeText(context, "Registration Image is missing", Toast.LENGTH_SHORT).show();
            }
            // pDialog.show();
            RequestBody requestFileRegistration = RequestBody.create(MediaType.parse("image/*"), file);
            picBody = MultipartBody.Part.createFormData("truck_insurance_image", file.getName(), requestFileRegistration);

            if (picBody == null) {
                Toast.makeText(context, "registration is missing", Toast.LENGTH_SHORT).show();
            }

            File file2 = new File(pictureMainPath);
            if (file2 == null) {
                Toast.makeText(context, "License Image is missing", Toast.LENGTH_SHORT).show();
            }
            // pDialog.show();
            final RequestBody requestFileLicense = RequestBody.create(MediaType.parse("image/*"), file);
            picBody2 = MultipartBody.Part.createFormData("truck_registration_image", file.getName(), requestFileLicense);

            if (picBody2 == null) {
                Toast.makeText(context, "registration is missing", Toast.LENGTH_SHORT).show();
            }

            RequestBody truck_owner_id = RequestBody.create(MediaType.parse("text/plain"), preferences.getUserId());
            RequestBody track_number = RequestBody.create(MediaType.parse("text/plain"), truckNum);
            RequestBody manufacturing_name = RequestBody.create(MediaType.parse("text/plain"), truckName);
            RequestBody truck_color = RequestBody.create(MediaType.parse("text/plain"), truckColor);

            APIService service=APIClient.getClient().create(APIService.class);
            Call<TruckModel> modelCall= service.modelTruck(truck_owner_id,track_number,manufacturing_name,truck_color,picBody,picBody2);
            modelCall.enqueue(new Callback<TruckModel>() {
                @Override
                public void onResponse(Call<TruckModel> call, Response<TruckModel> response) {
                    dialog.dismiss();
                    TruckModel model=response.body();
                    if (model.getStatus().equalsIgnoreCase("success")){
                        Toast.makeText(context, model.getParkingData(), Toast.LENGTH_SHORT).show();
                        preferences.setTruckID(model.getTruck_id()) ;
                    }else{
                        Toast.makeText(context, "Error while uploading", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TruckModel> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                }
            });




        }catch (Exception e){
            e.printStackTrace();
            dialog.dismiss();
        }
    }

    private void callEditProfileAPI() {

        String email=edit_text_Email.getText().toString();
        String name=edit_text_name.getText().toString();
        String number=edit_text_Number.getText().toString();

        if (TextUtils.isEmpty(email)){
            edit_text_Email.setError("Please enter email");
        }else if (TextUtils.isEmpty(name)){
            edit_text_name.setError("Please enter name");
        }else if (TextUtils.isEmpty(number)){
            edit_text_Number.setError("Please enter number");
        }else {

            dialog.show();
            APIService service = APIClient.getClient().create(APIService.class);
            Call<EditModel> modelCall = service.EditProfile(preferences.getUserId(), email,name,number,preferences.getPassword(),preferences.getFcmToken(),preferences.getStatusVaalue(),preferences.getLatitude(),preferences.getLongitude());
            modelCall.enqueue(new Callback<EditModel>() {
                @Override
                public void onResponse(Call<EditModel> call, Response<EditModel> response) {
                    dialog.dismiss();
                    EditModel model=response.body();
                    if (model.getStatus().equalsIgnoreCase("success")){
                        preferences.setName(model.getUserData().get(0).getName());
                        preferences.setPhone(model.getUserData().get(0).getPhone());
                        preferences.setPassword(model.getUserData().get(0).getPassword());
                        preferences.setEmail(model.getUserData().get(0).getEmail());
                        preferences.setTime(model.getUserData().get(0).getTimeStamp());
                        preferences.setUserId(model.getUserData().get(0).getId());
                        preferences.setStatusValue(model.getUserData().get(0).getStatusValue());
                        preferences.setLatitude(model.getUserData().get(0).getLatitude());
                        preferences.setLongitude(model.getUserData().get(0).getLongitude());
                    }else {
                        Toast.makeText(context,model.getError(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<EditModel> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: "+t.getMessage() );
                }
            });
        }
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
