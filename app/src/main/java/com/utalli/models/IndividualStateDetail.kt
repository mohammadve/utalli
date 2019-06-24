package com.utalli.models

import java.io.Serializable

data class IndividualStateDetail (
var id:Int,
var name : String ="",
var country_id : Int,

var isSelected : Boolean

):Serializable