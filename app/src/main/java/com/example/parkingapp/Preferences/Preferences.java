package com.example.parkingapp.Preferences;

import android.content.Context;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;

public class Preferences {

    public Preferences(Context context) {
        new Prefs.Builder().setContext(context).setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(context.getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }


    public void setFCMToken(String refreshedToken) {
        Prefs.putString("fcm_token", refreshedToken);
    }

    public String getFcmToken() {
        return Prefs.getString("fcm_token", "");
    }

    public void setName(String name) {
        Prefs.putString("name",name);
    }

    public String getName(){
        return Prefs.getString("name","");
    }

    public void setPhone(String phone) {
        Prefs.putString("phone",phone);
    }

    public String getPhone(){
        return Prefs.getString("phone","");
    }
    public void setPassword(String password) {
        Prefs.putString("password",password);
    }

    public String getPassword(){
        return Prefs.getString("password","");
    }

    public void setEmail(String email) {
        Prefs.putString("email",email);
    }

    public String getEmail(){
        return Prefs.getString("email","");
    }

    public void setTime(String timeStamp) {
        Prefs.putString("time_stamp",timeStamp);
    }

    public String getTime(){
        return Prefs.getString("time_stamp","");
    }

    public void setUserId(String id) {
        Prefs.putString("id",id);
    }

    public String getUserId(){
        return Prefs.getString("id","");
    }

    public void setStatusValue(String status_value) {
        Prefs.putString("status_value",status_value);
    }

    public String getStatusVaalue(){
        return Prefs.getString("status_value","");
    }

    public void setLatitude(String latitude) {
        Prefs.putString("latitude",latitude);
    }

    public String getLatitude(){
        return Prefs.getString("latitude","");
    }

    public void setLongitude(String longitude) {
        Prefs.putString("longitude",longitude);
    }

    public String getLongitude(){
        return Prefs.getString("longitude","");
    }

    public void setTrafficCheck(boolean b) {
        Prefs.putBoolean("traffic_check",b);
    }


    public void setNightModeCheck(boolean b) {
        Prefs.putBoolean("night_mood_check",b);
    }

    public boolean getTrafficCheck() {
        return   Prefs.getBoolean("traffic_check",true);
    }

    public boolean getNightModeCheck() {
        return   Prefs.getBoolean("night_mood_check",false);
    }

    public void clear() {

    }

    public void setTruckNum(String truckNum) {
        Prefs.putString("truckNum",truckNum);
    }

    public void setTruckColor(String truckColor) {
        Prefs.putString("truckColor",truckColor);
    }

    public void setTruckName(String truckName) {
        Prefs.putString("truckName",truckName);
    }

    public String getTruckNum() {
        return Prefs.getString("truckNum","");
    }

    public String getTruckColor() {
        return  Prefs.getString("truckColor","");
    }

    public String getTruckName() {
        return  Prefs.getString("truckName","");
    }
}
