package com.example.parkingapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddImageParking {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("error")
    @Expose
    public String error;

    @SerializedName("parking_data")
    @Expose
    public String parking_data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getParking_data() {
        return parking_data;
    }

    public void setParking_data(String parking_data) {
        this.parking_data = parking_data;
    }
}
