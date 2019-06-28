package com.example.parkingapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ImageParkingModel {


    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("error")
    @Expose
    public String error;

    @SerializedName("parking_pic_data")
    @Expose
    public ArrayList<ImageParkingModel> arrayList=null;

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

    public ArrayList<ImageParkingModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ImageParkingModel> arrayList) {
        this.arrayList = arrayList;
    }



    @SerializedName("picture_id")
    @Expose
    public String pictureID;

    @SerializedName("parking_id")
    @Expose
    public String parkingID;

    @SerializedName("picture")
    @Expose
    public String picture;

    @SerializedName("time_stamp")
    @Expose
    public String timeStamp;

    public String getPictureID() {
        return pictureID;
    }

    public void setPictureID(String pictureID) {
        this.pictureID = pictureID;
    }

    public String getParkingID() {
        return parkingID;
    }

    public void setParkingID(String parkingID) {
        this.parkingID = parkingID;
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
}
