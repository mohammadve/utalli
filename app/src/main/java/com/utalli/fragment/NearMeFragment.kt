package com.utalli.fragment

import android.content.Intent
import android.os.Bundle
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.transitionseverywhere.TransitionManager


import com.utalli.R
import com.utalli.activity.MyProfileActivity
import com.utalli.activity.NotificationActivity
import com.utalli.adapter.HomeListGuideAdapter
import com.utalli.helpers.Utils
import kotlinx.android.synthetic.main.fragment_near_me.*


class NearMeFragment : Fragment(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view?.id) {

            R.id.iv_notification -> startActivity(Intent(activity, NotificationActivity::class.java))
            R.id.iv_notification_toolbar -> startActivity(Intent(activity, NotificationActivity::class.java))
            R.id.profile_Pic -> startActivity(Intent(activity, MyProfileActivity::class.java))
            R.id.profile_Pic_toolbar -> startActivity(Intent(activity, MyProfileActivity::class.java))
        }
    }

    var homeListGuideAdapter: HomeListGuideAdapter? = null
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar_nearMe.title = ""
        activity!!.setActionBar(toolbar_nearMe)
        collapsing_toolbar.title = "hello hello"

        // appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener())


        /*     appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
                 internal var isShow = false
                 internal var scrollRange = -1
                 override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                     if (scrollRange == -1) {
                         scrollRange = appBarLayout.getTotalScrollRange()
                     }
                     if (scrollRange + verticalOffset == 0) {
                         if (profile_Pic_toolbar.visibility == View.GONE) {
                             profile_Pic_toolbar.visibility = View.VISIBLE
                             iv_notification_toolbar.visibility = View.VISIBLE
                             spacer.visibility = View.GONE
                         }
                         //toolbar_nearMe.title ="ebehbgrhgbrt"
                         // toolbar_nearMe.visibility = View.VISIBLE

                         isShow = true
                     } else if (isShow) {

                         if (profile_Pic_toolbar.visibility == View.VISIBLE) {
                             profile_Pic_toolbar.visibility = View.GONE
                             iv_notification_toolbar.visibility = View.GONE
                             spacer.visibility = View.VISIBLE
                         }
                         // toolbar_nearMe.setTitle("")
                         // toolbar_nearMe.visibility = View.GONE

                         isShow = false
                     }
                 }
             })
        */

        appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {


                if (verticalOffset == 0) {


                    profile_Pic_toolbar.visibility = View.GONE
                    iv_notification_toolbar.visibility = View.GONE

                    spacer.visibility = View.VISIBLE


                } else if (Math.abs(verticalOffset) == appBarLayout!!.getTotalScrollRange()) {
                    profile_Pic_toolbar.visibility = View.VISIBLE
                    iv_notification_toolbar.visibility = View.VISIBLE

                    spacer.visibility = View.GONE
                }
            }
        })
        iv_notification.setOnClickListener(this)
        iv_notification_toolbar.setOnClickListener(this)
        profile_Pic.setOnClickListener(this)
        profile_Pic_toolbar.setOnClickListener(this)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_near_me, container, false)

        var recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = activity?.let { HomeListGuideAdapter(it) }

        return view
    }

}