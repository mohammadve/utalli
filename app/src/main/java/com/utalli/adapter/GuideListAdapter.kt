package com.utalli.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utalli.R
import com.utalli.activity.GuideProfileDetailsActivity
import com.utalli.models.GuideListModel

class GuideListAdapter(var mcontext: Context, var guidesList: List<GuideListModel>) :
    RecyclerView.Adapter<GuideListAdapter.GuideViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideListAdapter.GuideViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_list_guides, parent, false)
        return GuideViewHolder(view)
    }

    override fun getItemCount(): Int {
        return guidesList.size
    }

    override fun onBindViewHolder(holder: GuideListAdapter.GuideViewHolder, position: Int) {

        holder.bind(guidesList.get(position))

        holder.itemView.setOnClickListener {
            var intent = Intent(mcontext, GuideProfileDetailsActivity::class.java)
            intent.putExtra("guideId", guidesList.get(position).guide_info.id.toInt())
            mcontext.startActivity(intent)
        }

    }


    inner class GuideViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvGuideName: TextView
        var tvRatingValue: TextView
        var tvLanguages: TextView
        var tvGuideCharges: TextView
        var tvGuideChargesGroup: TextView

        init {
            tvGuideName = view.findViewById(R.id.tv_guide_name)
            tvRatingValue = view.findViewById(R.id.txt_ratingValue)
            tvLanguages = view.findViewById(R.id.tv_languages)
            tvGuideCharges = view.findViewById(R.id.tv_guide_charges)
            tvGuideChargesGroup = view.findViewById(R.id.tv_guide_charges_group)
        }


        fun bind(guideItem: GuideListModel) {
            tvGuideName.text = guideItem.guide_info.name
            tvRatingValue.text = guideItem.guide_info.guiderating
            tvLanguages.text = guideItem.guide_info.lang
            tvGuideCharges.text = "$ " + guideItem.guide_info.guide_private_price
            tvGuideChargesGroup.text = "$ " + guideItem.guide_info.guide_pool_price

        }


    }


}