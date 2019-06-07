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

        tv_helpSupport.setOnClickListener(this)
        tv_privacyPolicy.setOnClickListener(this)
        tv_aboutUs.setOnClickListener(this)
    }


    override fun onClick(v: View?) {

        when(v?.id){
            R.id.tv_helpSupport ->{
                var intent = Intent(this,HelpAndSupportActivity::class.java)
                startActivity(intent)
            }

            R.id.tv_privacyPolicy ->{
                var intent = Intent(this,WebViewActivity::class.java)
                intent.putExtra("ScreenType","Privacy Policy")
                intent.putExtra("Url","https://policies.google.com/")
                startActivity(intent)
            }
            R.id.tv_aboutUs ->{
                var intent = Intent(this,WebViewActivity::class.java)
                intent.putExtra("ScreenType","About Us")
                intent.putExtra("Url","https://about.google/intl/en/")
                startActivity(intent)
            }

        }

    }


}