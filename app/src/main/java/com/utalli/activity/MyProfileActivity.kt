package com.utalli.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.utalli.R
import kotlinx.android.synthetic.main.my_profile_activity.*


class MyProfileActivity : AppCompatActivity(), View.OnClickListener {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_profile_activity)

        initViews()

    }

    private fun initViews() {

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
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
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


}
