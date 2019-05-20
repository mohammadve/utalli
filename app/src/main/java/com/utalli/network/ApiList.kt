package com.utalli.network

class ApiList {
    companion object {

        val BASE_URL = "http://ec2-34-217-28-142.us-west-2.compute.amazonaws.com:8000/"
        const val SIGNUP_URL = "user/user_register"
        const val VERIFY_OTP_URL = "user/verify_signup"
        const val LOGIN_URL = "user/signin"
        const val FORGOT_PASS_URL = "user/forgot_password"
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


        var ABOUT_US_URL = BASE_URL + "user/about"
        var TERMS_CONDITION_URL = BASE_URL + "user/terms-condition"


    }
}