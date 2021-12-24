package com.wise.studentdelivery.ui.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.wise.studentdelivery.R
import com.wise.studentdelivery.network.RestApiServer

class ProfileFragment : Fragment() {
    private lateinit var profileImage: ImageView
    private lateinit var useNameProfile: EditText
    private lateinit var phoneNumberProfile: EditText
    private lateinit var emailProfile: EditText
    private lateinit var universityProfile: EditText
    private lateinit var addressProfile: EditText
    private lateinit var saveProfileButton: Button
    private lateinit var apiServer: RestApiServer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiServer = RestApiServer()
        //TODO: pass email to get user information
        //TODO: implement change photo
        //TODO: implement save button
        //TODO: add scrollView

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileImage = view.findViewById(R.id.profile_image)
        useNameProfile = view.findViewById(R.id.user_name_profile)
        phoneNumberProfile = view.findViewById(R.id.phone_number_profile)
        emailProfile = view.findViewById(R.id.Email_profile)
        universityProfile = view.findViewById(R.id.university_name_profile)
        addressProfile = view.findViewById(R.id.address_profile)
        saveProfileButton = view.findViewById(R.id.save_profile)

        apiServer.getUserByEmail("khalled_95@hotmail.com") { user ->
            if (user != null) {
                apiServer.getImage("khalled_95@hotmail.com") {
                    val imageBytes = Base64.decode(it?.data, Base64.DEFAULT)
                    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    profileImage.setImageBitmap(decodedImage)
                }
                useNameProfile.setText(user.firstName + " " + user.lastName)
                phoneNumberProfile.setText(user.phoneNumber)
                emailProfile.setText(user.email)
                universityProfile.setText(user.uniName)
                addressProfile.setText(user.address.city + " " + user.address.country)
            }
        }
    }
}