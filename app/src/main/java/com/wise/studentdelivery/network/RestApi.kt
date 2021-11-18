package com.wise.studentdelivery.network

import com.wise.studentdelivery.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface RestApi {
    @POST("api/v1/users/add")
    fun addUser(@Body user:User):Call<User>
}