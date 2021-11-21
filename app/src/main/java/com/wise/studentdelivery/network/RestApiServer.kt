package com.wise.studentdelivery.network

import com.wise.studentdelivery.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiServer {
    fun addUser(userData: User, onResult: (User?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.addUser(userData).enqueue(
            object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    onResult(null)
                    println(t)
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val addedUser = response.body()
                    println(response)
                    onResult(addedUser)
                }
            }
        )
    }

    fun getAllUsers(onResult: (List<User>?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getAllUsers().enqueue(
            object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    onResult(null)
                    println("$t failed in get all user ")
                }

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    val findUserByEmail = response.body()
                    println("${response.body()} get all user successful")
                    onResult(findUserByEmail)
                }
            }
        )
    }

    fun checkIfUserExistByEmail(email: String, onResult: (String?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.checkIfUserExist(email).enqueue(
            object : Callback<String> {

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val checkIfUserExistByEmail = response.body()
                    println("${response.body()} get user by email successful")
                    onResult(checkIfUserExistByEmail)

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    onResult(null)
                    println("$t failed in get user by email")
                }

            }
        )
    }
}