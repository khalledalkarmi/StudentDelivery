package com.wise.studentdelivery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import com.wise.studentdelivery.R
import com.wise.studentdelivery.network.RestApiServer
import com.wise.studentdelivery.utilities.Validator


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class ForgotPasswordFragment : Fragment() {
    private lateinit var forgetPasswordButton: Button
    private lateinit var emailAddress: EditText
    private val validator = Validator()
    private lateinit var apiServer: RestApiServer
    var pinCode: Int = 0

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

        forgetPasswordButton = view.findViewById(R.id.pin_code_fragment_button)
        emailAddress = view.findViewById(R.id.pin_code_text)
        val pinCode = PinCode()
        forgetPasswordButton.setOnClickListener {
            if (emailValdir()) {
                apiServer.getPIN(emailAddress.text.toString()) {
                    println(it)
                    if (pinValdir(it.toString())) {
                        val bundle = bundleOf("pin_code" to it.toString(),"email" to emailAddress.text.toString())
                        parentFragmentManager.commit {
                            pinCode.arguments= bundle
                            replace(R.id.fragmentContainerView, pinCode)
                        }
                    }
                }
            }
        }

    }


    private fun emailValdir(): Boolean {
        return validator.isValidEmail(emailAddress)
    }

    private fun pinValdir(pin: String): Boolean {
        if (pin == "null")
            return false
        if (pin.toInt() > 0) {
            pinCode = pin.toInt()
            return true
        }
        return false
    }
}