package com.utalli.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.utalli.R
import com.utalli.adapter.TripDetailsStateListToVisitAdapter
import com.utalli.adapter.TripDetailsStateListAdapter
import com.utalli.callBack.StateNotToVisitCallBack
import com.utalli.callBack.TripDetailsStateListCallBack
import com.utalli.models.StateDetailsData
import kotlinx.android.synthetic.main.activity_trip_details.*
import java.util.*
import kotlin.collections.ArrayList


class TripDetailsActivity : AppCompatActivity(), View.OnClickListener, TripDetailsStateListCallBack {


    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var countryName: String? = null
    var stateList = ArrayList<StateDetailsData>()


    var remainingStateList = ArrayList<StateDetailsData>()
    var visibleStateList = ArrayList<StateDetailsData>()
    var itemAddedCount = 0


    var userSelectedStateList = ArrayList<StateDetailsData>()
    var tripDetailsStateListAdapter: TripDetailsStateListAdapter? = null
    var selectedStateAdapter: TripDetailsStateListToVisitAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)

        toolbar.title = ""
        toolbar.setNavigationIcon(R.drawable.arrow_back_white)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        countryName = intent.getStringExtra("countryName")

        tv_selected_country_name.text = countryName
       // tv_states_in_country.text = "States in" + countryName


        button_confirm.setOnClickListener(this)
        tv_date_of_arrival.setOnClickListener(this)
        tv_date_of_departure.setOnClickListener(this)
        tv_arrival_date_change.setOnClickListener(this)
        tv_departure_date_change.setOnClickListener(this)
        tv_change.setOnClickListener(this)
        tv_view_all.setOnClickListener(this)


        if (tv_date_of_arrival.text == null && tv_date_of_arrival.text.equals("DD/MM/YYYY")) {
            tv_arrival_date_change.visibility = View.GONE
        } else if (tv_date_of_arrival.text != null && !tv_date_of_arrival.text.equals("DD/MM/YYYY")) {
            tv_arrival_date_change.visibility = View.VISIBLE
        }

        if (tv_date_of_departure.text == null && tv_date_of_departure.text.equals("DD/MM/YYYY")) {
            tv_departure_date_change.visibility = View.GONE
        } else if (tv_date_of_departure.text != null && !tv_date_of_departure.text.equals("DD/MM/YYYY")) {
            tv_departure_date_change.visibility = View.VISIBLE
        }

        var data1 = StateDetailsData("1", "Western Australia", false)
        stateList.add(data1)

        var data2 = StateDetailsData("2", "Victoria", false)
        stateList.add(data2)

        var data3 = StateDetailsData("3", "UP", false)
        stateList.add(data3)

        var data4 = StateDetailsData("4", "Tasmania", false)
        stateList.add(data4)

        var data5 = StateDetailsData("5", "Queensland", false)
        stateList.add(data5)

        var data6 = StateDetailsData("6", "Abc", false)
        stateList.add(data6)

        var data7 = StateDetailsData("7", "New Delhi", false)
        stateList.add(data7)

        var data8 = StateDetailsData("9", "Goa", false)
        stateList.add(data8)

        var data9 = StateDetailsData("10", "Delhi", false)
        stateList.add(data9)

        var data10 = StateDetailsData("11", "Raipur", false)
        stateList.add(data10)

        var data11 = StateDetailsData("12", "Noida", false)
        stateList.add(data11)

        var data12 = StateDetailsData("13", "Gurgaon", false)
        stateList.add(data12)


        if (stateList.size > 5) {
            tv_view_all.visibility = View.VISIBLE
        } else {
            tv_view_all.visibility = View.GONE
        }

        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.SPACE_AROUND
        rv_states_list.layoutManager = layoutManager


        tripDetailsStateListAdapter = TripDetailsStateListAdapter(this, this, "TripDetailsActivity")
        rv_states_list.adapter = tripDetailsStateListAdapter


        if (stateList.size > 5) {
            visibleStateList = ArrayList(stateList.subList(0, 4))
            remainingStateList = ArrayList(stateList.subList(4, stateList.size))

        } else if (stateList.size < 5) {
            visibleStateList = stateList
            remainingStateList = ArrayList()
        }

        //   tripDetailsStateListAdapter?.setStateList(ArrayList<StateDetailsData>(stateList.subList(0, 4)), this)
        tripDetailsStateListAdapter?.setStateList(visibleStateList, this)



    }



    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_confirm -> {
                var intent = Intent(this@TripDetailsActivity, GuideListActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_change -> {
                var intent = Intent(this@TripDetailsActivity, SearchActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.iv_back_arrow -> {
                finish()
            }
            R.id.tv_date_of_arrival -> {

                val datePickerDialog = DatePickerDialog(
                    this, R.style.DialogTheme, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        tv_date_of_arrival.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)

                        if (tv_date_of_arrival.text == null && tv_date_of_arrival.text.equals("DD/MM/YYYY")) {
                            tv_arrival_date_change.visibility = View.GONE
                        } else if (tv_date_of_arrival.text != null && !tv_date_of_arrival.text.equals("DD/MM/YYYY")) {
                            tv_arrival_date_change.visibility = View.VISIBLE  //tv_arrival_date_change
                            button_confirm.setBackgroundResource(R.drawable.rounded_rect_blue)
                        }

                    }, year, month, day)

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)  // disable the previous date from the calendar
                datePickerDialog.show()

            }

            R.id.tv_date_of_departure -> {

                val datePickerDialog = DatePickerDialog(this, R.style.DialogTheme, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        tv_date_of_departure.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)

                        if (tv_date_of_departure.text == null && tv_date_of_departure.text.equals("DD/MM/YYYY")) {
                            tv_departure_date_change.visibility = View.GONE
                        } else if (tv_date_of_departure.text != null && !tv_date_of_departure.text.equals("DD/MM/YYYY")) {
                            tv_departure_date_change.visibility = View.VISIBLE
                            button_confirm.setBackgroundResource(R.drawable.rounded_rect_blue)
                        }
                    }, year, month, day)
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)
                datePickerDialog.show()
            }

            R.id.tv_departure_date_change -> {
                val datePickerDialog = DatePickerDialog(this, R.style.DialogTheme, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        tv_date_of_departure.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                    }, year, month, day)
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)
                datePickerDialog.show()
            }

            R.id.tv_arrival_date_change -> {
                val datePickerDialog = DatePickerDialog(this, R.style.DialogTheme, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        tv_date_of_arrival.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                    }, year, month, day)

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)
                datePickerDialog.show()
            }
            R.id.tv_view_all -> {
                var intent = Intent(this, ViewAllStateActivity::class.java)
                intent.putExtra("countryName", countryName)
               // remainingStateList.addAll(visibleStateList)
                intent.putExtra("stateDetailsList", remainingStateList)
                startActivityForResult(intent, 101)
            }

        }

    }


    override fun recyclerViewListClicked(itemDetails: StateDetailsData) {

        if (selectedStateAdapter == null) {
            userSelectedStateList.add(itemDetails)

            val layoutManager = FlexboxLayoutManager(this)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.justifyContent = JustifyContent.SPACE_AROUND
            rv_states_u_want_visit.layoutManager = layoutManager


            // data of deleted , state_you_want_to_visit and add items in stateList
            selectedStateAdapter = TripDetailsStateListToVisitAdapter(this, object : StateNotToVisitCallBack {
                override fun stateNotToVisitCallBack(itemDetails: StateDetailsData) {


                    if (visibleStateList.size < 4) {
                        visibleStateList.add(itemDetails)
                        tripDetailsStateListAdapter!!.notifyItemInserted(visibleStateList.size-1)
                    } else{
                        remainingStateList.add(itemDetails)
                    }

                    if(remainingStateList.size == 0){
                        tv_view_all.visibility = View.GONE
                    } else{
                        tv_view_all.visibility = View.VISIBLE
                    }

                    userSelectedStateList.remove(itemDetails)
                    selectedStateAdapter!!.notifyDataSetChanged()

                    Log.e("TAG","userSelectedStateList size === "+userSelectedStateList.size)
                    if(userSelectedStateList.size > 0){
                        view1.visibility = View.VISIBLE
                        tv_states_you_want_to_visit.visibility = View.VISIBLE
                        rv_states_u_want_visit.visibility = View.VISIBLE
                    } else{
                        view1.visibility = View.GONE
                        tv_states_you_want_to_visit.visibility = View.GONE
                        rv_states_u_want_visit.visibility = View.GONE
                    }

                    if(userSelectedStateList.size.equals(stateList.size)){
                        view2.visibility = View.GONE
                        tv_states_in_country.visibility = View.GONE
                        rv_states_list.visibility = View.GONE
                    }else {
                        view2.visibility = View.VISIBLE
                        tv_states_in_country.visibility = View.VISIBLE
                        rv_states_list.visibility = View.VISIBLE
                    }


                }
            })

            rv_states_u_want_visit.adapter = selectedStateAdapter
            selectedStateAdapter?.setSelectedStateList(userSelectedStateList, this)

            if(userSelectedStateList.size.equals(stateList.size)){
                view2.visibility = View.GONE
                tv_states_in_country.visibility = View.GONE
                rv_states_list.visibility = View.GONE
            }else {
                view2.visibility = View.VISIBLE
                tv_states_in_country.visibility = View.VISIBLE
                rv_states_list.visibility = View.VISIBLE
            }


        }
        else {
            userSelectedStateList.add(itemDetails)
            selectedStateAdapter!!.notifyItemInserted(userSelectedStateList.size - 1)

            if(userSelectedStateList.size.equals(stateList.size)){
                view2.visibility = View.GONE
                tv_states_in_country.visibility = View.GONE
                rv_states_list.visibility = View.GONE
            }else {
                view2.visibility = View.VISIBLE
                tv_states_in_country.visibility = View.VISIBLE
                rv_states_list.visibility = View.VISIBLE
            }


        }


        if(userSelectedStateList.size > 0){
            view1.visibility = View.VISIBLE
            tv_states_you_want_to_visit.visibility = View.VISIBLE
            rv_states_u_want_visit.visibility = View.VISIBLE
        } else{
            view1.visibility = View.GONE
            tv_states_you_want_to_visit.visibility = View.GONE
            rv_states_u_want_visit.visibility = View.GONE
        }




        if(remainingStateList.size == 0){
            tv_view_all.visibility = View.GONE
        } else{
            tv_view_all.visibility = View.VISIBLE
        }


        if (remainingStateList.size > 0) {
            visibleStateList.add(remainingStateList.get(0))
            remainingStateList.removeAt(0)
            tripDetailsStateListAdapter!!.notifyItemInserted(visibleStateList.size - 1)

        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // view all
        if (data != null && requestCode == 101) {

            var selecteStatesListNewViewAll = data.getSerializableExtra("selectedStates") as ArrayList<StateDetailsData>


            if (selectedStateAdapter == null) {
                userSelectedStateList.addAll(selecteStatesListNewViewAll)
                val layoutManager = FlexboxLayoutManager(this)
                layoutManager.flexDirection = FlexDirection.ROW
                layoutManager.justifyContent = JustifyContent.SPACE_AROUND
                rv_states_u_want_visit.layoutManager = layoutManager

                // data of deleted , state_you_want_to_visit and add items in stateList
                selectedStateAdapter = TripDetailsStateListToVisitAdapter(this, object : StateNotToVisitCallBack {
                    override fun stateNotToVisitCallBack(itemDetails: StateDetailsData) {

                    }
                })

                rv_states_u_want_visit.adapter = selectedStateAdapter
                selectedStateAdapter?.setSelectedStateList(userSelectedStateList, this)

            } else {
                userSelectedStateList.addAll(selecteStatesListNewViewAll)
                selectedStateAdapter!!.notifyItemInserted(userSelectedStateList.size - 1)

            }
        }
    }


}