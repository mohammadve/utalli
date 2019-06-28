package com.utalli.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import com.utalli.R
import com.utalli.adapter.RecentTourAdapter
import com.utalli.adapter.UpcomingTourAdapter
import com.utalli.helpers.Utils
import com.utalli.models.RecentTourListModel
import com.utalli.models.UpcomingTourListModel
import com.utalli.viewModels.MyToursViewModel
import kotlinx.android.synthetic.main.fragment_upcoming_tours.*

class RecentToursFragment : Fragment() {

    var myToursViewModel : MyToursViewModel?= null



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_my_tours.setHasFixedSize(true)
        rv_my_tours.layoutManager = LinearLayoutManager(activity!!)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_upcoming_tours, container, false)

        myToursViewModel = ViewModelProviders.of(activity!!).get(MyToursViewModel::class.java)

        getRecentToursData()

        return view
    }

    private fun getRecentToursData() {

        myToursViewModel!!.getRecentTours(activity!!,2).observe(this, Observer {

            if(it != null && it.has("status") && it.get("status").asString.equals("1")){

                if(it.has("data") && it.get("data") is JsonArray){

                    val type = object : TypeToken<List<RecentTourListModel>>() {}.type
                    var recentComingTourList = Gson().fromJson<List<RecentTourListModel>>(it.get("data"), type)

                    if(recentComingTourList != null && recentComingTourList.size >0){

                        rv_my_tours.adapter = RecentTourAdapter(activity!!, recentComingTourList)
                    }


                }

                else {
                    if (it.has("message"))
                        Utils.showToast(activity!!, it.get("message").asString)
                    else {
                        Utils.showToast(activity!!, getString(R.string.msg_common_error))
                    }
                }




            }






        })
    }





}