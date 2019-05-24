package com.utalli.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout


import com.utalli.R
import com.utalli.adapter.HomeListGuideAdapter
import kotlinx.android.synthetic.main.fragment_near_me.*


class NearMeFragment : Fragment(){
    var homeListGuideAdapter: HomeListGuideAdapter? = null
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar_nearMe.title =""
         activity!!.setActionBar(toolbar_nearMe)
         collapsing_toolbar.title = "hello hello"

       // appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener())


        appbar.addOnOffsetChangedListener(object: AppBarLayout.OnOffsetChangedListener {
            internal var isShow = false
            internal var scrollRange = -1
            override fun onOffsetChanged(appBarLayout:AppBarLayout, verticalOffset:Int) {
                if (scrollRange == -1)
                {
                    scrollRange = appBarLayout.getTotalScrollRange()
                }
                if (scrollRange + verticalOffset == 0)
                {
                    profile_Pic_toolbar.visibility = View.VISIBLE
                    iv_notification_toolbar.visibility = View.VISIBLE
                    //toolbar_nearMe.title ="ebehbgrhgbrt"
                   // toolbar_nearMe.visibility = View.VISIBLE

                    isShow = true
                }
                else if (isShow)
                {
                    profile_Pic_toolbar.visibility = View.GONE
                    iv_notification_toolbar.visibility = View.GONE
                   // toolbar_nearMe.setTitle("")
                   // toolbar_nearMe.visibility = View.GONE

                    isShow = false
                }
            }
        })

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