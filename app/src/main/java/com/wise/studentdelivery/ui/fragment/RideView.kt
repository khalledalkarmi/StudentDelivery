package com.wise.studentdelivery.ui.fragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.wise.studentdelivery.R
import com.wise.studentdelivery.network.RestApiServer

class RideView : Fragment() {

    private lateinit var profileImageView: ImageView
    private lateinit var nameTextview: TextView
    private lateinit var genderTextview: TextView
    private lateinit var uniTextview: TextView
    private lateinit var addressTextview: TextView
    private lateinit var phoneTextview: TextView
    private lateinit var emailTextview: TextView
    private lateinit var goTimeTextview: TextView
    private lateinit var comebackTimeTextview: TextView
    private lateinit var emptySeatsTextview: TextView
    private lateinit var costTextview: TextView
    private lateinit var genderSpecificTextview: TextView
    private lateinit var detailsTextview: TextView
    private lateinit var callButton: ImageButton
    private lateinit var email:String

    lateinit var apiServer: RestApiServer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiServer = RestApiServer()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        email = requireArguments().getString("email").toString()
        return inflater.inflate(R.layout.fragment_ride_view, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileImageView = view.findViewById(R.id.profile_imageview)
        nameTextview = view.findViewById(R.id.name_textview)
        genderTextview = view.findViewById(R.id.gender_textview)
        uniTextview = view.findViewById(R.id.uni_textview)
        addressTextview = view.findViewById(R.id.address_textView)
        phoneTextview = view.findViewById(R.id.phone_textView)
        emailTextview = view.findViewById(R.id.email_textView)
        goTimeTextview = view.findViewById(R.id.go_time_textview)
        comebackTimeTextview = view.findViewById(R.id.comeback_time_textview)
        emptySeatsTextview = view.findViewById(R.id.empty_seats_textview)
        costTextview = view.findViewById(R.id.cost_textview)
        genderSpecificTextview = view.findViewById(R.id.gender_specific_textview)
        detailsTextview = view.findViewById(R.id.details_textview)
        callButton = view.findViewById(R.id.call_button)


        apiServer.getUserByEmail(email = email) {
            if (it != null) {
                apiServer.getImage(it.email.toString()) {
                    if (it != null) {
                        val imageBytes = Base64.decode(it.data, Base64.DEFAULT)
                        val decodedImage =
                            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        profileImageView.setImageBitmap(decodedImage)
                    }
                }
                nameTextview.text = it.firstName.toString() + " " + it.lastName.toString()
                genderTextview.text = it.gender.toString()
                uniTextview.text = it.uniName.toString()
                addressTextview.text =
                    it.ride?.cityName.toString() + ", " + it.ride?.neighborhoodName.toString()
                phoneTextview.text = it.phoneNumber.toString()
                emailTextview.text = it.email.toString()
                goTimeTextview.text = it.ride?.goTime
                comebackTimeTextview.text = it.ride?.comeBackTime
                emptySeatsTextview.text = it.ride?.emptySeats
                costTextview.text = it.ride?.price
                genderSpecificTextview.text = it.ride?.genderSpecific
                detailsTextview.text = it.ride?.extraDetails
            }
        }

        callButton.setOnClickListener {
            println("${phoneTextview.text} phone number")
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + phoneTextview.text.toString().trim())
            startActivity(intent)
        }

    }
}