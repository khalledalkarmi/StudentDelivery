package com.wise.studentdelivery.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wise.studentdelivery.R
import com.wise.studentdelivery.model.Ride
import com.wise.studentdelivery.model.User
import com.wise.studentdelivery.network.RestApiServer
import com.wise.studentdelivery.ui.compose.ui.theme.Shapes
import com.wise.studentdelivery.ui.compose.ui.theme.StudentDeliveryTheme

class RequestsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiServer = RestApiServer()
//        var allRide: List<Ride>? = null
        apiServer.getAllRide {
            if (it != null) {
//                allRide = it
                setContent {
                    StudentDeliveryTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(color = MaterialTheme.colors.background) {
                            RideCompose(it)
                            //Greeting(name = "khalled")
                        }
                    }
                }
            }

            println(it)
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StudentDeliveryTheme {
        Greeting("Android")
    }
}

@Composable
fun RideCompose(allRide: List<Ride>) {
    val listModifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                title = { Text(stringResource(R.string.app_name)) }
            )
        }
    ) {
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
//    println("ridee $allRide ride")
//    Column() {
//        allRide.forEach { _a ->
//            Text(text = _a.goTime)
//        }
//    }
}

@Composable
fun RideText(ride: Ride) {
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
    )
}


