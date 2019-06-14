package com.utalli.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.utalli.R
import com.utalli.models.CardItems
import kotlinx.android.synthetic.main.activity_add_payment_card.*
import java.util.ArrayList
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.text.InputFilter





class AddPaymentCardActivity : AppCompatActivity(), View.OnClickListener {

    var aadCardItemsList = ArrayList<CardItems>()
    var strCardNumber : String ?= null
    var strCardHolderName : String ?= null
    var strCvv : String ?= null
    var strValidthrough : String ?= null
    var isDelete : Boolean = false
    var countValuee : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_payment_card)

        toolbar_card.title =""
        toolbar_card.setNavigationIcon(R.drawable.arrow_back_white)
        setSupportActionBar(toolbar_card)
        toolbar_card.setNavigationOnClickListener { finish() }

        initViews()

    }

    private fun initViews() {

        btn_next.setOnClickListener(this)
        btn_submit.setOnClickListener(this)


        ed_editText.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(cs: CharSequence, start: Int, before: Int, count: Int) {
                Log.e("TAG", "onTextChanged  ===  ")
               // Log.e("TAG", "count  ===  "+count + "  start === "+start + "   before === "+before)
                setDataOfCardOnTextChanged(before)
            }

            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                Log.e("TAG", "beforeTextChanged  ===  ")
               // Toast.makeText(applicationContext, "before text change", Toast.LENGTH_LONG).show()
            }

            override fun afterTextChanged(s: Editable) {
                Log.e("TAG", "afterTextChanged  ===  ")
              //  Toast.makeText(applicationContext, "after text change", Toast.LENGTH_LONG).show()
                setCardDataAfterTextChanged(s)
            }
        })

    }

    private fun setCardDataAfterTextChanged(s: Editable) {

        if(countValuee == 0) {
            val source = s.toString()
            val length = source.length
            val stringBuilder = StringBuilder()
            stringBuilder.append(source)
            if (length > 0 && length % 5 == 0) {
                if (isDelete){
                    stringBuilder.deleteCharAt(length - 1)
                }
                else{
                    stringBuilder.insert(length - 1, " ")
                }
                ed_editText.setText(stringBuilder)
                ed_editText.setSelection(ed_editText.getText()!!.length)
            }
            tv_card_number.text = stringBuilder

        }
        else if(countValuee == 1){
            tv_card_holder_name.text = s.toString()
        }
        else if(countValuee == 2){
            tv_cvv.text = s.toString()
        }
        else if(countValuee == 3){
            tv_validThrough.text = s.toString()
        }


    }


    private fun setDataOfCardOnTextChanged(before: Int) {

        Log.e("TAG", "countValuee  ===  "+countValuee)
        if(countValuee == 0){
            tv_common.text = "Card number"
            ed_editText.hint = "Card number"

            val maxLength = 16
            val FilterArray = arrayOfNulls<InputFilter>(1)
            FilterArray[0] = InputFilter.LengthFilter(maxLength)
            ed_editText.setFilters(FilterArray)
            ed_editText.inputType = InputType.TYPE_CLASS_NUMBER
            if(before==0){
                isDelete=false
            }
            else{
                isDelete=true
            }
        }

        else if(countValuee == 1){
            tv_common.text = "Name on card"
            ed_editText.hint = "Name on card"

            val maxLength = 25
            val FilterArray = arrayOfNulls<InputFilter>(1)
            FilterArray[0] = InputFilter.LengthFilter(maxLength)
            ed_editText.setFilters(FilterArray)
            ed_editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
        }
        else if(countValuee == 2){
            tv_common.text = "CVV"
            ed_editText.hint = "CVV"

            val maxLength = 4
            val FilterArray = arrayOfNulls<InputFilter>(1)
            FilterArray[0] = InputFilter.LengthFilter(maxLength)
            ed_editText.setFilters(FilterArray)
            ed_editText.inputType = InputType.TYPE_CLASS_NUMBER
        }
        else if(countValuee == 3){
            tv_common.text = "Valid through"
            ed_editText.hint = "Valid through"
            btn_next.visibility = View.GONE
            btn_submit.visibility = View.VISIBLE

            val maxLength = 10
            val FilterArray = arrayOfNulls<InputFilter>(1)
            FilterArray[0] = InputFilter.LengthFilter(maxLength)
            ed_editText.setFilters(FilterArray)
            ed_editText.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        }


    }


    override fun onClick(v: View?) {

        when(v?.id){
            R.id.btn_next->{
                if(countValuee == 0 && ed_editText.text.toString().equals("") ){
                    Toast.makeText(this,"Please fill card number",Toast.LENGTH_SHORT).show()
                }else if(countValuee == 1 && ed_editText.text.toString().equals("")){
                    Toast.makeText(this,"Please fill name of card",Toast.LENGTH_SHORT).show()
                }else if(countValuee == 2 && ed_editText.text.toString().equals("")){
                    Toast.makeText(this,"Please fill cvv",Toast.LENGTH_SHORT).show()
                }else if(countValuee == 3 && ed_editText.text.toString().equals("")){
                    Toast.makeText(this,"Please fill valid through",Toast.LENGTH_SHORT).show()
                }
                else{
                    countValuee = countValuee+1
                    ed_editText.setText("")
                }

              //  setCountValue(countValuee)

                Log.e("TAG", "countValuee nextClick  ===  "+countValuee)
            }

            R.id.btn_submit ->{
                strCardNumber = tv_card_number.getText().toString()
                strCardHolderName = tv_card_holder_name.text.toString()
                strCvv = tv_cvv.text.toString()
                strValidthrough = tv_validThrough.text.toString()

                if(strCardNumber.equals("")){
                    Toast.makeText(this,"Please enter the card number",Toast.LENGTH_SHORT).show()
                }else if(strCardHolderName.equals("")){
                    Toast.makeText(this,"Please enter the card holder name",Toast.LENGTH_SHORT).show()
                }else if(strCvv.equals("")){
                    Toast.makeText(this,"Please enter the cvv",Toast.LENGTH_SHORT).show()
                }else if(strValidthrough.equals("")){
                    Toast.makeText(this,"Please enter the card validity",Toast.LENGTH_SHORT).show()
                }
                else {
                    var intent = Intent()
                    var dataCardNumber = CardItems(strCardNumber!!,strCardHolderName!!,strCvv!!,strValidthrough!!)
                    aadCardItemsList?.add(dataCardNumber)
                    intent.putExtra("cardItemsList",aadCardItemsList)
                    setResult(201,intent)
                    finish()
                }
            }

        }

    }

    private fun setCountValue(countValuee: Int) {

        if(countValuee == 0){
            tv_common.text = "Card number"
            ed_editText.hint = "Card number"
            ed_editText.inputType = InputType.TYPE_CLASS_NUMBER
         /*   if(before==0){
                isDelete=false
            }
            else{
                isDelete=true
            }*/
        }

        else if(countValuee == 1){
            tv_common.text = "Name on card"
            ed_editText.hint = "Name on card"
        }
        else if(countValuee == 2){
            tv_common.text = "CVV"
            ed_editText.hint = "CVV"
        }

        else if(countValuee == 3){
            tv_common.text = "CVV"
            ed_editText.hint = "CVV"
            btn_next.visibility = View.GONE
            btn_submit.visibility = View.VISIBLE
        }

    }


}