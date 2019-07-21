package com.example.parkingapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ImageParkingModel {
    @SerializedName("error")
    @Expose
    public String error;

    public void setError(String error) {
        this.error = error;
    }

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("parking_pic_data")
    @Expose
    private ArrayList<ImageParkingModel> parkingPicData = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ImageParkingModel> getParkingPicData() {
        return parkingPicData;
    }

    public void setParkingPicData(ArrayList<ImageParkingModel> parkingPicData) {
        this.parkingPicData = parkingPicData;
    }

    @SerializedName("picture_id")
    @Expose
    private String pictureId;
    @SerializedName("parking_id")
    @Expose
    private String parkingId;
    @SerializedName("picture ")
    @Expose
    private String picture;
    @SerializedName("time_stamp")
    @Expose
    private String timeStamp;

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getParkingId() {
        return parkingId;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
