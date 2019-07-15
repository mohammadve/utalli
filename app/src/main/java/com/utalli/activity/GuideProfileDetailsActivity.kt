package com.utalli.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.utalli.R
import com.utalli.adapter.LanguageAdapter
import com.utalli.helpers.AppConstants
import com.utalli.helpers.AppPreference
import com.utalli.helpers.Utils
import com.utalli.models.GuideStateListModel
import com.utalli.viewModels.GuideProfileDetailsViewModel
import kotlinx.android.synthetic.main.activity_guide_profile_details.*
import java.text.DateFormat
import java.text.SimpleDateFormat


class GuideProfileDetailsActivity : AppCompatActivity(), View.OnClickListener {
    var languageAdapter: LanguageAdapter? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    var requestSendDialog: Dialog? = null
    var isComingFrom : Int =0
    var guideId : Int =0
    var userId : Int =0
    var tourStartDate = ""
    var tourEndDate = ""
    var selectedStatesId = ""
    var poolId: String = "0"
    var guideProfileDetailsViewModel : GuideProfileDetailsViewModel?= null
    var guideLocation: ArrayList<GuideStateListModel>? = null

    private val START_ACTIVITY_RESULT_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_profile_details)


        isComingFrom = intent.getIntExtra("IsComingFrom",0)
        guideId = intent.getIntExtra("guideId",0)
        userId = AppPreference.getInstance(this).getId()
        if (isComingFrom == AppConstants.GUIDE_PROFILE_SEE_FROM_HOME)
        {

        }
        else if (isComingFrom == AppConstants.GUIDE_PROFILE_SEE_FROM_PLACE_SEARCH)
        {
            tourStartDate = intent.getStringExtra("tourStartDate")
            tourEndDate = intent.getStringExtra("tourEndDate")
            selectedStatesId = intent.getStringExtra("selectedStatesId")

            var inputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            var outputFormat:DateFormat = SimpleDateFormat("yyyy-MM-dd")

            var inputStartDate = tourStartDate
            var startDate = inputFormat.parse(inputStartDate)
            tourStartDate = outputFormat.format(startDate)


            var inputEndDate = tourEndDate
            var endDate = inputFormat.parse(inputEndDate)
            tourEndDate = outputFormat.format(endDate)



            Log.e("TAG", "tourStartDate 11 === "+ tourStartDate)
            Log.e("TAG", "tourEndDate  11 === "+ tourEndDate)
            Log.e("TAG", "selectedStatesId  11 === "+ selectedStatesId)
        }
        initViews()
    }


    private fun initViews() {
        guideProfileDetailsViewModel = ViewModelProviders.of(this).get(GuideProfileDetailsViewModel::class.java)
        guideLocation = ArrayList<GuideStateListModel>()
        getGuideDetails()

        btn_chat.setOnClickListener(this)
        iv_backArrow.setOnClickListener(this)
        btn_hireHim.setOnClickListener(this)
        tv_requestPool.setOnClickListener(this)
        tv_cancelRequest.setOnClickListener(this)
    }


    private fun getGuideDetails() {

        Log.e("TAG", "GUIDE ID  guideDetails ==== " + guideId)

        guideProfileDetailsViewModel!!.guideDetails(this,guideId).observe(this, Observer {

            if(it != null && it.has("status") && it.get("status").asString.equals("1")){

                if(it.has("data")){

                    var dataObj = it.getAsJsonObject("data")

                    if(dataObj.has("name") && dataObj.get("name") != null){
                        val upperString = dataObj.get("name").asString.substring(0, 1).toUpperCase() + dataObj.get("name").asString.substring(1)
                        tv_name.setText(upperString)
                        tv_about_place.setText("About "+upperString)
                    }

                   if(dataObj.has("guiderating") && dataObj.get("guiderating") != null){
                        txt_ratingValue.setText("" + dataObj.get("guiderating").asInt)
                    }

                     if(dataObj.has("guide_private_price") && dataObj.get("guide_private_price") != null){
                        tv_guide_perDay_charges.setText("$" + dataObj.get("guide_private_price").asInt)
                    }

                     if(dataObj.has("guide_pool_price") && dataObj.get("guide_pool_price") != null){
                        tv_guide_pool_charges.setText("$" + dataObj.get("guide_pool_price").asInt)
                    }

                    if(dataObj.has("guide_about") && dataObj.get("guide_about") != null){
                        tv_description.setText(dataObj.get("guide_about").asString)
                    }

                    if(dataObj.has("lang") && dataObj.get("lang") != null){
                        list_languageKnown.setText(dataObj.get("lang").asString)
                    }

                    if (dataObj.has("guide_locations"))
                    {
                        var locationArray = dataObj.getAsJsonArray("guide_locations")


                        val listType = object : TypeToken<ArrayList<GuideStateListModel>>() {
                        }.type
                        val locationResponse: ArrayList<GuideStateListModel> = Gson().fromJson(locationArray, listType)
                        if (locationResponse != null && locationResponse.size > 0)
                        {
                            guideLocation!!.addAll(locationResponse)
                        }
                    }
                }
            }
        })
    }


    override fun onClick(v: View?) {
        //requeststatus: 1-panding, 2-accepted, 3-rejected
        //requesttype: 1-private,  2-pool

        when(v?.id){
            R.id.btn_chat ->{

            }

            R.id.tv_cancelRequest ->{
               // rejected - 3
                cancleAndAcceptReq(1)
            }

            R.id.iv_backArrow ->{
                finish()
            }

            R.id.tv_requestPool ->{
                sendTourRequestToGuide(2)
            }

            R.id.btn_hireHim ->{
                if (isComingFrom == AppConstants.GUIDE_PROFILE_SEE_FROM_HOME)
                {
                    var intent = Intent(this, TripDetailsActivity::class.java)
                    intent.putExtra("IsComingFrom", AppConstants.GUIDE_PROFILE_SEE_FROM_HOME)
                    intent.putParcelableArrayListExtra("GuideStateList", guideLocation)
                    startActivityForResult(intent, START_ACTIVITY_RESULT_CODE)
                }
                else
                {
                    sendTourRequestToGuide(1)
                }
            }
        }
    }

    private fun cancleAndAcceptReq(requestStatus: Int) {
        Log.e("TAG", "CANCLE RQ guideId === " + guideId)

        Log.e("TAG", "CANCLE RQ requestStatus === " + requestStatus)
        Log.e("TAG", "CANCLE RQ userId === " + userId)


        guideProfileDetailsViewModel!!.sendCancelRequestStatus(this, guideId , requestStatus, userId).observe(this, Observer {

             if(it!= null && it.has("status") && it.get("status").asString.equals("1")){

                 if(it.has("message")){
                     Utils.showToast(this, it.get("message").asString)
                 }

             } else{

                 if(it.has("message")){
                     Utils.showToast(this, it.get("message").asString)
                 } else{
                     Utils.showToast(this, getString(R.string.msg_common_error))
                 }
             }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode)
        {
            START_ACTIVITY_RESULT_CODE ->
            {
                when (resultCode) {
                    Activity.RESULT_OK -> receivedReturnedResult(data)
                }
            }
        }
    }

    private fun receivedReturnedResult(data: Intent?)
    {
        tourStartDate = data!!.getStringExtra("tourStartDate")
        tourEndDate = data!!.getStringExtra("tourEndDate")
        selectedStatesId = data!!.getStringExtra("selectedStatesId")

        var inputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        var outputFormat:DateFormat = SimpleDateFormat("yyyy-MM-dd")

        var inputStartDate = tourStartDate
        var startDate = inputFormat.parse(inputStartDate)
        tourStartDate = outputFormat.format(startDate)


        var inputEndDate = tourEndDate
        var endDate = inputFormat.parse(inputEndDate)
        tourEndDate = outputFormat.format(endDate)



        Log.e("TAG", "tourStartDate 11 === "+ tourStartDate)
        Log.e("TAG", "tourEndDate  11 === "+ tourEndDate)
        Log.e("TAG", "selectedStatesId  11 === "+ selectedStatesId)

        if (isComingFrom == AppConstants.GUIDE_PROFILE_SEE_FROM_HOME)
        {
            sendTourRequestToGuide(1)
        }
    }

    private fun sendTourRequestToGuide(requestType: Int) {
        // requeststatus:  1-cancel, 2-accepted
        // requesttype: 1-private,  2-pool

        Log.e("TAG", "  userId === " + userId)
        Log.e("TAG", " guideId === " + guideId)
        Log.e("TAG", "requestType === " + requestType)

        guideProfileDetailsViewModel!!.sendTourReqToGuide(this,guideId, requestType, userId, tourStartDate, tourEndDate,selectedStatesId,poolId).observe(this, Observer {

            if(it != null && it.has("status") && it.get("status").asString.equals("1")){

                onOpenDialog()

            }
        })
    }


    private fun onOpenDialog() {
        requestSendDialog = Dialog(this)
        requestSendDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        requestSendDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestSendDialog!!.setContentView(R.layout.dialog_request_sent_to_guide)
        requestSendDialog!!.show()
        requestSendDialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)


        var tvOKAY = requestSendDialog!!.findViewById<TextView>(R.id.tv_OKAY)

        tvOKAY.setOnClickListener {

            cl_requestStatus_cancle_pending_accept.visibility = View.VISIBLE
            cl_requesType_Pool_Private.visibility = View.GONE

            requestSendDialog!!.dismiss()
        }
    }
}