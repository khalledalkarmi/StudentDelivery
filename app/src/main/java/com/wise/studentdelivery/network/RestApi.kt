package com.wise.studentdelivery.network

import com.wise.studentdelivery.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface RestApi {
    @POST("api/v1/users/add")
    fun addUser(@Body user:User):Call<User>

    @GET("api/v1/users")
     fun getAllUsers():Call<List<User>>
}