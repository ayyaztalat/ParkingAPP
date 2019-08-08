package com.example.parkingapp.Models;

import com.example.parkingapp.Activities.Signup;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SignupModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user_data")
    @Expose
    private ArrayList<SignupModel> userData = null;
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

    public ArrayList<SignupModel> getUserData() {
        return userData;
    }

    public void setUserData(ArrayList<SignupModel> userData) {
        this.userData = userData;
    }

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("value_status")
    @Expose
    private String value_status;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getStatus_value() {
        return value_status;
    }

    public void setStatus_value(String value_status) {
        this.value_status = value_status;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getError() {
        return error;
    }

    public String getAddress() {
        return address;
    }

    @SerializedName("address")
    @Expose
    private String address;

    public void setAddress(String address) {
        this.address = address;
    }
}
