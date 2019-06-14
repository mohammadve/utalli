package com.utalli.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utalli.R
import com.utalli.activity.SearchActivity
import com.utalli.activity.TripDetailsActivity
import android.app.Activity





class SearchPlaceAdapter(var mcontext: Context, var languageList: ArrayList<String>) :
    RecyclerView.Adapter<SearchPlaceAdapter.SearchViewHolder>() {

   // var LocationSearchFilter: SearchActivity.LocationSearchFilter? = null


    fun updateSearchList(searchList: ArrayList<String>) {
        languageList.clear()
        languageList = ArrayList()
        languageList.addAll(searchList)

        notifyDataSetChanged()


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPlaceAdapter.SearchViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_list_location_search, parent, false)
        return SearchViewHolder(view)
    }


    override fun getItemCount(): Int {
        return languageList.size
    }


    override fun onBindViewHolder(holder: SearchPlaceAdapter.SearchViewHolder, position: Int) {


        holder.tvlocationName.text = languageList[position]


        holder.layoutSearch.setOnClickListener {
            var intent = Intent(mcontext, TripDetailsActivity::class.java)
            intent.putExtra("countryName",languageList[position].toString())
            mcontext.startActivity(intent)
           // (mcontext as Activity).finish()
        }

    }


   /* override fun getFilter(): SearchActivity.LocationSearchFilter? {
        return LocationSearchFilter
    }*/


    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvlocationName: TextView
        var layoutSearch : LinearLayout

        init {
            tvlocationName = itemView.findViewById(R.id.tv_locationName)
            layoutSearch = itemView.findViewById(R.id.layout_search)
        }



    }


}