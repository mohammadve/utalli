package com.utalli.models

import java.io.Serializable

class CardItems(
    var cardNumber : String ,
    var cardHolderName : String,
    var cvv : String,
    var validThrough : String


):Serializable