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
        tv_profileName.setText("Janice Crawford")
        tv_contactDetails.setText("+ 9634324545623546254")
        tv_email_id.setText("janicecraw@samplemail.com")
        tv_help_Support.setText("Help, support and FAQ")
        tv_appSettings.setText("Notification, Vibration etc.")
        tv_emergency_contact_number.setText("+1 3342484232")
        tv_address.setText("4848 Willow Greene Drive Montgomery")


        iv_right_arrow_helpAndSupport.setOnClickListener(this)
        iv_right_arrow_appSettings.setOnClickListener(this)
        iv_back_arrow.setOnClickListener(this)
        tv_logout.setOnClickListener(this)
        iv_editProfile_icon.setOnClickListener(this)

    }


    override fun onClick(v: View?) {

        when(v?.id){
            R.id.iv_back_arrow ->{
                finish()
            }
            R.id.tv_logout ->{

            }
            R.id.iv_editProfile_icon -> {

            }
            R.id.iv_right_arrow_appSettings ->{

            }
            R.id.iv_right_arrow_helpAndSupport ->{

            }


        }
    }


}
