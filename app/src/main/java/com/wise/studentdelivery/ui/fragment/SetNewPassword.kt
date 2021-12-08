package com.wise.studentdelivery.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.wise.studentdelivery.R
import com.wise.studentdelivery.network.RestApiServer
import com.wise.studentdelivery.utilities.Validator

class SetNewPassword : Fragment() {
    private lateinit var createNewPasswordButton: Button
    private lateinit var newPasswordText: EditText
    private lateinit var repeatNewPasswordText: EditText
    private lateinit var validator: Validator
    private lateinit var email:String
    lateinit var apiServer: RestApiServer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        validator = Validator()
        apiServer = RestApiServer()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        email = requireArguments().getString("email").toString()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_new_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createNewPasswordButton = view.findViewById(R.id.create_new_password_button)
        newPasswordText = view.findViewById(R.id.new_passwor_text)
        repeatNewPasswordText = view.findViewById(R.id.repeat_new_passwor_text)

        createNewPasswordButton.setOnClickListener {

            if (validator.isValidPassword(newPasswordText) && validator.isValidPassword(repeatNewPasswordText)) {
                val password = newPasswordText.text.toString()
                val rePassword = repeatNewPasswordText.text.toString()
                if ((password == rePassword)) {
                   // TODO("Implement setNewPasswordRequest")
                    apiServer.updatePassword(email,password){
                       if (it == "true"){
                           // TODO: go to signup activity
                       }else {
                           // TODO: handle error
                       }
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Password must be match repeat password",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}


