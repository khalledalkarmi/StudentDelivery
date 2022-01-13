package com.wise.studentdelivery.ui.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.wise.studentdelivery.R
import com.wise.studentdelivery.network.RestApiServer


class AuthenticationFragment : Fragment() {

    private lateinit var selectImageButton: Button
    private lateinit var idImageView: ImageView
    private lateinit var finishButton: Button
    private lateinit var apiServer: RestApiServer
    private val REQUEST_IMAGE_GALLERY = 132
    private val REQUEST_IMAGE_CAMERA = 142

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        apiServer = RestApiServer()

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_authentication, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectImageButton = view.findViewById(R.id.select_image_button)
        idImageView = view.findViewById(R.id.id_imageview)
        finishButton = view.findViewById(R.id.finish_button)


        finishButton.setOnClickListener {
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

        finishButton.setOnClickListener {

            // TODO: implement upload to server

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            idImageView.setImageURI(data.data)


        } else if (requestCode == REQUEST_IMAGE_CAMERA && resultCode == Activity.RESULT_OK && data != null) {
            idImageView.setImageBitmap(data.extras?.get("data") as Bitmap)
        } else {
            Toast.makeText(activity!!, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}