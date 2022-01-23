package com.wise.studentdelivery.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.*
import com.wise.studentdelivery.R
import com.wise.studentdelivery.model.Car
import com.wise.studentdelivery.model.Gender
import com.wise.studentdelivery.model.Ride
import com.wise.studentdelivery.model.User
import com.wise.studentdelivery.network.RestApiServer
import com.wise.studentdelivery.ui.MainFunActivity
import com.wise.studentdelivery.utilities.Validator

class MyRide : Fragment() {

    private lateinit var goTme: EditText
    private lateinit var comeBackTime: EditText
    private lateinit var emptySeats: EditText
    private lateinit var price: EditText
    private lateinit var carNumber: EditText
    private lateinit var carModel: EditText
    private lateinit var carColor: EditText
    private lateinit var genderSpecificSpinner: Spinner
    private lateinit var extraDetails: EditText
    private lateinit var privateSwitch: Switch
    private lateinit var saveRequest: Button
    private lateinit var apiServer: RestApiServer
    private lateinit var validator: Validator
    private lateinit var email: String
    private lateinit var user: User
    var firstName = ""
    var lastName = ""
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
        apiServer.getUserByEmail(email = email) {
            if (it != null) {
                firstName = it.firstName
                lastName = it.lastName
            }
        }
        return inflater.inflate(R.layout.fragment_my_ride, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        goTme = view.findViewById(R.id.go_time)
        comeBackTime = view.findViewById(R.id.comeback_time)

        emptySeats = view.findViewById(R.id.empty_seats)
        price = view.findViewById(R.id.price)
        genderSpecificSpinner = view.findViewById(R.id.genderSpecificSpinner)
        extraDetails = view.findViewById(R.id.extra_details)
        privateSwitch = view.findViewById(R.id.private_switch)
        saveRequest = view.findViewById(R.id.save_request_button)
        carColor = view.findViewById(R.id.car_color)
        carModel = view.findViewById(R.id.car_model)
        carNumber = view.findViewById(R.id.car_number)
        validator = Validator()

        val genderSpecificArrayAdapter = genderSpecificSpinner.adapter as ArrayAdapter<String>
        apiServer.getUserByEmail(email = email) {
            if (it != null) {
                user = it
            }
        }
        apiServer.getRideByUserEmail(email = email) {
            if (it != null) {
                goTme.setText(it.goTime)
                comeBackTime.setText(it.comeBackTime)
                emptySeats.setText(it.emptySeats)
                price.setText(it.price)

                carNumber.setText(it.haveCar.carNumber)
                carModel.setText(it.haveCar.carModel)
                carColor.setText(it.haveCar.carColor)

                if (it.genderSpecific != null) {
                    if (it.genderSpecific.toString() == "MALE")
                        genderSpecificSpinner.setSelection(genderSpecificArrayAdapter.getPosition("Male only"))
                    else if (it.genderSpecific.toString() == "FEMALE")
                        genderSpecificSpinner.setSelection(genderSpecificArrayAdapter.getPosition("Female only"))
                    extraDetails.setText(it.extraDetails)
                }
                privateSwitch.isChecked = it.isPrivate == "true"
            }
        }


        saveRequest.setOnClickListener {
            val gender = genderSpecificSpinner.selectedItem.toString()
            var genderSpecific = "No"
            if (gender == "Male only") {
                genderSpecific = Gender.MALE.toString()
            } else if (gender == "Female only") {
                genderSpecific = Gender.FEMALE.toString()
            }
            val ride: Ride = Ride(
                email = email,
                firstName = firstName,
                lastName = lastName,
                goTime = goTme.text.toString(),
                comeBackTime = comeBackTime.text.toString(),
                cityName = user.address.city,
                emptySeats = emptySeats.text.toString(),
                price = price.text.toString(),
                genderSpecific = genderSpecific,
                extraDetails = extraDetails.text.toString(),
                isPrivate = privateSwitch.isChecked.toString(),
                neighborhoodName = user.address.country,
                uniName = user.uniName,
                photo = user.photo,
                haveCar = Car(
                    carModel = carModel.text.toString(),
                    carColor = carColor.text.toString(),
                    carNumber = carNumber.text.toString()
                )
            )

            apiServer.addRideByEmail(ride, email) {
                if (it != null) {
                    Toast.makeText(activity, "ride saved successfully", Toast.LENGTH_LONG).show()
                    println("${ride.firstName} add")

                    println(ride)
                    val intent = Intent(activity, MainFunActivity::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                }
            }

        }
    }
}