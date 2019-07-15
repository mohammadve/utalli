package com.utalli.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utalli.R
import com.utalli.models.RecentTourListModel
import com.utalli.models.UpcomingTourListModel

class RecentTourAdapter(var mcontext: Context, var recentComingTourList: List<RecentTourListModel>) : RecyclerView.Adapter<RecentTourAdapter.MyRecentTourViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentTourAdapter.MyRecentTourViewHolder {
        return MyRecentTourViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recent_my_tours, parent, false))
    }




    override fun getItemCount(): Int {
      return recentComingTourList.size
    }



    override fun onBindViewHolder(holder: RecentTourAdapter.MyRecentTourViewHolder, position: Int) {

        holder.tv_tourDate.text = recentComingTourList.get(position).tourStartdate + " - " + recentComingTourList.get(position).tourEnddate
        holder.tv_tour_charges.text = recentComingTourList.get(position).tourcost
    }

    inner class MyRecentTourViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        var tv_tour_states : TextView
        var tv_tour_charges : TextView
        var tv_tourDate : TextView

        init {
            tv_tour_states = view.findViewById(R.id.tv_tour_states)
            tv_tour_charges = view.findViewById(R.id.tv_tour_charges)
            tv_tourDate = view.findViewById(R.id.tv_tourDate)
        }

    }

}