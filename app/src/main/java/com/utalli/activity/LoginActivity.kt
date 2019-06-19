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
import com.utalli.helpers.AppPreference
import com.utalli.helpers.Utils
import com.utalli.viewModels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    var showPassword: Boolean = false
    var loginViewModel : LoginViewModel?=null
    var preference: AppPreference? = null
    var token : String = ""
    var id: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()

    }

    private fun initViews() {

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        preference = AppPreference.getInstance(this)



        tv_login_btn.setOnClickListener(this)
        tv_forgot_pass.setOnClickListener(this)
        tv_signUp.setOnClickListener(this)
        iv_password_toggle.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_forgot_pass ->{
                val intent = Intent(this@LoginActivity,ForgetPasswordActivity::class.java)
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

     fun loginInUser() {



        if(checkValidation()){

           loginViewModel!!.loginUser(this,et_mobileNumber.text.toString(), et_password.text.toString())
               .observe(this, Observer {



                   if (it!= null && it.has("status") && it.get("status").asString.equals("1")) {

                       if (it.has("accessToken")) {
                           AppPreference.getInstance(this).setAuthToken(it.get("accessToken").asString)
                          //  token = preference!!.getAuthToken()
                           Log.e("TAG","accessToken in loginActivity ====   " + preference!!.getAuthToken())
                       }


                       if(it.has("data")){
                           var dataObject = it.getAsJsonObject("data")
                           if (dataObject.has("id")){
                               id = dataObject.get("id").asInt

                               AppPreference.getInstance(this).setId(dataObject.get("id").asInt)

                           }
                       }


                       Utils.showToast(this, it.get("message").asString)

                       Handler().postDelayed(Runnable {
                           val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                           startActivity(intent)
                           finish()
                       }, 3500)





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