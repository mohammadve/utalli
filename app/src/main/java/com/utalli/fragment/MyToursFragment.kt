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
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.transitionseverywhere.TransitionManager
import com.utalli.R
import com.utalli.adapter.MyToursViewPager
import com.utalli.adapter.UpcomingTourAdapter
import com.utalli.callBack.UpcomingTourCancelCallBack
import com.utalli.helpers.AppPreference
import com.utalli.helpers.Utils
import com.utalli.models.CurrentTourResponse
import com.utalli.models.Dashboard.NearByGuidResponse
import com.utalli.models.UpcomingTourListModel
import com.utalli.models.UserModel
import com.utalli.viewModels.MyToursViewModel
import kotlinx.android.synthetic.main.fragment_my_tour.*
import kotlinx.android.synthetic.main.fragment_my_tour.et_location
import kotlinx.android.synthetic.main.fragment_my_tour.profile_Pic
import kotlinx.android.synthetic.main.fragment_near_me.*
import kotlinx.android.synthetic.main.fragment_upcoming_tours.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MyToursFragment : Fragment(){

    var tabs : TabLayout ?=null
    var viewPager : ViewPager?= null
    var myToursViewPager : MyToursViewPager ?= null
    var user: UserModel? = null
    var myToursViewModel : MyToursViewModel?= null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_my_tour, container, false)

        myToursViewModel = ViewModelProviders.of(activity!!).get(MyToursViewModel::class.java)
        getCurrentTour()

        tabs = view!!.findViewById<TabLayout>(R.id.activities_tabs)
        viewPager = view!!.findViewById<ViewPager>(R.id.vp_tours)


        tabs!!.addTab(tabs!!.newTab().setText("Upcoming Tours"))
        tabs!!.addTab(tabs!!.newTab().setText("Recent Tours"))
        tabs!!.setTabTextColors(Color.parseColor("#80ffffff"), resources.getColor(R.color.colorWhite))
        tabs!!.tabGravity=TabLayout.GRAVITY_FILL

     //  myToursViewPager = fragmentManager?.let { MyToursViewPager(it, tabs!!.getTabCount()) }

        myToursViewPager = MyToursViewPager(fragmentManager!!,tabs!!.tabCount)

        viewPager!!.adapter = myToursViewPager
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        viewPager!!.offscreenPageLimit=1

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


    override fun onResume() {
        super.onResume()
        user = AppPreference.getInstance(activity!!).getUserData() as UserModel
        if (user != null) {
            Glide.with(this)
                .load(user!!.profile_img)
                .apply(RequestOptions().placeholder(R.drawable.dummy_icon).error(R.drawable.dummy_icon))
                .into(profile_Pic)
        }
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

    private fun getCurrentTour()
    {
        myToursViewModel!!.getCurrentTours(activity!!).observe(this, Observer {

            if(it != null && it.has("status") && it.get("status").asString.equals("1")) {

                if (it.has("data") && it.get("data") is JsonObject)
                {
                    var dataObject = it.getAsJsonObject("data")
                    var response: CurrentTourResponse = Gson().fromJson(dataObject, CurrentTourResponse::class.java)

                if (response != null && response.getId() != null)
                {
                    cl_current_tour.visibility = View.VISIBLE
                    cl_bottom.visibility = View.VISIBLE
                    var tourType = response.getTourtype()
                    var tourCharges = ""
                    if (tourType != 0)
                    {
                        if(tourType == 1)
                        {
                            tourCharges = "Private- \$" + response.getTourcost()
                        }
                        else if(tourType == 2)
                        {
                            tourCharges = "Pool- \$" + response.getTourcost()
                        }
                    }
                    tv_tour_charges.text = tourCharges
                    var tourStartDate = response.getTourStartdate()
                    var tourEndDate = response.getTourEnddate()
                    var daysDifference = Utils.getTourDaysCount(tourStartDate, tourEndDate)
                    var tourDaysText = ""
                    if (daysDifference != 0)
                    {
                        if (daysDifference > 2)
                        {
                            tourDaysText = ("Today, +" + (daysDifference)).toString()
                        }
                        else
                        {
                            tourDaysText = "Today"
                        }
                    }
                    tv_tour_dates.text = tourDaysText

                    var guideName = response.getGuideInfo()!!.getName()
                    tv_guide_status.text = guideName + " guiding you."
                }
                else
                {
                    cl_current_tour.visibility = View.GONE
                    cl_bottom.visibility = View.GONE
                }
                }
            }
        })
    }
}
