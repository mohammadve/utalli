package com.utalli.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utalli.R
import com.utalli.RobotoRegularTextView
import kotlinx.android.synthetic.main.item_famous_location.view.*


class HomeListGuideAdapter(var mcontext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var FAMOUS_LOC_TYPE = 0
    var GUIDE_TYPE = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == FAMOUS_LOC_TYPE) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_famous_location, parent, false)
            return HomeListDestinationViewHolder(v)
        } else {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_row_list_guide_home, parent, false)
            return HomeListGuideViewHolder(v)
        }

    }


    override fun getItemCount(): Int {
        return 10
    }


    override fun getItemViewType(position: Int): Int {
        if (position == 0){
            return FAMOUS_LOC_TYPE
        }
        else{
            return GUIDE_TYPE
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is HomeListDestinationViewHolder) {
            holder.tv_famousLocation.text = "Popular destinations"
            holder.bindLocationView()

        } else if (holder is HomeListGuideViewHolder) {
           // holder.tv_famousLocation.text = "Guides in durban"
            holder.bindGuideView()
        }



    }


    inner class HomeListGuideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var tv_famousLocation : RobotoRegularTextView

        init {
           // tv_famousLocation = itemView.findViewById(R.id.tv_famousLocation)
        }

        fun bindGuideView() {
           /* itemView.setOnClickListener(View.OnClickListener {
                fun onClick(p0: View?) {


                }

            })*/

        }

    }


    inner class HomeListDestinationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rvFamousLocations: RecyclerView
        lateinit var tv_famousLocation : RobotoRegularTextView

        init {
            rvFamousLocations = itemView.findViewById(R.id.rv_famousLocation)
            tv_famousLocation = itemView.findViewById(R.id.tv_famousLocation)

            rvFamousLocations.setHasFixedSize(true)

            var horizontalLayoutManager=LinearLayoutManager(mcontext)
            horizontalLayoutManager.orientation=LinearLayoutManager.HORIZONTAL
            rvFamousLocations.layoutManager=horizontalLayoutManager

            rvFamousLocations.adapter=HomeListDestinationAdapter(mcontext)

        }

        fun bindLocationView() {

        }
    }


}