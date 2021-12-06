package com.wise.studentdelivery.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.wise.studentdelivery.R
import com.wise.studentdelivery.network.RestApiServer
import com.wise.studentdelivery.utilities.Validator


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class ForgotPasswordFragment : Fragment() {
    lateinit var forgetPasswordButton:Button
    lateinit var emailAddress:EditText
    private val validator = Validator()
    lateinit var apiServer: RestApiServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiServer = RestApiServer()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_forget_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forgetPasswordButton = view.findViewById(R.id.forget_password_fragment_button)
        emailAddress = view.findViewById(R.id.email_address_forget_fragment)

        forgetPasswordButton.setOnClickListener {
            if (emailValdir()){
                apiServer.getPIN(emailAddress.text.toString()){
                    println(it)
                }
            }

        }
    }

    private fun emailValdir():Boolean {
        return validator.isValidEmail(emailAddress)
    }

}