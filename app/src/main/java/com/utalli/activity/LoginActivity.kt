package com.utalli.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.utalli.R
import com.utalli.helpers.Utils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    var showPassword: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()

    }

    private fun initViews() {

        tv_login_btn.setOnClickListener(this)
        tv_forgot_pass.setOnClickListener(this)
        tv_signUp.setOnClickListener(this)
        iv_password_toggle.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_forgot_pass ->{
                val intent = Intent(this@LoginActivity,RecoveryActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_signUp ->{
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
                finish()
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
            R.id.tv_login_btn ->{
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
                /*if(checkValidation()){
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }*/

            }

        }
    }

    private fun checkValidation(): Boolean {
        if (et_mobileNumber.text!!.length == 0) {
            Utils.showToast(this, resources.getString(R.string.msg_empty_mobile_no))
            return false
        } else if (et_password.text!!.toString().length == 0) {
            Utils.showToast(this, resources.getString(R.string.msg_empty_pass))
            return false
        } else if (et_password.text!!.toString().length < 6) {
            Utils.showToast(this, resources.getString(R.string.msg_invalid_pass))
            return false
        }

        return true
    }




}