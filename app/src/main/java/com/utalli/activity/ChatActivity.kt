package com.utalli.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.utalli.R

class ChatActivity : AppCompatActivity(), View.OnClickListener {
    var userName : String?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        initViews()

    }

    private fun initViews() {


    }


    override fun onClick(v: View?) {

    }


}