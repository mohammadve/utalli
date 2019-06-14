package com.utalli.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.utalli.R
import com.utalli.fragment.VerifyOTPDialogFragment
import com.utalli.helpers.Utils
import com.utalli.models.SignupRequestModel
import com.utalli.viewModels.SignUpViewModel
import kotlinx.android.synthetic.main.activity_sign_up.*
import okhttp3.internal.Util
import java.util.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener, VerifyOTPDialogFragment.OnButtonClick {



    override fun onDismiss() {
        bottomSheetDialogFragment = null
    }

    override fun onResendClick() {
        bottomSheetDialogFragment!!.dismiss()
        signupUser()
    }

    override fun onSubmitClick() {
        Utils.hideSoftKeyboard(this)
        bottomSheetDialogFragment!!.dismiss()

        signupViewModel!!.verifyOTP(this, SignupRequestModel(
            et_name.text.toString(),
            et_dateOfBirth.text.toString(),
            genderValue.toString(),
            et_email_id.text.toString(),
            et_mobileNumber.text.toString(),
            et_newPassword.text.toString(),
            otp
        )
        ).observe(this, androidx.lifecycle.Observer {

            Utils.showLog(it.toString()!!)

            if (it.has("status") && it.get("status").asString.equals("1")) {


                Utils.showToast(this, getString(R.string.msg_user_registered))

                Handler().postDelayed(Runnable {

                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                }, 800)

            } else {
                Utils.showToast(this, getString(R.string.msg_common_error))
            }
        })

    }



    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var genderValue : String? = null
    var signupViewModel:SignUpViewModel?= null
    var otp: String = ""
    var bottomSheetDialogFragment: VerifyOTPDialogFragment? = null



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

        signupViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)

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

        if(checkValidations()){

            Log.e("TAG","genderValue send to server part signUp === "+genderValue.toString())

            signupViewModel!!.signupUser(this, SignupRequestModel(
                et_name.text.toString(),
                et_dateOfBirth.text.toString(),
                genderValue.toString(),
                et_email_id.text.toString(),
                et_mobileNumber.text.toString(),
                et_newPassword.text.toString(),
                ""
            )
            ).observe(this, androidx.lifecycle.Observer {

             //   Utils.showLog(it.toString())
                Log.e("TAG","SignUp it.toString() === "+it.toString())

                if(it != null && it.has("status") && it.get("status").asString.equals("1")){

                    if (it.has("otp")){

                        Utils.showToast(this, getString(R.string.msg_otp_sent))


                        otp = it.get("otp").asString



                        if (bottomSheetDialogFragment == null) {
                            bottomSheetDialogFragment = VerifyOTPDialogFragment.newInstance(otp, this)
                            bottomSheetDialogFragment!!.show(supportFragmentManager, "VerifyOTPDialogFragment")
                        }



                    } else {
                        Utils.showToast(this, getString(R.string.msg_common_error))
                    }

                }
                else {
                      if(it != null && it.has("message")){
                          Utils.showToast(this,it.get("message").asString)
                          Log.e("TAG","message status 0 SignUp  === "+it.get("message").asString)
                      }

                }
            })

        }

    }

    private fun checkValidations(): Boolean {


        Log.e("TAG","gender value on validation part signUp ====   "+genderValue.toString())

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
        else if(genderValue.toString().equals("") || genderValue!!.isEmpty() || genderValue.toString() == null || genderValue!!.length == 0){
            Utils.showToast(this, resources.getString(R.string.msg_empty_gender))
            return false
        }
        else if(et_dateOfBirth.text.toString().equals("")){
            Utils.showToast(this, resources.getString(R.string.msg_empty_dob))
            return false
        }
        else if(!(et_reEnterNew_Password.text.toString()).equals(et_newPassword.text.toString())){
            Utils.showToast(this, resources.getString(R.string.msg_not_same_pass))
            return false
        }

        return true
    }


}