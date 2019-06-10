package com.utalli.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.os.Bundle
import android.transition.Transition
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation


import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout


import com.utalli.activity.MyProfileActivity
import com.utalli.activity.NotificationActivity
import com.utalli.activity.SearchActivity
import com.utalli.adapter.HomeListGuideAdapter
import com.utalli.helpers.Utils
import kotlinx.android.synthetic.main.fragment_near_me.*

import android.view.animation.AnimationUtils.loadAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.transitionseverywhere.TransitionManager
import com.utalli.R
import android.location.Geocoder
import com.utalli.helpers.AppPreference
import java.lang.Exception
import java.util.*


class NearMeFragment : Fragment(), View.OnClickListener {

    private val PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.6f
    private val PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f
    private val ALPHA_ANIMATIONS_DURATION = 200

    private var mIsTheTitleVisible = false
    private var mIsTheTitleContainerVisible = true


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar_nearMe.title = ""
        activity!!.setActionBar(toolbar_nearMe)
        collapsing_toolbar.title = "hello hello"




        appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {

                Log.e("TAG", "vertical offsett === " + verticalOffset)


                var maxScroll = appBarLayout!!.getTotalScrollRange()
                var percentage = (Math.abs(verticalOffset)).toFloat() / (maxScroll).toFloat()

                Log.e("TAG", "vertical offsett maxScroll === " + maxScroll)
                Log.e("TAG", "vertical offsett percentage === " + percentage)


                if (verticalOffset == 0) {

                    TransitionManager.beginDelayedTransition(appBarLayout)
                    profile_Pic_toolbar.visibility = View.GONE
                    iv_notification_toolbar.visibility = View.GONE
                    cl_collapsingToolbar_items.visibility = View.VISIBLE

                    /*  val animFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                      profile_Pic_toolbar.startAnimation(animFadeIn)*/
                } else if (Math.abs(verticalOffset) == appBarLayout!!.getTotalScrollRange()) {

                    TransitionManager.beginDelayedTransition(appBarLayout)
                    cl_collapsingToolbar_items.visibility = View.GONE
                    profile_Pic_toolbar.visibility = View.VISIBLE
                    iv_notification_toolbar.visibility = View.VISIBLE
                }
            }
        })

        et_location.text = AppPreference.getInstance(activity!!).getUserLastLocation()
        iv_notification.setOnClickListener(this)
        iv_notification_toolbar.setOnClickListener(this)
        profile_Pic.setOnClickListener(this)
        profile_Pic_toolbar.setOnClickListener(this)
        tv_searchSecond_toolbar.setOnClickListener(this)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_near_me, container, false)

        var recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = activity?.let { HomeListGuideAdapter(it) }

        registerReceiver()

        return view
    }


    private fun registerReceiver() {

        val filter = IntentFilter()

        filter.addAction("LOCATION_UPDATED")
        LocalBroadcastManager.getInstance(activity!!).registerReceiver(mReciever, filter)



    }


    var mReciever = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
         /*   if (intent != null && intent!!.hasExtra("location")) {
                *//*var mLocation = intent.getParcelableExtra("location") as Location
                if (mLocation != null) {

                   *//**//* val geocoder = Geocoder(context, Locale.getDefault())
                    var result: String? = null

                    try {

                        var addressList = geocoder.getFromLocation(
                            mLocation.latitude, mLocation.longitude, 1
                        )

                        if (addressList != null && addressList.size > 0) {

                            var address = addressList.get(0)

                            result = address.locality + ", " + address.countryName


                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }*//**//*

                    if (et_location != null)
                        et_location!!.text = AppPreference.getInstance(activity!!).getUserLastLocation()


                }*//*


            }
*/

            if (et_location != null)
                et_location!!.text = AppPreference.getInstance(activity!!).getUserLastLocation()

          //  LocalBroadcastManager.getInstance(activity!!).unregisterReceiver(this)


        }
    }

    override fun onStop() {
        super.onStop()


    }

    override fun onDestroy() {
        super.onDestroy()

    }



    override fun onClick(view: View?) {
        when (view?.id) {

            R.id.iv_notification -> startActivity(Intent(activity, NotificationActivity::class.java))
            R.id.iv_notification_toolbar -> startActivity(Intent(activity, NotificationActivity::class.java))
            R.id.profile_Pic -> startActivity(Intent(activity, MyProfileActivity::class.java))
            R.id.profile_Pic_toolbar -> startActivity(Intent(activity, MyProfileActivity::class.java))
            R.id.tv_searchSecond_toolbar -> startActivity(Intent(activity, SearchActivity::class.java))
        }
    }


    fun startAlphaAnimation(v: View, duration: Int, visibility: Int) {
        val alphaAnimation = if (visibility == View.VISIBLE)
            AlphaAnimation(0f, 1f)
        else
            AlphaAnimation(1f, 0f)

        alphaAnimation.duration = duration.toLong()
        alphaAnimation.fillAfter = true
        v.startAnimation(alphaAnimation)
    }


}