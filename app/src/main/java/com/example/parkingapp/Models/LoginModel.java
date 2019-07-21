package com.example.parkingapp.Models;import com.google.gson.annotations.Expose;import com.google.gson.annotations.SerializedName;import com.pixplicity.easyprefs.library.Prefs;import java.util.ArrayList;public class LoginModel {    @SerializedName("status")    @Expose    private String status;    @SerializedName("user_data")    @Expose    private ArrayList<LoginModel> userData = null;    @SerializedName("error")    @Expose    private String Error;    public void setError(String error) {        Error = error;    }    public String getStatus() {        return status;    }    public void setStatus(String status) {        this.status = status;    }    public ArrayList<LoginModel> getUserData() {        return userData;    }    public void setUserData(ArrayList<LoginModel> userData) {        this.userData = userData;    }    @SerializedName("id")    @Expose    private String id;    @SerializedName("email")    @Expose    private String email;    @SerializedName("password")    @Expose    private String password;    @SerializedName("name")    @Expose    private String name;    @SerializedName("phone")    @Expose    private String phone;    @SerializedName("device_id")    @Expose    private String deviceId;    @SerializedName("status_value")    @Expose    private String status_value;    @SerializedName("latitude")    @Expose    private String latitude;    @SerializedName("longitude")    @Expose    private String longitude;    @SerializedName("time_stamp")    @Expose    private String timeStamp;    @SerializedName("card_token")    @Expose    private String cardToken;    @SerializedName("card_bin")    @Expose    private String cardBin;    @SerializedName("card_last4")    @Expose    private String cardLast4;    @SerializedName("card_type")    @Expose    private String cardType;    @SerializedName("card_customerLocation")    @Expose    private String cardCustomerLocation;    @SerializedName("card_cardholderName")    @Expose    private String cardCardholderName;    @SerializedName("card_expirationDate")    @Expose    private String cardExpirationDate;    @SerializedName("customer_id")    @Expose    private String customerId;    public String getId() {        return id;    }    public void setId(String id) {        this.id = id;    }    public String getEmail() {        return email;    }    public void setEmail(String email) {        this.email = email;    }    public String getPassword() {        return password;    }    public void setPassword(String password) {        this.password = password;    }    public String getName() {        return name;    }    public void setName(String name) {        this.name = name;    }    public String getPhone() {        return phone;    }    public void setPhone(String phone) {        this.phone = phone;    }    public String getDeviceId() {        return deviceId;    }    public void setDeviceId(String deviceId) {        this.deviceId = deviceId;    }    public String getStatusValue() {        return status_value;    }    public void setStatusValue(String status_value) {        this.status_value = status_value;    }    public String getLatitude() {        return latitude;    }    public void setLatitude(String latitude) {        this.latitude = latitude;    }    public String getLongitude() {        return longitude;    }    public void setLongitude(String longitude) {        this.longitude = longitude;    }    public String getTimeStamp() {        return timeStamp;    }    public void setTimeStamp(String timeStamp) {        this.timeStamp = timeStamp;    }    public String getCardToken() {        return cardToken;    }    public void setCardToken(String cardToken) {        this.cardToken = cardToken;    }    public String getCardBin() {        return cardBin;    }    public void setCardBin(String cardBin) {        this.cardBin = cardBin;    }    public String getCardLast4() {        return cardLast4;    }    public void setCardLast4(String cardLast4) {        this.cardLast4 = cardLast4;    }    public String getCardType() {        return cardType;    }    public void setCardType(String cardType) {        this.cardType = cardType;    }    public String getCardCustomerLocation() {        return cardCustomerLocation;    }    public void setCardCustomerLocation(String cardCustomerLocation) {        this.cardCustomerLocation = cardCustomerLocation;    }    public String getCardCardholderName() {        return cardCardholderName;    }    public void setCardCardholderName(String cardCardholderName) {        this.cardCardholderName = cardCardholderName;    }    public String getCardExpirationDate() {        return cardExpirationDate;    }    public void setCardExpirationDate(String cardExpirationDate) {        this.cardExpirationDate = cardExpirationDate;    }    public String getCustomerId() {        return customerId;    }    public void setCustomerId(String customerId) {        this.customerId = customerId;    }    public String  getError() {        return Error;    }}