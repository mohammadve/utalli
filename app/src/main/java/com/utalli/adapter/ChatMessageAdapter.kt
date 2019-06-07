package com.utalli.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.utalli.R
import com.utalli.activity.ChatActivity
import com.utalli.helpers.Utils.Companion.formatToYesterdayOrToday

class ChatMessageAdapter (var mcontext : Context, var dateTime : ArrayList<String>, var msgg : ArrayList<String>) : RecyclerView.Adapter<ChatMessageAdapter.MessageViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageAdapter.MessageViewHolder {
       var view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_list_chat_message, parent, false)
        return MessageViewHolder(view)
    }



    override fun getItemCount(): Int {
       return msgg.size
    }



    override fun onBindViewHolder(holder: ChatMessageAdapter.MessageViewHolder, position: Int) {

        Log.e("TAG", "positionnn  ===== "+position)
        Log.e("TAG","tvMsgDay ===== "+dateTime.get(position))
       // Log.e("TAG","tvMsgDay - 1 ===== "+(dateTime.get(position - 1)))

        holder.tvUser_name.setText(msgg.get(position))
        holder.tvMsgDay.setText(formatToYesterdayOrToday(dateTime.get(position)))



        if (position > 0) {
            if (dateTime.get(position).equals(dateTime.get(position - 1))) {
                holder.tvMsgDay.setVisibility(View.GONE);
            } else {
                holder.tvMsgDay.setVisibility(View.VISIBLE);
            }
        } else {
            holder.tvMsgDay.setVisibility(View.VISIBLE);
        }



        holder.clMain.setOnClickListener {
           var intent = Intent(mcontext, ChatActivity::class.java)
            intent.putExtra("userName",msgg.get(position))
            mcontext.startActivity(intent)
        }


    }




    inner class MessageViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        var tvMsgDay: TextView
        var  tvUser_name : TextView
        var clMain : ConstraintLayout

        init {
            tvMsgDay = itemView.findViewById(R.id.tv_msg_day)
            tvUser_name = itemView.findViewById(R.id.tv_user_name)
            clMain = itemView.findViewById(R.id.cl_main)
        }

    }

}