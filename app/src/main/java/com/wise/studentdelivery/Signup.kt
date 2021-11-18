package com.wise.studentdelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.wise.studentdelivery.utilities.Validator

class Signup : AppCompatActivity() {
    lateinit var firstName: EditText
    lateinit var lastName: EditText
    lateinit var studentID: EditText
    lateinit var email: EditText
    private lateinit var password: EditText
    lateinit var rePassword: EditText
    lateinit var phoneNumber:EditText
    lateinit var male: RadioButton
    lateinit var female: RadioButton
    lateinit var signUpButton: Button
    private val validator = Validator()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singup)
        firstName = findViewById(R.id.first_name)
        lastName = findViewById(R.id.last_name)
        studentID = findViewById(R.id.student_id)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        rePassword = findViewById(R.id.rewrite_password)
        phoneNumber = findViewById(R.id.phone_number)
        male = findViewById(R.id.male_radioButton)
        female = findViewById(R.id.female_radioButton)

        signUpButton = findViewById(R.id.signup_button)

        signUpButton.setOnClickListener { v -> signUpValidator() }

    }

    private fun signUpValidator() {
        validator.isValidEmail(email)
        validator.isValidName(firstName)
        validator.isValidName(lastName)
        validator.isValidName(lastName)
        validator.isValidStudentID(studentID)
        validator.isValidPassword(password)
        validator.isValidPassword(rePassword)
        validator.isMatch(password, rePassword)
        val genderValid = validator.genderSelected(male, female)
        validator.isValidPhone(phoneNumber)
        if (!genderValid)
            Toast.makeText(this,"Please select gender",Toast.LENGTH_SHORT).show()


    }


}