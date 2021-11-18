package com.wise.studentdelivery.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
//TODO: fix localDataTime
data class User(
    @SerializedName("firstName") val firstName: String,
    @SerializedName ("lastName")val lastName: String,
    @SerializedName ("password")val password: String,
    @SerializedName ("gender")val gender: Gender,
    @SerializedName ("email")val email: String,
    @SerializedName ("uniName")val uniName: String,
    @SerializedName ("phoneNumber")val phoneNumber: String,
    @SerializedName ("graduateYear")val graduateYear: String,
    @SerializedName("address") val address: Address,
    @SerializedName("haveCar") val haveCar: Car,
    @SerializedName("createdTime") val createdTime: LocalDateTime?=null
)
