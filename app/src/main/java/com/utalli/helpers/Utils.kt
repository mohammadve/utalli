package com.utalli.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.utalli.BuildConfig
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class Utils {
    companion object {

        var progressDialog: ProgressDialog? = null


        fun convertTimeIntoDate(time: String): String {
            var date = Date(time.toLong())
            var sdf = SimpleDateFormat("dd MMM yyyy")

            return sdf.format(date)

        }


        fun showProgressDialog(mContext: Context) {
            if (progressDialog != null && progressDialog!!.isShowing)
                progressDialog!!.dismiss()


            progressDialog = ProgressDialog(mContext)
            progressDialog!!.setMessage("Please wait")
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()


        }

        fun hideProgressDialog() {
            if (progressDialog != null && progressDialog!!.isShowing)
                progressDialog!!.dismiss()

        }


        fun isInternetAvailable(mContext: Context?): Boolean {
            if (mContext != null) {
                val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                @SuppressLint("MissingPermission") val netInfo = cm.activeNetworkInfo
                return netInfo != null && netInfo.isConnectedOrConnecting
            } else
                return false
        }


        fun hideSoftKeyboard(activity: Context) {
            try {
                val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow((activity as Activity).currentFocus!!.windowToken, 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun isEmailValid(email: String): Boolean {
            var isValid = false

            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"

            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(email)
            if (matcher.matches()) {
                isValid = true
            }
            return isValid
        }


        fun showToast(mContext: Context, mesage: String) {
            Toast.makeText(mContext.applicationContext, mesage, Toast.LENGTH_LONG).show()

        }

        fun showLog(message: String) {
            if (BuildConfig.DEBUG)
                Log.e("Utalli", "Log message : " + message)

        }


    }


}