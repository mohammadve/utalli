package com.utalli.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.utalli.R
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity(), View.OnClickListener{



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        toolbar_payment.title =""
        toolbar_payment.setNavigationIcon(R.drawable.arrow_back_white)
        setSupportActionBar(toolbar_payment)
        toolbar_payment.setNavigationOnClickListener { finish() }


    }


    override fun onClick(v: View?) {

    }








}