package com.example.spotsync.ui.theme.todolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
//import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TodoDetail(
    todo: Todo,
    onToggleDone: (Todo) -> Unit,
    onBack: () -> Unit
) {
//    var isDone by rememberSaveable { mutableStateOf(todo.isDone) }
//    var isDone by rememberSaveable(todo) { mutableStateOf(todo.isDone) }
    var isDone = false;
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = todo.title, style = MaterialTheme.typography.titleLarge)
        Text(text = todo.subtitle, style = MaterialTheme.typography.bodyMedium)
        Text(text = "Priority: ${todo.priority}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Location: ${todo.location}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Date: ${todo.date}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Description: ${todo.description}", style = MaterialTheme.typography.bodySmall)
        Text(text = if (isDone) "Status: Done" else "Status: Not Done", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            isDone = !isDone
            onToggleDone(todo.copy(isDone = isDone))
        }) {
            Text(text = if (isDone) "Mark as Not Done" else "Mark as Done")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack) {
            Text(text = "Back")
        }
    }
}
