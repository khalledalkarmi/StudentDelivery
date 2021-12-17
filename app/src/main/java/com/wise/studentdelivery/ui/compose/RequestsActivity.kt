package com.wise.studentdelivery.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wise.studentdelivery.model.Ride
import com.wise.studentdelivery.model.User
import com.wise.studentdelivery.network.RestApiServer
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
fun RideCompose(allRide :List<Ride>) {

    println("ridee $allRide ride")
    Column() {
        allRide.forEach { _a ->
            Text(text = _a.goTime)
        }
    }
}