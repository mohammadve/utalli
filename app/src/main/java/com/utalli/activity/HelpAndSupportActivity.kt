package com.utalli.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.utalli.R
import kotlinx.android.synthetic.main.activity_help_and_support.*

class HelpAndSupportActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_and_support)

        toolbar_helpSupport.title =""
        toolbar_helpSupport.setNavigationIcon(R.drawable.arrow_back_white)
        setSupportActionBar(toolbar_helpSupport)
        toolbar_helpSupport.setNavigationOnClickListener { finish() }


        initViews()

    }


    private fun initViews() {



    }


}