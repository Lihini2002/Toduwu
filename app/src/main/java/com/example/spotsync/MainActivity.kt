package com.example.spotsync

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text

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
import androidx.compose.ui.graphics.Color
import com.example.spotsync.ui.theme.OliveGreen
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.spotsync.navigation.NavGraph
//screens
import com.example.spotsync.ui.theme.SpotSyncTheme
import com.example.spotsync.ui.theme.todolist.TodoListScreen
import com.example.spotsync.ui.theme.account.Account
import com.example.spotsync.ui.theme.places.Places
import com.example.spotsync.ui.theme.todolist.AddTaskForm
import com.example.spotsync.ui.theme.todolist.ProtestStrikeFamily
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context
import com.example.spotsync.ui.theme.home.HomeScreen as HomePage
import com.example.spotsync.ui.theme.login.LoginScreen as Login

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)


    class MainActivity : ComponentActivity() {
        private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
        @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
        @OptIn(ExperimentalMaterial3Api::class)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                // setting up the individual tabs

                // creating our navController


                SpotSyncTheme() {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),

                    ) {
                        FirebaseApp.initializeApp(this)
                        val firebaseAuth = FirebaseAuth.getInstance()
                        Places.initialize(applicationContext, getString(R.string.maps_api_key))
                        placesClient = Places.createClient(this)

                        // Construct a FusedLocationProviderClient.
                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                        App()
                        }
                    }
                }
            }
        }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    navController: NavHostController = rememberNavController(),
)
{
//    FirebaseApp.initializeApp()

    val navController = rememberNavController()
     val bottomBarState: MutableState<Boolean> = rememberSaveable { (mutableStateOf(true)) }
     val topBarState: MutableState<Boolean> = rememberSaveable { (mutableStateOf(true)) }

    val HomeTab = TabBarItem(title = "Home", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
    val ToDuwusTab = TabBarItem(title = "ToDuwus", selectedIcon = Icons.Filled.Done, unselectedIcon = Icons.Outlined.Done, badgeAmount = 7)
    val PlacesTab = TabBarItem(title = "Places", selectedIcon = Icons.Filled.LocationOn, unselectedIcon = Icons.Outlined.LocationOn)
    val AccountTab = TabBarItem(title = "Account", selectedIcon = Icons.Filled.Person, unselectedIcon = Icons.Outlined.Person)

    // creating a list of all the tabs
    val tabBarItems = listOf(HomeTab, ToDuwusTab ,PlacesTab ,AccountTab)

    Scaffold(
        bottomBar = { TabView(tabBarItems, navController)

                    } ,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = OliveGreen,
                    titleContentColor = Color.White
                )
                ,




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

        NavGraph(navController = navController)


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


@Preview
@Composable
fun GreetingPreview() {
    // Create sample tab items
    SpotSyncTheme(darkTheme = true) {
        App()
    }

}

