package com.utalli.activity

import android.app.Dialog
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.utalli.R
import com.utalli.adapter.LanguageAdapter
import kotlinx.android.synthetic.main.activity_guide_profile_details.*

class GuideProfileDetailsActivity : AppCompatActivity(), View.OnClickListener {
    var languageAdapter: LanguageAdapter? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    var requestSendDialog: Dialog? = null
    var statusType : String ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_profile_details)

        initViews()

    }


    private fun initViews() {

        btn_requested.setOnClickListener(this)
        iv_backArrow.setOnClickListener(this)

      //  list_languageKnown.layoutManager = ChipsLayoutManager

        //  val stars = ratingbar_star.getProgressDrawable() as LayerDrawable
       // stars.getDrawable(2).setColorFilter(Color.parseColor("#f4ad42"), PorterDuff.Mode.SRC_ATOP)
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