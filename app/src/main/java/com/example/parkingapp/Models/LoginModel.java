package com.example.parkingapp.Models;import com.google.gson.annotations.Expose;import com.google.gson.annotations.SerializedName;import com.pixplicity.easyprefs.library.Prefs;import java.util.ArrayList;public class LoginModel {    @SerializedName("status")    @Expose    private String status;    @SerializedName("user_data")    @Expose    private ArrayList<LoginModel> userData = null;    @SerializedName("error")    @Expose    private String Error;    public void setError(String error) {        Error = error;    }    public String getStatus() {        return status;    }    public void setStatus(String status) {        this.status = status;    }    public ArrayList<LoginModel> getUserData() {        return userData;    }    public void setUserData(ArrayList<LoginModel> userData) {        this.userData = userData;    }    @SerializedName("id")    @Expose    private String id;    @SerializedName("email")    @Expose    private String email;    @SerializedName("password")    @Expose    private String password;    @SerializedName("name")    @Expose    private String name;    @SerializedName("phone")    @Expose    private String phone;    @SerializedName("device_id")    @Expose    private String deviceId;    @SerializedName("status_value")    @Expose    private String status_value;    @SerializedName("latitude")    @Expose    private String latitude;    @SerializedName("longitude")    @Expose    private String longitude;    @SerializedName("time_stamp")    @Expose    private String timeStamp;    @SerializedName("card_token")    @Expose    private Object cardToken;    @SerializedName("card_bin")    @Expose    private Object cardBin;    @SerializedName("card_last4")    @Expose    private Object cardLast4;    @SerializedName("card_type")    @Expose    private Object cardType;    @SerializedName("card_customerLocation")    @Expose    private Object cardCustomerLocation;    @SerializedName("card_cardholderName")    @Expose    private Object cardCardholderName;    @SerializedName("card_expirationDate")    @Expose    private Object cardExpirationDate;    @SerializedName("customer_id")    @Expose    private Object customerId;    public String getId() {        return id;    }    public void setId(String id) {        this.id = id;    }    public String getEmail() {        return email;    }    public void setEmail(String email) {        this.email = email;    }    public String getPassword() {        return password;    }    public void setPassword(String password) {        this.password = password;    }    public String getName() {        return name;    }    public void setName(String name) {        this.name = name;    }    public String getPhone() {        return phone;    }    public void setPhone(String phone) {        this.phone = phone;    }    public String getDeviceId() {        return deviceId;    }    public void setDeviceId(String deviceId) {        this.deviceId = deviceId;    }    public String getStatusValue() {        return status_value;    }    public void setStatusValue(String status_value) {        this.status_value = status_value;    }    public String getLatitude() {        return latitude;    }    public void setLatitude(String latitude) {        this.latitude = latitude;    }    public String getLongitude() {        return longitude;    }    public void setLongitude(String longitude) {        this.longitude = longitude;    }    public String getTimeStamp() {        return timeStamp;    }    public void setTimeStamp(String timeStamp) {        this.timeStamp = timeStamp;    }    public Object getCardToken() {        return cardToken;    }    public void setCardToken(Object cardToken) {        this.cardToken = cardToken;    }    public Object getCardBin() {        return cardBin;    }    public void setCardBin(Object cardBin) {        this.cardBin = cardBin;    }    public Object getCardLast4() {        return cardLast4;    }    public void setCardLast4(Object cardLast4) {        this.cardLast4 = cardLast4;    }    public Object getCardType() {        return cardType;    }    public void setCardType(Object cardType) {        this.cardType = cardType;    }    public Object getCardCustomerLocation() {        return cardCustomerLocation;    }    public void setCardCustomerLocation(Object cardCustomerLocation) {        this.cardCustomerLocation = cardCustomerLocation;    }    public Object getCardCardholderName() {        return cardCardholderName;    }    public void setCardCardholderName(Object cardCardholderName) {        this.cardCardholderName = cardCardholderName;    }    public Object getCardExpirationDate() {        return cardExpirationDate;    }    public void setCardExpirationDate(Object cardExpirationDate) {        this.cardExpirationDate = cardExpirationDate;    }    public Object getCustomerId() {        return customerId;    }    public void setCustomerId(Object customerId) {        this.customerId = customerId;    }    public String  getError() {        return Error;    }}