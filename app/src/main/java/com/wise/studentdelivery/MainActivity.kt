package com.wise.studentdelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.wise.studentdelivery.network.RestApiServer
import com.wise.studentdelivery.ui.ForgotPassword
import com.wise.studentdelivery.ui.MainFunActivity
import com.wise.studentdelivery.ui.Signup

/*
TODO: make request to rewrite password
TODO: check if internet connected
TODO: check if email not exist and show error in textView
TODO: get password from database and compare it with entered password

 */
class MainActivity : AppCompatActivity() {
    private lateinit var signupButton: TextView
    private lateinit var forgetPasswordButton: TextView
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var apiServer: RestApiServer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = findViewById(R.id.email_text)
        password = findViewById(R.id.password_text)
        loginButton = findViewById(R.id.login_button)
        signupButton = findViewById(R.id.signup_button)
        forgetPasswordButton = findViewById(R.id.forget_password_text)
        apiServer = RestApiServer()
        signupButton.setOnClickListener{
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            if (checkIfUserExist(email).toString().isNotEmpty()) {
                apiServer.getUserPassword(email) {
                    println("$it password for $email")
                    val intent =Intent(this,MainFunActivity::class.java)
                    if (it.toString() == password){
                        startActivity(intent)
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
        return apiServer.checkIfUserExistByEmail(email = email) {}
    }
}