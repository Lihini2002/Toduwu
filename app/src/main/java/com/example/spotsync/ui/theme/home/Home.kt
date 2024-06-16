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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.example.spotsync.R
import com.example.spotsync.ui.theme.*
import com.example.spotsync.ui.theme.places.location


@Composable
fun HomeScreen() {
  SpotSyncTheme() {
      Box(
          modifier = Modifier
              .padding(top= 52.dp , start=16.dp , end=16.dp )


      ) {
          Column (
              modifier = Modifier
                  .align(Alignment.Center)
                  .verticalScroll(rememberScrollState())
                  .fillMaxSize()

          ) {
              CurrentLocation()
              Text(
                  text = "Less than 1km Radius",
                  color = Color.DarkGray,
                  fontWeight = FontWeight(700),
                  fontSize = 16.sp
              )
              TaskList()
              Spacer(modifier = Modifier.height(10.dp))
              Text(
                  text = "In 2km Radius",
                  color = Color.DarkGray,
                  fontWeight = FontWeight(700),
                  fontSize = 16.sp
              )
              TaskList()
              Spacer(modifier = Modifier.height(10.dp))
              Text(
                  text = "In 5km Radius",
                  color = Color.DarkGray,
                  fontWeight = FontWeight(700),
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
               .shadow(8.dp, RoundedCornerShape(16.dp))

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
                    fontSize = 14.sp,
                   fontWeight = FontWeight(400),
                   maxLines = 3
                   ,
                   modifier = Modifier.padding(horizontal = 8.dp),

                   )
               Spacer(modifier = Modifier.height(8.dp))
               ElevatedButton(
                   onClick = { /* Handle button click */ },
//                   colors = ButtonColors(
//                       containerColor =  OliveGreen,
//                    contentColor = Color.White,
//                       disabledContainerColor = Color.Gray,
//                       disabledContentColor = Color.White
//               )

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





//@Com
@Composable
fun CurrentLocation() {
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
                   Text(text = "Kadawala , Katana", color = Color.Gray , fontSize = 12.sp , textAlign = TextAlign.Start)

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
        Task("Complete Project Proposal", "Prepare a detailed project proposal for the upcoming meeting", Icons.Default.Email, "Home", "High"),
        Task("Review Design Mockups", "Review and provide feedback on the design mockups for the new feature", Icons.Default.Person, "Office", "Medium"),
        Task("Call Client for Follow-up", "Follow up with the client regarding the latest project updates", Icons.Default.Phone, "School", "Low"),
        Task("Prepare Presentation Slides", "Create presentation slides for the team meeting next week", Icons.Default.Place, "Home", "High"),
        Task("Send Status Update Email", "Compose and send a status update email to the project stakeholders", Icons.Default.Email, "Home", "High"),
        Task("Meet with Team Lead", "Schedule a meeting with the team lead to discuss project timelines", Icons.Default.Person, "Office", "Medium"),
        Task("Research New Technologies", "Conduct research on emerging technologies for potential project enhancements", Icons.Default.Phone, "School", "Low"),
        Task("Plan Team Building Event", "Coordinate and plan a team-building event for the upcoming month", Icons.Default.Place, "Home", "High")
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


