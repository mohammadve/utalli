package com.utalli.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.utalli.R
import com.utalli.models.CardItems

class CardsAdapter : BaseAdapter() {
    lateinit var context : Context
    var data: ArrayList<CardItems>?=null



    fun setData(context:Context,cardItems: ArrayList<CardItems> ){
        this.context = context
        this.data = cardItems
    }


    override fun getItem(position: Int): Any {
        return data!!.get(position)
    }



    override fun getItemId(position: Int): Long {
        return position.toLong()
    }



    override fun getCount(): Int {
        return data!!.size
    }



    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View?
        val holder: CardViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_row_list_payment, parent, false)
            holder = CardViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as CardViewHolder
        }

        holder.tvCardNumber.text = data!!.get(position).cardNumber
        holder.tvCardHolderName.text = data!!.get(position).cardHolderName
        holder.tvCvv.text = data!!.get(position).cvv
        holder.tvValidThrough.text = data!!.get(position).validThrough

        return view!!
    }


     inner class CardViewHolder(view: View) {
         var tvCardNumber : TextView
         var tvCardHolderName : TextView
         var  tvCvv : TextView
         var  tvValidThrough :TextView


        init {
            tvCardNumber = view.findViewById(R.id.tv_card_number) as TextView
            tvCardHolderName = view.findViewById(R.id.tv_card_holder_name) as TextView
            tvCvv = view.findViewById(R.id.tv_cvv) as TextView
            tvValidThrough  = view.findViewById(R.id.tv_validThrough) as TextView

        }
    }



}