package com.utalli.activity

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import com.utalli.viewModels.GuideProfileDetailsViewModel
import kotlinx.android.synthetic.main.activity_guide_profile_details.*

class GuideProfileDetailsActivity : AppCompatActivity(), View.OnClickListener {
    var languageAdapter: LanguageAdapter? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    var requestSendDialog: Dialog? = null
    var statusType : String ?= null
    var guideProfileDetailsViewModel : GuideProfileDetailsViewModel?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_profile_details)

        initViews()

    }


    private fun initViews() {
        guideProfileDetailsViewModel = ViewModelProviders.of(this).get(GuideProfileDetailsViewModel::class.java)

        getGuideDetails()

        btn_requested.setOnClickListener(this)
        iv_backArrow.setOnClickListener(this)


    }

    private fun getGuideDetails() {

        guideProfileDetailsViewModel!!.guideDetails(this,2).observe(this, Observer {

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

                }

            }

        })


    }


    override fun onClick(v: View?) {

        when(v?.id){
            R.id.btn_requested ->{
              onOpenDialog()
            }
            R.id.iv_backArrow ->{
                finish()
            }

        }

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
            requestSendDialog!!.dismiss()

            statusType = "Requested"

            cl_guideStatus_Cancel_Requested.visibility = View.GONE
            cl_guideStatus_requestPool_hireHim.visibility = View.VISIBLE


        }

      /*  if(statusType.equals("Requested")){
            cl_guideStatus_Cancel_Requested.visibility = View.GONE
            cl_guideStatus_requestPool_hireHim.visibility = View.VISIBLE
        }
        else {
            cl_guideStatus_Cancel_Requested.visibility = View.VISIBLE
            cl_guideStatus_requestPool_hireHim.visibility = View.GONE
        }*/

    }


}