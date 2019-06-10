package com.utalli.activity

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Filter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.utalli.R
import com.utalli.adapter.SearchPlaceAdapter
import kotlinx.android.synthetic.main.activity_search.*
import android.content.Intent
import androidx.core.app.ShareCompat.IntentBuilder
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import android.app.Activity
import com.utalli.helpers.Utils


class SearchActivity : AppCompatActivity(), View.OnClickListener {
    var languageList = ArrayList<String>()
    var searchPlaceAdapter: SearchPlaceAdapter? = null
var PLACE_PICKER_REQUEST=121

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        languageList!!.add("Australia")
        languageList!!.add("Afghanistan")
        languageList!!.add("Albania")
        languageList!!.add("Armenia")
        languageList!!.add("Bhutan")
        languageList!!.add("Greece")
        languageList!!.add("Iran")
        languageList!!.add("Iraq")
        languageList!!.add("Israel")
        languageList!!.add("Liberia")
        languageList!!.add("Mongolia")
        languageList!!.add("North Korea")
        languageList!!.add("Panama")
        languageList!!.add("Rwanda")
        languageList!!.add("Samoa")


        rv_languageList.layoutManager = LinearLayoutManager(this)
        rv_languageList.adapter = SearchPlaceAdapter(this, languageList!!)

        initViews()

    }

    private fun initViews() {


        iv_back.setOnClickListener(this)
        iv_cancel.setOnClickListener(this)
        et_search.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(s: CharSequence, i: Int, i1: Int, i2: Int) {
                // searchPlaceAdapter?.getFilter()?.filter(s.toString())

                if (s != null && s.equals("")) {


                    iv_cancel.visibility = View.GONE

                    searchPlaceAdapter!!.updateSearchList(languageList)
                } else {


                    iv_cancel.visibility = View.VISIBLE

                    var tempLocList = ArrayList<String>()

                    for (i in 0..languageList.size - 1) {
                        if (languageList.get(i).contains(s.toString())) {
                            tempLocList.add(languageList.get(i))
                        }
                    }

                    searchPlaceAdapter?.updateSearchList(tempLocList)
                }

            }

            override fun afterTextChanged(editable: Editable) {

            }
        })



        et_search.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(p0: TextView?, actionId: Int, p2: KeyEvent?): Boolean {


                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;

            }
        })



    }

    fun performSearch()
    {
     /*   if(!et_search.text.toString().equals(""))
        {
            try {
                val intentBuilder = PlacePicker.IntentBuilder()
                val intent = intentBuilder.build(this)
                startActivityForResult(intent, PLACE_PICKER_REQUEST)

            } catch (e: GooglePlayServicesRepairableException) {
                e.printStackTrace()
            } catch (e: GooglePlayServicesNotAvailableException) {
                e.printStackTrace()
            }

        }*/
    }



    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> finish()
            R.id.iv_cancel -> et_search.setText("")

        }
    }


    inner class LocationSearchFilter(var context: Context) : Filter() {

        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            var constraint = constraint


            constraint = constraint!!.toString().toLowerCase()

            val newFilterResults = Filter.FilterResults()

            if (constraint != null && constraint.length > 0) {

                val auxData = ArrayList<String>()

                for (i in languageList.indices) {
                    if (languageList[i].toLowerCase().contains(constraint))
                        auxData.add(languageList[i])
                }

                newFilterResults.count = auxData.size
                newFilterResults.values = auxData
            } else {

                newFilterResults.count = languageList.size
                newFilterResults.values = languageList
            }

            return newFilterResults
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {

            var resultData = ArrayList<String>()

            resultData = results.values as ArrayList<String>

            val adapter = SearchPlaceAdapter(context, resultData)
            SearchActivity().rv_languageList.adapter = adapter

            //              notifyDataSetChanged();
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)




    }

}

