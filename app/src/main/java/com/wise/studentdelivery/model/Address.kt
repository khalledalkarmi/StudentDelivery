package com.wise.studentdelivery.model

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("country") val country: String,
    @SerializedName("city") val city: String
) {

}
