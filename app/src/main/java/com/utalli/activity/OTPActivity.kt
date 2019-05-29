package com.utalli.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.utalli.R
import kotlinx.android.synthetic.main.activity_otp.*

class OTPActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)



        toolbar_otp.title = ""

        toolbar_otp.setNavigationIcon(R.drawable.arrow_back_black)
        setSupportActionBar(toolbar_otp)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar_otp.setNavigationOnClickListener { finish() }

        tv_verify_btn.setOnClickListener(this)






    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_verify_btn -> {
                val intent = Intent(this, RecoveryPasswordActivity::class.java)
                startActivity(intent)
            }
        }
    }
}