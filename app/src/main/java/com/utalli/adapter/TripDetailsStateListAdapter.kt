package com.utalli.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utalli.R

class TripDetailsStateListAdapter (var mcontext:Context) : RecyclerView.Adapter<TripDetailsStateListAdapter.StateListViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripDetailsStateListAdapter.StateListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_list_states,parent,false)
        return StateListViewHolder(view)
    }



    override fun getItemCount(): Int {
      return 20
    }



    override fun onBindViewHolder(holder: TripDetailsStateListAdapter.StateListViewHolder, position: Int) {

        if(position==2)
        holder.tv_state_name.text="Goa"

        if(position==3)
            holder.tv_state_name.text="Delhi"




    }

    class StateListViewHolder (itemView : View) :  RecyclerView.ViewHolder(itemView) {

        var tv_state_name:TextView
        init {
            tv_state_name=itemView.findViewById(R.id.tv_state_name)
        }










    }


}