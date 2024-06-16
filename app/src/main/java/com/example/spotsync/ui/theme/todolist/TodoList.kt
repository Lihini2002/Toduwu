package com.example.spotsync.ui.theme.todolist

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.spotsync.R
import com.example.spotsync.ui.theme.OffWhite
import com.example.spotsync.ui.theme.OliveGreen
import com.example.spotsync.ui.theme.SpotSyncTheme
import java.time.LocalDate
import com.example.spotsync.ui.theme.uicomponents.SearchFunction

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.InputStreamReader
import java.io.Serializable
import java.lang.reflect.Type
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)


val ProtestStrikeFamily = FontFamily(
Font(R.font.proteststrike , weight=Light)
)



data class Todo(
    val title: String,
    val subtitle: String,
    val priority: Int,
    val isDone: Boolean,
    val location: String,
    val date: LocalDate,
    val description: String
) : Serializable


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoListScreen(
//    onNavigateToAdd:() -> Unit
) {
  SpotSyncTheme() {
      val today = LocalDate.of(2023, 2, 14)
      val tomorrow = today.plusDays(1)
      val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
      val context = LocalContext.current
      val todos = loadTodosFromFile(context)
      Column(
          modifier = Modifier
              .fillMaxSize()
              .padding(top = 16.dp) // Add top margin to the entire screen
      ) {
          SearchFunction()
          Button(
              onClick = {},
//              onNavigateToAdd,

              modifier = Modifier
                  .padding(10.dp)
                  .align(Alignment.End)
          ) {
              Icon(Icons.Filled.Add, contentDescription = "Add new todo")
          }

          Box(modifier = Modifier.weight(1f)

          )
          {
              Row(){
                  TodoListSection(title = "Today", todos = getTodosForDate(todos , today) , isPortrait=isPortrait )

              }


          }

          Spacer(modifier = Modifier.height(25.dp))
          Box(modifier = Modifier.weight(1f)) {
              TodoListSection(title = "Tomorrow", todos = getTodosForDate(todos , tomorrow) , isPortrait=isPortrait)
          }

          // Add more sections for other dates as needed


      }
  }
}



//@android.support.annotation.RequiresApi(Build.VERSION_CODES.O)
fun loadTodosFromFile(context: Context): List<Todo> {
    val inputStream = context.resources.openRawResource(R.raw.todolist)
    val reader = InputStreamReader(inputStream)
    val listType = object : TypeToken<List<Todo>>() {}.type
//    val json = File("/todolist.json").readText() // Read the content of the file into a String
//  val listType = object : TypeToken<List<Todo>>() {}.type

    val gson = GsonBuilder()
        .registerTypeAdapter(Todo::class.java, TodoDeserializer())
        .create()

    return gson.fromJson(reader, listType) // Deserialize the JSON string into a List<Todo>
}


class TodoDeserializer : JsonDeserializer<Todo> {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Todo {
        json as JsonObject

        val title = json.get("title").asString
        val priority = json.get("priority").asInt
        val isDone = json.get("isDone").asBoolean
        val description = json.get("description").asString
        val subtitle = json.get("subtitle").asString
        val location = json.get("location").asString
        val dateStr = json.get("date").asString
        val date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE)

        return Todo(
            title = title,
            subtitle = subtitle,
            priority = priority,
            isDone = isDone,
            location = location,
            date = date,
            description = description
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTodosForDate(todos: List<Todo> , todoDate: LocalDate): List<Todo> {
    // Assuming todos is a list of all todos in your application



    // Assuming you have a List<Todo> named 'todos' and a target LocalDate named 'targetDate'
    val todosForDate = todos.filter { it.date == todoDate }


    // Filter todos based on the specified date
        return todosForDate

}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SectionHeading(text: String) {
    Text(
        text = text,
        fontSize = 28.sp,
        fontFamily = ProtestStrikeFamily,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable


fun TodoListSection(title: String, todos: List<Todo>, isPortrait: Boolean) {
    val numColumns = if(isPortrait) 2 else 4
    var selectedTodo by remember { mutableStateOf<Todo?>(null) }

    Column {
        SectionHeading(text = title)
        Spacer(modifier = Modifier.height(20.dp))

        LazyVerticalGrid(
            GridCells.Fixed(numColumns),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
//            columns = GridCells.Fixed(numColumns)
        ) {
            items(todos) { todo ->
                TodoCard(
                    title = todo.title,
                    subtitle = todo.subtitle,
                    description = todo.description,
                    priority = todo.priority,
                    isDone = todo.isDone,
                    date = todo.date,
                    location = todo.location,
                    onClick = { selectedTodo = todo }
                )
            }
        }
    }
}

//fun TodoListSection(title: String, todos: List<Todo>, isPortrait: Boolean) {
//    val numColumns = if (isPortrait) 2 else 4
//    var selectedTodo by remember { mutableStateOf<Todo?>(null) }
//
//    if (selectedTodo == null) {
//        Column {
//            SectionHeading(text = title)
//            Spacer(modifier = Modifier.height(20.dp))
//
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(numColumns),
//                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
//            ) {
//                items(todos) { todo ->
//                    TodoCard(
//                        title = todo.title,
//                        subtitle = todo.subtitle,
//                        priority = todo.priority,
//                        isDone = todo.isDone,
//                        location = todo.location,
//                        date = todo.date,
//                        description = todo.description,
//                        onClick = { selectedTodo = todo }
//                    )
//                }
//            }
//        }
//    } else {
//        TodoDetail(
//            todo = selectedTodo!!,
//            onToggleDone = { updatedTodo ->
//                // Update the todo list with the toggled done status
//                selectedTodo = updatedTodo
//                // Assuming you have a function to update the list
//                // updateTodoList(updatedTodo)
//            },
//            onBack = { selectedTodo = null }
//        )
//    }
//}





@Composable
fun TodoCard(
    title: String,
    subtitle: String,
    priority: Int,
    isDone: Boolean,
    location: String,
    description: String,
    onClick: (Todo) -> Unit,
    date: LocalDate,

) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Black),
        backgroundColor = if (isDone) OliveGreen else OffWhite

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                PriorityIndicator(priority = priority)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = location,
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "2024/02/14",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun PriorityIndicator(priority: Int) {
    Box(
        modifier = Modifier
            .size(16.dp)
            .background(
                color = when (priority) {
                    1 -> MaterialTheme.colors.error
                    2 -> MaterialTheme.colors.secondary
                    3 -> MaterialTheme.colors.surface
                    else -> MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
                }
            )
    )
}





@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun TodoListScreenPreview(){


    TodoListScreen()

}



