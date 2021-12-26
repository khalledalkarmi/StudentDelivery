package com.wise.studentdelivery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wise.studentdelivery.R
import com.wise.studentdelivery.ui.compose.RequestsViewFragment

//TODO: use navHost for navigation
class BottomNavigationFragment : Fragment() {
    private lateinit var rideNav: BottomNavigationView
    private lateinit var email: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        email = requireArguments().getString("email").toString()

        return inflater.inflate(R.layout.bottom_navigation_bar, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rideNav = view.findViewById(R.id.bottomNavigationView)
        val requestsViewFragment = RequestsViewFragment()
        val mainProfileFragment = MainProfileFragment()
        rideNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ride_nav -> {
                    parentFragmentManager.commit {
                        val bundle = bundleOf("email" to email)
                        setReorderingAllowed(true)
                        requestsViewFragment.arguments = bundle
                        replace(R.id.fragmentContainerViewMain, requestsViewFragment)
                    }
                }
                R.id.profile_nav -> {
                    parentFragmentManager.commit {
                        val bundle = bundleOf("email" to email)
                        setReorderingAllowed(true)
                        mainProfileFragment.arguments = bundle
                        replace(R.id.fragmentContainerViewMain, mainProfileFragment)
                    }
                }
            }
            false
        }
        val bundle = bundleOf("email" to email)
        requestsViewFragment.arguments = bundle
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainerViewMain, requestsViewFragment)
        }
    }
}

