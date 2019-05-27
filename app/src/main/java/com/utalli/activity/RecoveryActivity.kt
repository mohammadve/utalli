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


        initViews();
    }

    private fun initViews() {

        tv_send_otp.setOnClickListener(this)
        iv_back.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_send_otp -> {
                val intent = Intent(this@RecoveryActivity, OTPActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_back -> finish()
        }

    }


}