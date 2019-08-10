package com.example.parkingapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FilterModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("parking_data")
    @Expose
    private ArrayList<FilterModel> parkingData = null;

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

    public ArrayList<FilterModel> getParkingData() {
        return parkingData;
    }

    public void setParkingData(ArrayList<FilterModel> parkingData) {
        this.parkingData = parkingData;
    }


    @SerializedName("parking_id")
    @Expose
    private String parkingId;
    @SerializedName("parking_owner_id")
    @Expose
    private String parkingOwnerId;
    @SerializedName("parking_latitude")
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
    @SerializedName("parking_price")
    @Expose
    private String parkingPrice;
    @SerializedName("parking_description")
    @Expose
    private String parkingDescription;
    @SerializedName("type_of_vehicle")
    @Expose
    private String typeOfVehicle;
    @SerializedName("time_stamp")
    @Expose
    private String timeStamp;
    @SerializedName("total_parking_spots")
    @Expose
    private String totalParkingSpots;
    @SerializedName("filled_parking_spots")
    @Expose
    private String filledParkingSpots;
    @SerializedName("remaining_parking_spots")
    @Expose
    private String remainingParkingSpots;

    @SerializedName("parking_image1")
    @Expose
    private String parking_image1;

    @SerializedName("parking_image2")
    @Expose
    private String parking_image2;

    public String getParking_image1() {
        return parking_image1;
    }

    public void setParking_image1(String parking_image1) {
        this.parking_image1 = parking_image1;
    }

    public String getParking_image2() {
        return parking_image2;
    }

    public void setParking_image2(String parking_image2) {
        this.parking_image2 = parking_image2;
    }

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

    public String getTypeOfVehicle() {
        return typeOfVehicle;
    }

    public void setTypeOfVehicle(String typeOfVehicle) {
        this.typeOfVehicle = typeOfVehicle;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTotalParkingSpots() {
        return totalParkingSpots;
    }

    public void setTotalParkingSpots(String totalParkingSpots) {
        this.totalParkingSpots = totalParkingSpots;
    }

    public String getFilledParkingSpots() {
        return filledParkingSpots;
    }

    public void setFilledParkingSpots(String filledParkingSpots) {
        this.filledParkingSpots = filledParkingSpots;
    }

    public String getRemainingParkingSpots() {
        return remainingParkingSpots;
    }

    public void setRemainingParkingSpots(String remainingParkingSpots) {
        this.remainingParkingSpots = remainingParkingSpots;
    }

    public String  getError() {
        return error;
    }
}
