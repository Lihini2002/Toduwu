package com.example.spotsync.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spotsync.TabBarItem
import com.example.spotsync.ui.theme.account.Account
//import com.example.spotsync.ui.theme.todolist.TodoListScreen
import com.example.spotsync.ui.theme.home.HomeScreen
import com.example.spotsync.ui.theme.login.LoginScreen
import com.example.spotsync.ui.theme.places.Places
import com.example.spotsync.ui.theme.todolist.AddTaskForm
import com.example.spotsync.ui.theme.todolist.TodoListScreen
import com.example.spotsync.ui.theme.SpotSyncTheme
import com.example.spotsync.ui.theme.todolist.TodoListScreen
import com.example.spotsync.ui.theme.account.Account
import com.example.spotsync.ui.theme.login.SigninScreen

import com.example.spotsync.ui.theme.places.Places
import com.example.spotsync.ui.theme.todolist.AddTaskForm
import com.example.spotsync.ui.theme.todolist.ProtestStrikeFamily
import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.hilt.lifecycle.ViewModelInject

import com.example.spotsync.uilayer.UserSignupViewModel
import com.example.spotsync.ui.theme.home.HomeScreen as HomePage
import com.example.spotsync.ui.theme.login.LoginScreen as Login

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)


class navgraph {


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController ) {
    val HomeTab = TabBarItem(title = "Home", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
    val ToDuwusTab = TabBarItem(title = "ToDuwus", selectedIcon = Icons.Filled.Done, unselectedIcon = Icons.Outlined.Done, badgeAmount = 7)
    val PlacesTab = TabBarItem(title = "Places", selectedIcon = Icons.Filled.LocationOn, unselectedIcon = Icons.Outlined.LocationOn)
    val AccountTab = TabBarItem(title = "Account", selectedIcon = Icons.Filled.Person, unselectedIcon = Icons.Outlined.Person)

    // creating a list of all the tabs
    val tabBarItems = listOf(HomeTab, ToDuwusTab ,PlacesTab ,AccountTab)
    val navController = rememberNavController()
    val bottomBarState: MutableState<Boolean> = rememberSaveable { (mutableStateOf(true)) }
    val topBarState: MutableState<Boolean> = rememberSaveable { (mutableStateOf(true)) }





    NavHost(navController = navController, startDestination = "Login") {

//        composable("Login") {
//            LoginScreen(onNavigateToLogin = {
//                navController.navigate(HomeTab.title)
//                bottomBarState.value = false
//                topBarState.value = false
//            },)
//        }
        composable(Routes.Login.name) {
            LoginScreen(navController = navController)
        }

        composable(Routes.Signin.name) {
            val userSignupViewModel: UserSignupViewModel = hiltViewModel()
            SigninScreen(userSignupViewModel = userSignupViewModel, navController = navController)
        }

        composable(HomeTab.title) {
            HomeScreen()
            bottomBarState.value = true
            topBarState.value = false
        }
        composable(ToDuwusTab.title) {
            TodoListScreen(
                onNavigateToAdd = {
                    navController.navigate("AddNewTodoScreen")
                    topBarState.value = true
                    bottomBarState.value = true
                }
            )
        }

        composable("AddNewTodoScreen") {
            // Composable for the "Add New Todo" screen
            AddTaskForm()
        }

        composable(PlacesTab.title) {
            Places()

            bottomBarState.value = true
            topBarState.value = true
        }
        composable(AccountTab.title) {
            Account()
            topBarState.value = true
            bottomBarState.value = true
        }




    }
}


