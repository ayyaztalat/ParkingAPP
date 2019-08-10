package com.example.parkingapp.Models;

public class CustomizationMapModel {
     String latitude;
     String longitude ;
     String parking_title ;
     String parking_owner_name;
     String parking_avaibility;
     String parking_id ;
     String parking_description ;
     String parking_price;
     String parking_owner_id ;
     String remaining_parking_spots ;
     String filled_parking_spots ;
    String typeofVehical;
    String parkingName;
    String OwnerNumber;
    String parking_image1;
    String parking_image2;

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getOwnerNumber() {
        return OwnerNumber;
    }

    public void setOwnerNumber(String ownerNumber) {
        OwnerNumber = ownerNumber;
    }

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

    public CustomizationMapModel(String latitude, String longitude, String parking_title, String parking_owner_name, String parking_avaibility, String parking_id, String parking_description, String parking_price, String parking_owner_id, String remaining_parking_spots, String filled_parking_spots, String typeofVehical, String parkingName, String ownerNumber, String parking_image1, String parking_image2) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.parking_title = parking_title;
        this.parking_owner_name = parking_owner_name;
        this.parking_avaibility = parking_avaibility;
        this.parking_id = parking_id;
        this.parking_description = parking_description;
        this.parking_price = parking_price;
        this.parking_owner_id = parking_owner_id;
        this.remaining_parking_spots = remaining_parking_spots;
        this.filled_parking_spots = filled_parking_spots;
        this.typeofVehical = typeofVehical;
        this.parkingName=parkingName;
        this.OwnerNumber=ownerNumber;
        this.parking_image1=parking_image1;
        this.parking_image2=parking_image2;
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

    public String getParking_title() {
        return parking_title;
    }

    public void setParking_title(String parking_title) {
        this.parking_title = parking_title;
    }

    public String getParking_owner_name() {
        return parking_owner_name;
    }

    public void setParking_owner_name(String parking_owner_name) {
        this.parking_owner_name = parking_owner_name;
    }

    public String getParking_avaibility() {
        return parking_avaibility;
    }

    public void setParking_avaibility(String parking_avaibility) {
        this.parking_avaibility = parking_avaibility;
    }

    public String getParking_id() {
        return parking_id;
    }

    public void setParking_id(String parking_id) {
        this.parking_id = parking_id;
    }

    public String getParking_description() {
        return parking_description;
    }

    public void setParking_description(String parking_description) {
        this.parking_description = parking_description;
    }

    public String getParking_price() {
        return parking_price;
    }

    public void setParking_price(String parking_price) {
        this.parking_price = parking_price;
    }

    public String getParking_owner_id() {
        return parking_owner_id;
    }

    public void setParking_owner_id(String parking_owner_id) {
        this.parking_owner_id = parking_owner_id;
    }

    public String getRemaining_parking_spots() {
        return remaining_parking_spots;
    }

    public void setRemaining_parking_spots(String remaining_parking_spots) {
        this.remaining_parking_spots = remaining_parking_spots;
    }

    public String getFilled_parking_spots() {
        return filled_parking_spots;
    }

    public void setFilled_parking_spots(String filled_parking_spots) {
        this.filled_parking_spots = filled_parking_spots;
    }

    public String getTypeofVehical() {
        return typeofVehical;
    }

    public void setTypeofVehical(String typeofVehical) {
        this.typeofVehical = typeofVehical;
    }
}
