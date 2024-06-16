package com.example.spotsync

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import android.Manifest

//import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.spotsync.ui.theme.OliveGreen
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.spotsync.datalayer.*
//screens
import com.example.spotsync.ui.theme.SpotSyncTheme
import com.example.spotsync.ui.theme.todolist.TodoListScreen
import com.example.spotsync.ui.theme.account.Account
import com.example.spotsync.ui.theme.login.SigninScreen
import com.example.spotsync.ui.theme.places.Places
import com.example.spotsync.ui.theme.todolist.AddTaskForm
import com.example.spotsync.ui.theme.todolist.ProtestStrikeFamily
import com.example.spotsync.ui.theme.todolist.TodoDetail
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.*
import com.example.spotsync.ui.theme.home.HomeScreen as Home
import com.example.spotsync.ui.theme.login.LoginScreen as Login

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)


    class MainActivity : ComponentActivity() {
        @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
        @OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
        override fun onCreate(savedInstanceState: Bundle?) {

            val locationViewModel: MainActivityVM by viewModels()
            super.onCreate(savedInstanceState)
            setContent {


                val permissionState = rememberMultiplePermissionsState(
                    permissions = listOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )

                val viewState by locationViewModel.viewState.collectAsStateWithLifecycle()

                SpotSyncTheme() {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Gray
                    ) {

                        LaunchedEffect(!hasLocationPermission()) {
                            permissionState.launchMultiplePermissionRequest()
                        }

                        when {
                            permissionState.allPermissionsGranted -> {
                                LaunchedEffect(Unit) {
                                    locationViewModel.handle(PermissionEvent.Granted)
                                }
                            }

                            permissionState.shouldShowRationale -> {
                                RationaleAlert(onDismiss = { }) {
                                    permissionState.launchMultiplePermissionRequest()
                                }
                            }

                            !permissionState.allPermissionsGranted && !permissionState.shouldShowRationale -> {
                                LaunchedEffect(Unit) {
                                    locationViewModel.handle(PermissionEvent.Revoked)
                                }
                            }
                        }

                        with(viewState) {
                            when (this) {
                                ViewState.Loading -> {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }

                                ViewState.RevokedPermissions -> {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(24.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text("We need permissions to use this app")
                                        Button(
                                            onClick = {
                                                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                                            },
                                            enabled = !hasLocationPermission()
                                        ) {
                                            if (hasLocationPermission()) CircularProgressIndicator(
                                                modifier = Modifier.size(14.dp),
                                                color = Color.White
                                            )
                                            else Text("Settings")
                                        }
                                    }
                                }

                                is ViewState.Success -> {
                                    val currentLoc =
                                        LatLng(
                                            location?.latitude ?: 0.0,
                                            location?.longitude ?: 0.0
                                        )
                                    val cameraState = rememberCameraPositionState()

                                    LaunchedEffect(key1 = currentLoc) {
                                        cameraState.centerOnLocation(currentLoc)
                                    }

                                    com.example.spotsync.ui.theme.home.HomeScreen(
                                        currentPosition = LatLng(
                                            currentLoc.latitude,
                                            currentLoc.longitude
                                        ),
                                        cameraState = cameraState
                                    )
                                }
                            }
                        }
                    }
                }
            }
                // setting up the individual tabs

                // creating our navController


                }
            }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    navController: NavHostController = rememberNavController(),
)
{
    val navController = rememberNavController()

    val HomeTab = TabBarItem(title = "Home", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
    val ToDuwusTab = TabBarItem(title = "ToDuwus", selectedIcon = Icons.Filled.Done, unselectedIcon = Icons.Outlined.Done, badgeAmount = 7)
    val PlacesTab = TabBarItem(title = "Places", selectedIcon = Icons.Filled.LocationOn, unselectedIcon = Icons.Outlined.LocationOn)
    val AccountTab = TabBarItem(title = "Account", selectedIcon = Icons.Filled.Person, unselectedIcon = Icons.Outlined.Person)

    // creating a list of all the tabs
    val tabBarItems = listOf(HomeTab, ToDuwusTab ,PlacesTab ,AccountTab)

    Scaffold(
        bottomBar = { TabView(tabBarItems, navController) } ,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = OliveGreen,
                    titleContentColor = Color.White

                ),


                title = {
                    Text(
                        "TODUWU",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = ProtestStrikeFamily,
                        fontSize = 18.sp
                    )
                }
            )
        }









    )

    {




        NavHost(navController = navController, startDestination = "Login") {

            composable("Login") {
              Login(onNavigateToLogin = {
                  navController.navigate("Home")
              },)
            }
            composable(HomeTab.title) {
//                Home(currentPosition , CameraPositionState)
            }

//            val userSignupViewModel: UserSignupViewModel = hiltViewModel()
//            SigninScreen(userSignupViewModel = userSignupViewModel, navController = navController
            composable(ToDuwusTab.title) {
                TodoListScreen(
//                    onNavigateToAdd = {
//                        navController.navigate("AddNewTodoScreen")
//                    },

                )
            }
            
//            composable("TodoDetail")
//            {
//                TodoDetail(todo = , onToggleDone = ) {
//
//                }
//            }
//

            composable("AddNewTodoScreen") {
                // Composable for the "Add New Todo" screen
                AddTaskForm()
            }

            composable(PlacesTab.title) {
                Places()
            }
            composable(AccountTab.title) {
                Account()
            }




        }
    }
}


    // ----------------------------------------
