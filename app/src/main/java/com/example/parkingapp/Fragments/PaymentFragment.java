package com.example.parkingapp.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.example.parkingapp.Intefaces.APIClient;
import com.example.parkingapp.Intefaces.APIService;
import com.example.parkingapp.Models.BrainTreeModel;
import com.example.parkingapp.Preferences.Preferences;
import com.example.parkingapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.braintreepayments.api.BraintreeFragment.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PaymentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
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

    TextView card_add_payment;
    Preferences preference;
    Context context;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=container.getContext();
        View view=inflater.inflate(R.layout.fragment_payment, container, false);
        progressDialog=new ProgressDialog(context);
        card_add_payment=view.findViewById(R.id.card_add_payment);
        card_add_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentNonce();
            }
        });

        return view;
    }

    private void PaymentNonce() {

            preference=new Preferences(context);
            DropInRequest dropInRequest=new DropInRequest().clientToken(preference.getBrainTreeToker());
            startActivityForResult(dropInRequest.getIntent(context),1000);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000){
            if (resultCode== Activity.RESULT_OK){
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String paymentNonce=result.getPaymentMethodNonce().getNonce();
                Log.e("nonce", "onActivityResult: "+ paymentNonce);
                Toast.makeText(context, "Successfully Created", Toast.LENGTH_SHORT).show();

                callCardSavingApi(paymentNonce);



            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(context, "User Cancelled The Request", Toast.LENGTH_SHORT).show();
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Toast.makeText(context,"error "+error,Toast.LENGTH_SHORT).show();
                Log.e("error", "onActivityResult: "+error );
            }
        }
    }

    private void callCardSavingApi(String paymentNonce) {
        progressDialog.setTitle("Saving Card");
        progressDialog.setMessage("Please Wait while are saving");
        progressDialog.setCancelable(false);

        progressDialog.show();
        APIService service= APIClient.getClient().create(APIService.class);
        Call<BrainTreeModel> modelCall=service.BrainTree(paymentNonce,preference.getEmail(),preference.getUserId());
        modelCall.enqueue(new Callback<BrainTreeModel>() {
            @Override
            public void onResponse(Call<BrainTreeModel> call, Response<BrainTreeModel> response) {
                BrainTreeModel model=response.body();
                progressDialog.dismiss();

                preference.setCustomerID("");
            }

            @Override
            public void onFailure(Call<BrainTreeModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context,"Network error",Toast.LENGTH_SHORT).show();
                Log.e("error", "onFailure: "+t.getMessage() );
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
