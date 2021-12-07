package com.wise.studentdelivery.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wise.studentdelivery.R

class PinCode : Fragment() {
    lateinit var timerText: TextView
    lateinit var pinCode: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pinCode = requireArguments().getString("pin_code").toString()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pin_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timerText = view.findViewById(R.id.timer_text)

        val timer = object : CountDownTimer(300000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var min = (millisUntilFinished / 60000)%60
                var sec = (millisUntilFinished/1000)%60
                timerText.text = getString(R.string.pin_well_expire_after_5_00_minute,min,sec)
            }

            override fun onFinish() {
                //TODO("Not yet implemented")
                timerText.text = getString(R.string.pin_expired)
            }

        }.start()
    }
}