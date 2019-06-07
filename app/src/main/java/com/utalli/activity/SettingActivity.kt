package com.utalli.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.utalli.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingActivity : AppCompatActivity(), View.OnClickListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toolbar.title = ""
        toolbar.setNavigationIcon(R.drawable.arrow_back_white)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        initView()
    }



    private fun initView() {

        iv_right_arrow_helpSupport.setOnClickListener(this)
    }


    override fun onClick(v: View?) {

        when(v?.id){
            R.id.iv_right_arrow_helpSupport ->{
                var intent = Intent(this,HelpAndSupportActivity::class.java)
                startActivity(intent)
            }

        }

    }


}