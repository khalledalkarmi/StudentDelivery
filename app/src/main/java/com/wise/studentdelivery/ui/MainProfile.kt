package com.wise.studentdelivery.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.wise.studentdelivery.R
import com.wise.studentdelivery.network.RestApiServer
//TODO: implement button navigation
class MainProfile : Fragment() {
    private lateinit var profileImage :ImageView
    private lateinit var myRequestButton: Button
    private lateinit var savedRequestButton: Button
    private lateinit var accountSittingButton:Button
    private lateinit var reportProblemButton:Button
    private lateinit var apiServer: RestApiServer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiServer=RestApiServer()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileImage = view.findViewById(R.id.main_profile_image)
        myRequestButton = view.findViewById(R.id.my_requset)
        savedRequestButton = view.findViewById(R.id.saved_request)
        accountSittingButton = view.findViewById(R.id.account_settings)
        reportProblemButton = view.findViewById(R.id.reporta_problem)

        //TODO: pass email from any activity
        apiServer.getImage("khalled_95@hotmail.com"){
            println(it.toString())
            if (it != null) {
                val imageBytes = Base64.decode(it.data, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                profileImage.setImageBitmap(decodedImage)
            }
        }

    }


}