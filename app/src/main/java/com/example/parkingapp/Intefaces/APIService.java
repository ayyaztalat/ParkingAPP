package com.example.parkingapp.Intefaces;


import com.example.parkingapp.Models.AddImageParking;
import com.example.parkingapp.Models.AddParkingModel;
import com.example.parkingapp.Models.BrainTreeModel;
import com.example.parkingapp.Models.DeleteParkingModel;
import com.example.parkingapp.Models.EditModel;
import com.example.parkingapp.Models.EditParkingModel;
import com.example.parkingapp.Models.ImageParkingModel;
import com.example.parkingapp.Models.LoginModel;
import com.example.parkingapp.Models.ParkingModel;
import com.example.parkingapp.Models.ReservationModel;
import com.example.parkingapp.Models.SignupModel;
import com.example.parkingapp.Models.TruckModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginModel> login(@Field("email")String email,
                           @Field("password")String password,
                           @Field("device_id")String fcmID);

    @FormUrlEncoded
    @POST("signup.php")
        Call<SignupModel> signup(@Field("name")String name,
                             @Field("email")String email,
                             @Field("password")String password,
                             @Field("phone")String phone,
                             @Field("status_value")String status_value,
                             @Field("latitude")String latitude,
                             @Field("longitude")String longitude,
                             @Field("device_id")String device_id);
    @FormUrlEncoded
    @POST("edit_profile.php")
    Call<EditModel> EditProfile(@Field("id") String userId,
                                @Field("email") String email,
                                @Field("name") String name,
                                @Field("phone") String number,
                                @Field("password") String password,
                                @Field("device_id") String fcmToken,
                                @Field("status_value") String statusVaalue,
                                @Field("latitude") String latitude,
                                @Field("longitude") String longitude);
    @GET("view_all_reserved.php")
    Call<ReservationModel> MyReservationCall();

    @GET("view_all_parking.php")
    Call<ParkingModel> MyParkingCall();

    @Multipart
    @POST("add_truck.php")
    Call<TruckModel> modelTruck(@Part("truck_owner_id") RequestBody truck_owner_id,
                                @Part("truck_number") RequestBody track_number,
                                @Part("manifacturing_name") RequestBody manufacturing_name,
                                @Part("truck_color") RequestBody truck_color,
                                @Part MultipartBody.Part image,
                                @Part MultipartBody.Part reg_image);

    @FormUrlEncoded
    @POST("add_parking.php")
    Call<AddParkingModel> AddParking(@Field("parking_owner_id")String id,
                                     @Field("parking_latitude")String lat,
                                     @Field("parking_longitude")String lng,
                                     @Field("parking_name")String park_name,
                                     @Field("parking_time")String park_timing,
                                     @Field("parking_owner_name")String park_owner,
                                     @Field("parking_owner_number")String park_owner_number,
                                     @Field("parking_status")String park_status,
                                     @Field("parking_price")String park_price,
                                     @Field("parking_description")String park_description,
                                     @Field("type_of_vehicle")String type_of_vehicle);

    @Multipart
    @POST("add_parking_pic.php")
    Call<AddImageParking> AddParkingImage(@Part("parking_id")RequestBody parking_id,
                                          @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("view_parking_pics.php")
    Call<ImageParkingModel> getImage(@Field("parking_id")String parking_id);

    @FormUrlEncoded
    @POST("delete_parking.php")
    Call<DeleteParkingModel> Delete(@Field("parking_id") String parkingId);

    @FormUrlEncoded
    @POST("edit_parking.php")
    Call<EditParkingModel> editParking(@Field("parking_id")String parking_id,
                                       @Field("parking_name")String parking_name,
                                       @Field("parking_time")String parking_time,
                                       @Field("parking_owner_number")String parking_owner_number,
                                       @Field("parking_price")String parking_price,
                                       @Field("parking_description")String parking_description,
                                       @Field("type_of_vehicle")String type_of_vehicle);

    @FormUrlEncoded
    @POST("")
    Call<BrainTreeModel> BrainTree(@Field("nonce") String paymentNonce,@Field("email") String email,@Field("userid") String userId);




}