package com.trylaarsdam.assignmentcanvas.ui

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.trylaarsdam.assignmentcanvas.api.apiRequest
import com.trylaarsdam.assignmentcanvas.api.responses.APIAnnouncement
import com.trylaarsdam.assignmentcanvas.api.responses.APILogin
import kotlinx.coroutines.launch

@Composable
fun Login(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("com.trylaarsdam.assignmentcanvas.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
    val (snackbarVisibleState, setSnackBarState) = remember { mutableStateOf(false) }
    val snackbarMessage = remember {mutableStateOf("An unknown error occurred.")}

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val email = sharedPref.getString("email", null)
            val password = sharedPref.getString("password", null)
            if(email != null && password != null) {
                val loginResult = Gson().fromJson(apiRequest("internal/login?email=${email}&password=${password}"), APILogin::class.java)
                if (loginResult.status == "success") {

                } else {

                }
            }
        }
    }

    if (snackbarVisibleState) {
        Snackbar(
            action = {
//                Button(onClick = {}) {
//                    Text("MyAction")
//                }
            },
            modifier = Modifier.padding(8.dp)
        ) { Text(snackbarMessage.value) }
    }
    Text("Login")
}