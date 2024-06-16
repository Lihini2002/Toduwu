package com.example.spotsync.ui.theme.uicomponents
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spotsync.ui.theme.OffWhite
import com.example.spotsync.ui.theme.SpotSyncTheme
import com.example.spotsync.ui.theme.lightYellowishGray

@Composable
fun SearchFunction() {
    var query by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = query,
            onValueChange = {
                query = it
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    // Handle search action here
                    keyboardController?.hide()
                }
            ),
            maxLines = 1 ,
            minLines = 1 ,

            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(lightYellowishGray)
                .border(
                    0.4.dp,
                    MaterialTheme.colors.onSurface,
                    MaterialTheme.shapes.medium
                )
                .clip(MaterialTheme.shapes.medium)
                .padding(start = 16.dp, end = 16.dp)
        )

        androidx.compose.material.IconButton(
            onClick = {
                isSearching = !isSearching
                if (!isSearching) {
                    query = ""
                }
            },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchFunctionPreview() {
    SpotSyncTheme {
        SearchFunction()
    }
}
