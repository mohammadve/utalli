package com.utalli.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.utalli.R
import com.utalli.adapter.GuideListAdapter
import com.utalli.adapter.NotificationAdapter
import kotlinx.android.synthetic.main.activity_guide_lists.*
import kotlinx.android.synthetic.main.activity_notification.*

class GuideListActivity : AppCompatActivity(), View.OnClickListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_lists)

        initViews()


    }

    private fun initViews() {

        iv_three_dots.setOnClickListener(this)
        iv_back_arrow.setOnClickListener(this)
        tv_edit_trip.setOnClickListener(this)
        tv_cancel.setOnClickListener(this)


        rv_guide_list.setHasFixedSize(true)
        rv_guide_list.layoutManager = LinearLayoutManager(this)
        rv_guide_list.adapter = GuideListAdapter(this)

    }


    override fun onClick(v: View?) {

        when(v?.id){
            R.id.iv_three_dots->{
                cl_pop_up.visibility = View.VISIBLE
            }
            R.id.iv_back_arrow ->{
                finish()
            }
            R.id.tv_edit_trip -> {
                cl_pop_up.visibility = View.GONE
            }
            R.id.tv_cancel ->{
                cl_pop_up.visibility = View.GONE
            }


        }

    }

}