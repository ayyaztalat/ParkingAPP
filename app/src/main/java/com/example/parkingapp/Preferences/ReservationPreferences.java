package com.example.parkingapp.Preferences;

import android.content.Context;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;

public class ReservationPreferences {
    public ReservationPreferences(Context context) {
        new Prefs.Builder().setContext(context).setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(context.getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }


    public void setLatitude(String latitude) {
        Prefs.putString("latitude",latitude);
    }

    public void setLongitude(String longitude) {
        Prefs.putString("longitude",longitude);
    }

    public void setParkingOwner(String parking_owner) {
        Prefs.putString("parking_owner",parking_owner);
    }

    public void setParkingName(String parking_name) {
        Prefs.putString("parking_name",parking_name);
    }

    public void setParkingAvailablity(String parking_availablity) {
        Prefs.putString("parking_availability",parking_availablity);
    }

    public void setParkingID(String parking_id) {
        Prefs.putString("parking_id",parking_id);
    }

    public void setParkingDes(String parking_des) {
        Prefs.putString("parking_des",parking_des);
    }

    public void setParkingPrice(String parking_price) {
        Prefs.putString("parking_price",parking_price);
    }

    public String getLatitude() {
        return Prefs.getString("latitude","");
    }

    public String getLongitude() {
        return Prefs.getString("longitude","");
    }

    public String getParkingOwner() {
     return    Prefs.getString("parking_owner","");

    }

    public String getParkingName() {
        return    Prefs.getString("parking_name","");
    }

    public String getParkingAvailablity() {
        return    Prefs.getString("parking_availability","");
    }

    public String getParkingID() {
        return    Prefs.getString("parking_id","");
    }

    public String getParkingDes() {
        return    Prefs.getString("parking_des","");
    }

    public String getParkingPrice() {
        return    Prefs.getString("parking_price","");
    }

    public void setParkingOwnerId(String parking_owner_id) {
        Prefs.putString("owner_id",parking_owner_id);
    }
    public String getParkingOwnerId(){
        return Prefs.getString("owner_id","");
    }

    public void setRemainingParkingSpots(String RemainingParkingSpots){
         Prefs.putString("RemainingParkingSpots",RemainingParkingSpots);
    }

    public String getRemainingParkingSpots(){
        return Prefs.getString("RemainingParkingSpots","");
    }

    public void setFilledParkingSpots(String filled_parking_spots) {
        Prefs.putString("filled_parking_spots",filled_parking_spots);
    }

    public String getFilled_parking_spots() {
      return  Prefs.getString("filled_parking_spots","");
    }


}
