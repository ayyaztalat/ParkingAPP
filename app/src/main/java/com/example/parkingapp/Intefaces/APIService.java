package com.example.parkingapp.Intefaces;


import com.example.parkingapp.Models.AddImageParking;
import com.example.parkingapp.Models.AddParkingModel;
import com.example.parkingapp.Models.AddReservationModel;
import com.example.parkingapp.Models.AvailabilityTypeModel;
import com.example.parkingapp.Models.BookingModel;
import com.example.parkingapp.Models.BrainTreeToken;
import com.example.parkingapp.Models.CancelReservationModel;
import com.example.parkingapp.Models.DeleteParkingModel;
import com.example.parkingapp.Models.EditModel;
import com.example.parkingapp.Models.EditParkingModel;
import com.example.parkingapp.Models.FilterModel;
import com.example.parkingapp.Models.ForgotModel;
import com.example.parkingapp.Models.FreeParkingModel;
import com.example.parkingapp.Models.HistoryModel;
import com.example.parkingapp.Models.ImageParkingModel;
import com.example.parkingapp.Models.LoginModel;
import com.example.parkingapp.Models.ParkingModel;
import com.example.parkingapp.Models.ProfileModel;
import com.example.parkingapp.Models.ReservationModel;
import com.example.parkingapp.Models.SignupModel;
import com.example.parkingapp.Models.SupportModel;
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

public interface APIService {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginModel> login(@Field("email") String email,
                           @Field("password") String password,
                           @Field("device_id") String fcmID);

    @FormUrlEncoded
    @POST("signup.php")
    Call<SignupModel> signup(@Field("name") String name,
                             @Field("email") String email,
                             @Field("password") String password,
                             @Field("phone") String phone,
                             @Field("status_value") String status_value,
                             @Field("latitude") String latitude,
                             @Field("longitude") String longitude,
                             @Field("device_id") String device_id,
                             @Field("address") String address,
                             @Field("city") String citys,
                             @Field("state") String states,
                             @Field("company_name") String company_names,
                             @Field("tac_id") String tacID);

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

    @FormUrlEncoded
    @POST("view_all_reserveds.php")
    Call<ReservationModel> MyReservationCall(@Field("truck_owner_id")String truck_owner_id);

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
    Call<AddParkingModel> AddParking(@Field("parking_owner_id") String id,
                                     @Field("parking_latitude") String lat,
                                     @Field("parking_longitude") String lng,
                                     @Field("parking_name") String park_name,
                                     @Field("parking_time") String park_timing,
                                     @Field("parking_owner_name") String park_owner,
                                     @Field("parking_owner_number") String park_owner_number,
                                     @Field("parking_status") String park_status,
                                     @Field("parking_price") String park_price,
                                     @Field("parking_description") String park_description,
                                     @Field("type_of_vehicle") String type_of_vehicle);

    @Multipart
    @POST("add_parking_pic.php")
    Call<AddImageParking> AddParkingImage(@Part("parking_id") RequestBody parking_id,
                                          @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("view_parking_pics.php")
    Call<ImageParkingModel> getImage(@Field("parking_id") String parking_id);

    @FormUrlEncoded
    @POST("delete_parking.php")
    Call<DeleteParkingModel> Delete(@Field("parking_id") String parkingId);

    @FormUrlEncoded
    @POST("edit_parking.php")
    Call<EditParkingModel> editParking(@Field("parking_id") String parking_id,
                                       @Field("parking_name") String parking_name,
                                       @Field("parking_time") String parking_time,
                                       @Field("parking_owner_number") String parking_owner_number,
                                       @Field("parking_price") String parking_price,
                                       @Field("parking_description") String parking_description,
                                       @Field("type_of_vehicle") String type_of_vehicle);

    @FormUrlEncoded
    @POST("user_forgot_password_e.php")
    Call<ForgotModel> ForgotPassword(@Field("email") String emails);

    @FormUrlEncoded
    @POST("add_reserved_parking.php")
    Call<AddReservationModel> addReservation(@Field("truck_id") String truck_id,
                                             @Field("parking_id") String parking_id,
                                             @Field("truck_owner_name") String truck_owner_name,
                                             @Field("parking_owner_name") String parking_owner_name,
                                             @Field("truck_number") String truck_number,
                                             @Field("truck_color") String truck_color,
                                             @Field("estimated_time") String estimated_time,
                                             @Field("from_date") String from_date,
                                             @Field("to_date") String to_date,
                                             @Field("amount") String amount,
                                             @Field("truck_owner_id") String truck_owner_id,
                                             @Field("parking_owner_id") String parking_owner_id,
                                             @Field("remaining_parking_spots") String Remaining_parking_spots,
                                             @Field("filled_parking_spots") String Filled_parking_spots,
                                             @Field("reserved_parking_spots") String reserved_parking_spots
    );

    @FormUrlEncoded
    @POST("delete_reservation.php")
    Call<CancelReservationModel> cancelReservation(@Field("reserved_parking_id") String reserved_parking_id,
                                                   @Field("parking_id") String parking_id,
                                                   @Field("filled_parking_spots")String filled_parking_spots,
                                                   @Field("remaining_parking_spots")String remaining_parking_spots);

    @GET("generate_token.php")
    Call<BrainTreeToken> braintree();

    @FormUrlEncoded
    @POST("transaction_payment.php")
    Call<BookingModel> booking(@Field("truck_id") String truck_id,
                               @Field("parking_id") String parking_id,
                               @Field("truck_owner_name") String truck_owner_name,
                               @Field("parking_owner_name") String parking_owner_name,
                               @Field("truck_number") String truck_number,
                               @Field("truck_color") String truck_color,
                               @Field("estimated_time") String estimated_time,
                               @Field("from_date") String from_date,
                               @Field("to_date") String to_date,
                               @Field("payment_method_nonce") String payment_method_nonce,
                               @Field("amount") String amount,
                               @Field("customer_id") String customer_id,
                               @Field("truck_owner_id") String truck_owner_id,
                               @Field("total_price") String total_price,
                               @Field("booked_parking_spots")String booked_parking_spots
    );

    @FormUrlEncoded
    @POST("view_profile.php")
    Call<ProfileModel> profile(@Field("email") String email);

    @FormUrlEncoded
    @POST("comment_mail.php")
    Call<SupportModel> Support(@Field("user_email") String email, @Field("comment") String comment);

    @FormUrlEncoded
    @POST("view_all_booked_history.php")
    Call<HistoryModel> History(@Field("parking_owner_id")String parking_owner_id);


    @GET("view_all_free_parkings.php")
    Call<FreeParkingModel> FREE_PARKING_MODEL_CALL();

    @FormUrlEncoded
    @POST("filter_parking.php")
    Call<FilterModel> Filter(@Field("truck_stops") String truck,
                             @Field("weight_station") String weight,
                             @Field("parking_areas") String parking_area,
                             @Field("rest_areas") String rest_parkings);


    @FormUrlEncoded
    @POST("availability_type.php")
    Call<AvailabilityTypeModel> availableType(@Field("parking_id")String parking_id,
                                              @Field("availability_type")String availability_type);
}
