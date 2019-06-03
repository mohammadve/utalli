package com.utalli.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utalli.R
import com.utalli.adapter.ChatMessageAdapter

class ChatMessageFragment : Fragment(){
    var chatListAdapter: ChatMessageAdapter? = null





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat_message,container,false)

        var recyclerView_messageList = view!!.findViewById<RecyclerView>(R.id.recyclerView_messageList)
        recyclerView_messageList.layoutManager = LinearLayoutManager(activity)

        chatListAdapter = activity?.let { ChatMessageAdapter(it) }
        recyclerView_messageList.adapter = chatListAdapter

        return view
    }






}