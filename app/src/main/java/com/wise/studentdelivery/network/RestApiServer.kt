package com.wise.studentdelivery.network

import com.wise.studentdelivery.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RestApiServer {
    fun addUser(userData: User, onResult: (User?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.addUser(userData).enqueue(
            object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    onResult(null)
                    println(t)
                }
                override fun onResponse( call: Call<User>, response: Response<User>) {
                    val addedUser = response.body()
                    println(response)
                    onResult(addedUser)
                }
            }
        )
    }
}