package com.example.parkingapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddReservationModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("reserved_parking_id")
    @Expose
    private String reservedParkingId;
    @SerializedName("parking_id")
    @Expose
    private String parkingId;

    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReservedParkingId() {
        return reservedParkingId;
    }

    public void setReservedParkingId(String reservedParkingId) {
        this.reservedParkingId = reservedParkingId;
    }

    public String getParkingId() {
        return parkingId;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }
}
