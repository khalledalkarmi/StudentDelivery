package com.wise.studentdelivery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.wise.studentdelivery.R


class ReportFragment : Fragment() {

    private lateinit var reportEditText: EditText
    private lateinit var sendButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_report, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reportEditText = view.findViewById(R.id.report_edittext)
        sendButton = view.findViewById(R.id.report_button)


        sendButton.setOnClickListener {

            // TODO: implement upload to server or intent send email to admin

        }
    }
}