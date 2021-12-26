package com.wise.studentdelivery.ui.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.wise.studentdelivery.R
import com.wise.studentdelivery.network.RestApiServer
import kotlin.math.log

//TODO: implement button navigation
class MainProfileFragment : Fragment() {


    private lateinit var profileImage :ImageView
    private lateinit var myRequestButton: Button
    private lateinit var savedRequestButton: Button
    private lateinit var accountSittingButton:Button
    private lateinit var reportProblemButton:Button
    private lateinit var changePasswordButton: Button
    private lateinit var profileFragment: ProfileFragment
    private lateinit var setNewPassword: SetNewPassword
    private lateinit var logOutButton: Button
    private lateinit var email:String
    private lateinit var apiServer: RestApiServer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiServer=RestApiServer()
        profileFragment = ProfileFragment()
        setNewPassword = SetNewPassword()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        email = requireArguments().getString(email).toString()
        return inflater.inflate(R.layout.fragment_main_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileImage = view.findViewById(R.id.main_profile_image)
        myRequestButton = view.findViewById(R.id.my_requset)
        savedRequestButton = view.findViewById(R.id.saved_request)
        accountSittingButton = view.findViewById(R.id.account_information)
        changePasswordButton = view.findViewById(R.id.change_password)
        logOutButton = view.findViewById(R.id.log_out)
        reportProblemButton = view.findViewById(R.id.report_a_problem)

        accountSittingButton.setOnClickListener {
            parentFragmentManager.commit {
                val bundle= bundleOf("email" to email)
                profileFragment.arguments = bundle
                replace(R.id.fragmentContainerViewMainFun,profileFragment)
            }
        }

        changePasswordButton.setOnClickListener {
            parentFragmentManager.commit {
                val bundle= bundleOf("email" to email)
                setNewPassword.arguments = bundle
                replace(R.id.fragmentContainerViewMainFun,setNewPassword)
            }
        }
        //TODO: pass email from any activity
        apiServer.getImage(email = email){
            println(it.toString())
            if (it != null) {
                val imageBytes = Base64.decode(it.data, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                profileImage.setImageBitmap(decodedImage)
            }
        }

    }


}