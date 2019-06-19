package com.utalli.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.utalli.R
import com.utalli.helpers.AppPreference
import com.utalli.viewModels.MyProfileViewModel
import com.utalli.viewModels.SearchLocationViewModel
import kotlinx.android.synthetic.main.my_profile_activity.*


class MyProfileActivity : AppCompatActivity(), View.OnClickListener {

    var myProfileViewModel : MyProfileViewModel?= null
    var preference: AppPreference? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_profile_activity)

        initViews()

    }

    private fun initViews() {

        myProfileViewModel = ViewModelProviders.of(this).get(MyProfileViewModel::class.java)
        preference = AppPreference.getInstance(this)


        myProfileViewModel!!.myProfileUser(this, preference!!.getId()).observe(this, Observer {

           if(it != null && it.has("status") && it.get("status").asString.equals("1")){

               if(it.has("data")){

                   var dataObj = it.getAsJsonObject("data")

                   if(dataObj.has("u_name") && dataObj.get("u_name") != null){
                       tv_profileName.setText(dataObj.get("u_name").asString)
                   }

                   if(dataObj.has("mobile_no") && dataObj.get("mobile_no") != null ){
                       tv_contactDetails.setText(dataObj.get("mobile_no").asString)
                   }

                   if(dataObj.has("u_email") && dataObj.get("u_email") != null){
                       tv_email_id.setText(dataObj.get("u_email").asString)
                   }

                   if(dataObj.has("emry_contact") && dataObj.get("emry_contact") != null){
                       tv_emergency_contact_number.setText(dataObj.get("emry_contact").asString)
                   }

                   if (dataObj.has("u_address") && dataObj.get("u_address") != null){
                       tv_address.setText(dataObj.get("u_address").asString)
                   }


               }
           }
        })

        civ_profile_image.setImageResource(R.drawable.dummy_icon)
      /*  tv_profileName.setText("Janice Crawford")
        tv_contactDetails.setText("+ 9634324545623546254")
        tv_email_id.setText("janicecraw@samplemail.com")
        tv_help_Support.setText("Help, support and FAQ")
        tv_appSettings.setText("Notification, Vibration etc.")
        tv_emergency_contact_number.setText("+1 3342484232")
        tv_address.setText("4848 Willow Greene Drive Montgomery")*/


        tv_settings.setOnClickListener(this)
        tv_payment.setOnClickListener(this)
        iv_backArrow.setOnClickListener(this)
        tv_logout.setOnClickListener(this)
        iv_editProfile_icon.setOnClickListener(this)

    }


    override fun onClick(v: View?) {

        when(v?.id){
            R.id.iv_backArrow ->{
                finish()
            }
            R.id.tv_logout ->{
                logout()

            }
            R.id.iv_editProfile_icon -> {

            }
            R.id.tv_payment ->{
                val intent = Intent(this, PaymentActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_settings ->{
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun logout() {
        AppPreference.getInstance(this).setAuthToken("")
        AppPreference.getInstance(this).setId(0)
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }


}
