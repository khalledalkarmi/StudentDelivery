package com.wise.studentdelivery.ui.compose

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.wise.studentdelivery.R
import com.wise.studentdelivery.model.Ride
import com.wise.studentdelivery.network.RestApiServer
import com.wise.studentdelivery.ui.compose.ui.theme.StudentDeliveryTheme
import com.wise.studentdelivery.ui.fragment.FilterFragment
import com.wise.studentdelivery.ui.fragment.RideView

class RequestsViewFragment : Fragment() {
    lateinit var apiServer: RestApiServer
    private lateinit var email: String
    private var uniName = ""
    private lateinit var filterFragment: FilterFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiServer = RestApiServer()
        filterFragment = FilterFragment()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        email = requireArguments().getString("email").toString()
        return ComposeView(requireContext()).apply {
            apiServer.getUserByEmail(email = email) {
                if (it != null) {
                    println("compares here")
                    uniName = it.uniName
                }
            }

            apiServer.getAllRide {
                if (it != null) {
                    setContent {
                        StudentDeliveryTheme {
                            Surface(color = MaterialTheme.colors.background) {
                                Column {
                                    Button(onClick = {
                                        parentFragmentManager.commit {
                                            replace(R.id.fragmentContainerViewMain, filterFragment)
                                        }
                                    }) {
                                        Icon(
                                            Icons.Filled.Search,
                                            contentDescription = "",
                                            modifier = Modifier.size(ButtonDefaults.IconSpacing)
                                        )
                                        Text(text = "Filter")
                                    }
                                    CountrySelection()
                                    RideCompose(allRide = it)
                                }
                            }
                        }
                    }
                    println("size  of all ride list: ${it.size}")
                }
            }
        }
    }

    @Composable
    fun CountrySelection(): List<Ride>? {
        // State variables
        var filterRide = mutableListOf<Ride>()
        val countriesList: Array<String> = resources.getStringArray(R.array.universities)
        var countryName: String by remember { mutableStateOf(countriesList[0]) }
        var expanded by remember { mutableStateOf(false) }

        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Row(
                Modifier
                    .padding(24.dp)
                    .clickable {
                        expanded = !expanded
                    }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) { // Anchor view
                Text(
                    text = countryName,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(end = 8.dp)
                ) // Country name label
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")

                //
                DropdownMenu(expanded = expanded, onDismissRequest = {
                    expanded = false
                }) {
                    countriesList.forEach { country ->
                        DropdownMenuItem(onClick = {
                            expanded = false
                            countryName = country
                            apiServer.getRideByUni(country) {
                                println("gggggggggggggggggggg")
                                println(it)
                                filterRide = it as MutableList<Ride>
                            }
                        }) {
                            Text(text = country)
                        }
                    }
                }
            }
        }
        println(filterRide)
        return filterRide

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

    @Composable
    fun RideText(ride: Ride) {
        val rideView = RideView()
        var rideNeighborhood = ""
        if (ride.neighborhoodName != null) {
            rideNeighborhood = ride.neighborhoodName
        }
        val rideInfo = String.format(
            "Name: ${ride.firstName} ${ride.lastName} \n" +
                    "From: ${ride.cityName}, $rideNeighborhood \n" +
                    "To: ${ride.uniName} \n" +
                    "Go time: ${ride.goTime} \n" +
                    "Comeback time ${ride.comeBackTime} \n" +
                    "Empty seats: ${ride.emptySeats} \n" +
                    "Cost: ${ride.price} JD"
        )
        Text(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            text = rideInfo,

            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clickable
                {
                    parentFragmentManager.commit {
                        val bundle = bundleOf("email" to ride.email.toString())
                        addToBackStack("requestView")
                        rideView.arguments = bundle
                        replace(R.id.fragmentContainerViewMainFun, rideView)
                    }
                }
        )
    }

    @Composable
    fun CustomAlert(): Unit {
        val openDialog = remember { mutableStateOf(true) }
        var text by remember { mutableStateOf("") }

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = "Title")
                },
                text = {
                    Column() {
                        TextField(
                            value = text,
                            onValueChange = { text = it }
                        )
                        Text("Custom Text")
                        Checkbox(checked = false, onCheckedChange = {})
                    }
                },
                buttons = {
                    Row(
                        modifier = Modifier.padding(all = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { openDialog.value = false }
                        ) {
                            Text("Dismiss")
                        }
                    }
                }
            )
        }
    }
}