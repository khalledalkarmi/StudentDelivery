package com.wise.studentdelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit

class ForgetPassword : AppCompatActivity(R.layout.activity_forget_password) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null){
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ForgetPasswordFragment>(R.id.fragmentContainerView)
            }
        }
    }
}