package com.utalli.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.utalli.R
import com.utalli.adapter.GuideListAdapter
import kotlinx.android.synthetic.main.activity_guide_list_new.*
import kotlinx.android.synthetic.main.activity_guide_list_new.spacer

import kotlinx.android.synthetic.main.activity_guide_lists.cl_pop_up
import kotlinx.android.synthetic.main.activity_guide_lists.iv_back_arrow
import kotlinx.android.synthetic.main.activity_guide_lists.iv_three_dots
import kotlinx.android.synthetic.main.activity_guide_lists.rv_guide_list
import kotlinx.android.synthetic.main.activity_guide_lists.tv_cancel
import kotlinx.android.synthetic.main.activity_guide_lists.tv_edit_trip



class GuideListActivity : AppCompatActivity(), View.OnClickListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_list_new)

        initViews()

    }

    private fun initViews() {

        iv_three_dots.setOnClickListener(this)
        iv_back_arrow.setOnClickListener(this)
        tv_edit_trip.setOnClickListener(this)
        tv_cancel.setOnClickListener(this)
        toolbar_back_arroww.setOnClickListener(this)



        appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {

                if (verticalOffset == 0) {
                    spacer.visibility = View.VISIBLE
                    cl_countryDateDetails.visibility = View.VISIBLE
                    cl_countryDetails.visibility = View.GONE
                }
                else if (Math.abs(verticalOffset) == appBarLayout!!.getTotalScrollRange()) {
                    spacer.visibility = View.GONE
                    cl_countryDateDetails.visibility = View.GONE
                    cl_countryDetails.visibility = View.VISIBLE
                }
            }
        })



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
            R.id.toolbar_back_arroww ->{
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