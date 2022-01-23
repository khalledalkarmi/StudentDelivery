package com.wise.studentdelivery.model

data class Ride(
    val email: String,
    val firstName: String,
    val lastName: String,
    val photo: Photo?,
    val goTime: String,
    val comeBackTime: String,
    val uniName: String?,
    val cityName: String?,
    val neighborhoodName: String?,
    val emptySeats: String,
    val price: String,
    val extraDetails: String,
    val genderSpecific: String,
    val haveCar: Car,
    val isPrivate: String
)
