package com.wise.studentdelivery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import androidx.fragment.app.*
import com.wise.studentdelivery.R
import com.wise.studentdelivery.model.Ride
import com.wise.studentdelivery.network.RestApiServer

class MyRide : Fragment() {
    private lateinit var goTme: EditText
    private lateinit var comeBackTime: EditText
    private lateinit var uniNameSpinner: Spinner
    private lateinit var cityNameSpinner: Spinner
    private lateinit var neighborhoodName: EditText
    private lateinit var emptySeats: EditText
    private lateinit var price: EditText
    private lateinit var extraDetails: EditText
    private lateinit var privateSwitch: Switch
    private lateinit var saveRequest: Button
    private lateinit var apiServer: RestApiServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiServer = RestApiServer()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_my_ride, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goTme = view.findViewById(R.id.go_time)
        comeBackTime = view.findViewById(R.id.comeback_time)
        uniNameSpinner = view.findViewById(R.id.spUni)
        cityNameSpinner = view.findViewById(R.id.city_spinner)
        neighborhoodName = view.findViewById(R.id.neighborhood_name)
        emptySeats = view.findViewById(R.id.empty_seats)
        price = view.findViewById(R.id.price)
        extraDetails = view.findViewById(R.id.extra_details)
        privateSwitch = view.findViewById(R.id.private_switch)
        saveRequest = view.findViewById(R.id.save_request_button)

        saveRequest.setOnClickListener {
            val city = cityNameSpinner.selectedItem.toString()
            val uniName = uniNameSpinner.selectedItem.toString()
            val ride: Ride = Ride(
                goTime = goTme.text.toString(),
                comeBackTime = comeBackTime.text.toString(),
                cityName = city,
                emptySeats = emptySeats.text.toString(),
                price = price.text.toString(),
                extraDetails = extraDetails.text.toString(),
                isPrivate = privateSwitch.isChecked,
                neighborhoodNAme = neighborhoodName.text.toString(),
                uniName = uniName
            )
            apiServer.addRideByEmail(ride,"khalled_95@hotmail.com"){
                if (it != null)
                    println("${it.firstName} add")
            }
        }
    }
}