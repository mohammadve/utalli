package com.utalli.callBack

import com.utalli.models.IndividualStateDetail
import com.utalli.models.StateDetailsData

interface TripDetailsStateListCallBack {


    fun recyclerViewListClicked(itemDetails: IndividualStateDetail)

}