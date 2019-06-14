package com.utalli.network

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {


    @POST(ApiList.SIGNUP_URL)
    @FormUrlEncoded
    fun signupUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("mobile_no") mobile_no: String,
        @Field("password") password: String,
        @Field("dob") dob:String,
        @Field("gender") gender:String,
        @Field("otp") otp:String
//        @Field("password") password: String
    ): Call<JsonObject>

    @POST(ApiList.VERIFY_OTP_URL)
    @FormUrlEncoded
    fun verifySignupUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("mobile_no") mobile_no: String,
        @Field("password") password: String,
        @Field("dob") dob: String,
        @Field("gender") gender:String,
        @Field("otp") otp: String

    ): Call<JsonObject>


    @POST(ApiList.LOGIN_URL)
    @FormUrlEncoded
    fun loginUser(
        @Field("mobile_no") mobile_no: String,
        @Field("password") password: String
    ): Call<JsonObject>


    @POST(ApiList.FORGOT_PASS_URL)
    @FormUrlEncoded
    fun forgotPassword(
        @Field("mobile_no") mobile_no: String
    ): Call<JsonObject>


    @POST(ApiList.RESET_PASS_URL)
    @FormUrlEncoded
    fun resetPassword(
        @Field("password") password:String,
        @Field("otp") otp:String,
        @Field("id") id:Int
    ):Call<JsonObject>


    @POST(ApiList.UPDATE_PASS_URL)
    @FormUrlEncoded
    fun updatePassword(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<JsonObject>


    @POST(ApiList.UPDATE_PROFILE_URL)
    @FormUrlEncoded
    fun updateProfile(
        @Header("x-auth") token: String, @FieldMap params: HashMap<String, String>
    ): Call<JsonObject>

    @Multipart
    @POST(ApiList.UPDATE_PROFILE_PIC_URL)
    fun updateProfilePic(
        @Header("x-auth") token: String,
        @Part("user_id") userId: RequestBody,
        @Part profilePic: MultipartBody.Part
    ): Call<JsonObject>


    @GET(ApiList.GET_CREDENTIALS_URL)
    fun getCredentials(): Call<JsonObject>


    @POST(ApiList.SENT_PATIENT_REFERAL_URL)
    @FormUrlEncoded
    fun submitPatientReferal(
        @Header("x-auth") token: String,
        @Field("patient_name") patient_name: String,
        @Field("facility_name") facility_name: String,
        @Field("room_number") room_number: String,
        @Field("reason") reason: String,
        @Field("user_id") user_id: String

    ): Call<JsonObject>


    @POST(ApiList.GET_PROVIDERS_URL)
    @FormUrlEncoded
    fun getProviderList(
        @Header("x-auth") token: String,
        @Field("provider_status") provider_status: String
    ): Call<JsonObject>

    @GET(ApiList.GET_VIDEOS_URL)
    fun getVideosList(
        @Header("x-auth") token: String
    ): Call<JsonObject>

    @POST(ApiList.INVITE_VIDEO_CALLING_URL)
    @FormUrlEncoded
    fun inviteUser(
        @Header("x-auth") token: String,
        @Field("callee_id") calleeId: String,
        @Field("alert_message") alert_message: String,
        @Field("title") title: String
    ): Call<JsonObject>

    @POST(ApiList.REJECT_VIDEO_CALLING_URL)
    @FormUrlEncoded
    fun rejectVideoCall(
        @Header("x-auth") token: String,
        @Field("callee_id") calleeId: String,
        @Field("alert_message") alert_message: String,
        @Field("title") title: String
    ): Call<JsonObject>

    @POST(ApiList.GET_NOTIFICATIONS_URL)
    @FormUrlEncoded
    fun getNotifications(
        @Header("x-auth") token: String,
        @Field("user_id") user_id: String

    ): Call<JsonObject>

    @POST(ApiList.SEND_NOTIFICATION_URL)
    @FormUrlEncoded
    fun sendNotification(
        @Field("token") token: String,
        @Field("title") title: String,
        @Field("message") message: String

    ): Call<JsonObject>


}