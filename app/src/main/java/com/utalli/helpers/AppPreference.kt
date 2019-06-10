package com.utalli.helpers

import android.content.Context
import android.content.SharedPreferences

class AppPreference()
{
    var mContext: Context? = null
    var APP_PREFERENCE = "APP_PREFERENCE"
    var USER_DATA = "USER_DATA"
    var LAST_LOCATION = "LAST_LOCATION"

    var mPreference: SharedPreferences? = null


    constructor(mContext: Context) : this() {
        this.mContext = mContext
        mPreference = mContext.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)


    }

    companion object {
        var instance: AppPreference? = null


        fun getInstance(mContext: Context): AppPreference {

            if (instance == null)
                instance = AppPreference(mContext)

            return instance as AppPreference


        }


    }



    fun getUserLastLocation(): String {
        return getString(LAST_LOCATION)
    }


    fun setUserLastLocation(location: String) {
        setString(LAST_LOCATION, location)
    }



    fun setString(key: String, value: String) {
        val editor = mPreference!!.edit()
        editor.putString(key, value)
        editor.apply()


    }

    fun getString(key: String): String {
        return mPreference!!.getString(key, "")
    }


    fun setBoolean(key: String, value: Boolean) {
        val editor = mPreference!!.edit()
        editor.putBoolean(key, value)
        editor.apply()


    }

    fun getBoolean(key: String): Boolean {
        return mPreference!!.getBoolean(key, false)
    }


}