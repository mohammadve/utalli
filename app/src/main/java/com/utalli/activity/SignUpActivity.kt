package com.utalli.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.utalli.R
import com.utalli.helpers.Utils
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var genderValue : String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        toolbar.title = ""
        toolbar.setNavigationIcon(R.drawable.arrow_back_black)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        initView()
    }

    private fun initView() {

        btn_signUp.setOnClickListener(this)
        tv_sign_in.setOnClickListener(this)
        iv_calendar_icon.setOnClickListener(this)

        cl_first_male.setOnClickListener(this)
        cl_second_female.setOnClickListener(this)
        cl_third_other.setOnClickListener(this)

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
            R.id.iv_calendar_icon -> {
                val datePickerDialog = DatePickerDialog(this,R.style.DialogTheme, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    et_dateOfBirth.setText("" + dayOfMonth + "/" + (monthOfYear+1) + "/" + year)
                }, year, month, day)

                datePickerDialog.show()
            }
            R.id.cl_first_male ->{
                genderValue = "MALE"
                cl_first_male.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.rounded_rect_bottom_blue))
                //cl_first_male.setBackgroundColor(resources.getColor(R.color.color_blue))
                cl_second_female.setBackgroundColor(resources.getColor(R.color.colorWhite))
                cl_third_other.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.rounded_rect_top_white))

                tv_male.setTextColor(Color.parseColor("#ffffff"))
                tv_female.setTextColor(Color.parseColor("#000000"))
                tv_other.setTextColor(Color.parseColor("#000000"))

            }
            R.id.cl_second_female -> {
                genderValue = "FEMALE"
                cl_second_female.setBackgroundColor(resources.getColor(R.color.color_blue))
                cl_first_male.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.rounded_rect_bottom_white))
                cl_third_other.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.rounded_rect_top_white))

                tv_male.setTextColor(Color.parseColor("#000000"))
                tv_female.setTextColor(Color.parseColor("#ffffff"))
                tv_other.setTextColor(Color.parseColor("#000000"))

            }
            R.id.cl_third_other ->{
                genderValue = "OTHER"
                cl_third_other.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.rounded_rect_top_blue))
                cl_first_male.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.rounded_rect_bottom_white))
                cl_second_female.setBackgroundColor(resources.getColor(R.color.colorWhite))

                tv_male.setTextColor(Color.parseColor("#000000"))
                tv_female.setTextColor(Color.parseColor("#000000"))
                tv_other.setTextColor(Color.parseColor("#ffffff"))
            }

        }

    }

    private fun signupUser() {

        Utils.hideSoftKeyboard(this)

        //if(checkValidations()){
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()

       // }
        Toast.makeText(this,genderValue,Toast.LENGTH_SHORT).show()

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