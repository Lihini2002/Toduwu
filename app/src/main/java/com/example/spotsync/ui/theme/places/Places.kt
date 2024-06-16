package com.example.spotsync.ui.theme.places

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spotsync.R
import com.example.spotsync.ui.theme.SpotSyncTheme
import com.example.spotsync.ui.theme.uicomponents.SearchFunction
import com.example.spotsync.uilayer.PlacesViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import com.example.spotsync.datalayer.Location


@Composable
fun Places() {
    SpotSyncTheme {
        val viewModel: PlacesViewModel = hiltViewModel()
        val locations by viewModel.locations.observeAsState(initial = emptyList())

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 52.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                SearchFunction()
                Text(
                    text = "Saved Places", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp)
                )
                PlacesList(locations)
            }
        }
    }
}

@Composable
fun PlacesList(locations: List<Location>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(locations) { location ->
            TwoColumnCard(location)
            Spacer(modifier = Modifier.height(16.dp)) // Add spacing between cards
        }
    }
}

@Composable
fun TwoColumnCard(location: Location) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.60f)
            .padding(top = 8.dp, bottom = 8.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .border(
                0.4.dp,
                MaterialTheme.colors.onSurface,
                MaterialTheme.shapes.medium
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            // First Column
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .padding(16.dp)
            ) {
                Text(location.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, maxLines = 2, minLines = 2)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${location.distance} away", color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${location.todosCount} Tasks", color = Color.Red, fontWeight = FontWeight.Bold)
            }

            // Second Column
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxHeight()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mapimage),
                    contentDescription = null,
                    alignment = Alignment.CenterStart,
                    modifier = Modifier
                        .height(100.dp)
                        .width(270.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )
            }
        }
    }
}
