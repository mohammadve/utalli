package com.utalli.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.utalli.R
import com.utalli.helpers.Utils
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initView()
    }

    private fun initView() {

        toolbar.title = ""
        toolbar.setNavigationIcon(R.drawable.arrow_back_black)
        setSupportActionBar(toolbar)

        btn_signUp.setOnClickListener(this)
        tv_sign_in.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_signUp ->{
                signupUser()
            }
            R.id.tv_sign_in ->{
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

    }

    private fun signupUser() {

        Utils.hideSoftKeyboard(this)

        if(checkValidations()){
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.getItemId()){
            android.R.id.home ->{
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun checkValidations(): Boolean {
        if (!Utils.isInternetAvailable(this)) {
            Utils.showToast(this, resources.getString(R.string.msg_no_internet))
            return false
        }
        else if (et_name.text!!.length < 2) {
            Utils.showToast(this, resources.getString(R.string.msg_invalid_name))
            return false
        }
        else if (!Utils.isEmailValid(et_email_id.text.toString())) {
            Utils.showToast(this, resources.getString(R.string.msg_invalid_email))
            return false
        }
        else if (et_mobileNumber.text!!.length == 0) {
            Utils.showToast(this, resources.getString(R.string.msg_empty_mobile_no))
            return false
        }
        else if (et_newPassword.text!!.length == 0) {
            Utils.showToast(this, resources.getString(R.string.msg_empty_pass))
            return false
        }
        else if (et_newPassword.text!!.length < 6) {
            Utils.showToast(this, resources.getString(R.string.msg_invalid_pass))
            return false
        }

        return true
    }


}