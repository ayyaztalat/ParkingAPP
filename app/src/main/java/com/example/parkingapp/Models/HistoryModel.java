package com.example.parkingapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HistoryModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("parking_data")
    @Expose
    private ArrayList<HistoryModel> parkingData = null;
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

    public ArrayList<HistoryModel> getParkingData() {
        return parkingData;
    }

    public void setParkingData(ArrayList<HistoryModel> parkingData) {
        this.parkingData = parkingData;
    }



    @SerializedName("booked_parking_id")
    @Expose
    private String bookedParkingId;
    @SerializedName("truck_id")
    @Expose
    private String truckId;
    @SerializedName("parking_id")
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
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("time_stamp")
    @Expose
    private String timeStamp;

    public String getBookedParkingId() {
        return bookedParkingId;
    }

    public void setBookedParkingId(String bookedParkingId) {
        this.bookedParkingId = bookedParkingId;
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

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String  getError() {
        return error;
    }
}
