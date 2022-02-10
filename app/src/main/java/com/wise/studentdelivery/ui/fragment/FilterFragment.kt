package com.wise.studentdelivery.ui.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.core.os.bundleOf
import androidx.core.os.persistableBundleOf
import androidx.core.view.get
import androidx.fragment.app.commit
import com.wise.studentdelivery.R
import com.wise.studentdelivery.model.Ride
import com.wise.studentdelivery.network.RestApiServer
import com.wise.studentdelivery.ui.compose.RequestsViewFragment
import java.util.ArrayList

class FilterFragment : Fragment() {
    private lateinit var citySpinner: Spinner
    private lateinit var uniSpinner: Spinner
    private lateinit var filterButton: Button
    private lateinit var apiServer: RestApiServer
    private lateinit var bottomNavigationFragment: BottomNavigationFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiServer = RestApiServer()
        bottomNavigationFragment = BottomNavigationFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        citySpinner = view.findViewById(R.id.city_spinner_filter)
        uniSpinner = view.findViewById(R.id.uni_spinner_filter)



        filterButton = view.findViewById(R.id.button_filter)
        filterButton.setOnClickListener {
            parentFragmentManager.commit {
                val bundle = bundleOf("ride" to uniSpinner.selectedItem.toString())
                bottomNavigationFragment.arguments = bundle
                replace(R.id.fragmentContainerViewMainFun, bottomNavigationFragment)
            }

        }

    }
}

