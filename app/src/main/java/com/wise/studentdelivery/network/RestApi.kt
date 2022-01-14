package com.wise.studentdelivery.network

import com.wise.studentdelivery.model.Photo
import com.wise.studentdelivery.model.Ride
import com.wise.studentdelivery.model.User
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface RestApi {
    @POST("api/v1/users/add")
    fun addUser(@Body user: User): Call<Boolean>

    @POST("api/v1/users/updateuser")
    fun updateUser(@Body user: User): Call<Boolean>

    @POST("api/v1/users/addride/{email}")
    fun addRide(@Body ride: Ride, @Path("email") email: String): Call<User>

    @POST("api/v1/users/updatePassword/{email}/{newPassword}")
    fun updatePassword(
        @Path("email") email: String,
        @Path("newPassword") newPassword: String
    ): Call<String>

    @GET("api/v1/users")
    fun getAllUsers(): Call<List<User>>

    @GET("api/v1/users/getallride")
    fun getAllRide(): Call<List<Ride>>

    @GET("api/v1/users/email/{email}")
    fun checkIfUserExist(@Path("email") email: String): Call<String>

    @GET("api/v1/users/pin/{email}")
    fun getPINCode(@Path("email") email: String): Call<String>

    @GET("api/v1/users/getpassword/{email}")
    fun getUserPassword(@Path("email") email: String): Call<String>

    @GET("api/v1/users/getimage/{email}")
    fun getImage(@Path("email") email: String): Call<Photo>

    @GET("api/v1/users/{email}")
    fun getUserByEmail(@Path("email") email: String): Call<User>

    @GET("api/v1/users/getride/{email}")
    fun getRideByEmail(@Path("email") email: String): Call<Ride>

    @POST("api/v1/users/addimage/{email}")
    @Multipart
    fun setProfileImage(
        @Path("email") email: String,
        @Part image: MultipartBody.Part
    ): Call<Void>

    @GET("api/v1/users/report/{report}")
    fun sendMailReport(@Path("report") reportBody: String): Call<String>
}