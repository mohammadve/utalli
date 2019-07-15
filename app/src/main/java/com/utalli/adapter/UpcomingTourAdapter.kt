package com.utalli.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utalli.R
import com.utalli.callBack.UpcomingTourCancelCallBack
import com.utalli.models.UpcomingTourListModel


class UpcomingTourAdapter(var mcontext: Context, var upComingTourList: List<UpcomingTourListModel>, var itemListener: UpcomingTourCancelCallBack) : RecyclerView.Adapter<UpcomingTourAdapter.MyToursViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyToursViewHolder {
        return MyToursViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_upcoming_my_tours, parent, false))
    }



    override fun getItemCount(): Int {
        return upComingTourList.size
    }



    override fun onBindViewHolder(holder: MyToursViewHolder, position: Int) {

        holder.tv_tour_charges.setText(upComingTourList.get(position).tourcost)
        holder.tv_tour_dates.setText(upComingTourList.get(position).tourdates)


        holder.tv_cancel.setOnClickListener {
            itemListener.upComingTourListener(upComingTourList.get(position))
        }
    }



    inner class MyToursViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var tv_tour_charges : TextView
        var tv_tour_dates : TextView
        var tv_cancel : TextView


        init {
            tv_tour_charges = view.findViewById(R.id.tv_tour_charges)
            tv_tour_dates = view.findViewById(R.id.tv_tour_dates)
            tv_cancel = view.findViewById(R.id.tv_cancel)
        }




    }



}