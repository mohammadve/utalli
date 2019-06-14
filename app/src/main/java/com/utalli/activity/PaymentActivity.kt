package com.utalli.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.utalli.R
import com.utalli.adapter.CardsAdapter
import com.utalli.models.CardItems
import kotlinx.android.synthetic.main.activity_payment.toolbar_payment
import kotlinx.android.synthetic.main.activity_payment_new.*
import link.fls.swipestack.SwipeStack
import java.util.ArrayList

class PaymentActivity : AppCompatActivity(), View.OnClickListener {

    var currentPosition: Int? = 0
    var cardStack: SwipeStack? = null
    var cardItems = ArrayList<CardItems>()
    var cardsAdapter: CardsAdapter? = null

    var str_CardNumber: String? = null
    var str_CardHolderName: String? = null
    var str_Cvv: String? = null
    var str_Validthrough: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_new)


        /* str_CardNumber = intent.getStringExtra("CardNumber")
         str_CardHolderName = intent.getStringExtra("CardHolderName")
         str_Cvv = intent.getStringExtra("Cvv")
         str_Validthrough = intent.getStringExtra("Validthrough")*/


        initViews()

    }

    private fun initViews() {

        toolbar_payment.title = ""
        toolbar_payment.setNavigationIcon(R.drawable.arrow_back_white)
        setSupportActionBar(toolbar_payment)
        toolbar_payment.setNavigationOnClickListener { finish() }


        cardStack = findViewById(R.id.cardStack) as SwipeStack

        btn_add_card.setOnClickListener(this)

        setCardStackAdapter()

        cardStack!!.setListener(object : SwipeStack.SwipeStackListener {

            override fun onViewSwipedToLeft(position: Int) {
                Log.e("TAG", "swipe left == ")
                currentPosition = position - 1
            }

            override fun onViewSwipedToRight(position: Int) {
                Log.e("TAG", "swipe right == ")
                currentPosition = position + 1
            }

            override fun onStackEmpty() {
                cardStack!!.resetStack()
                Log.e("TAG", "swipe onStackEmpty == ")
            }
        })


    }

    public fun setCardStackAdapter() {


        // var data1 =


        /*cardItems!!.add(CardItems("1234    3456    4567    4567", "Huyen 1", "234","10/35"))
        cardItems!!.add(CardItems("1234    3456    4567    4567", "DoHa 2", "133","10/25"))
        cardItems!!.add(CardItems("1234    3456    4567    4567", "DongNhi 3", "133","10/25"))
        cardItems!!.add(CardItems("1234    3456    4567    4567", "LeQuyen 4", "133","10/25"))
        cardItems!!.add(CardItems("1234    3456    4567    4567", "Phuong 5", "133","10/25"))
        cardItems!!.add(CardItems("1234    3456    4567    4567", "Phuon 6", "133","10/25"))
        cardItems!!.add(CardItems("1234    3456    4567    4567", "HaHo 7", "133","10/25"))*/



        if (cardItems!!.size == 0) {
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
        }


    }


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btn_add_card -> {
                var intent = Intent(this, AddPaymentCardActivity::class.java)
                startActivityForResult(intent, 201)
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data!= null && requestCode == 201){

            var  cardDetailsNew = data.getSerializableExtra("cardItemsList") as ArrayList<CardItems>

            cardItems!!.addAll(cardDetailsNew)
            setCardStackAdapter()

        }


    }


}