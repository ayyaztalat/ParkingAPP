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
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("truck_owner_id")
    @Expose
    private String truckOwnerId;
    @SerializedName("parking_owner_id")
    @Expose
    private String parkingOwnerId;
    @SerializedName("time_stamp")
    @Expose
    private String timeStamp;
    @SerializedName("reserved_parking_spots")
    @Expose
    private String reservedParkingSpots;
    @SerializedName("remaining_parking_spots")
    @Expose
    private String remainingParkingSpots;
    @SerializedName("filled_parking_spots")
    @Expose
    private String filledParkingSpots;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTruckOwnerId() {
        return truckOwnerId;
    }

    public void setTruckOwnerId(String truckOwnerId) {
        this.truckOwnerId = truckOwnerId;
    }

    public String getParkingOwnerId() {
        return parkingOwnerId;
    }

    public void setParkingOwnerId(String parkingOwnerId) {
        this.parkingOwnerId = parkingOwnerId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getReservedParkingSpots() {
        return reservedParkingSpots;
    }

    public void setReservedParkingSpots(String reservedParkingSpots) {
        this.reservedParkingSpots = reservedParkingSpots;
    }

    public String getRemainingParkingSpots() {
        return remainingParkingSpots;
    }

    public void setRemainingParkingSpots(String remainingParkingSpots) {
        this.remainingParkingSpots = remainingParkingSpots;
    }

    public String getFilledParkingSpots() {
        return filledParkingSpots;
    }

    public void setFilledParkingSpots(String filledParkingSpots) {
        this.filledParkingSpots = filledParkingSpots;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
