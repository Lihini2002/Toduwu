package com.example.spotsync.ui.theme.todolist
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.spotsync.ui.theme.OffWhite
import com.example.spotsync.ui.theme.SpotSyncTheme


@Composable
fun AddTaskForm() {
SpotSyncTheme() {
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("") }

    val priorityOptions = listOf("High", "Medium", "Low")

    Column(
        modifier = Modifier

            .padding(20.dp)
        ,
        horizontalAlignment= Alignment.CenterHorizontally
    ) {

        Text( text="Add New Task" , fontSize = 16.sp , fontWeight = FontWeight(600))
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        TextField(
            value = subtitle,
            onValueChange = { subtitle = it },
            label = { Text("Details") },
            maxLines = 3,
            minLines = 3,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        TextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )


        TextField(
            value = priority,
            onValueChange = {priority = it},
            label = { Text("Priority") },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        ElevatedButton(
            onClick = {
//                val newTask = Todo(
//                    title = title,
//                    subtitle = subtitle,
//                    priority = priority,
//                    isDone = false,
//                    location = location,
//                )
//                onAddTaskClick(newTask)
            },
            modifier = Modifier.fillMaxWidth(),
//            colors = ButtonColors(
//                containerColor = OffWhite ,
//                contentColor = Color.Black,
//                disabledContainerColor = Color.LightGray,
//                disabledContentColor =Color.DarkGray
//            )
        ) {
            Text("Add Task")
        }
    }




}



    }




@Preview
@Composable
fun AddTaskFormPreview(){


}
