package com.utalli.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.utalli.helpers.AppPreference
import com.utalli.network.ApiClient
import com.utalli.network.ApiService

class AddPaymentCardViewModel : ViewModel(){

    private var addCardResult : MutableLiveData<JsonObject> ?= null
    var preference : AppPreference?= null


    fun addPaymentCard(mContext : Context) : MutableLiveData<JsonObject>{

        preference = AppPreference.getInstance(mContext)
        val token = preference!!.getAuthToken()

        addCardResult = MutableLiveData()

        var apiService = ApiClient.getClient().create(ApiService::class.java)




        return addCardResult!!

    }







}