package com.utalli.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.utalli.helpers.Utils
import com.utalli.network.ApiClient
import com.utalli.network.ApiService
import retrofit2.Call
import retrofit2.Response

class NearMeViewModel : ViewModel() {

    var nearestGuidesResult: MutableLiveData<JsonObject>? = null

    fun getNearbyGuides(mContext: Context, authToken: String, latitude: String, longitude: String, countryCode: String): MutableLiveData<JsonObject> {
        nearestGuidesResult = MutableLiveData()
        var apiService = ApiClient.getClient().create(ApiService::class.java)
        var call = apiService.getNearByGuid(authToken, latitude, longitude, countryCode)
        Utils.showProgressDialog(mContext)
        call.enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Utils.hideProgressDialog()
                Utils.showLog(t.message!!)
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                Utils.hideProgressDialog()
                nearestGuidesResult!!.value = response.body()
            }
        })
        return nearestGuidesResult!!
    }
}