package com.wise.studentdelivery.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilder {
    private val client = OkHttpClient.Builder().connectTimeout(500,TimeUnit.SECONDS).build()
    // to make retrofit to accept all body not only json
    private val gsonBuilder = GsonBuilder().setLenient()
    private val gson: Gson = gsonBuilder.create()


// and in you adapter set this instance


    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/") // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}