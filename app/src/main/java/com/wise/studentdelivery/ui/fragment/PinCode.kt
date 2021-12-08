package com.wise.studentdelivery.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.wise.studentdelivery.R
import com.wise.studentdelivery.network.RestApiServer

class PinCode : Fragment() {
    private lateinit var timerText: TextView
    private lateinit var resendText: TextView
    private lateinit var confirmPinCodeText: EditText
    private lateinit var pinCode: String
    private lateinit var email: String
    private lateinit var confirmPinCodeButton: Button
    private lateinit var apiServer: RestApiServer
    private var isExpired: Boolean = false
    private val setNewPassword = SetNewPassword()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiServer = RestApiServer()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pinCode = requireArguments().getString("pin_code").toString()
        email = requireArguments().getString("email").toString()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pin_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timerText = view.findViewById(R.id.timer_text)
        confirmPinCodeButton = view.findViewById(R.id.pin_code_fragment_button)
        confirmPinCodeText = view.findViewById(R.id.pin_code_text)
        resendText = view.findViewById(R.id.resend_text)
        val timer = object : CountDownTimer(300000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var min = (millisUntilFinished / 60000) % 60
                var sec = (millisUntilFinished / 1000) % 60
                timerText.text = getString(R.string.pin_well_expire_after_5_00_minute, min, sec)
            }

            override fun onFinish() {
                //TODO("Not yet implemented")
                timerText.text = getString(R.string.pin_expired)
                isExpired = true
            //    println(isExpired)

            }

        }
        timer.start()

            confirmPinCodeButton.setOnClickListener {
                if (!isExpired) {

                    if (confirmPinCodeText.text.toString() != "" && confirmPinCodeText.text.toString()
                            .toInt() == pinCode.toInt()
                    ) {
                      //  println("$pinCode from confirmPinCodeButton in if ")
                        println("pin matched")
                            parentFragmentManager.commit {
                                timer.cancel()
                                val bundle= bundleOf("email" to email)
                                setNewPassword.arguments=bundle
                                replace(R.id.fragmentContainerView,setNewPassword)
                            }
                    } else {
                        println("$pinCode not match with ${confirmPinCodeText.text.toString()}")
                    }
                } else if (isExpired) {
                   // println("$pinCode from confirmPinCodeButton in else ")
                    Toast.makeText(
                        context,
                        "the pin expired please click Resend.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        resendText.setOnClickListener {
            apiServer.getPIN(email) {
                if (it != null) {
                    pinCode = it.toString()
                    timer.start()
                    println("$pinCode from resendText ")
                }
                isExpired = false
            }
        }
    }
}