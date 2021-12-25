package com.wise.studentdelivery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.*
import com.wise.studentdelivery.R
import com.wise.studentdelivery.model.Ride
import com.wise.studentdelivery.network.RestApiServer

class MyRide : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var goTme: EditText
    private lateinit var comeBackTime: EditText
    private lateinit var uniNameSpinner: Spinner
    private lateinit var cityNameSpinner: Spinner
    private lateinit var neighborhoodNameSpinner: Spinner
    private lateinit var emptySeats: EditText
    private lateinit var price: EditText
    private lateinit var genderSpecificSpinner: Spinner
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
        neighborhoodNameSpinner = view.findViewById(R.id.spNeighborhoods)
        emptySeats = view.findViewById(R.id.empty_seats)
        price = view.findViewById(R.id.price)
        genderSpecificSpinner = view.findViewById(R.id.genderSpecificSpinner)
        extraDetails = view.findViewById(R.id.extra_details)
        privateSwitch = view.findViewById(R.id.private_switch)
        saveRequest = view.findViewById(R.id.save_request_button)


        val uniNameArrayAdapter = uniNameSpinner.adapter as ArrayAdapter<String>
        val cityNameArrayAdapter = cityNameSpinner.adapter as ArrayAdapter<String>
        val neighborhoodNameArrayAdapter = neighborhoodNameSpinner.adapter as ArrayAdapter<String>
        val genderSpecificArrayAdapter = genderSpecificSpinner.adapter as ArrayAdapter<String>

        val adapter1 = ArrayAdapter.createFromResource(activity!!, R.array.cities, android.R.layout.simple_spinner_item)
        cityNameSpinner.adapter = adapter1
        cityNameSpinner.onItemSelectedListener = this


        apiServer.getRideByUserEmail("khalled_95@hotmail.com"){
            if (it != null){
                goTme.setText(it.goTime)
                comeBackTime.setText(it.comeBackTime)
                uniNameSpinner.setSelection(uniNameArrayAdapter.getPosition(it.uniName))
                cityNameSpinner.setSelection(cityNameArrayAdapter.getPosition(it.cityName))
                neighborhoodNameSpinner.setSelection(neighborhoodNameArrayAdapter.getPosition(it.neighborhoodNAme))
                emptySeats.setText(it.emptySeats)
                price.setText(it.price)
                genderSpecificSpinner.setSelection(genderSpecificArrayAdapter.getPosition(it.genderSpecific))
                extraDetails.setText(it.extraDetails)
                privateSwitch.isChecked=it.isPrivate
            }
        }


        saveRequest.setOnClickListener {
            val city = cityNameSpinner.selectedItem.toString()
            val uniName = uniNameSpinner.selectedItem.toString()
            val neighborhoodName = neighborhoodNameSpinner.selectedItem.toString()
            val genderSpecific = genderSpecificSpinner.selectedItem.toString()
            val ride: Ride = Ride(
                //TODO: get email, first name and last name  from data model
                email = "khalled_95@hotmail.com",
                firstName = "khalled",
                lastName = "alkarmi",
                goTime = goTme.text.toString(),
                comeBackTime = comeBackTime.text.toString(),
                cityName = city,
                emptySeats = emptySeats.text.toString(),
                price = price.text.toString(),
                genderSpecific = genderSpecific,
                extraDetails = extraDetails.text.toString(),
                isPrivate = privateSwitch.isChecked,
                neighborhoodNAme = neighborhoodName,
                uniName = uniName
            )

            apiServer.addRideByEmail(ride, "khalled_95@hotmail.com") {
                if (it != null)
                    println("${it.firstName} add")
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (cityNameSpinner.selectedItem == "عمان") {
            val adapter2 = ArrayAdapter.createFromResource(activity!!,
                R.array.amman_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        } else if (cityNameSpinner.selectedItem == "الزرقاء") {
            val adapter2 = ArrayAdapter.createFromResource(activity!!,
                R.array.zarqa_neighborhoods, android.R.layout.simple_spinner_item
            )
            neighborhoodNameSpinner.adapter = adapter2
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}