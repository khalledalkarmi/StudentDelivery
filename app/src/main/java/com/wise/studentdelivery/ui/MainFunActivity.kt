package com.wise.studentdelivery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.wise.studentdelivery.R
import com.wise.studentdelivery.ui.compose.RequestsViewFragment
import com.wise.studentdelivery.ui.fragment.BottomNavigationFragment
import com.wise.studentdelivery.ui.fragment.ForgotPasswordFragment

class MainFunActivity : AppCompatActivity(R.layout.activity_main_fun) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null){
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<RequestsViewFragment>(R.id.fragmentContainerViewMainFun)
            }
        }
    }
}