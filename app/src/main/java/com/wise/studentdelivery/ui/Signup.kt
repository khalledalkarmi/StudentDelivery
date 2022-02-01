package com.wise.studentdelivery.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import at.favre.lib.crypto.bcrypt.BCrypt
import com.wise.studentdelivery.MainActivity
import com.wise.studentdelivery.R
import com.wise.studentdelivery.model.Address
import com.wise.studentdelivery.model.Gender
import com.wise.studentdelivery.model.User
import com.wise.studentdelivery.network.RestApiServer
import com.wise.studentdelivery.utilities.Validator

class Signup : AppCompatActivity(), AdapterView.OnItemSelectedListener {
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
    private lateinit var uniNameSpinner: Spinner
    private lateinit var cityNameSpinner: Spinner
    private lateinit var graduateYear: Spinner
    private lateinit var neighborhoodNameSpinner: Spinner

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
        uniNameSpinner = findViewById(R.id.spUni)
        cityNameSpinner = findViewById(R.id.city_spinner)
        neighborhoodNameSpinner = findViewById(R.id.spNeighborhoods)
        graduateYear = findViewById(R.id.spinner)

        val uniNameArrayAdapter = uniNameSpinner.adapter as ArrayAdapter<String>
        val cityNameArrayAdapter = cityNameSpinner.adapter as ArrayAdapter<String>
        val neighborhoodNameArrayAdapter = neighborhoodNameSpinner.adapter as ArrayAdapter<String>

        val adapter1 = ArrayAdapter.createFromResource(
            this,
            R.array.cities,
            android.R.layout.simple_spinner_item
        )
        cityNameSpinner.adapter = adapter1
        cityNameSpinner.onItemSelectedListener = this

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
            address = Address(neighborhoodNameSpinner.selectedItem.toString(), cityNameSpinner.selectedItem.toString()),
            createdTime = null,
            graduateYear = graduateYear.selectedItem.toString(),
            uniName = uniNameSpinner.selectedItem.toString(),
            studentNumber = studentID.text.toString(),
            photo = null,
            ride = null

        )


        apiServer.addUser(user) {
            println("$it add user")
            when (it) {
                "true" -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
                                or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                or Intent.FLAG_ACTIVITY_NEW_TASK
                    )
                    startActivity(intent)
                }
                "emailDup" -> {
                    email.error = "email already been taken"
                }
                "phoneNumberDup" -> {
                    phoneNumber.error = "Phone number unavailable"
                }
                "studentNumberDup" -> {
                    studentID.error = "Student ID unavailable"
                }
            }
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

    private fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(10, password.toCharArray())
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (cityNameSpinner.selectedItem == "عمان") {
            val adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.amman_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "الزرقاء") {
            val adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.zarqa_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "البلقاء") {
            val adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.balqa_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "العقبة") {
            val adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.aqaba_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "معان") {
            val adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.maan_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "مادبا") {
            val adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.madaba_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "جرش") {
            val adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.jarash_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "عجلون") {
            val adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.ajloun_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "اربد") {
            val adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.irbid_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "الطفيلة") {
            val adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.tafila_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "الكرك") {
            val adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.karak_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "المفرق") {
            val adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.mafraq_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}