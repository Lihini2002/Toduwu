package com.example.spotsync.ui.theme.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spotsync.R

@Composable
fun SigninScreen() {
    val mutableStateOf = mutableStateOf<String>("")
    var username by remember { mutableStateOf }
    var email by remember { mutableStateOf }

    var password by remember { mutableStateOf }

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
        Email(value=email , onValueChange = { newEmail ->
            email = newEmail
        }, label = "Enter Email")


        Spacer(modifier = Modifier.height(8.dp))
        PasswordOutlinedTextFieldSample(
            password = password,
            onPasswordChange = { newPassword ->
                password = newPassword
            },
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
        SignInButton {

        }


        // Add a button or any other login-related UI elements here
    }
}

@Composable
fun SignInButton(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text("SignIn")
    }
}

@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun UsernameBox(email: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = email,
        onValueChange = onValueChange,
        label = { Text(label) },
        maxLines = 1 ,
        textStyle = TextStyle(
            fontFamily = FontFamily.Monospace
        ),
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

@Composable
fun Email(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        maxLines = 1 ,
        textStyle = TextStyle(
            fontFamily = FontFamily.Monospace
        ),
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



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PasswordTextBox(
    password: String,
    onPasswordChange: (String) -> Unit,
    label: String
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
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                // Handle the "Done" action here
            }
        )
    )
}

@Preview
@Composable
fun PreviewSignIn()
{
    SigninScreen()
}
