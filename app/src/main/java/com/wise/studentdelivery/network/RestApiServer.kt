package com.wise.studentdelivery.network

import com.wise.studentdelivery.model.Photo
import com.wise.studentdelivery.model.Ride
import com.wise.studentdelivery.model.User
import okhttp3.MediaType
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class RestApiServer {

    fun addUser(userData: User, onResult: (String?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.addUser(userData).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    onResult(null)
                    println(t)
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val addedUser = response.body()
                    println(response)
                    onResult(addedUser)
                }
            }
        )
    }

    fun updateUser(userData: User, onResult: (Boolean?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.updateUser(userData).enqueue(
            object : Callback<Boolean> {
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    onResult(null)
                    println(t)
                }

                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    val addedUser = response.body()
                    println(response)
                    onResult(addedUser)
                }
            }
        )
    }

    fun getAllRide(onResult: (List<Ride>?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getAllRide().enqueue(
            object : Callback<List<Ride>> {
                override fun onResponse(call: Call<List<Ride>>, response: Response<List<Ride>>) {
                    val allRide = response.body()
                    onResult(allRide)
                }

                override fun onFailure(call: Call<List<Ride>>, t: Throwable) {
                    onResult(null)
                    println("$t cannot get all ride ")

                }

            }
        )
    }

    fun getRideByUserEmail(email: String, onResult: (Ride?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getRideByEmail(email).enqueue(
            object : Callback<Ride> {
                override fun onResponse(call: Call<Ride>, response: Response<Ride>) {
                    val ride = response.body()
                    onResult(ride)
                }

                override fun onFailure(call: Call<Ride>, t: Throwable) {
                    onResult(null)
                }

            }

        )
    }

    fun addRideByEmail(ride: Ride, email: String, onResult: (String?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.addRide(ride, email).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val addRide = response.body()
                    onResult(addRide)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    onResult(null)
                    println("$t can't add ride")
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

    fun getPIN(email: String, onResult: (String?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getPINCode(email).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val getPIN = response.body()
                    onResult(getPIN)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    onResult(null)
                    println("$t failed in get user by PIN")

                }

            }
        )
    }

    fun updatePassword(email: String, newPassword: String, onResult: (String?) -> Unit) {
        val retrofit = ServiceBuilder.buildService((RestApi::class.java))
        retrofit.updatePassword(email, newPassword).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val updatePassword = response.body()
                    onResult(updatePassword)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    onResult(null)
                    println("$t can't update password")

                }

            }
        )
    }

    fun getUserPassword(email: String, onResult: (String?) -> Unit) {
        val retrofit = ServiceBuilder.buildService((RestApi::class.java))
        retrofit.getUserPassword(email).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val getUserPassword = response.body()
                    onResult(getUserPassword)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {

                }

            }
        )
    }

    fun getImage(email: String, onResult: (Photo?) -> Unit) {
        val retrofit = ServiceBuilder.buildService((RestApi::class.java))
        retrofit.getImage(email).enqueue(
            object : Callback<Photo> {
                override fun onResponse(call: Call<Photo>, response: Response<Photo>) {
                    val getImage = response.body()
                    onResult(getImage)
                }

                override fun onFailure(call: Call<Photo>, t: Throwable) {
                    onResult(null)
                    println("$t can't get user image")
                }

            }
        )
    }
    fun getRideByUni(uni:String,onResult: (List<Ride>?) -> Unit): Unit {
        val retrofit =ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getRideByUni(uni = uni).enqueue(
            object :Callback<List<Ride>>{
                override fun onResponse(call: Call<List<Ride>>, response: Response<List<Ride>>) {
                    val ride = response.body()
                    onResult(ride)
                }

                override fun onFailure(call: Call<List<Ride>>, t: Throwable) {
                    onResult(null)
                    println("$t can't get ride by uni name ")
                }
            }
        )
    }
    fun getUserByEmail(email: String, onResult: (User?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getUserByEmail(email).enqueue(
            object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val user = response.body()
                    onResult(user)
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    onResult(null)
                    println("$t can't get user by email ")

                }
            }
        )
    }

    fun setImageByEmail(imageBitmap: String?, email: String, onResult: (Void?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        val imageFile = File(imageBitmap!!)
        val imageRequestFile =
            MultipartBody.create(MediaType.parse("multipart/form-data"), imageFile)
        val multipartBody =
            MultipartBody.Part.createFormData("image", imageFile.name, imageRequestFile)
        retrofit.setProfileImage(email, multipartBody).enqueue(
            object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    val image = response.body()
                    onResult(image)
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onResult(null)
                    println("$t can't add image user by email ")
                }
            }
        )

    }

    fun sendReport(report: String, onResult: (String?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.sendMailReport(report).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val reportMass = response.body()
                    onResult(reportMass)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    onResult(null)
                    println("$t can't send email report massage to admin ")
                }

            }
        )
    }

    fun setIdImage(imageBitmap: String?, email: String, onResult: (Boolean?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        val imageFile = File(imageBitmap!!)
        val imageRequestFile =
            MultipartBody.create(MediaType.parse("multipart/form-data"), imageFile)
        val multipartBody =
            MultipartBody.Part.createFormData("image", imageFile.name, imageRequestFile)
        retrofit.setIdImage(email, multipartBody).enqueue(
            object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    val image = response.body()
                    onResult(image)
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    onResult(null)
                    println("$t can't add image user by email ")
                }
            }
        )
    }
}