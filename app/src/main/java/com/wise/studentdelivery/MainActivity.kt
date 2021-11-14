package com.wise.studentdelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var signupButton:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signupButton = findViewById(R.id.signup_button)

        signupButton.setOnClickListener(View.OnClickListener {
                v ->
            val intent = Intent(this,Singup::class.java )
            startActivity(intent)

        })
    }
}