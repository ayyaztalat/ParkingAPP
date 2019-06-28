package com.example.parkingapp.FCM;

import android.content.Context;
import android.util.Log;

import com.example.parkingapp.Preferences.Preferences;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class firebaseClass extends FirebaseInstanceIdService {



    Preferences pref;
    private static final String TAG = firebaseClass.class.getSimpleName();


    public void onTokenRefresh(Context context) {


        pref = new Preferences(context);
//        // Get updated InstanceID token.
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        pref.setDeviceId(refreshedToken);
//        Log.d("", "Refreshed token: " + refreshedToken);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        //  pref.setFCMToken(refreshedToken);

        if (refreshedToken!=null) {
//            SettingPreferences.setStringValueInPref(this, SettingPreferences.REG_ID, refreshedToken);
            pref.setFCMToken(refreshedToken);

        }
        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        pref.setFCMToken(token);
    }
}
