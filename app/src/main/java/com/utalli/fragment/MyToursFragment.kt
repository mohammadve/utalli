package com.utalli.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.transitionseverywhere.Transition
import com.transitionseverywhere.TransitionManager
import com.utalli.R
import com.utalli.adapter.MyToursAdapter
import com.utalli.helpers.AppPreference
import kotlinx.android.synthetic.main.fragment_my_tour.*

class MyToursFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_my_tour, container, false)

        var tabs = view!!.findViewById<TabLayout>(R.id.activities_tabs)

        tabs.addTab(tabs.newTab().setText("Upcoming Tours"))
        tabs.addTab(tabs.newTab().setText("Recent Tours"))



        registerReceiver()

        return view
    }


    var isCardVisible=false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_my_tours.setHasFixedSize(true)
        rv_my_tours.layoutManager = LinearLayoutManager(activity)
        rv_my_tours.adapter = MyToursAdapter()
        et_location.text = AppPreference.getInstance(activity!!).getUserLastLocation()

        cl_top.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {

                isCardVisible=!isCardVisible

                if(isCardVisible)
                {
                    TransitionManager.beginDelayedTransition(cv_current_tour)

                    cl_bottom.visibility=View.VISIBLE

                }
                else
                {
                    //TransitionManager.beginDelayedTransition(cv_current_tour)
                    cl_bottom.visibility=View.GONE
                }





            }

        })



    }



    private fun registerReceiver() {

        val filter = IntentFilter()

        filter.addAction("LOCATION_UPDATED")
        LocalBroadcastManager.getInstance(activity!!).registerReceiver(mReciever, filter)



    }


    var mReciever = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {


            if (et_location != null)
                et_location!!.text = AppPreference.getInstance(activity!!).getUserLastLocation()



        }
    }


}