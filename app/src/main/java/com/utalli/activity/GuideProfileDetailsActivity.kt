package com.utalli.activity

import android.app.Dialog
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
import com.utalli.R
import com.utalli.adapter.LanguageAdapter
import com.utalli.helpers.AppPreference
import com.utalli.helpers.Utils
import com.utalli.viewModels.GuideProfileDetailsViewModel
import kotlinx.android.synthetic.main.activity_guide_profile_details.*

class GuideProfileDetailsActivity : AppCompatActivity(), View.OnClickListener {
    var languageAdapter: LanguageAdapter? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    var requestSendDialog: Dialog? = null
    var guideId : Int =0
    var userId : Int =0
    var guideProfileDetailsViewModel : GuideProfileDetailsViewModel?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_profile_details)


        guideId = intent.getIntExtra("guideId",0)
        userId = AppPreference.getInstance(this).getId()


        initViews()

    }


    private fun initViews() {
        guideProfileDetailsViewModel = ViewModelProviders.of(this).get(GuideProfileDetailsViewModel::class.java)

        getGuideDetails()

        btn_chat.setOnClickListener(this)
        iv_backArrow.setOnClickListener(this)
        btn_hireHim.setOnClickListener(this)
        tv_requestPool.setOnClickListener(this)
        tv_cancelRequest.setOnClickListener(this)


    }

    private fun getGuideDetails() {

        guideProfileDetailsViewModel!!.guideDetails(this,guideId).observe(this, Observer {

            if(it != null && it.has("status") && it.get("status").asString.equals("1")){

                if(it.has("data")){

                    var dataObj = it.getAsJsonObject("data")

                    if(dataObj.has("name") && dataObj.get("name") != null){
                        tv_name.setText(dataObj.get("name").asString)
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
                sendTourRequestToGuide(1)
            }

        }

    }

    private fun cancleAndAcceptReq(requestStatus: Int) {

        guideProfileDetailsViewModel!!.sendRequestStatus(this, guideId , requestStatus, userId).observe(this, Observer {

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





    private fun sendTourRequestToGuide(requestType: Int) {
        // requeststatus: 1-panding, 2-accepted, 3-rejected
        // requesttype: 1-private,  2-pool

        guideProfileDetailsViewModel!!.sendTourReqToGuide(this,guideId, requestType, userId).observe(this, Observer {

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