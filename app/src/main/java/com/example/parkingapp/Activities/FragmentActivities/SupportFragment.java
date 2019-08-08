package com.example.parkingapp.Activities.FragmentActivities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.SupportModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SupportFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Preferences preferences;

    private OnFragmentInteractionListener mListener;

    public SupportFragment() {
        // Required empty public constructor
    }

    EditText message,edit_text_Email;
    Button send;
    public static SupportFragment newInstance(String param1, String param2) {
        SupportFragment fragment = new SupportFragment();
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
Context context;
    Preferences preferenceMain;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        preferenceMain=new Preferences(context);

        if (preferenceMain.getSwitchNightMod()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.activity_support,container,false);
        message=view.findViewById(R.id.message);
        edit_text_Email=view.findViewById(R.id.edit_text_Email);
        send=view.findViewById(R.id.send);

        preferences=new Preferences(container.getContext());

        edit_text_Email.setText(preferences.getEmail());

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail(container.getContext());
            }
        });

        return view;
    }

    private void sendMail(final Context context) {
        String mes=message.getText().toString();
        String email=edit_text_Email.getText().toString();

        if (TextUtils.isEmpty(mes)){
            message.setError("Please enter message");
        }else if (TextUtils.isEmpty(email)){
            edit_text_Email.setError("Please enter your email");
        }else {

            APIService service= APIClient.getClient().create(APIService.class);
            Call<SupportModel> modelCall=service.Support(email,mes);
            modelCall.enqueue(new Callback<SupportModel>() {
                @Override
                public void onResponse(Call<SupportModel> call, Response<SupportModel> response) {
                    SupportModel model=response.body();
                    if (model.getStatus().equalsIgnoreCase("success")){
                        Toast.makeText(context, model.getStatus(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, model.getError(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SupportModel> call, Throwable t) {
                    Toast.makeText(context, "network error", Toast.LENGTH_SHORT).show();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
