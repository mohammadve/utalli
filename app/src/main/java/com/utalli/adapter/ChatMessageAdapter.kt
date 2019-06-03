package com.utalli.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utalli.R

class ChatMessageAdapter (var mcontext : Context) : RecyclerView.Adapter<ChatMessageAdapter.MessageViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageAdapter.MessageViewHolder {
       var view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_list_chat_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int {
       return 30
    }

    override fun onBindViewHolder(holder: ChatMessageAdapter.MessageViewHolder, position: Int) {

    }




    inner class MessageViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){




    }

}