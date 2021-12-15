package com.wise.studentdelivery.ui

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import com.wise.studentdelivery.R
import com.wise.studentdelivery.network.RestApiServer

class MainProfile : AppCompatActivity() {
    private lateinit var profileImage :ImageView
    private lateinit var myRequestButton: Button
    private lateinit var savedRequestButton: Button
    private lateinit var accountSittingButton:Button
    private lateinit var reportProblemButton:Button
    private lateinit var apiServer: RestApiServer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_profile)
        profileImage = findViewById(R.id.main_profile_image)
        myRequestButton = findViewById(R.id.my_requset)
        savedRequestButton = findViewById(R.id.saved_request)
        accountSittingButton = findViewById(R.id.account_settings)
        reportProblemButton = findViewById(R.id.reporta_problem)
        apiServer=RestApiServer()
        //TODO: pass email from any activity
            apiServer.getImage("khalled_95@hotmail.com"){
                println(it.toString())
            if (it != null) {
                val imageBytes = Base64.decode(it.data, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                profileImage.setImageBitmap(decodedImage)
                }
        }

    }


}