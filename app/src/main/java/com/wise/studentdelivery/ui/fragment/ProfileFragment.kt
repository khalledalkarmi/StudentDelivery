package com.wise.studentdelivery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.wise.studentdelivery.R
import com.wise.studentdelivery.network.RestApiServer

class ProfileFragment : Fragment() {


    private lateinit var firstNameEdit: EditText
    private lateinit var lastNameEdit: EditText
    private lateinit var phoneNumEdit: EditText
    private lateinit var emailEdit: EditText
    private lateinit var saveButton: Button
    lateinit var apiServer: RestApiServer
    lateinit var email: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiServer = RestApiServer()

        //TODO: add scrollView

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        email = requireArguments().getString("email").toString()
        println("$email email in Profile ")
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstNameEdit = view.findViewById(R.id.fname_edittext)
        lastNameEdit = view.findViewById(R.id.lname_edittext)
        emailEdit = view.findViewById(R.id.email_edittext)
        phoneNumEdit = view.findViewById(R.id.phone_edittext)
        saveButton = view.findViewById(R.id.save_button)

        apiServer.getUserByEmail(email) { user ->
            if (user != null) {

                firstNameEdit.setText(user.firstName)
                lastNameEdit.setText(user.lastName)
                emailEdit.setText(user.email)
                phoneNumEdit.setText(user.phoneNumber)
            }
        }
        saveButton.setOnClickListener {
            //TODO: implement save button
        }
    }
}