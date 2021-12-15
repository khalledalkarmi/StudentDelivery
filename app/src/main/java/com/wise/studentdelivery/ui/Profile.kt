package com.wise.studentdelivery.ui

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.wise.studentdelivery.R
import com.wise.studentdelivery.network.RestApiServer

class Profile : AppCompatActivity() {
    private lateinit var profileImage:ImageView
    private lateinit var useNameProfile:EditText
    private lateinit var phoneNumberProfile:EditText
    private lateinit var emailProfile:EditText
    private lateinit var universityProfile:EditText
    private lateinit var addressProfile:EditText
    private lateinit var saveProfileButton: Button
    private lateinit var apiServer: RestApiServer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileImage = findViewById(R.id.profile_image)
        useNameProfile = findViewById(R.id.user_name_profile)
        phoneNumberProfile = findViewById(R.id.phone_number_profile)
        emailProfile = findViewById(R.id.Email_profile)
        universityProfile = findViewById(R.id.university_name_profile)
        addressProfile = findViewById(R.id.address_profile)
        saveProfileButton = findViewById(R.id.save_profile)

        apiServer = RestApiServer()
        //TODO: pass email to get user information
        apiServer.getUserByEmail("khalled_95@hotmail.com"){ user ->
            if (user != null) {
               apiServer.getImage("khalled_95@hotmail.com") {
                   val imageBytes = Base64.decode(it?.data, Base64.DEFAULT)
                   val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                   profileImage.setImageBitmap(decodedImage)
               }
                useNameProfile.setText(user.firstName+" "+user.lastName)
                phoneNumberProfile.setText(user.phoneNumber)
                emailProfile.setText(user.email)
                universityProfile.setText(user.uniName)
                addressProfile.setText(user.address.city+" "+user.address.country)
            }
        }
    }
}