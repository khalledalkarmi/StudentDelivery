package com.wise.studentdelivery.model

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("neighborhood") val neighborhood: String,
    @SerializedName("city") val city: String
) {

}
