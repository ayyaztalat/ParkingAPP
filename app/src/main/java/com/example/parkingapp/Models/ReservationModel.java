package com.example.parkingapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReservationModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("reserved_data")
    @Expose
    private ArrayList<ReservationModel> reservedData = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ReservationModel> getReservedData() {
        return reservedData;
    }

    public void setReservedData(ArrayList<ReservationModel> reservedData) {
        this.reservedData = reservedData;
    }


    @SerializedName("error")
    @Expose
    private String error;


    @SerializedName("reserved_parking_id")
    @Expose
    private String reservedParkingId;
    @SerializedName("truck_id")
    @Expose
    private String truckId;
    @SerializedName("parking_id ")
    @Expose
    private String parkingId;
    @SerializedName("truck_owner_name")
    @Expose
    private String truckOwnerName;
    @SerializedName("parking_owner_name")
    @Expose
    private String parkingOwnerName;
    @SerializedName("truck_number")
    @Expose
    private String truckNumber;
    @SerializedName("truck_color")
    @Expose
    private String truckColor;
    @SerializedName("estimated_time")
    @Expose
    private String estimatedTime;
    @SerializedName("from_date")
    @Expose
    private String fromDate;
    @SerializedName("to_date")
    @Expose
    private String toDate;
    @SerializedName("time_stamp")
    @Expose
    private String timeStamp;

    public String getReservedParkingId() {
        return reservedParkingId;
    }

    public void setReservedParkingId(String reservedParkingId) {
        this.reservedParkingId = reservedParkingId;
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }

    public String getParkingId() {
        return parkingId;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }

    public String getTruckOwnerName() {
        return truckOwnerName;
    }

    public void setTruckOwnerName(String truckOwnerName) {
        this.truckOwnerName = truckOwnerName;
    }

    public String getParkingOwnerName() {
        return parkingOwnerName;
    }

    public void setParkingOwnerName(String parkingOwnerName) {
        this.parkingOwnerName = parkingOwnerName;
    }

    public String getTruckNumber() {
        return truckNumber;
    }

    public void setTruckNumber(String truckNumber) {
        this.truckNumber = truckNumber;
    }

    public String getTruckColor() {
        return truckColor;
    }

    public void setTruckColor(String truckColor) {
        this.truckColor = truckColor;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getError() {
        return error;
    }
}
