package com.utalli.models

import java.io.Serializable

class UpcomingTourListModel (

    var id : Int,
    var guideId : Int,
    var UserId : Int,
    var tourReqId : Int,
    var tourStartdate : String ="",
    var tourEnddate : String ="",
    var tourtype : Int,
    var tourcost : String ="",
    var tourdays : Int,
    var tourStatus : Int,
    var tourdates : String =""

):Serializable