package com.wise.studentdelivery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wise.studentdelivery.R
import com.wise.studentdelivery.ui.compose.RequestsViewFragment

//TODO: use navHost for navigation
class BottomNavigationFragment : Fragment() {
    private lateinit var email:String
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
        val requestsViewFragment = RequestsViewFragment()
        val bundle = bundleOf("email" to email)
        requestsViewFragment.arguments= bundle
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainerViewMain,requestsViewFragment)
        }
    }
}