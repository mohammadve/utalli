package com.utalli.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.utalli.R
import com.utalli.helpers.Utils
import com.utalli.viewModels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    var showPassword: Boolean = false
    var loginViewModel : LoginViewModel?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()

    }

    private fun initViews() {

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

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
                loginInUser()
            }

        }
    }

    public fun loginInUser() {

   /*     val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()*/

        if(checkValidation()){

           loginViewModel!!.loginUser(this,et_mobileNumber.text.toString(), et_password.text.toString())
               .observe(this, Observer {

                  // Utils.showLog(it.toString())

              //     Log.e("TAG","Login  it.toString() === "+it.toString())


                   if (it!= null && it.has("status") && it.get("status").asString.equals("1")) {

                       Utils.showToast(this, it.get("message").asString)
                       Log.e("TAG","message status 1 Login  === "+it.get("message"))

                       Handler().postDelayed(Runnable {
                           val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                           startActivity(intent)
                           finish()

                       }, 1000)
                   }
                   else {
                          if (it!= null && it.has("message")){
                              Utils.showToast(this, it.get("message").asString)
                          }
                   }

               })



        }

    }

    private fun checkValidation(): Boolean {

        if (!Utils.isInternetAvailable(this)) {
            Utils.showToast(this, resources.getString(R.string.msg_no_internet))
            return false
        }
        else if (et_mobileNumber.text!!.length == 0) {
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