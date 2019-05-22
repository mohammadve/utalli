package com.utalli.activity

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.utalli.R
import kotlinx.android.synthetic.main.activity_guide_profile_details.*

class GuideProfileDetailsActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_profile_details)

        initViews()

    }


    private fun initViews() {
        val stars = ratingbar_star.getProgressDrawable() as LayerDrawable
        stars.getDrawable(2).setColorFilter(Color.parseColor("#f4ad42"), PorterDuff.Mode.SRC_ATOP)
    }


    override fun onClick(v: View?) {

    }




}