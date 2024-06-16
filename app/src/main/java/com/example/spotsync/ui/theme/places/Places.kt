package com.example.spotsync.ui.theme.places

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spotsync.R
import com.example.spotsync.ui.theme.*
import com.example.spotsync.ui.theme.todolist.TodoCard
import com.example.spotsync.ui.theme.places.placesExample as placesList
import com.example.spotsync.ui.theme.uicomponents.SearchFunction

@Composable
fun Places() {
  SpotSyncTheme() {
      Box(
          modifier = Modifier
              .fillMaxSize()
              .background(color = lightYellowishGray)
      ) {
          Column(
              modifier = Modifier
                  .fillMaxSize()
                  .padding(16.dp)

          ) {

//            Text("Saved Places", fontWeight = FontWeight.Bold, fontSize = 16.sp , color= Color.Gray
//                    , modifer = Modifier
//                    .align(Alignment.Start))
              SearchFunction()
              Text( text ="Saved Places", fontWeight = FontWeight.Bold, fontSize = 16.sp , color= Color.Black ,
                  modifier = Modifier.padding(start=8.dp))
              PlacesList(placesList)
          }
      }
  }
}





@Preview
@Composable
fun PlacesPreview(){
Places()
}





@Composable
fun PlacesList(locations: List<location>) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    , columns = GridCells.Fixed(1), verticalArrangement = Arrangement.SpaceEvenly


    ) {
//        items(places) { place ->
//            TwoColumnCard()
//            Spacer(modifier = Modifier.height(16.dp)) // Add spacing between cards
//        }
        items(locations) { location ->
           TwoColumnCard(
               location)
        }
    }
}

@Composable
fun TwoColumnCard(place: location) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.60f)
            .padding(top = 8.dp, bottom = 8.dp)
            .border(
                2.dp,
                MaterialTheme.colors.onSurface,
                MaterialTheme.shapes.medium
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(LightGreen)
        ) {
            // First Column
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .padding(16.dp)
            ) {
                Text(place.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, maxLines = 2 , minLines = 2)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${place.distance} away", color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${place.todosCount} Tasks", color = darkRed, fontWeight = FontWeight.Bold)
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
                    alignment =  Alignment.CenterStart,
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
