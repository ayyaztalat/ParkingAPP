package com.example.parkingapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportModel {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("mail")
    @Expose
    private String mail;

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
