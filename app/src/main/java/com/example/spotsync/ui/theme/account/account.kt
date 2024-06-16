package com.example.spotsync.ui.theme.account
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spotsync.ui.theme.SpotSyncTheme


@Composable
fun Account() {
 SpotSyncTheme() {
     Column(
         modifier = Modifier
             .fillMaxSize()
             .padding(16.dp)
     ) {
         // Title
         Text(
             text = "Settings",
             style = TextStyle(
                 fontSize = 24.sp,
                 fontWeight = FontWeight.Bold
             ),
             modifier = Modifier.padding(bottom = 16.dp)
         )

         // Divider
         Divider(
             color = Color.Gray,
             thickness = 1.dp,
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(vertical = 8.dp)
         )

         var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

         MyImageArea(
             uri = selectedImageUri,
             onSetUri = { uri ->
                 selectedImageUri = uri
             }
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

@Composable
fun MyImageArea(
    uri: Uri? = null, //target url to preview
    directory: File? = null, // stored directory
    onSetUri : (Uri) -> Unit = {}, // selected / taken uri
) {
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
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
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
                AsyncImage(
                    model = it,
                    modifier = Modifier.size(
                        160.dp
                    ),
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}
@Preview(showBackground = true)
@Composable
fun AccountPreview() {

    Account()
}