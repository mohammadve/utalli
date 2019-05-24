package com.utalli.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utalli.R
import kotlinx.android.synthetic.main.item_row_list_destination_home.view.*


class HomeListDestinationAdapter (var mcontext : Context) : RecyclerView.Adapter<HomeListDestinationAdapter.HomeListDestinationViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListDestinationViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_row_list_destination_home, parent, false)
        return HomeListDestinationViewHolder(v)

    }


    override fun getItemCount(): Int {
       return 10
    }



    override fun onBindViewHolder(holder: HomeListDestinationViewHolder, position: Int) {

    }



     class HomeListDestinationViewHolder(view : View) : RecyclerView.ViewHolder(view) {

         val ivPlacepic = view.iv_place_pic


    }

}