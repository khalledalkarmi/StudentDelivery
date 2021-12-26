package com.wise.studentdelivery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.wise.studentdelivery.R
import com.wise.studentdelivery.ui.compose.RequestsViewFragment
import com.wise.studentdelivery.ui.fragment.BottomNavigationFragment
import com.wise.studentdelivery.ui.fragment.ForgotPasswordFragment

class MainFunActivity : AppCompatActivity(R.layout.activity_main_fun) {
    private lateinit var email: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            email = intent.getStringExtra("email").toString()
            val bundle = bundleOf("email" to email)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<BottomNavigationFragment>(R.id.fragmentContainerViewMainFun, args = bundle)
            }
        }
    }
}