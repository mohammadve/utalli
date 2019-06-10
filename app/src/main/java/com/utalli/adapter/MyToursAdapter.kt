package com.utalli.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utalli.R

class MyToursAdapter : RecyclerView.Adapter<MyToursAdapter.MyToursViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyToursViewHolder {
        return MyToursViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_tours, parent, false))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: MyToursViewHolder, position: Int) {
    }


    class MyToursViewHolder(view: View) : RecyclerView.ViewHolder(view)

}