package com.wise.studentdelivery.model

import com.google.gson.annotations.SerializedName

data class Car(
    @SerializedName("carModel") val carModel: String,
    @SerializedName("carColor") val carColor: String,
    @SerializedName("carNumber")val carNumber: String
) {

}
