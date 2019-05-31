package com.utalli.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utalli.R
import com.utalli.activity.GuideProfileDetailsActivity

class GuideListAdapter(var mcontext : Context) : RecyclerView.Adapter<GuideListAdapter.GuideViewHolder>(){





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideListAdapter.GuideViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_list_guides,parent,false)
        return GuideViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: GuideListAdapter.GuideViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            var intent = Intent(mcontext, GuideProfileDetailsActivity::class.java)
            mcontext.startActivity(intent)
        }

    }


    inner class GuideViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        init {

        }

    }


}