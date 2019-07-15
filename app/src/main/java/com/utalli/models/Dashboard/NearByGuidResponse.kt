package com.utalli.models.Dashboard

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import android.R.attr.data
import android.R.id.message



class NearByGuidResponse {
    @SerializedName("status")
    @Expose
    private var status: Int? = null
    @SerializedName("message")
    @Expose
    private var message: String? = null
    @SerializedName("populardestinations")
    @Expose
    private var populardestinations: List<PopulardestinationList>? = null
    @SerializedName("data")
    @Expose
    private var data: List<GuidListData>? = null

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getPopulardestinations(): List<PopulardestinationList>? {
        return populardestinations
    }

    fun setPopulardestinations(populardestinations: List<PopulardestinationList>) {
        this.populardestinations = populardestinations
    }

    fun getData(): List<GuidListData>? {
        return data
    }

    fun setData(data: List<GuidListData>) {
        this.data = data
    }
}