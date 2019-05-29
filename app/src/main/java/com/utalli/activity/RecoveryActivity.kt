package com.utalli.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.utalli.R
import kotlinx.android.synthetic.main.activity_recovery.*

class RecoveryActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery)

        toolbar_recovery.title = ""

        toolbar_recovery.setNavigationIcon(R.drawable.arrow_back_black)
        setSupportActionBar(toolbar_recovery)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar_recovery.setNavigationOnClickListener { finish() }


        initViews();
    }

    private fun initViews() {

        tv_send_otp.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_send_otp -> {
                val intent = Intent(this@RecoveryActivity, OTPActivity::class.java)
                startActivity(intent)
            }

        }

    }


}