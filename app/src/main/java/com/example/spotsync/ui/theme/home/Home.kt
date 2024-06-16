package com.example.spotsync.ui.theme.home
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.lightColors
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.spotsync.MainScreen
import com.example.spotsync.R

import com.example.spotsync.ui.theme.*
import com.example.spotsync.ui.theme.places.location
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState


@Composable
fun HomeScreen(currentPosition: LatLng, cameraState: CameraPositionState)  {
//    val homeViewModel: HomeViewModel = hiltViewModel()
//    val locationState = homeViewModel.locationState.collectAsState()

    // Access the current location state
//    val currentLocationState = locationState.value
//    val currentLatitude = currentLocationState.latitude
//    val currentLongitude = currentLocationState.longitude

    // Now you can use homeViewModel to access its properties and functions
    // and observe its state

    // Example usage

  SpotSyncTheme() {
      Box(
          modifier = Modifier
              .padding(top= 52.dp , start=16.dp , end=16.dp )


      ) {
          Column (
              modifier = Modifier
                  .align(Center)
                  .verticalScroll(rememberScrollState())
                  .fillMaxSize()

          ) {
//              CurrentLocation(locationState)
              MainScreen(currentPosition, cameraState)
              Text(

                  text = "Less than 1km Radius",
                  color = Color.DarkGray,
                  fontWeight = FontWeight(500),
                  fontSize = 16.sp
              )
              TaskList()
              Spacer(modifier = Modifier.height(10.dp))
              Text(
                  text = "In 2km Radius",
                  color = Color.DarkGray,
                  fontWeight = FontWeight(500),
                  fontSize = 16.sp
              )
              TaskList()
              Spacer(modifier = Modifier.height(10.dp))
              Text(
                  text = "In 5km Radius",
                  color = Color.DarkGray,
                  fontWeight = FontWeight(500),
                  fontSize = 16.sp
              )
              TaskList()




          }

      }
  }




}







@Composable
fun TaskCard(task: Task) {

       Card(
           modifier = Modifier
               .padding(8.dp)
               .width(200.dp)
               .height(IntrinsicSize.Min)
               .border(width = 2.dp, color = Color.Black, shape = MaterialTheme.shapes.medium)
           ,


       ) {
           Column(
               modifier = Modifier
                   .fillMaxSize()
                   .padding(8.dp)
           ) {
               Row(
                   verticalAlignment = Alignment.CenterVertically,
                   modifier = Modifier.fillMaxWidth()
               ) {
                   Icon(
                       imageVector = task.icon,
                       contentDescription = null,
                       modifier = Modifier.size(16.dp)
                   )
                   Spacer(modifier = Modifier.width(8.dp))
                   Text(text = task.taskPlace)
               }
               Spacer(modifier = Modifier.height(8.dp))
               Text(
                   text = task.title,
                    fontSize = 17.sp,
                   modifier = Modifier.padding(horizontal = 8.dp),

                   )
               Spacer(modifier = Modifier.height(8.dp))
               ElevatedButton(
                   onClick = { /* Handle button click */ },
                   colors = ButtonColors(
                       containerColor =  OliveGreen,
                    contentColor = Color.White,
                       disabledContainerColor = Color.Gray,
                       disabledContentColor = Color.White
               )
                   ,
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(horizontal = 8.dp)
                   ,



                   ) {
                   Column(
                       horizontalAlignment = Alignment.CenterHorizontally
                   ){
                       Text(text = "1km Away" ,  fontWeight = FontWeight(600), textAlign = TextAlign.Center)
                   }


               }
           }
       }
   }


// Example function to demonstrate extracting location from LocationState
fun getLocationFromState(locationState: LocationState): LatLng? {
    return when (locationState) {
        is LocationState.Idle -> null // No location available in Idle state
        is LocationState.LocationUpdate -> locationState.location // Extract location from LocationUpdate
        is LocationState.Error -> null // Handle error state as needed
    }
}



//@Com
@Composable
fun CurrentLocation(locationState: State<LocationState>) {
    val currentLocationState = locationState.value

    val currentLocation = getLocationFromState(currentLocationState)
   Box(
       contentAlignment = Alignment.Center,
       modifier = Modifier
           .fillMaxWidth(1f)
           .height(IntrinsicSize.Min)
           .padding(8.dp)



   ){
       Card(
           modifier = Modifier
               .padding(top = 8.dp, bottom = 8.dp)
               .align(Center)


       ) {
           Row(
               modifier = Modifier
                   .background(LightGreen)

           ) {
               // First Column
               Column(
                   modifier = Modifier
                       .weight(0.5f)
                       .padding(10.dp)
               ) {
                   Text(text="Current Location",color = Color.DarkGray , fontSize = 16.sp, textAlign = TextAlign.Start ,fontWeight = FontWeight.Bold, maxLines = 2 , minLines = 2)
                   Text(text="${currentLocation?.latitude}  ${currentLocation?.longitude}" , color = Color.Gray , fontSize = 12.sp , textAlign = TextAlign.Start)

               }

               // Second Column
               Column(
                   modifier = Modifier
                       .weight(0.4f)
                       .fillMaxHeight()
               ) {
                   Image(
                       painter = painterResource(id = R.drawable.mapimage),
                       contentDescription = null,
                       alignment =  Alignment.CenterStart,
                       modifier = Modifier
                           .height(70.dp)
                           .width(200.dp)
                           .clip(shape = RoundedCornerShape(8.dp))
                           .padding(8.dp)
                   )
               }
           }
       }
   }

}


data class Task(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val taskPlace: String,
    val priority: String
)



@Composable
fun TaskList() {
    // ... Existing code ...
    // ...
    val taskData = listOf(
        Task("Title 1", "Subtitle 1", Icons.Filled.Email, "Home", "High"),
        Task("Title 2", "Subtitle 2", Icons.Filled.Person, "Office", "Medium"),
        Task("Title 3", "Subtitle 3", Icons.Filled.Phone, "School", "Low"),
        Task("Title 4", "Subtitle 4", Icons.Filled.Place, "Home", "High"),
        Task("Title 1", "Subtitle 1", Icons.Filled.Email, "Home", "High"),
        Task("Title 2", "Subtitle 2", Icons.Filled.Person, "Office", "Medium"),
        Task("Title 3", "Subtitle 3", Icons.Filled.Phone, "School", "Low"),
        Task("Title 4", "Subtitle 4", Icons.Filled.Place, "Home", "High"),


        // Add more tasks as needed
    )
//
//    LazyColumn {
//        items(taskData) { task ->
//            TaskCard(
//                title = task.title,
//                subtitle = task.subtitle,
//                icon = task.icon,
//                taskPlace = task.taskPlace,
//                priority = task.priority
//            )
//        }
//    }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items(taskData) { task ->
            TaskCard(task)
        }
    }


}









@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}


