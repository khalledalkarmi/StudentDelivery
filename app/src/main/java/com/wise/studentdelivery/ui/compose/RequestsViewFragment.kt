package com.wise.studentdelivery.ui.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
                    RideText(ride = ride)
                }
            }
        }
    }


    //TODO add name for ride and image
    @Composable
    fun RideText(ride: Ride) {
        val rideView = RideView()
        var rideNeighborhood = ""
        if (ride.neighborhoodNAme != null) {
            rideNeighborhood = ride.neighborhoodNAme
        }
        val rideInfo = String.format(
            "from ${ride.cityName}, $rideNeighborhood \n" +
                    "to ${ride.uniName}, \n" +
                    "go time: ${ride.goTime}, \n" +
                    "back time ${ride.comeBackTime}, \n" +
                    "empty seats: ${ride.emptySeats}, \n" +
                    "price: ${ride.price}"
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
                        rideView.arguments = bundle
                        replace(R.id.fragmentContainerViewMainFun, rideView)
                    }
                }
        )
    }


}