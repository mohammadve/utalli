package com.utalli.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.utalli.R
import com.utalli.adapter.TripDetailsStateListAdapter
import com.utalli.callBack.TripDetailsStateListCallBack
import com.utalli.models.StateDetailsData
import kotlinx.android.synthetic.main.activity_view_all_state.*


class ViewAllStateActivity : AppCompatActivity(), View.OnClickListener {
    var tripDetailsStateListAdapter: TripDetailsStateListAdapter? = null
    var allStateList = ArrayList<StateDetailsData>()
    var selectedStateList = ArrayList<StateDetailsData>()
    var countryName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_state)


        toolbarr.setNavigationIcon(R.drawable.arrow_back_white)
        setSupportActionBar(toolbarr)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbarr.setNavigationOnClickListener {
            finish()
        }

        countryName = intent.getStringExtra("countryName")
        allStateList = getIntent().getSerializableExtra("stateDetailsList") as ArrayList<StateDetailsData>

        Log.e("TAG", "ViewAllActivity allStateList send to viewAll == "+allStateList.size)
        toolbarr.title = ""
        toolbarr_title.setText(countryName)
      //  Log.e("TAG", "countryName view All === " + countryName)


        initViews()

    }

    private fun initViews() {
        btn_add.setOnClickListener(this)

        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.SPACE_AROUND
        recyclerView_allState.layoutManager = layoutManager

        tripDetailsStateListAdapter = TripDetailsStateListAdapter(this, object : TripDetailsStateListCallBack {
            override fun recyclerViewListClicked(itemDetails: StateDetailsData) {
                if (itemDetails.isSelected){
                    selectedStateList.add(itemDetails)
                }
                else{
                    selectedStateList.remove(itemDetails)
                }
            }

        }, "ViewAllStateActivity")
        recyclerView_allState.adapter = tripDetailsStateListAdapter
        tripDetailsStateListAdapter?.setStateList(allStateList, this)


    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add -> {
                var i = Intent()
                i.putExtra("selectedStates", selectedStateList)
                setResult(101, i)
                finish()
            }
        }


    }

}