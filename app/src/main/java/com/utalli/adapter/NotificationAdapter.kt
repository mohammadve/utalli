package com.utalli.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utalli.R

class NotificationAdapter (var mcontext : Context) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>(){





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.NotificationViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_list_notification,parent,false)
        return NotificationViewHolder(view)
    }



    override fun getItemCount(): Int {
        return 20
    }



    override fun onBindViewHolder(holder: NotificationAdapter.NotificationViewHolder, position: Int) {

        holder.bindNotificationView()

    }





    class NotificationViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        // var rv_notification_list : RecyclerView

        init {
        //    rv_notification_list = itemView.findViewById(R.id.notification_list)

        }


        fun bindNotificationView(){

        }

    }


}