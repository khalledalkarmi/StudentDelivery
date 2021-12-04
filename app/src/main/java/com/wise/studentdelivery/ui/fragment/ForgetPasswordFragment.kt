package com.wise.studentdelivery.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.wise.studentdelivery.R
import com.wise.studentdelivery.utilities.Validator
import kotlin.concurrent.fixedRateTimer


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class ForgetPasswordFragment : Fragment() {
    lateinit var forgetPasswordButton:Button
    lateinit var emailAddress:EditText
    private val validator = Validator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_forget_password, container, false)
        // Inflate the layout for this fragment
        forgetPasswordButton = view.findViewById(R.id.forget_password_fragment_button)
        emailAddress = view.findViewById(R.id.email_address_forget_fragment)

        forgetPasswordButton.setOnClickListener {
            if (emailValdir())
                println("email valid")
        }
        return view
    }

    private fun emailValdir():Boolean {
        return validator.isValidEmail(emailAddress)
    }

}