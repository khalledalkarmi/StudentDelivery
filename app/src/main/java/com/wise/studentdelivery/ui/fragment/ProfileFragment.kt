package com.wise.studentdelivery.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.wise.studentdelivery.MainActivity
import com.wise.studentdelivery.R
import com.wise.studentdelivery.model.User
import com.wise.studentdelivery.network.RestApiServer
import com.wise.studentdelivery.ui.MainFunActivity

class ProfileFragment : Fragment() {


    private lateinit var firstNameEdit: EditText
    private lateinit var lastNameEdit: EditText
    private lateinit var phoneNumEdit: EditText
    private lateinit var emailEdit: EditText
    private lateinit var saveButton: Button
    private lateinit var apiServer: RestApiServer
    private lateinit var email: String
    private lateinit var userOriginalData: User
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
                userOriginalData = user
                firstNameEdit.setText(user.firstName)
                lastNameEdit.setText(user.lastName)
                emailEdit.setText(user.email)
                phoneNumEdit.setText(user.phoneNumber)
            }
        }
        saveButton.setOnClickListener {
            val updatedUser = User(
                firstName = firstNameEdit.text.toString(),
                lastName = lastNameEdit.text.toString(),
                email = emailEdit.text.toString(),
                phoneNumber = phoneNumEdit.text.toString(),
                photo = userOriginalData.photo,
                uniName = userOriginalData.uniName,
                ride = userOriginalData.ride,
                studentNumber = userOriginalData.studentNumber,
                address = userOriginalData.address,
                createdTime = userOriginalData.createdTime,
                gender = userOriginalData.gender,
                graduateYear = userOriginalData.graduateYear,
                haveCar = userOriginalData.haveCar,
                password = userOriginalData.password
            )
            apiServer.updateUser(userData = updatedUser){ userUpdated ->
                if (userUpdated == true){
                    Toast.makeText(
                        context,
                        "User Information updated",
                        Toast.LENGTH_LONG
                    ).show()
                    activity?.let {
                        val intent = Intent(it, MainFunActivity::class.java)
                        intent.putExtra("email",email)
                        intent.addFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    or Intent.FLAG_ACTIVITY_NEW_TASK
                        )
                        startActivity(intent)
                    }
                }

            }
        }
    }
}