package com.example.spotsync.ui.theme.login

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
//import androidx.compose.material.ExperimentalMaterial3Api
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.spotsync.R
import com.example.spotsync.navigation.NavGraph
import com.example.spotsync.navigation.Routes
import com.example.spotsync.navigation.navgraph
import com.example.spotsync.ui.theme.SpotSyncTheme
//import com.example.spotsync.navigation.NavGraph
import com.example.spotsync.uilayer.UserSignupViewModel
//import androidx.hilt.navigation.compose.hiltViewModel



@Composable
fun SigninScreen(userSignupViewModel: UserSignupViewModel, navController: NavHostController) {
    val mutableStateOf = mutableStateOf<String>("")
    var username by remember { mutableStateOf ("") }
    var email by remember { mutableStateOf ("")}

    var password by remember { mutableStateOf("") }
//    val homeViewModel: HomeViewModel = hiltViewModel()

//    var emailAddress by rememberSaveable { mutableStateOf("") }

//    var password by rememberSaveable { mutableStateOf("") }

    val resourceId: Int = R.drawable.loginlogo


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                //I want to make the entire screen green
                color = Color(
                    0xA4 / 255f,
                    0xAF / 255f,
                    0x69 / 255f
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Add any additional UI elements you want for the login screen


        Image(
            painter = painterResource(id = resourceId),
            contentDescription = "Logo",
            modifier = Modifier
                .size(300.dp)

        )

        Spacer(modifier = Modifier.height(8.dp))
        UsernameTextBox(value = username, onValueChange = { username = it }, label = "Username")
        Spacer(modifier = Modifier.height(8.dp))
        Email (value=email , onValueChange = { email = it } ,  label = "Enter Email")
//        value: String, onValueChange: (String) -> Unit, KeyboardOptions: KeyboardOptions, label: String


        Spacer(modifier = Modifier.height(8.dp))
//        PasswordOutlinedTextFieldSample(
//            password = password,
//            onPasswordChange = { newPassword ->
//                password = newPassword
//            },
//            label = "Enter password"
//        )

        PasswordTextBox(value = password,
            onPasswordChange = {password = it},
        label = "Enter password"
       )

        Spacer(modifier = Modifier.height(8.dp))
        PasswordOutlinedTextFieldSample(
            password = password,
            onPasswordChange = { newPassword ->
                password = newPassword
            },
            label = "Confirm Password"
        )
        Spacer(modifier = Modifier.height(8.dp))
        SignInButton(
            userSignupViewModel = userSignupViewModel,
            navController = navController,
            email = email,
            password = password
        )


        // Add a button or any other login-related UI elements here
    }
}

@Composable
    fun PasswordTextBox(
        value: String,
        onPasswordChange: (String) -> Unit,
        label: String,
        applyVisualTransformation: Boolean = false

    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onPasswordChange,

            maxLines = 1,
            label = { Text(text = label) },
            textStyle = TextStyle(
                fontFamily = FontFamily.Monospace
            ),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Handle the "Done" action here
                }

            ),
            visualTransformation = if (applyVisualTransformation) PasswordVisualTransformation() else VisualTransformation.None
        )
}

@Composable
fun Email(value: String, onValueChange: (String) -> Unit,  label: String)
{

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
//               label
        },
        maxLines = 1 ,
        textStyle = TextStyle(
            fontFamily = FontFamily.Monospace
        ),
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email, // Adjust this based on your requirements
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                // Handle the "Done" action here
            }
        )
    )
}

@Composable
fun SignInButton(
    userSignupViewModel: UserSignupViewModel,
    navController: NavHostController,
    email: String,
    password: String
) {
    Button(onClick = {
        userSignupViewModel.registerUser(email = email, password = password)
        navController.navigate(Routes.Home.name)
    }) {
        Text("Sign In")
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
private class PasswordVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            AnnotatedString("*".repeat(text.text.length)),

            /**
             * [OffsetMapping.Identity] is a predefined [OffsetMapping] that can be used for the
             * transformation that does not change the character count.
             */
            OffsetMapping.Identity
        )
    }

    @Composable
    fun UsernameBox(
        email: String, onValueChange: (String) -> Unit, label: String, value: String,
        onValueChanged: (String) -> Unit,
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = onValueChange,
            label = { Text(label) },
            maxLines = 1,
            textStyle = TextStyle(
                fontFamily = FontFamily.Monospace
            ),
//
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, // Adjust this based on your requirements
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Handle the "Done" action here
                }
            )
        )
    }
//
//@Composable
//fun Email(value: String, onValueChange: (String) -> Unit, label1: KeyboardOptions, label: String) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        label = { Text(label) },
//        maxLines = 1 ,
//        textStyle = TextStyle(
//            fontFamily = FontFamily.Monospace
//        ),
//        shape = RoundedCornerShape(10.dp),
//        keyboardOptions = KeyboardOptions(
//            keyboardType = KeyboardType.Email, // Adjust this based on your requirements
//            imeAction = ImeAction.Next
//        ),
//        keyboardActions = KeyboardActions(
//            onDone = {
//                // Handle the "Done" action here
//            }
//        )
//    )
//}


    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun PasswordTextBox(
        password: String,
        onPasswordChange: (String) -> Unit,
        label: String,
        applyVisualTransformation: Boolean = false

    ) {
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,

            maxLines = 1,
            label = { Text(text = label) },
            textStyle = TextStyle(
                fontFamily = FontFamily.Monospace
            ),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Handle the "Done" action here
                }

            ),
            visualTransformation = if (applyVisualTransformation) PasswordVisualTransformation() else VisualTransformation.None
        )

    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewSignIn() {
    SpotSyncTheme {
        val navController = rememberNavController()
        val userSignupViewModel: UserSignupViewModel = hiltViewModel()
        NavGraph(navController = navController)
        SigninScreen(
            userSignupViewModel = userSignupViewModel,
            navController = navController
        )
    }
}