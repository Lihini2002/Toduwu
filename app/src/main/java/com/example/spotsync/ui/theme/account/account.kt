package com.example.spotsync.ui.theme.account
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.spotsync.R
import com.example.spotsync.ui.theme.SpotSyncTheme
import java.io.File


@Composable
fun Account() {
 SpotSyncTheme() {
     Column(
         modifier = Modifier
             .fillMaxSize()
             .padding(16.dp)
             .background(Color.White)
     ) {
         // Title
         Text(
             text = "Settings",
             style = TextStyle(
                 fontSize = 24.sp,
                 fontWeight = FontWeight.Bold,
                color = Color.Gray
             ),
             modifier = Modifier.padding(bottom = 16.dp)
         )

         MyImageArea(
             null, //target url to preview
             null, // stored directory
               // s
         )

         // Divider
         Divider(
             color = Color.Gray,
             thickness = 1.dp,
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(vertical = 8.dp)
         )

         // Switch for notifications
         SettingsItem(
             icon = Icons.Default.Notifications,
             title = "Receive Notifications",
             description = "Enable or disable app notifications",
             trailing = {
                 Switch(
                     checked = false,
                     onCheckedChange = {}
                 )
             }
         )

         // Divider
         Divider(
             color = Color.Gray,
             thickness = 1.dp,
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(vertical = 8.dp)
         )

         // Slider for volume control
         SettingsItem(
             icon = Icons.Default.Call,
             title = "Volume",
             description = "Adjust the volume level",
             trailing = {
                 Slider(
                     value = 0.5f,
                     onValueChange = {},
                     modifier = Modifier.width(200.dp)
                 )
             }
         )

         // Divider
         Divider(
             color = Color.Gray,
             thickness = 1.dp,
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(vertical = 8.dp)
         )

         // Button to sign out
         SettingsItem(
             icon = Icons.Default.ExitToApp,
             title = "Sign Out",
             description = "Sign out of your account",
             trailing = {
                 Button(onClick = {}, modifier = Modifier.padding(vertical = 8.dp)) {
                     Text("Sign Out")
                 }
             }
         )
     }

 }
}
@Composable
fun MyImageArea(
    uri: Uri? = null, //target url to preview
    directory: File? = null, // stored directory
    onSetUri : (Uri) -> Unit = {}) // s {
{
    val context = LocalContext.current
    val tempUri = remember { mutableStateOf<Uri?>(null) }
    val authority = stringResource(id = R.string.fileprovider)

    // for takePhotoLauncher used
    fun getTempUri(): Uri? {
        directory?.let {
            it.mkdirs()
            val file = File.createTempFile(
                "image_" + System.currentTimeMillis().toString(),
                ".jpg",
                it
            )

            return FileProvider.getUriForFile(
                context,
                authority,
                file
            )
        }
        return null
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let {
                onSetUri.invoke(it)
            }
        }
    )

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {isSaved ->
            tempUri.value?.let {
                onSetUri.invoke(it)
            }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted, launch takePhotoLauncher
            val tmpUri = getTempUri()
            tempUri.value = tmpUri
            takePhotoLauncher.launch(tempUri.value)
        } else {
            // Permission is denied, handle it accordingly
        }
    }

    var showBottomSheet by remember { mutableStateOf(false) }
    if (showBottomSheet){
        MyModalBottomSheet(
            onDismiss = {
                showBottomSheet = false
            },
            onTakePhotoClick = {
                showBottomSheet = false

                val permission = Manifest.permission.CAMERA
                if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission is already granted, proceed to step 2
                    val tmpUri = getTempUri()
                    tempUri.value = tmpUri
                    takePhotoLauncher.launch(tempUri.value)
                } else {
                    // Permission is not granted, request it
                    cameraPermissionLauncher.launch(permission)
                }
            },
            onPhotoGalleryClick = {
                showBottomSheet = false
                imagePicker.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
        )
    }

    Column (
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Profile Picture",
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    showBottomSheet = true
                }
            ) {
                Text(text = "Select / Take")
            }
        }

        //preview selfie
        uri?.let {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
//                AsyncImage(
//                    model = it,
//                    modifier = Modifier.size(
//                        160.dp
//                    ),
//                    contentDescription = null,
//                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}

@Composable
fun SettingsItem(icon: ImageVector, title: String, description: String, trailing: @Composable () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Icon
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp))

        // Content
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            // Title
            Text(text = title, fontWeight = FontWeight.Bold)

            // Description
            Text(text = description, color = Color.Gray, fontSize = 12.sp)
        }

        // Trailing element (Switch, Slider, Button, etc.)
        trailing()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyModalBottomSheet(
    onDismiss: () -> Unit,
    onTakePhotoClick: () -> Unit,
    onPhotoGalleryClick: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.padding(16.dp)
    ) {
        Column {
            Button(
                onClick = onTakePhotoClick,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(text = "Take Photo")

            }
            Button(
                onClick = onPhotoGalleryClick,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(text = "Choose from Gallery")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountPreview() {

    Account()
}