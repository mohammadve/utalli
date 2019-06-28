package com.utalli.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.transitionseverywhere.TransitionManager
import com.utalli.R
import com.utalli.adapter.MyToursViewPager
import com.utalli.helpers.AppPreference
import kotlinx.android.synthetic.main.fragment_my_tour.*

class MyToursFragment : Fragment(){

    var tabs : TabLayout ?=null
    var viewPager : ViewPager?= null
    var myToursViewPager : MyToursViewPager ?= null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_my_tour, container, false)

        tabs = view!!.findViewById<TabLayout>(R.id.activities_tabs)
        viewPager = view!!.findViewById<ViewPager>(R.id.view_pager)


        tabs!!.addTab(tabs!!.newTab().setText("Upcoming Tours"))
        tabs!!.addTab(tabs!!.newTab().setText("Recent Tours"))
        tabs!!.setTabTextColors(Color.parseColor("#80ffffff"), resources.getColor(R.color.colorWhite))
        tabs!!.tabGravity=TabLayout.GRAVITY_FILL

     //  myToursViewPager = fragmentManager?.let { MyToursViewPager(it, tabs!!.getTabCount()) }

        myToursViewPager = MyToursViewPager(fragmentManager!!,tabs!!.tabCount)

        viewPager!!.adapter = myToursViewPager
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        tabs!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager!!.currentItem = tab!!.position
            }


        })


        registerReceiver()

        return view
    }


    var isCardVisible=false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)




   /*     viewPager!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
               tabs.setS
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

            }


        })*/

     /*   rv_my_tours.setHasFixedSize(true)
        rv_my_tours.layoutManager = LinearLayoutManager(activity)
        rv_my_tours.adapter = UpcomingTourAdapter()*/
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