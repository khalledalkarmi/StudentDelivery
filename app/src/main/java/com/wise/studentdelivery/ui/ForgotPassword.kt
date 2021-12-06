package com.wise.studentdelivery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.wise.studentdelivery.R
import com.wise.studentdelivery.ui.fragment.ForgotPasswordFragment

class ForgotPassword : AppCompatActivity(R.layout.activity_forget_password) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null){
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ForgotPasswordFragment>(R.id.fragmentContainerView)
            }
        }
    }
}