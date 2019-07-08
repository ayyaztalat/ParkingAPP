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

    public String getBrainTreeToker() {
        return Prefs.getString("braintree_token","");
    }

    private void SetBrainTreeToken(String BrainTree){
        Prefs.putString("braintree_token",BrainTree);
    }

    public void setCustomerID(String customerID) {
        Prefs.putString("customer_id",customerID);
    }
    private String getCustomerID(){
        return Prefs.getString("customer_id","");
    }

    public void setSession(boolean b) {
        Prefs.putBoolean("session",b);
    }
    public Boolean getSession(){
        return Prefs.getBoolean("session",false);
    }

    public void setTruckID(String truck_id) {
        Prefs.putString("truck_id",truck_id);
    }
    public String getTruckID(){
        return Prefs.getString("truck_id","");
    }

    public void setSwitchNightMod(boolean b) {
        Prefs.putBoolean("nightMod",b);
    }

    public Boolean getSwitchNightMod() {
      return   Prefs.getBoolean("nightMod",false);
    }

    public void setTypeGuest(String guest) {
        Prefs.putString("guest_type",guest);
    }
    public String getTypeGuest(){
        return Prefs.getString("guest_type","");
    }
}
