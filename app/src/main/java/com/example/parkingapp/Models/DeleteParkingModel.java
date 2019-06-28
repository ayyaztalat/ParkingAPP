package com.example.parkingapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteParkingModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("parking_data")
    @Expose
    private String parkingData;
    @SerializedName("error")
    @Expose
    private String error;

    public void setError(String error) {
        this.error = error;
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
}
