package com.utalli.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.utalli.R
import kotlinx.android.synthetic.main.activity_recovery_password.*

class RecoveryPasswordActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery_password)


        initViews();

    }

    private fun initViews() {

        tv_save.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_save ->{
                val intent = Intent(this@RecoveryPasswordActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

    }


}