// This is a wrapper view that allows us to easily and cleanly
// reuse this component in any future project
    @Composable
    fun TabView(tabBarItems: List<TabBarItem>, navController: NavController) {
        var selectedTabIndex by rememberSaveable {
            mutableStateOf(0)
        }

        NavigationBar (
            containerColor = OliveGreen ,
                    contentColor = Color.White
        ){
            // looping over each tab to generate the views and navigation for each item


            tabBarItems.forEachIndexed { index, tabBarItem ->
                NavigationBarItem(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        navController.navigate(tabBarItem.title)
                    },
                    icon = {
                        TabBarIconView(
                            isSelected = selectedTabIndex == index,
                            selectedIcon = tabBarItem.selectedIcon,
                            unselectedIcon = tabBarItem.unselectedIcon,
                            title = tabBarItem.title,
                            badgeAmount = tabBarItem.badgeAmount
                        )
                    },
                    label = {Text(tabBarItem.title)})
            }
        }
    }

    // This component helps to clean up the API call from our TabView above,
// but could just as easily be added inside the TabView without creating this custom component
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TabBarIconView(
        isSelected: Boolean,
        selectedIcon: ImageVector,
        unselectedIcon: ImageVector,
        title: String,
        badgeAmount: Int? = null
    ) {
        BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
            Icon(
                imageVector = if (isSelected) {selectedIcon} else {unselectedIcon},
                contentDescription = title
            )
        }
    }

    // This component helps to clean up the API call from our TabBarIconView above,
// but could just as easily be added inside the TabBarIconView without creating this custom component
    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun TabBarBadgeView(count: Int? = null) {
        if (count != null) {
            Badge {
                Text(count.toString())
            }
        }
    }
// end of the reusable components that can be copied over to any new projects
// ----------------------------------------

    // This was added to demonstrate that we are infact changing views when we click a new tab
    @Composable
    fun MoreView() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Thing 1")
            Text("Thing 2")
            Text("Thing 3")
            Text("Thing 4")
            Text("Thing 5")
        }
    }


//    @Composable
//    fun GreetingPreview() {
//        SpotSyncTheme() {
//            TabBarIconView(
//                True ,
//                selectedIcon: ImageVector,
//                unselectedIcon: ImageVector,
//                title: String,
//                badgeAmount: Int? = null
//            )
//            MoreView()
//        }
//    }

//@Composable
//@Preview(showBackground = true)
//fun GreetingPreview() {
//    MainActivity()
    // Create sample tab items
//    val homeTab = TabBarItem(title = "Home", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
//    val alertsTab = TabBarItem(title = "Alerts", selectedIcon = Icons.Filled.Notifications, unselectedIcon = Icons.Outlined.Notifications, badgeAmount = 7)
//    val settingsTab = TabBarItem(title = "Settings", selectedIcon = Icons.Filled.Settings, unselectedIcon = Icons.Outlined.Settings)
//    val moreTab = TabBarItem(title = "More", selectedIcon = Icons.Filled.List, unselectedIcon = Icons.Outlined.List)
//
//    // Create a list of tab items
//    val tabBarItems = listOf(homeTab, alertsTab, settingsTab, moreTab)
//
//    // Set up the UI with a sample navController
//    val navController = rememberNavController()
//
//    // Display the TabView in the preview
//    TabView(tabBarItems, navController)



//}

@Preview
@Composable
fun GreetingPreview() {
    // Create sample tab items
MainActivity()
}

@Composable
fun MainScreen(currentPosition: LatLng, cameraState: CameraPositionState) {
    val marker = LatLng(currentPosition.latitude, currentPosition.longitude)
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraState,
        properties = MapProperties(
            isMyLocationEnabled = true,
            mapType = MapType.HYBRID,
            isTrafficEnabled = true
        )
    ) {
        Marker(
            state = MarkerState(position = marker),
            title = "MyPosition",
            snippet = "This is a description of this Marker",
            draggable = true
        )
    }
}

@Composable
fun RationaleAlert(onDismiss: () -> Unit, onConfirm: () -> Unit) {

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties()
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
//            shape = Resources.Theme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "We need location permissions to use this app",
                )
                Spacer(modifier = Modifier.height(24.dp))
//                Button(
//                    onClick = {
//                        onConfirm()
//                        onDismiss()
//                    },
//                    modifier = Modifier.align(Alignment.End)
//                ) {
//                    Text("OK")
//                }
            }
        }
    }
}

private suspend fun CameraPositionState.centerOnLocation(
    location: LatLng
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        location,
        15f
    ),
    durationMs = 1500
)
