package com.wise.studentdelivery.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import com.wise.studentdelivery.R
import com.wise.studentdelivery.model.Address
import com.wise.studentdelivery.model.Car
import com.wise.studentdelivery.model.Gender
import com.wise.studentdelivery.model.User
import com.wise.studentdelivery.network.RestApiServer
import com.wise.studentdelivery.utilities.Validator

class Signup : AppCompatActivity() {
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var studentID: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var rePassword: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var male: RadioButton
    private lateinit var female: RadioButton
    private lateinit var signUpButton: Button
    private lateinit var genderRadioGroup: RadioGroup

    private val validator = Validator()

    @RequiresApi(Build.VERSION_CODES.O)
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
        genderRadioGroup = findViewById(R.id.gende_rradio_group)

        signUpButton = findViewById(R.id.signup_button)

        signUpButton.setOnClickListener { v ->
            addNewUser()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNewUser() {
        val checkGender = genderRadioGroup.checkedRadioButtonId
        val gender = returnGender(checkGender)
        val apiServer = RestApiServer()
        val user = User(
            firstName = firstName.text.toString(),
            lastName = lastName.text.toString(),
            password = password.text.toString(),
            gender = gender,
            email = email.text.toString(),
            phoneNumber = phoneNumber.text.toString(),
            address = Address("azarqa", "amman"),
            createdTime = null,
            graduateYear = "2024",
            haveCar = Car("BMW", "Green", "400-1203456"),
            uniName = "JU",
            studentNumber = studentID.text.toString(),
            photo = null,
            ride = null

        )


        apiServer.addUser(user) {
            println(user.createdTime)
        }


    }

    private fun signUpValidator(): Boolean {

        val emailValid = validator.isValidEmail(email)
        val firstNameValid = validator.isValidName(firstName)
        val lastNameValid = validator.isValidName(lastName)
        val studentIDValid = validator.isValidStudentID(studentID)
        val passwordValid = validator.isValidPassword(password)
        val rePasswordValid = validator.isValidPassword(rePassword)
        val isPasswordMatches = validator.isMatch(password, rePassword)
        val genderValid = true
        validator.isValidPhone(phoneNumber)

        if (!genderValid)
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()

        return emailValid && firstNameValid && lastNameValid && studentIDValid && passwordValid && rePasswordValid && isPasswordMatches && genderValid
    }

    private fun returnGender(id: Int): Gender? {
        if (id == R.id.female_radioButton)
            return Gender.FEMALE
        if (id == R.id.male_radioButton)
            return Gender.MALE
        return null
    }
}