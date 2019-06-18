package com.utalli.network

class ApiList {
    companion object {

        val BASE_URL =  "http://172.16.200.38:8000/api/v1/"  // http://192.168.6.47:5000/api/v1/
        const val SIGNUP_URL = "users/register"
        const val VERIFY_OTP_URL = "users/register"
        const val LOGIN_URL = "users/login"
        const val FORGOT_PASS_URL = "users/forgetPassword"
        const val UPDATE_PASS_URL = "user/update_password"
        const val UPDATE_PROFILE_URL = "user/update_profile"
        const val UPDATE_PROFILE_PIC_URL = "user/update_profile_pic"
        const val GET_CREDENTIALS_URL = "user/credantials?search=all"
        const val SENT_PATIENT_REFERAL_URL = "patient/patient_refferel"
        const val GET_PROVIDERS_URL = "contact/contactprovider"
        const val GET_VIDEOS_URL = "education/get_videos"
        const val INVITE_VIDEO_CALLING_URL = "contact/invite_calling"
        const val REJECT_VIDEO_CALLING_URL = "contact/reject_calling"
        const val GET_NOTIFICATIONS_URL = "user/notifications"
        const val SEND_NOTIFICATION_URL = "contact/send_message"
        const val RESET_PASS_URL = "users/resetPassword"

        const val SEARCH_LOCATION = "deshboard/searchLocation"
        const val GET_STATE_BY_COUNTRY = "deshboard/getstateBycountry"
        const val GUIDE_SEARCH = "deshboard/guidsearch"


        var ABOUT_US_URL = BASE_URL + "user/about"
        var TERMS_CONDITION_URL = BASE_URL + "user/terms-condition"


    }
}