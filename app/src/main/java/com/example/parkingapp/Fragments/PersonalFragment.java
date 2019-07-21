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
import android.widget.TextView;
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

    AppCompatEditText edit_text_name,edit_text_Email,edit_text_Number;
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

    TextView truck_details;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_personal, container, false);
        context=container.getContext();

        preferences=new Preferences(context);

        String status_value=preferences.getStatusVaalue();

        truck_details=view.findViewById(R.id.truck_details);
        truck_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,TruckAddClass.class));
            }
        });
        save=view.findViewById(R.id.save);
        cancel=view.findViewById(R.id.cancel);
        edit_text_Email=view.findViewById(R.id.edit_text_Email);
        edit_text_name=view.findViewById(R.id.edit_text_name);
        edit_text_Number=view.findViewById(R.id.edit_text_Number);




        if (status_value.equalsIgnoreCase("parking_owner")){
            truck_details.setVisibility(View.GONE);
        }else{
            truck_details.setVisibility(View.VISIBLE);
        }


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




        return view;
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
