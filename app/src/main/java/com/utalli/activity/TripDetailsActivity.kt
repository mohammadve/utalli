package com.utalli.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.utalli.R
import com.utalli.adapter.TripDetailsStateListToVisitAdapter
import com.utalli.adapter.TripDetailsStateListAdapter
import com.utalli.callBack.StateNotToVisitCallBack
import com.utalli.callBack.TripDetailsStateListCallBack
import com.utalli.helpers.AppConstants
import com.utalli.helpers.AppPreference
import com.utalli.helpers.Utils
import com.utalli.models.GuideStateListModel
import com.utalli.models.IndividualStateDetail
import com.utalli.models.LocationSearchDataItems
import com.utalli.models.SubStateData
import com.utalli.viewModels.TripDetailsViewModel
import kotlinx.android.synthetic.main.activity_trip_details.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TripDetailsActivity : AppCompatActivity(), View.OnClickListener, TripDetailsStateListCallBack {


    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var countryName: String = ""
    var countryId: Int? = null
    var stateList = ArrayList<IndividualStateDetail>()


    var subStateDataList = ArrayList<SubStateData>()
    var individualStateDetail = ArrayList<IndividualStateDetail>()


    var remainingStateList = ArrayList<IndividualStateDetail>()
    var visibleStateList = ArrayList<IndividualStateDetail>()
    var itemAddedCount = 0


    var userSelectedStateList = ArrayList<IndividualStateDetail>()
    var tripDetailsStateListAdapter: TripDetailsStateListAdapter? = null
    var selectedStateAdapter: TripDetailsStateListToVisitAdapter? = null

    var arrivalDateStr: String? = null

    var ddArrivalDate: Date? = null
    var ddDepartureDate: Date? = null
    var departureDateStr: String? = null

    var getStateByCountryIdViewModel: TripDetailsViewModel? = null
    var selectedCountry: LocationSearchDataItems? = null

    var isComingFrom : Int =0
    var guideLocation: ArrayList<GuideStateListModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)

        toolbar.title = ""
        toolbar.setNavigationIcon(R.drawable.arrow_back_white)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
