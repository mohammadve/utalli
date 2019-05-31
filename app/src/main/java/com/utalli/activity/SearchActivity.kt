package com.utalli.activity

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Filter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.utalli.R
import com.utalli.adapter.SearchPlaceAdapter
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity(), View.OnClickListener{
    var languageList = ArrayList<String>()
    var searchPlaceAdapter: SearchPlaceAdapter? = null



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
        rv_languageList.adapter = SearchPlaceAdapter(this,languageList!!)

        initViews()

    }

    private fun initViews() {

        et_search.addTextChangedListener(object:TextWatcher {

            override fun beforeTextChanged(s:CharSequence, i:Int, i1:Int, i2:Int) {

            }
            override fun onTextChanged(s:CharSequence, i:Int, i1:Int, i2:Int) {
               // searchPlaceAdapter?.getFilter()?.filter(s.toString())

                if(s!=null && s.equals(""))
                {
                    searchPlaceAdapter!!.updateSearchList(languageList)
                }
                else
                {

                    var tempLocList = ArrayList<String>()

                    for( i in 0..languageList.size-1)
                    {
                        if(languageList.get(i).contains(s.toString()))
                        {
                            tempLocList.add(languageList.get(i))
                        }
                    }

                    searchPlaceAdapter?.updateSearchList(tempLocList)
                }

            }
            override fun afterTextChanged(editable:Editable) {

            }
        })

    }




    override fun onClick(v: View?) {

    }


    inner class LocationSearchFilter( var context: Context) : Filter() {

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




}

