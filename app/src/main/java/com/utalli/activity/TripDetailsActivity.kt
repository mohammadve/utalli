package com.utalli.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.utalli.R
import com.utalli.adapter.TripDetailsStateListAdapter
import kotlinx.android.synthetic.main.activity_trip_details.*
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class TripDetailsActivity : AppCompatActivity(), View.OnClickListener {
    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)

        initViews()
    }

    private fun initViews() {
        tv_date_of_arrival!!.text = "DD/MM/YYYY"
        tv_date_of_departure!!.text = "DD/MM/YYYY"

        iv_back_arrow.setOnClickListener(this)
        button_confirm.setOnClickListener(this)
        tv_date_of_arrival.setOnClickListener(this)
        tv_date_of_departure.setOnClickListener(this)
        tv_change.setOnClickListener(this)

        if(tv_date_of_arrival.text.equals("DD/MM/YYYY")){
            tv_arrival_date_change.visibility = View.GONE
        } else{
            tv_arrival_date_change.visibility = View.VISIBLE
        }

        if(tv_date_of_departure.text.equals("DD/MM/YYYY")){
            tv_departure_date_change.visibility = View.GONE
        } else {
            tv_departure_date_change.visibility = View.VISIBLE
        }


        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.SPACE_AROUND
        rv_states_list.layoutManager= layoutManager
        rv_states_list.adapter = TripDetailsStateListAdapter(this)

    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button_confirm -> {

            }
            R.id.tv_change ->{
                var intent = Intent(this@TripDetailsActivity, SearchActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.iv_back_arrow ->{
                finish()
            }
            R.id.tv_date_of_arrival ->{
            /*     c = Calendar.getInstance()
                 year = c.get(Calendar.YEAR)
                 month = c.get(Calendar.MONTH)
                 day = c.get(Calendar.DAY_OF_MONTH)*/

                val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    tv_date_of_arrival.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year)
                }, year, month, day)
                tv_arrival_date_change.visibility = View.VISIBLE
                datePickerDialog.show()

            }

            R.id.tv_date_of_departure ->{
            /*    c = Calendar.getInstance()
                year = c.get(Calendar.YEAR)
                month = c.get(Calendar.MONTH)
                day = c.get(Calendar.DAY_OF_MONTH)*/

                val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    tv_date_of_departure.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year)
                }, year, month, day)
                tv_departure_date_change.visibility = View.VISIBLE
                datePickerDialog.show()
            }

        }

    }




}