/*

        countryName = intent.getStringExtra("countryName")
        countryId = intent.getIntExtra("countryId", 0)
*/

        getStateByCountryIdViewModel = ViewModelProviders.of(this).get(TripDetailsViewModel::class.java)
        isComingFrom = intent.getIntExtra("IsComingFrom",0)
        if (isComingFrom == AppConstants.GUIDE_PROFILE_SEE_FROM_HOME)
        {
            var guideLocation: ArrayList<GuideStateListModel>  = intent.getParcelableArrayListExtra("GuideStateList")
            countryName = AppPreference.getInstance(this).getCountryName();
            tv_change.visibility = View.GONE
            setUpGuideStateListUI(guideLocation)
        }
        else if (isComingFrom == AppConstants.GUIDE_PROFILE_SEE_FROM_PLACE_SEARCH)
        {
            selectedCountry = intent.getParcelableExtra("selectedCountry")

            countryName = selectedCountry!!.name
            countryId = selectedCountry!!.id
            tv_change.visibility = View.VISIBLE
            getData(countryId!!)
        }

        initViews()
    }


    private fun initViews() {

        button_confirm.setOnClickListener(this)
        tv_date_of_arrival.setOnClickListener(this)
        tv_date_of_departure.setOnClickListener(this)
        tv_arrival_date_change.setOnClickListener(this)
        tv_departure_date_change.setOnClickListener(this)
        tv_change.setOnClickListener(this)
        tv_view_all.setOnClickListener(this)


        if (tv_date_of_arrival.text.toString().equals("")) {
            tv_arrival_date_change.visibility = View.GONE
        } else {
            tv_arrival_date_change.visibility = View.VISIBLE
        }

        if (tv_date_of_departure.text.toString().equals("")) {
            tv_departure_date_change.visibility = View.GONE
        } else {
            tv_departure_date_change.visibility = View.VISIBLE
        }
    }

    private fun setUpGuideStateListUI(guideLocation: ArrayList<GuideStateListModel>)
    {
        if (guideLocation != null && guideLocation.size > 0)
        {
            for (item in guideLocation) {
                var individualStateList = IndividualStateDetail(item.getStateId()!!, item.getStatename()!!,
                    item.getCountryId()!!, false)
                individualStateDetail.add(individualStateList)
            }

            if (individualStateDetail != null && individualStateDetail.size > 0)
            {
                setupAdapter()
            }
        }
        else
        {
            Utils.showToast(this, "Guide State list not available")
        }
    }


    private fun getData(countryId: Int) {

        getStateByCountryIdViewModel!!.getStateByCountry(this, countryId).observe(this, androidx.lifecycle.Observer {

            if (it != null && it.has("status") && it.get("status").asString.equals("1")) {

                if (it.has("data")) {

                    //  var dataArr=it.getAsJsonArray("data")

                    //var dataObj=dataArr.get(0) as JsonObject
                    var dataObj = it.getAsJsonObject("data")

                    if (dataObj.has("states")) {

                        var stateDataArryList = object : TypeToken<ArrayList<IndividualStateDetail>>() {}.type
                        individualStateDetail.addAll(
                            Gson().fromJson<ArrayList<IndividualStateDetail>>(
                                dataObj.get("states").toString(),
                                stateDataArryList
                            )
                        )
                        Log.e("TAG", "individualStateDetail.size() ====  " + individualStateDetail.size)
                    }
                    setupAdapter()
                }

            } else {
                Utils.showToast(this, getString(R.string.msg_common_error))
            }
        })
    }

    private fun setupAdapter()
    {
        tv_selected_country_name.text = countryName

        if (individualStateDetail.size > 5) {
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

        if (individualStateDetail.size > 5) {
            visibleStateList = ArrayList(individualStateDetail.subList(0, 4))
            remainingStateList = ArrayList(individualStateDetail.subList(4, individualStateDetail.size))

        } else if (individualStateDetail.size < 5) {
            visibleStateList = individualStateDetail
            remainingStateList = ArrayList()
        }

        tripDetailsStateListAdapter?.setStateList(visibleStateList, this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_confirm -> {
                if (tv_date_of_arrival.text.toString().equals("")) {
                    Utils.showToast(this, "Please choose arrival date")
                } else if (tv_date_of_departure.text.toString().equals("")) {
                    Utils.showToast(this, "Please choose departure date")
                } else if (ddArrivalDate!!.compareTo(ddDepartureDate) > 0) {
                    Utils.showToast(this, "Please choose arrival date smaller or equal to departure date")
                }
                else if (userSelectedStateList.size == 0)
                {
                    Utils.showToast(this, "Please select state")
                }
                else {
                    if(isComingFrom == AppConstants.GUIDE_PROFILE_SEE_FROM_HOME)
                    {
                        var selectedStatesId = ""
                        if (userSelectedStateList != null && userSelectedStateList.size > 0) {

                            for (i in 0..userSelectedStateList.size - 1) {

                                if (i == 0){
                                    selectedStatesId = userSelectedStateList.get(i).id.toString()
                                }

                                else if (i <= userSelectedStateList.size - 1){
                                    selectedStatesId = selectedStatesId + "," + userSelectedStateList.get(i).id.toString()
                                }
                            }
                        }

                        var returnIntent = Intent()
                        returnIntent.putExtra("selectedStatesId", selectedStatesId)
                        returnIntent.putExtra("tourStartDate", tv_date_of_arrival.text.toString())
                        returnIntent.putExtra("tourEndDate", tv_date_of_departure.text.toString())
                        setResult(Activity.RESULT_OK, returnIntent)
                        finish()
                    }
                    else if(isComingFrom == AppConstants.GUIDE_PROFILE_SEE_FROM_PLACE_SEARCH)
                    {
                        var intent = Intent(this@TripDetailsActivity, GuideListActivity::class.java)

                        intent.putExtra("selectedCountry", selectedCountry)
                        intent.putExtra("selectedStates", userSelectedStateList)
                        intent.putExtra("tourStartDate", tv_date_of_arrival.text.toString())
                        intent.putExtra("tourEndDate", tv_date_of_departure.text.toString())
                        startActivity(intent)
                    }
                }
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
                    this,
                    R.style.DialogTheme,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        tv_date_of_arrival.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)

                        arrivalDateStr = ("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                        var sdf = SimpleDateFormat("dd/MM/yyyy")
                        ddArrivalDate = sdf.parse(arrivalDateStr)

                        if (tv_date_of_arrival.text.toString().equals("")) {
                            tv_arrival_date_change.visibility = View.GONE
                        } else {
                            tv_arrival_date_change.visibility = View.VISIBLE  //tv_arrival_date_change
                            button_confirm.setBackgroundResource(R.drawable.rounded_rect_blue)
                        }

                    },
                    year,
                    month,
                    day
                )

                datePickerDialog.getDatePicker()
                    .setMinDate(System.currentTimeMillis() - 1000)  // disable the previous date from the calendar
                datePickerDialog.show()

            }

            R.id.tv_date_of_departure -> {

                if (tv_date_of_arrival.text.toString().equals("")) {
                    Toast.makeText(this, "Please choose arrival date first", Toast.LENGTH_SHORT).show()
                } else {

                    val datePickerDialog = DatePickerDialog(
                        this,
                        R.style.DialogTheme,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                            tv_date_of_departure.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                            departureDateStr = ("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                            var sdf = SimpleDateFormat("dd/MM/yyyy")
                            ddDepartureDate = sdf.parse(departureDateStr)


                            if (tv_date_of_departure.text.toString().equals("")) {
                                tv_departure_date_change.visibility = View.GONE
                            } else {
                                tv_departure_date_change.visibility = View.VISIBLE
                                button_confirm.setBackgroundResource(R.drawable.rounded_rect_blue)
                            }
                        },
                        year,
                        month,
                        day
                    )
                    datePickerDialog.getDatePicker().setMinDate(ddArrivalDate!!.time)
                    datePickerDialog.show()

                }


            }

            R.id.tv_departure_date_change -> {

                if (tv_date_of_arrival.text.toString().equals("")) {
                    Toast.makeText(this, "Please choose arrival date first", Toast.LENGTH_SHORT).show()
                } else {

                    val datePickerDialog = DatePickerDialog(
                        this,
                        R.style.DialogTheme,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                            tv_date_of_departure.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                            departureDateStr = ("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                            var sdf = SimpleDateFormat("dd/MM/yyyy")
                            ddDepartureDate = sdf.parse(departureDateStr)

                            Log.e("Tag", "deptDate === " + arrivalDateStr)
                        },
                        year,
                        month,
                        day
                    )

                    datePickerDialog.getDatePicker().setMinDate(ddArrivalDate!!.time)
                    datePickerDialog.show()

                }

            }

            R.id.tv_arrival_date_change -> {
                val datePickerDialog = DatePickerDialog(
                    this,
                    R.style.DialogTheme,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        tv_date_of_arrival.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                        arrivalDateStr = ("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)

                        var sdf = SimpleDateFormat("dd/MM/yyyy")
                        ddArrivalDate = sdf.parse(arrivalDateStr)

                    },
                    year,
                    month,
                    day
                )

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)
                datePickerDialog.show()
            }
            R.id.tv_view_all -> {
                var intent = Intent(this, ViewAllStateActivity::class.java)
                intent.putExtra("countryName", countryName)
                // remainingStateList.addAll(visibleStateList)
                intent.putExtra("stateDetailsList", remainingStateList)
                Log.e("TAG", "TripDetailsActivity remainingStateList send to viewAll == " + remainingStateList.size)
                startActivityForResult(intent, 101)
            }

        }

    }


    override fun recyclerViewListClicked(itemDetails: IndividualStateDetail) {

        if (selectedStateAdapter == null) {
            userSelectedStateList.add(itemDetails)

            val layoutManager = FlexboxLayoutManager(this)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.justifyContent = JustifyContent.SPACE_AROUND
            rv_states_u_want_visit.layoutManager = layoutManager


            // data of deleted , state_you_want_to_visit and add items in stateList
            selectedStateAdapter = TripDetailsStateListToVisitAdapter(this, object : StateNotToVisitCallBack {
                override fun stateNotToVisitCallBack(itemDetails: IndividualStateDetail) {

                    if (visibleStateList.size < 4) {
                        visibleStateList.add(itemDetails)
                        tripDetailsStateListAdapter!!.notifyItemInserted(visibleStateList.size - 1)
                    } else {
                        remainingStateList.add(itemDetails)
                    }

                    if (remainingStateList.size == 0) {
                        tv_view_all.visibility = View.GONE
                    } else {
                        tv_view_all.visibility = View.VISIBLE
                    }

                    userSelectedStateList.remove(itemDetails)
                    selectedStateAdapter!!.notifyDataSetChanged()

                    Log.e("TAG", "userSelectedStateList size === " + userSelectedStateList.size)
                    if (userSelectedStateList.size > 0) {
                        view1.visibility = View.VISIBLE
                        tv_states_you_want_to_visit.visibility = View.VISIBLE
                        rv_states_u_want_visit.visibility = View.VISIBLE
                    } else {
                        view1.visibility = View.GONE
                        tv_states_you_want_to_visit.visibility = View.GONE
                        rv_states_u_want_visit.visibility = View.GONE
                    }

                    if (userSelectedStateList.size.equals(individualStateDetail.size)) {
                        view2.visibility = View.GONE
                        tv_states_in_country.visibility = View.GONE
                        rv_states_list.visibility = View.GONE
                    } else {
                        view2.visibility = View.VISIBLE
                        tv_states_in_country.visibility = View.VISIBLE
                        rv_states_list.visibility = View.VISIBLE
                    }


                }
            })

            rv_states_u_want_visit.adapter = selectedStateAdapter
            selectedStateAdapter?.setSelectedStateList(userSelectedStateList, this)

            if (userSelectedStateList.size.equals(individualStateDetail.size)) {
                view2.visibility = View.GONE
                tv_states_in_country.visibility = View.GONE
                rv_states_list.visibility = View.GONE
            } else {
                view2.visibility = View.VISIBLE
                tv_states_in_country.visibility = View.VISIBLE
                rv_states_list.visibility = View.VISIBLE
            }


        } else {
            userSelectedStateList.add(itemDetails)
            selectedStateAdapter!!.notifyItemInserted(userSelectedStateList.size - 1)

            if (userSelectedStateList.size.equals(individualStateDetail.size)) {
                view2.visibility = View.GONE
                tv_states_in_country.visibility = View.GONE
                rv_states_list.visibility = View.GONE
            } else {
                view2.visibility = View.VISIBLE
                tv_states_in_country.visibility = View.VISIBLE
                rv_states_list.visibility = View.VISIBLE
            }

        }


        if (userSelectedStateList.size > 0) {
            view1.visibility = View.VISIBLE
            tv_states_you_want_to_visit.visibility = View.VISIBLE
            rv_states_u_want_visit.visibility = View.VISIBLE
        } else {
            view1.visibility = View.GONE
            tv_states_you_want_to_visit.visibility = View.GONE
            rv_states_u_want_visit.visibility = View.GONE
        }




        if (remainingStateList.size == 0) {
            tv_view_all.visibility = View.GONE
        } else {
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

            var selecteStatesListNewViewAll =
                data.getSerializableExtra("selectedStates") as ArrayList<IndividualStateDetail>


            if (selectedStateAdapter == null) {
                userSelectedStateList.addAll(selecteStatesListNewViewAll)
                val layoutManager = FlexboxLayoutManager(this)
                layoutManager.flexDirection = FlexDirection.ROW
                layoutManager.justifyContent = JustifyContent.SPACE_AROUND
                rv_states_u_want_visit.layoutManager = layoutManager

                // data of deleted , state_you_want_to_visit and add items in stateList
                selectedStateAdapter = TripDetailsStateListToVisitAdapter(this, object : StateNotToVisitCallBack {
                    override fun stateNotToVisitCallBack(itemDetails: IndividualStateDetail) {

                        ////////////////////////////////////
                        userSelectedStateList.remove(itemDetails)
                        selectedStateAdapter!!.notifyDataSetChanged()
                        //////////////////////////////////////////////

                        if (userSelectedStateList.size > 0) {
                            view1.visibility = View.VISIBLE
                            tv_states_you_want_to_visit.visibility = View.VISIBLE
                            rv_states_u_want_visit.visibility = View.VISIBLE
                        } else {
                            view1.visibility = View.GONE
                            tv_states_you_want_to_visit.visibility = View.GONE
                            rv_states_u_want_visit.visibility = View.GONE
                        }

                    }
                })

                rv_states_u_want_visit.adapter = selectedStateAdapter
                selectedStateAdapter?.setSelectedStateList(userSelectedStateList, this)

            } else {
                userSelectedStateList.addAll(selecteStatesListNewViewAll)
                selectedStateAdapter!!.notifyItemInserted(userSelectedStateList.size - 1)


            }

            if (userSelectedStateList.size > 0) {
                view1.visibility = View.VISIBLE
                tv_states_you_want_to_visit.visibility = View.VISIBLE
                rv_states_u_want_visit.visibility = View.VISIBLE
            } else {
                view1.visibility = View.GONE
                tv_states_you_want_to_visit.visibility = View.GONE
                rv_states_u_want_visit.visibility = View.GONE
            }

        }
    }


}