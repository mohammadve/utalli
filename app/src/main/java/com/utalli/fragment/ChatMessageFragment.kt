package com.utalli.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utalli.R
import com.utalli.adapter.ChatMessageAdapter
import com.utalli.helpers.AppPreference
import kotlinx.android.synthetic.main.fragment_chat_message.*
import com.sendbird.android.SendBirdException
import com.sendbird.android.User
import com.sendbird.android.SendBird.ConnectHandler
import com.sendbird.android.SendBird
import com.utalli.models.UserModel
import com.sendbird.android.GroupChannel
import com.sendbird.android.GroupChannelListQuery
import com.utalli.helpers.Utils


class ChatMessageFragment : Fragment() {
    var chatListAdapter: ChatMessageAdapter? = null
    var dateTimeList = ArrayList<String>()
    var mssg = ArrayList<String>()
    var name = ArrayList<String>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat_message, container, false)


        var user = AppPreference.getInstance(activity!!).getUserData() as UserModel

        SendBird.connect("" + user!!.id, ConnectHandler { user, e ->
            if (e != null) {    // Error.
                return@ConnectHandler
            }
        })


        val channelListQuery = GroupChannel.createMyGroupChannelListQuery()
        channelListQuery.isIncludeEmpty = true
        channelListQuery.next(GroupChannelListQuery.GroupChannelListQueryResultHandler { list, e ->
            if (e != null) {    // Error.
                return@GroupChannelListQueryResultHandler
            }

            Utils.showLog("Conversation List size : " + list.size)
            if (list.size == 0) {
                cl_no_conversation_found.visibility = View.VISIBLE
                recyclerView_messageList.visibility = View.GONE
            }

        })


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


        registerReceiver()

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        et_location.text = AppPreference.getInstance(activity!!).getUserLastLocation()
    }

    private fun registerReceiver() {

        val filter = IntentFilter()

        filter.addAction("LOCATION_UPDATED")
        LocalBroadcastManager.getInstance(activity!!).registerReceiver(mReciever, filter)


    }


    var mReciever = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {


            if (et_location != null)
                et_location!!.text = AppPreference.getInstance(activity!!).getUserLastLocation()


        }
    }


}