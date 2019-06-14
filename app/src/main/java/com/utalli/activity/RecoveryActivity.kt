package com.utalli.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.JsonObject
import com.utalli.R
import com.utalli.helpers.Utils
import com.utalli.viewModels.ForgetPassViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_recovery.*
import kotlinx.android.synthetic.main.activity_recovery.et_mobileNumber

class RecoveryActivity : AppCompatActivity(), View.OnClickListener {

    var forgetPassViewModel: ForgetPassViewModel? = null
    var idd: Int = 0;
    var OTP: String = ""


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
        forgetPassViewModel = ViewModelProviders.of(this).get(ForgetPassViewModel::class.java)

        tv_send_otp.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_send_otp -> {

                if (checkValidation()) {

                    forgetPassViewModel!!.forgetPass(this, et_mobileNumber.text.toString())
                        .observe(this, Observer {

                            //  Utils.showLog(it.toString())
                            Log.e("TAG", "forgetPass ==== " + it.toString())

                            if (it != null && it.has("status") && it.get("status").asString.equals("1")) {

                                if (it.has("data")) {

                                    var dataObject = it.getAsJsonObject("data")

                                    if (dataObject.has("id") && dataObject.has("otp")) {
                                        Utils.showToast(this, it.get("message").asString)
                                        idd = dataObject.get("id").asInt
                                        OTP = (it.get("data") is JsonObject).toString()

                                        Handler().postDelayed(Runnable {
                                            val intent = Intent(this, OTPActivity::class.java)
                                            intent.putExtra("id",idd)
                                            intent.putExtra("OTP",OTP)
                                            startActivity(intent)
                                            //  finish()
                                        }, 1000)

                                    } else {

                                        Utils.showToast(this, getString(R.string.msg_common_error))

                                    }


                                }



                            } else {
                                if (it != null && it.has("message")) {
                                    Utils.showToast(this, it.get("message").asString)
                                    Log.e("TAG", "message status 0 forgetPass  === " + it.has("message"))
                                }
                            }

                        })

                }


            }
        }
    }


    private fun checkValidation(): Boolean {
        if (et_mobileNumber.text!!.length == 0) {
            Utils.showToast(this, resources.getString(R.string.msg_empty_mobile_no))
            return false
        }

        return true
    }


}