package com.wise.studentdelivery.model

import java.time.LocalDateTime

data class User(
    val firstName: String,
    val lastName: String,
    val password: String,
    val gender: Gender,
    val email: String,
    val uniName:String,
    val phoneNumber: String,
    val graduateYear:String,
    val address:Address,
    val haveCar:Car,
    val createdTime: LocalDateTime
    )
