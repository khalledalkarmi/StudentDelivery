package com.wise.studentdelivery.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.wise.studentdelivery.R
import com.wise.studentdelivery.model.Address
import com.wise.studentdelivery.model.Ride
import com.wise.studentdelivery.model.User
import com.wise.studentdelivery.network.RestApiServer
import com.wise.studentdelivery.ui.MainFunActivity

//TODO: implement upload to server & view from server

class ProfileFragment : Fragment(), AdapterView.OnItemSelectedListener {


    private lateinit var firstNameEdit: EditText
    private lateinit var lastNameEdit: EditText
    private lateinit var phoneNumEdit: EditText
    private lateinit var emailEdit: EditText
    private lateinit var saveButton: Button
    private lateinit var apiServer: RestApiServer
    private lateinit var email: String
    private lateinit var userOriginalData: User
    private lateinit var uniNameSpinner: Spinner
    private lateinit var cityNameSpinner: Spinner
    private lateinit var neighborhoodNameSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiServer = RestApiServer()


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
        uniNameSpinner = view.findViewById(R.id.uni_name_spinner)
        cityNameSpinner = view.findViewById(R.id.city_name_spinner)
        neighborhoodNameSpinner = view.findViewById(R.id.neighborhood_name_spinner)

        val uniNameArrayAdapter = uniNameSpinner.adapter as ArrayAdapter<String>
        val cityNameArrayAdapter = cityNameSpinner.adapter as ArrayAdapter<String>
        val neighborhoodNameArrayAdapter = neighborhoodNameSpinner.adapter as ArrayAdapter<String>
        val adapter1 = ArrayAdapter.createFromResource(
            activity!!,
            R.array.cities,
            android.R.layout.simple_spinner_item
        )
        cityNameSpinner.adapter = adapter1
        cityNameSpinner.onItemSelectedListener = this


        apiServer.getUserByEmail(email) { user ->
            if (user != null) {
                userOriginalData = user
                firstNameEdit.setText(user.firstName)
                lastNameEdit.setText(user.lastName)
                emailEdit.setText(user.email)
                phoneNumEdit.setText(user.phoneNumber)
                uniNameSpinner.setSelection(uniNameArrayAdapter.getPosition(user.uniName))
                cityNameSpinner.setSelection(cityNameArrayAdapter.getPosition(user.address.city))
                neighborhoodNameSpinner.setSelection(neighborhoodNameArrayAdapter.getPosition(user.address.city))
            }
        }
        saveButton.setOnClickListener {
            val city = cityNameSpinner.selectedItem.toString()
            val uniName = uniNameSpinner.selectedItem.toString()
            val neighborhoodName = neighborhoodNameSpinner.selectedItem.toString()
            val updatedUser = User(
                firstName = firstNameEdit.text.toString(),
                lastName = lastNameEdit.text.toString(),
                email = emailEdit.text.toString(),
                phoneNumber = phoneNumEdit.text.toString(),
                photo = userOriginalData.photo,
                uniName = uniName,
                ride =Ride(email = userOriginalData.email,neighborhoodName=neighborhoodName,haveCar = userOriginalData.ride!!.haveCar,uniName = uniName,photo = userOriginalData.photo,lastName = userOriginalData.lastName,firstName = userOriginalData.firstName,goTime = userOriginalData.ride!!.goTime,comeBackTime = userOriginalData.ride!!.comeBackTime,cityName = city,emptySeats = userOriginalData.ride!!.emptySeats,isPrivate = userOriginalData.ride!!.isPrivate,price = userOriginalData.ride!!.price,extraDetails = userOriginalData.ride!!.extraDetails,genderSpecific = userOriginalData.ride!!.genderSpecific),
                studentNumber = userOriginalData.studentNumber,
                address = Address(neighborhood = neighborhoodName, city = city),
                createdTime = userOriginalData.createdTime,
                gender = userOriginalData.gender,
                graduateYear = userOriginalData.graduateYear,
                password = userOriginalData.password
            )
            apiServer.updateUser(userData = updatedUser) { userUpdated ->
                if (userUpdated == true) {
                    Toast.makeText(
                        context,
                        "User Information updated",
                        Toast.LENGTH_LONG
                    ).show()
                    activity?.let {
                        val intent = Intent(it, MainFunActivity::class.java)
                        intent.putExtra("email", email)
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (cityNameSpinner.selectedItem == "????????") {
            val adapter2 = ArrayAdapter.createFromResource(
                activity!!,
                R.array.amman_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "??????????????") {
            val adapter2 = ArrayAdapter.createFromResource(
                activity!!,
                R.array.zarqa_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "??????????????") {
            val adapter2 = ArrayAdapter.createFromResource(
                activity!!,
                R.array.balqa_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "????????????") {
            val adapter2 = ArrayAdapter.createFromResource(
                activity!!,
                R.array.aqaba_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "????????") {
            val adapter2 = ArrayAdapter.createFromResource(
                activity!!,
                R.array.maan_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "??????????") {
            val adapter2 = ArrayAdapter.createFromResource(
                activity!!,
                R.array.madaba_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "??????") {
            val adapter2 = ArrayAdapter.createFromResource(
                activity!!,
                R.array.jarash_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "??????????") {
            val adapter2 = ArrayAdapter.createFromResource(
                activity!!,
                R.array.ajloun_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "????????") {
            val adapter2 = ArrayAdapter.createFromResource(
                activity!!,
                R.array.irbid_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "??????????????") {
            val adapter2 = ArrayAdapter.createFromResource(
                activity!!,
                R.array.tafila_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "??????????") {
            val adapter2 = ArrayAdapter.createFromResource(
                activity!!,
                R.array.karak_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "????????????") {
            val adapter2 = ArrayAdapter.createFromResource(
                activity!!,
                R.array.mafraq_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}