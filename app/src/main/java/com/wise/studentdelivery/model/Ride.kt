package com.wise.studentdelivery.model

data class Ride(
    val goTime: String,
    val comeBackTime: String,
    val uniName: String,
    val cityName: String,
    val neighborhoodNAme:String,
    val emptySeats:String,
    val price:String,
    val extraDetails:String,
    val isPrivate:Boolean
)
