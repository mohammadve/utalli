package com.utalli.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.utalli.R
import kotlinx.android.synthetic.main.activity_recovery_password.*

class RecoveryPasswordActivity : AppCompatActivity(), View.OnClickListener {
    var showPassword: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery_password)

        toolbar_recoveryPass.title = ""

        toolbar_recoveryPass.setNavigationIcon(R.drawable.arrow_back_black)
        setSupportActionBar(toolbar_recoveryPass)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar_recoveryPass.setNavigationOnClickListener { finish() }


        initViews()

    }

    private fun initViews() {


        iv_password_toggle.setOnClickListener(this)
        iv_reTypePassword_toggle.setOnClickListener(this)

        tv_save.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_save -> {
                val intent = Intent(this@RecoveryPasswordActivity, LoginActivity::class.java)
                startActivity(intent)
            }

            R.id.iv_password_toggle ->{
                if (showPassword) {
                    et_password.transformationMethod = PasswordTransformationMethod.getInstance()

                    iv_password_toggle.setImageResource(R.drawable.eye_hide)
                } else {

                    et_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    iv_password_toggle.setImageResource(R.drawable.eye)
                }
                et_password.setSelection(et_password.text!!.length)
                showPassword = !showPassword
            }
            R.id.iv_reTypePassword_toggle ->{
                if (showPassword) {
                    et_retype_password.transformationMethod = PasswordTransformationMethod.getInstance()

                    iv_reTypePassword_toggle.setImageResource(R.drawable.eye_hide)
                } else {

                    et_retype_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    iv_reTypePassword_toggle.setImageResource(R.drawable.eye)
                }
                et_retype_password.setSelection(et_retype_password.text!!.length)
                showPassword = !showPassword
            }

        }

    }


}