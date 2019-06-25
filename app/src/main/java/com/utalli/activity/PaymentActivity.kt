package com.utalli.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import com.utalli.R
import com.utalli.adapter.CardsAdapter
import com.utalli.helpers.Utils
import com.utalli.models.CardItems
import com.utalli.viewModels.PaymentViewModel
import kotlinx.android.synthetic.main.activity_payment.toolbar_payment
import kotlinx.android.synthetic.main.activity_payment_new.*
import link.fls.swipestack.SwipeStack
import java.util.ArrayList



class PaymentActivity : AppCompatActivity(), View.OnClickListener {

    var currentPosition: Int? = 0
    var cardStack: SwipeStack? = null
    var cardItems = ArrayList<CardItems>()
    var cardsAdapter: CardsAdapter? = null
    var paymentViewModel : PaymentViewModel ? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_new)

        initViews()

    }

    private fun initViews() {

        toolbar_payment.title = ""
        toolbar_payment.setNavigationIcon(R.drawable.arrow_back_white)
        setSupportActionBar(toolbar_payment)
        toolbar_payment.setNavigationOnClickListener { finish() }

        paymentViewModel = ViewModelProviders.of(this).get(PaymentViewModel::class.java)

        cardStack = findViewById(R.id.cardStack) as SwipeStack

        btn_add_card.setOnClickListener(this)

       // setCardStackAdapter()

        cardStack!!.setListener(object : SwipeStack.SwipeStackListener {

            override fun onViewSwipedToLeft(position: Int) {

                val leftSwipedElement = cardsAdapter!!.getItem(position)
                Log.e("TAG", "leftSwipedElement == "+leftSwipedElement.toString())
               // currentPosition = position - 1
            }

            override fun onViewSwipedToRight(position: Int) {

                val rigthSwipedElement = cardsAdapter!!.getItem(position)
                Log.e("TAG", "RigthSwipedElement  == "+rigthSwipedElement.toString())
                //currentPosition = position + 1
            }

            override fun onStackEmpty() {
                cardStack!!.resetStack()
                Log.e("TAG", "swipe onStackEmpty == ")
            }
        })


    }

    override fun onResume() {
        super.onResume()

        setCardStackAdapter()
    }




    public fun setCardStackAdapter() {

        paymentViewModel!!.getCardDetails(this).observe(this, Observer {

            if(it!= null && it.has("status") && it.get("status").asString.equals("1")){

                if(it.has("data") && it.get("data") is JsonArray){

                    val type = object : TypeToken<List<CardItems>>() {}.type
                    var cardItemLists = Gson().fromJson<ArrayList<CardItems>>(it.get("data"), type)
                   // cardItems.addAll(cardItemLists)

                    if(cardItemLists != null && cardItemLists.size > 0){
                        cardStack!!.setVisibility(View.VISIBLE)
                        layout_no_card.visibility = View.GONE

                        if (cardsAdapter == null) {
                            cardsAdapter = CardsAdapter()
                            cardStack!!.setAdapter(cardsAdapter)
                            cardsAdapter!!.setData(this, cardItemLists)
                        }
                        else {
                            cardsAdapter!!.notifyDataSetChanged()
                        }

                    } else if(cardItemLists!!.size == 0){
                        layout_no_card.visibility = View.VISIBLE
                        cardStack!!.setVisibility(View.GONE)
                    }

                }

            }

            else {
                Utils.showToast(this, resources.getString(R.string.msg_common_error))
            }


        })



        // var data1 =


        /*cardItems!!.add(CardItems("1234    3456    4567    4567", "Huyen 1", "234","10/35"))
        cardItems!!.add(CardItems("1234    3456    4567    4567", "DoHa 2", "133","10/25"))
        cardItems!!.add(CardItems("1234    3456    4567    4567", "DongNhi 3", "133","10/25"))
        cardItems!!.add(CardItems("1234    3456    4567    4567", "LeQuyen 4", "133","10/25"))
        cardItems!!.add(CardItems("1234    3456    4567    4567", "Phuong 5", "133","10/25"))
        cardItems!!.add(CardItems("1234    3456    4567    4567", "Phuon 6", "133","10/25"))
        cardItems!!.add(CardItems("1234    3456    4567    4567", "HaHo 7", "133","10/25"))*/



       /* if (cardItems!!.size == 0) {
            layout_no_card.visibility = View.VISIBLE
            cardStack!!.setVisibility(View.GONE)

        } else {
            cardStack!!.setVisibility(View.VISIBLE)
            layout_no_card.visibility = View.GONE

            if (cardsAdapter == null) {
                cardsAdapter = CardsAdapter()
                cardStack!!.setAdapter(cardsAdapter)
                cardsAdapter!!.setData(this, cardItems!!)
            } else {
                cardsAdapter!!.notifyDataSetChanged()
            }
        }*/


    }


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btn_add_card -> {
                var intent = Intent(this, AddPaymentCardActivity::class.java)
                startActivity(intent)
               // startActivityForResult(intent, 201)
            }
        }

    }


  /*  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data!= null && requestCode == 201){

            var  cardDetailsNew = data.getSerializableExtra("cardItemsList") as ArrayList<CardItems>

            cardItems!!.addAll(cardDetailsNew)
            setCardStackAdapter()

        }


    }*/


}