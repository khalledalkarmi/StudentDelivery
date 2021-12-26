package com.wise.studentdelivery.utilities

import android.util.Patterns
import android.widget.EditText
import android.widget.RadioButton
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class Validator {
    companion object {

        // Default validation messages

        private val NAME_VALIDATION_MSG = "Enter a valid name"
        private val EMAIL_VALIDATION_MSG = "Enter a valid email address"
        private val PHONE_VALIDATION_MSG = "Enter a valid phone number"
        private val STUDENTID_VALIDATION_MSG = "Enter a valid student ID"
        private val GENDER_VALIDATION_MSG = "can't leave it empty"

        private val PASSWORD_POLICY = """Password should be minimum 8 characters long,
            |should contain at least one capital letter,
            |at least one small letter,
            |at least one number and
            |at least one special character among ~!@#$%^&*()-_=+|[]{};:'\",<.>/?""".trimMargin()
    }

    private fun getText(data: Any): String {
        var string = ""
        if (data is EditText)
            string = data.text.toString()
        else if (data is String)
            string = data
        return string
    }

    fun isValidPassword(data: Any, updateUI: Boolean = true): Boolean {
        val str = getText(data)
        var valid = true

        // Password policy check
        // Password should be minimum minimum 8 characters long
        if (str.length < 8) {
            valid = false
        }
        // Password should contain at least one number
        var exp = ".*[0-9].*"
        var pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        var matcher = pattern.matcher(str)
        if (!matcher.matches()) {
            valid = false
        }

        // Password should contain at least one capital letter
        exp = ".*[A-Z].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(str)
        if (!matcher.matches()) {
            valid = false
        }

        // Password should contain at least one small letter
        exp = ".*[a-z].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(str)
        if (!matcher.matches()) {
            valid = false
        }

        // Password should contain at least one special character
        // Allowed special characters : "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
        exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(str)
        if (!matcher.matches()) {
            valid = false
        }

        // Set error if required
        if (updateUI) {
            val error: String? = if (valid) null else PASSWORD_POLICY
            setError(data, error)

        }

        return valid
    }


    fun isValidName(data: Any, updateUI: Boolean = true): Boolean {
        val string = getText(data)
        val valid = string.trim().length > 2 && string.all { it.isLetter() }

        if (updateUI) {
            val error: String? = if (valid) null else NAME_VALIDATION_MSG
            setError(data, error)
        }

        return valid
    }

    fun isValidPhone(data: Any, updateUI: Boolean = true): Boolean {
        val str = getText(data)
        val valid = Patterns.PHONE.matcher(str).matches()

        // Set error if required
        if (updateUI) {
            val error: String? = if (valid) null else PHONE_VALIDATION_MSG
            setError(data, error)
        }

        return valid
    }

    fun isValidEmail(data: Any, updateUI: Boolean = true): Boolean {
        val str = getText(data)
        val valid = Patterns.EMAIL_ADDRESS.matcher(str).matches()

        // Set error if required
        if (updateUI) {
            val error: String? = if (valid) null else EMAIL_VALIDATION_MSG
            setError(data, error)
        }

        return valid
    }

    fun isValidStudentID(data: Any, updateUI: Boolean = true): Boolean {
        val studentID = getText(data)
        val valid = studentID.all { it.isDigit() } && studentID.trim().length > 8
        if (updateUI) {
            val error: String? = if (valid) null else STUDENTID_VALIDATION_MSG
            setError(data, error)
        }

        return valid
    }

    fun isMatch(data1: Any, data2: Any, updateUI: Boolean = true): Boolean {
        val password = getText(data1)
        val rePassword = getText(data2)
        val valid = password.equals(rePassword)
        if (updateUI) {
            val error: String? = if (valid) null else STUDENTID_VALIDATION_MSG
            setError(data2, error)
        }

        return valid
    }


    private fun setError(data: Any, error: String?) {
        if (data is EditText) {
            if (data.parent.parent is TextInputLayout) {
                (data.parent.parent as TextInputLayout).error = error
            } else {
                data.error = error
            }
        }
    }

    private fun genderSelected(male: RadioButton, female: RadioButton): Boolean {

        return male.isSelected || female.isSelected
    }

    private fun isNotNull(data: Any,updateUI: Boolean = true):Boolean{
        val str = getText(data)
        val valid = str.isNotEmpty()
        if (updateUI) {
            val error: String? = if (valid) null else EMAIL_VALIDATION_MSG
            setError(data, error)
        }
        return valid
    }


}