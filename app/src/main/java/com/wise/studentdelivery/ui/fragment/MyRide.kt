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
    private lateinit var saveRequest:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
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
    }
}