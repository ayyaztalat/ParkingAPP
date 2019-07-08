package com.example.parkingapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TruckModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("parking_data")
    @Expose
    private String parkingData;
    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("truck_id")
    @Expose
    private String truck_id;

    public String getTruck_id() {
        return truck_id;
    }

    public void setTruck_id(String truck_id) {
        this.truck_id = truck_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParkingData() {
        return parkingData;
    }

    public void setParkingData(String parkingData) {
        this.parkingData = parkingData;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
