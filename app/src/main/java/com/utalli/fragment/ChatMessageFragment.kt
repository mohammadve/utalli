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
    var dateTimeList = ArrayList<String>()
    var mssg = ArrayList<String>()
    var name = ArrayList<String>()





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat_message,container,false)

        var recyclerView_messageList = view!!.findViewById<RecyclerView>(R.id.recyclerView_messageList)
        recyclerView_messageList.layoutManager = LinearLayoutManager(activity)

        dateTimeList!!.add("03/06/2019")
        dateTimeList!!.add("03/06/2019")
        dateTimeList!!.add("03/06/2019")
        dateTimeList!!.add("02/06/2019")
        dateTimeList!!.add("03/06/2019")
        dateTimeList!!.add("02/06/2019")
        dateTimeList!!.add("03/06/2019")
        dateTimeList!!.add("30/06/2019")
        dateTimeList!!.add("03/06/2019")
        dateTimeList!!.add("01/06/2019")
        dateTimeList!!.add("01/06/2019")
        dateTimeList!!.add("30/05/2019")

       // name


        mssg!!.add("XYZ 123")
        mssg!!.add("XYZ 123")
        mssg!!.add("XYZ 123")
        mssg!!.add("XYZ 123")
        mssg!!.add("XYZ 123")
        mssg!!.add("XYZ 123")
        mssg!!.add("XYZ 123")
        mssg!!.add("XYZ 123")
        mssg!!.add("XYZ 123")
        mssg!!.add("XYZ 123")
        mssg!!.add("XYZ 123")
        mssg!!.add("XYZ 123")


        chatListAdapter = activity?.let { ChatMessageAdapter(it, dateTimeList, mssg) }
        recyclerView_messageList.adapter = chatListAdapter

        return view
    }






}