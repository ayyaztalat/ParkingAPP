package com.example.parkingapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.EditModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;


public class SecurityFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private OnFragmentInteractionListener mListener;

  public SecurityFragment() {
    // Required empty public constructor
  }

  AppCompatEditText edit_text_oldPassword,edit_text_newPassword,edit_text_confirmPassword;
  Button save;
  Context context;
  Preferences preferences;
  ProgressDialog dialog;
  public static SecurityFragment newInstance(String param1, String param2) {
    SecurityFragment fragment = new SecurityFragment();
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
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view= inflater.inflate(R.layout.fragment_security, container, false);
    context=container.getContext();
    preferences=new Preferences(context);
    edit_text_oldPassword=view.findViewById(R.id.edit_text_oldPassword);
    dialog=new ProgressDialog(context);
    dialog.setTitle("Updating Password");
    dialog.setMessage("Please Wait");
    dialog.setCancelable(false);

    edit_text_newPassword=view.findViewById(R.id.edit_text_newPassword);
    edit_text_confirmPassword=view.findViewById(R.id.edit_text_confirmPassword);

    save=view.findViewById(R.id.save);

    save.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        callPasswordChangeAPI();
      }
    });

    return view;
  }

  private void callPasswordChangeAPI() {

    String oldPassword=edit_text_oldPassword.getText().toString();
    String newPassword=edit_text_newPassword.getText().toString();
    String confirmPassword=edit_text_confirmPassword.getText().toString();

    if (TextUtils.isEmpty(oldPassword)){
      edit_text_oldPassword.setError("Please enter Old Password");
    }else if (TextUtils.isEmpty(newPassword)){
      edit_text_newPassword.setError("Please enter new Password");
    }else if (TextUtils.isEmpty(confirmPassword)){
      edit_text_confirmPassword.setError("Please confirm password again");
    }else if (!confirmPassword.equalsIgnoreCase(newPassword)){
      edit_text_confirmPassword.setError("Confirm password doesnot match new Password");
    }else{
      dialog.show();
      APIService service= APIClient.getClient().create(APIService.class);
      Call<EditModel> modelCall=service.EditProfile(preferences.getUserId(),preferences.getEmail(),preferences.getName(),preferences.getPhone(),newPassword,preferences.getFcmToken(),preferences.getStatusVaalue(),preferences.getLatitude(),preferences.getLongitude());
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
            Toast.makeText(context, model.getError(), Toast.LENGTH_SHORT).show();
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
