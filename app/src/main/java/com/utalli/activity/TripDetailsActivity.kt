package com.utalli.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    var countryName : String?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)

        toolbar.title = ""
        toolbar.setNavigationIcon(R.drawable.arrow_back_white)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        countryName = intent.getStringExtra("countryName")

        Log.e("TAG","Country  Name ===== "+countryName)

        initViews()
    }

    private fun initViews() {
        tv_date_of_arrival!!.text = "DD/MM/YYYY"
        tv_date_of_departure!!.text = "DD/MM/YYYY"


        tv_selected_country_name.text = countryName


        button_confirm.setOnClickListener(this)
        tv_date_of_arrival.setOnClickListener(this)
        tv_date_of_departure.setOnClickListener(this)
        tv_arrival_date_change.setOnClickListener(this)
        tv_departure_date_change.setOnClickListener(this)
        tv_change.setOnClickListener(this)


        if(tv_date_of_arrival.text == null && tv_date_of_arrival.text.equals("DD/MM/YYYY")){
            tv_arrival_date_change.visibility = View.GONE
        }
        else if(tv_date_of_arrival.text != null && !tv_date_of_arrival.text.equals("DD/MM/YYYY")){
            tv_arrival_date_change.visibility = View.VISIBLE
        }

        if(tv_date_of_departure.text == null && tv_date_of_departure.text.equals("DD/MM/YYYY")){
            tv_departure_date_change.visibility = View.GONE
        }
        else if(tv_date_of_departure.text != null && !tv_date_of_departure.text.equals("DD/MM/YYYY")) {
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
              var intent = Intent(this@TripDetailsActivity,GuideListActivity::class.java)
                startActivity(intent)
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

                val datePickerDialog = DatePickerDialog(this,R.style.DialogTheme, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    tv_date_of_arrival.setText("" + dayOfMonth + "/" + (monthOfYear+1) + "/" + year)

                    if(tv_date_of_arrival.text == null && tv_date_of_arrival.text.equals("DD/MM/YYYY")){
                        tv_arrival_date_change.visibility = View.GONE
                    }
                    else if (tv_date_of_arrival.text != null && !tv_date_of_arrival.text.equals("DD/MM/YYYY")){
                        tv_arrival_date_change.visibility = View.VISIBLE  //tv_arrival_date_change
                    }

                }, year, month, day)

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)



                datePickerDialog.show()

            }

            R.id.tv_date_of_departure ->{

                val datePickerDialog = DatePickerDialog(this,R.style.DialogTheme, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    tv_date_of_departure.setText("" + dayOfMonth + "/" + (monthOfYear+1) + "/" + year)

                    if(tv_date_of_departure.text == null && tv_date_of_departure.text.equals("DD/MM/YYYY")){
                        tv_departure_date_change.visibility = View.GONE
                    }
                    else if(tv_date_of_departure.text != null && !tv_date_of_departure.text.equals("DD/MM/YYYY"))
                    {
                        tv_departure_date_change.visibility = View.VISIBLE
                    }



                }, year, month, day)

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)



                datePickerDialog.show()
            }

            R.id.tv_departure_date_change ->{
                val datePickerDialog = DatePickerDialog(this,R.style.DialogTheme, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    tv_date_of_departure.setText("" + dayOfMonth + "/" + (monthOfYear+1) + "/" + year)
                }, year, month, day)
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)
                datePickerDialog.show()
            }

            R.id.tv_arrival_date_change -> {
                val datePickerDialog = DatePickerDialog(this,R.style.DialogTheme, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    tv_date_of_arrival.setText("" + dayOfMonth + "/" + (monthOfYear+1) + "/" + year)
                }, year, month, day)

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)
                datePickerDialog.show()
            }


        }

    }




}