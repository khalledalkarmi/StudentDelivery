package com.wise.studentdelivery.ui.compose

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.wise.studentdelivery.R
import com.wise.studentdelivery.model.Ride
import com.wise.studentdelivery.model.User
import com.wise.studentdelivery.network.RestApiServer
import com.wise.studentdelivery.ui.compose.ui.theme.Shapes
import com.wise.studentdelivery.ui.compose.ui.theme.StudentDeliveryTheme
import com.wise.studentdelivery.ui.fragment.RideView

class RequestsViewFragment : Fragment() {
    lateinit var apiServer: RestApiServer
    private lateinit var email: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiServer = RestApiServer()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        email = requireArguments().getString("email").toString()
        return ComposeView(requireContext()).apply {
            apiServer.getAllRide {
                if (it != null) {
                    setContent {
                        StudentDeliveryTheme {
                            Surface(color = MaterialTheme.colors.background) {
                                RideCompose(allRide = it)
                            }
                        }
                    }
                    println("size  of all ride list: ${it.size}")
                }
            }
        }
    }


    @Composable
    fun RideCompose(allRide: List<Ride>) {
        val listModifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        LazyColumn(
            modifier = listModifier,
            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(allRide) { ride ->
                Box(
                    modifier = Modifier.border(
                        border = BorderStroke(
                            width = 3.dp,
                            color = Color.Black
                        )
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    println("${ride.isPrivate} ${ride.firstName}")
                    if (ride.isPrivate == "false")
                        RideText(ride = ride)
                }
                // Image(email = ride.email)

            }
        }
    }

//    @Composable
//    fun Image(email:String){
//        var decodedImage = BitmapFactory.decodeResource(
//            context?.resources,
//            R.drawable.blank_profile_picture
//        )
//        apiServer.getImage(email = email) {
//            if (it != null) {
//                val imageBytes = Base64.decode(it.data, Base64.DEFAULT)
//                decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//
//                println("in if" + decodedImage)
//            }
//        }
//        Image(bitmap = decodedImage.asImageBitmap(), contentDescription = "image")
//    }

    @Composable
    fun RideText(ride: Ride) {
        val rideView = RideView()
        var rideNeighborhood = ""
        if (ride.neighborhoodNAme != null) {
            rideNeighborhood = ride.neighborhoodNAme
        }
        val rideInfo = String.format(
            "name: ${ride.firstName} ${ride.lastName},\n" +
                    "from: ${ride.cityName}, $rideNeighborhood \n" +
                    "to: ${ride.uniName}, \n" +
                    "go time: ${ride.goTime}, \n" +
                    "back time ${ride.comeBackTime}, \n" +
                    "empty seats: ${ride.emptySeats}, \n" +
                    "price: ${ride.price} JD"
        )
        Text(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            text = rideInfo,

            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clickable {
                    parentFragmentManager.commit {
                        val bundle = bundleOf("email" to ride.email.toString())
                        addToBackStack("requestView")
                        rideView.arguments = bundle
                        replace(R.id.fragmentContainerViewMainFun, rideView)
                    }
                }
        )
    }


}