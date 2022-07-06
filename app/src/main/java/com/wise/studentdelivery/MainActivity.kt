package com.wise.studentdelivery

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import at.favre.lib.crypto.bcrypt.BCrypt
import com.wise.studentdelivery.network.RestApiServer
import com.wise.studentdelivery.ui.ForgotPassword
import com.wise.studentdelivery.ui.MainFunActivity
import com.wise.studentdelivery.ui.Signup

/*
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
    private lateinit var test:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        test = findViewById(R.id.test_b)
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

        test.setOnClickListener {
            Toast.makeText(this,"tsest",Toast.LENGTH_LONG).show()
        }

        loginButton.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            println("$password password from local")
            if (checkIfUserExist(email).toString().isNotEmpty()) {
                apiServer.getUserPassword(email) {
                    println("$it password for $email")
                    //if (BCrypt.verifyer().verify(password.toCharArray(),it.toString()).verified){
                    if (password==it.toString()){
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