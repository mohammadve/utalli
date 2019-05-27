package com.utalli.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.utalli.R
import com.utalli.adapter.NotificationAdapter
import com.utalli.fragment.CustomBottomSheetDialog
import kotlinx.android.synthetic.main.activity_notification.*


class NotificationActivity : AppCompatActivity(), View.OnClickListener {
    var notificationAdapter: NotificationAdapter? = null
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_bottom_sheet)

        /*     var bottomSheetBehavior = BottomSheetBehavior.from(cl_bottom_sheet)

             bottomSheetBehavior.setPeekHeight(320);
             bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


             iv_notification_icon.setOnClickListener {
               *//*  if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }*//*

            val bottomSheetDialog = CustomBottomSheetDialog()
            bottomSheetDialog.show(getSupportFragmentManager(), "Custom Bottom Sheet")

        }

        iv_cancel.setOnClickListener(){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }


        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // React to dragging events
            }
        })*/


        notification_list.setHasFixedSize(true)
        notification_list.layoutManager = LinearLayoutManager(this)
        notification_list.adapter = NotificationAdapter(this)



        iv_cancel.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                finish()
            }


        })


    }


    override fun onClick(v: View?) {
        when (v?.id) {
        }
    }


}