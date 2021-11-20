package com.wise.studentdelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.wise.studentdelivery.network.RestApiServer

class MainActivity : AppCompatActivity() {
    lateinit var signupButton: TextView
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var loginButton: Button
    lateinit var apiServer: RestApiServer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = findViewById(R.id.email_text)
        password = findViewById(R.id.password_text)
        loginButton = findViewById(R.id.login_button)
        signupButton = findViewById(R.id.signup_button)
        apiServer = RestApiServer()
        signupButton.setOnClickListener(View.OnClickListener { v ->
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        })

        loginButton.setOnClickListener { v ->

        TODO("not implemented yet :)")
        }
    }


}