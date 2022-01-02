package com.wise.studentdelivery

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import com.wise.studentdelivery.network.RestApiServer
import com.wise.studentdelivery.ui.ForgotPassword
import com.wise.studentdelivery.ui.MainFunActivity
import com.wise.studentdelivery.ui.Signup

/*
TODO: make request to rewrite password
TODO: check if internet connected
 */
class MainActivity : AppCompatActivity() {
    private lateinit var signupButton: TextView
    private lateinit var forgetPasswordButton: TextView
    private lateinit var errorTextView: TextView
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
        errorTextView = findViewById(R.id.error_text)
        errorTextView.isVisible = false
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
                    if (it.toString() == password){
                        val intent =Intent(this,MainFunActivity::class.java)
                        intent.putExtra("email",email)
                        errorTextView.isVisible = false
                        startActivity(intent)
                    }else{
                        errorTextView.setTextColor(Color.RED)
                        errorTextView.isVisible = true
                    }
                }
            }else {
                errorTextView.setTextColor(Color.RED)
                errorTextView.isVisible = true
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