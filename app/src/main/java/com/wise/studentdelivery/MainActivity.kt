package com.wise.studentdelivery

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import com.wise.studentdelivery.network.RestApiServer
import com.wise.studentdelivery.ui.ForgotPassword
import com.wise.studentdelivery.ui.Signup

/*
TODO: make request to rewrite password
TODO: check if internet connected by add fragment on mainActivity
 */
class MainActivity : AppCompatActivity() {
    private lateinit var signupButton: TextView
    private lateinit var forgetPasswordButton: TextView
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    lateinit var errorText: TextView
    private lateinit var apiServer: RestApiServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        errorText = findViewById(R.id.error_text)
        email = findViewById(R.id.email_text)
        password = findViewById(R.id.password_text)
        loginButton = findViewById(R.id.login_button)
        signupButton = findViewById(R.id.signup_button)
        forgetPasswordButton = findViewById(R.id.forget_password_text)
        apiServer = RestApiServer()

        errorText.isVisible = false
        signupButton.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val email = email.text.toString()
            if (checkIfUserExist(email).toString().isNotEmpty()) {
                apiServer.getUserPassword(email) {
                    println("$it password from server for $email")
                    if (it.toString() == password.text.toString()) {
                        //TODO: create request activity and goto it
                        println("password ${it.toString()} is match the $email password")
                        errorText.isVisible = false
                    } else {
                        errorText.isVisible = true
                        errorText.setText(R.string.email_error)
                        errorText.setTextColor(Color.RED)
                    }
                }
            }

        }

        forgetPasswordButton.setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }
    }

    private fun checkIfUserExist(email: String) {
        return apiServer.checkIfUserExistByEmail(email = email) {
            if (it.isNullOrEmpty()) {
                errorText.setText(R.string.email_error)
                errorText.setTextColor(Color.RED)
            }
        }
    }
}