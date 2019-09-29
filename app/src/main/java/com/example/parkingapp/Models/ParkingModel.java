package com.example.parkingapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ParkingModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("parking_data")
    @Expose
    private ArrayList<ParkingModel> parkingData = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ParkingModel> getParkingData() {
        return parkingData;
    }

    public void setParkingData(ArrayList<ParkingModel> parkingData) {
        this.parkingData = parkingData;
    }



    @SerializedName("error")
    @Expose
    private String error;

    public void setError(String error) {
        this.error = error;
    }

    @SerializedName("parking_id")
    @Expose
    private String parkingId;
    @SerializedName("parking_owner_id")
    @Expose
    private String parkingOwnerId;
    @SerializedName("parking_latitude ")
    @Expose
    private String parkingLatitude;
    @SerializedName("parking_longitude")
    @Expose
    private String parkingLongitude;
    @SerializedName("parking_name")
    @Expose
    private String parkingName;
    @SerializedName("parking_time")
    @Expose
    private String parkingTime;
    @SerializedName("parking_owner_name")
    @Expose
    private String parkingOwnerName;
    @SerializedName("parking_owner_number")
    @Expose
    private String parkingOwnerNumber;
    @SerializedName("parking_status")
    @Expose
    private String parkingStatus;
    @SerializedName("availability_type")
    @Expose
    private String availabilityType;
    @SerializedName("parking_price")
    @Expose
    private String parkingPrice;
    @SerializedName("parking_description")
    @Expose
    private String parkingDescription;
    @SerializedName("total_parking_spots")
    @Expose
    private String totalParkingSpots;
    @SerializedName("remaining_parking_spots")
    @Expose
    private String remainingParkingSpots;
    @SerializedName("filled_parking_spots")
    @Expose
    private String filledParkingSpots;
    @SerializedName("time_stamp")
    @Expose
    private String timeStamp;
    @SerializedName("parking_image1")
    @Expose
    private String parkingImage1;
    @SerializedName("parking_image2")
    @Expose
    private String parkingImage2;

    public String getParkingId() {
        return parkingId;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }

    public String getParkingOwnerId() {
        return parkingOwnerId;
    }

    public void setParkingOwnerId(String parkingOwnerId) {
        this.parkingOwnerId = parkingOwnerId;
    }

    public String getParkingLatitude() {
        return parkingLatitude;
    }

    public void setParkingLatitude(String parkingLatitude) {
        this.parkingLatitude = parkingLatitude;
    }

    public String getParkingLongitude() {
        return parkingLongitude;
    }

    public void setParkingLongitude(String parkingLongitude) {
        this.parkingLongitude = parkingLongitude;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getParkingTime() {
        return parkingTime;
    }

    public void setParkingTime(String parkingTime) {
        this.parkingTime = parkingTime;
    }

    public String getParkingOwnerName() {
        return parkingOwnerName;
    }

    public void setParkingOwnerName(String parkingOwnerName) {
        this.parkingOwnerName = parkingOwnerName;
    }

    public String getParkingOwnerNumber() {
        return parkingOwnerNumber;
    }

    public void setParkingOwnerNumber(String parkingOwnerNumber) {
        this.parkingOwnerNumber = parkingOwnerNumber;
    }

    public String getParkingStatus() {
        return parkingStatus;
    }

    public void setParkingStatus(String parkingStatus) {
        this.parkingStatus = parkingStatus;
    }

    public String getAvailabilityType() {
        return availabilityType;
    }

    public void setAvailabilityType(String availabilityType) {
        this.availabilityType = availabilityType;
    }

    public String getParkingPrice() {
        return parkingPrice;
    }

    public void setParkingPrice(String parkingPrice) {
        this.parkingPrice = parkingPrice;
    }

    public String getParkingDescription() {
        return parkingDescription;
    }

    public void setParkingDescription(String parkingDescription) {
        this.parkingDescription = parkingDescription;
    }

    public String getTotalParkingSpots() {
        return totalParkingSpots;
    }

    public void setTotalParkingSpots(String totalParkingSpots) {
        this.totalParkingSpots = totalParkingSpots;
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getParkingImage1() {
        return parkingImage1;
    }

    public void setParkingImage1(String parkingImage1) {
        this.parkingImage1 = parkingImage1;
    }

    public String getParkingImage2() {
        return parkingImage2;
    }

    public void setParkingImage2(String parkingImage2) {
        this.parkingImage2 = parkingImage2;
    }
    public String  getError() {
        return error;
    }
}
