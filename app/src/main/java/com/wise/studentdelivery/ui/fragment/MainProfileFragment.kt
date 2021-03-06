package com.wise.studentdelivery.ui.fragment


import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.wise.studentdelivery.MainActivity
import com.wise.studentdelivery.R
import com.wise.studentdelivery.network.RestApiServer
import com.wise.studentdelivery.utilities.RealPathUtil

class MainProfileFragment : Fragment() {


    private lateinit var profileImage: ImageView
    private lateinit var changeImageButton: ImageButton
    private lateinit var nameTextView: TextView
    private lateinit var accountSittingButton: Button
    private lateinit var changePasswordButton: Button
    private lateinit var reportProblemButton: Button
    private lateinit var profileFragment: ProfileFragment
    private lateinit var reportFragment: ReportFragment
    private lateinit var setNewPassword: SetNewPassword
    private lateinit var myRide: MyRide
    private lateinit var logOutButton: Button
    private lateinit var email: String
    private lateinit var apiServer: RestApiServer
    private val REQUEST_IMAGE_GALLERY = 132
    private val REQUEST_IMAGE_CAMERA = 142
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiServer = RestApiServer()
        profileFragment = ProfileFragment()
        setNewPassword = SetNewPassword()
        reportFragment = ReportFragment()
        myRide = MyRide()
        email = requireArguments().getString("email").toString()

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
        changeImageButton = view.findViewById(R.id.change_image_button)
        nameTextView = view.findViewById(R.id.name_textview)
        accountSittingButton = view.findViewById(R.id.account_information)
        changePasswordButton = view.findViewById(R.id.change_password)
        logOutButton = view.findViewById(R.id.log_out)
        reportProblemButton = view.findViewById(R.id.report_a_problem)

        accountSittingButton.setOnClickListener {
            parentFragmentManager.commit {
                val bundle = bundleOf("email" to email)
                addToBackStack("mainProfile")
                profileFragment.arguments = bundle
                replace(R.id.fragmentContainerViewMainFun, profileFragment)
            }
        }

        changePasswordButton.setOnClickListener {
            parentFragmentManager.commit {
                val bundle = bundleOf("email" to email)
                addToBackStack("changePassword")
                setNewPassword.arguments = bundle
                replace(R.id.fragmentContainerViewMainFun, setNewPassword)
            }
        }

        /*myRequestButton.setOnClickListener {
            parentFragmentManager.commit {
                val bundle = bundleOf("email" to email)
                myRide.arguments = bundle
                addToBackStack("myRide")
                replace(R.id.fragmentContainerViewMainFun, myRide)
            }
        }*/

        apiServer.getUserByEmail(email) { user ->
            if (user != null) {
                nameTextView.setText(user.firstName + " " + user.lastName)
            }
        }

        apiServer.getImage(email = email) {
            println(it.toString())
            if (it != null) {
                val imageBytes = Base64.decode(it.data, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                profileImage.setImageBitmap(decodedImage)
            }
        }
        changeImageButton.setOnClickListener {

            val builder = AlertDialog.Builder(activity!!)
            builder.setTitle("Select Image")
            builder.setMessage("Choose your option:")
            builder.setPositiveButton("Gallery") { dialog, which ->
                dialog.dismiss()

                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/"
                startActivityForResult(intent, REQUEST_IMAGE_GALLERY)

            }
            builder.setNegativeButton("Camera") { dialog, which ->
                dialog.dismiss()

                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                        val permission = ContextCompat.checkSelfPermission(
                            activity!!,
                            android.Manifest.permission.CAMERA
                        )

                        if (permission != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(
                                activity!!,
                                arrayOf(android.Manifest.permission.CAMERA),
                                1
                            )
                        } else {
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAMERA)
                        }
                    }
                }
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()

        }
        reportProblemButton.setOnClickListener {
            parentFragmentManager.commit() {
                val bundle = bundleOf("email" to email)
                addToBackStack("reportFragment")
                reportFragment.arguments = bundle
                replace(R.id.fragmentContainerViewMainFun, reportFragment)
            }
        }
        logOutButton.setOnClickListener {
            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it).setTitle("Do you want to logout?")
                builder.apply {
                    setPositiveButton(
                        "Log out"
                    ) { dialog, id ->
                        val intent = Intent(it, MainActivity::class.java)
                        intent.addFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    or Intent.FLAG_ACTIVITY_NEW_TASK
                        )
                        startActivity(intent)
                    }
                    setNegativeButton(
                        "cancel"
                    ) { dialog, id ->
                        dialog.dismiss()
                    }
                }

                builder.create()
            }
            alertDialog?.show()
        }
    }
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
                profileImage.setImageURI(data.data)
                val imageRealPath =
                    RealPathUtil.getRealPath(requireContext().applicationContext, data.data!!)
                apiServer.setImageByEmail(imageRealPath, email) {

                }
            } else if (requestCode == REQUEST_IMAGE_CAMERA && resultCode == Activity.RESULT_OK && data != null) {
                profileImage.setImageBitmap(data.extras?.get("data") as Bitmap)
            } else {
                Toast.makeText(activity!!, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
