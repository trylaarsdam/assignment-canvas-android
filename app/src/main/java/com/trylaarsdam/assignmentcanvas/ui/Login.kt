package com.trylaarsdam.assignmentcanvas.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
    var loginState = remember { mutableStateOf("loading") }
    var enteredPassword by rememberSaveable { mutableStateOf("") }
    var enteredEmail by rememberSaveable { mutableStateOf("") }
    var invalidCredentials = remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val email = sharedPref.getString("email", null)
            val password = sharedPref.getString("password", null)
            if(email != null && password != null) {
                val loginResult = Gson().fromJson(apiRequest("internal/login?email=${email}&password=${password}", context), APILogin::class.java)
                if (loginResult.status == "success") {
                    Log.d(TAG, "Restoration of login successful")
                } else {
                    loginState.value = "prompt"
                    Log.d(TAG, "Stored login credentials invalid")
                    sharedPref.edit().putString("email", null).commit()
                    sharedPref.edit().putString("password", null).commit()
                }
            }
            else {
                loginState.value = "prompt"
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (loginState.value) {
            "loading" -> {
                CircularProgressIndicator(modifier = Modifier.padding(all = 10.dp))
                Text("Logging you in...")
            }
            "prompt" -> {
                Text("Login to Assignment Canvas", modifier = Modifier.padding(all = 10.dp))
                if(invalidCredentials.value) {
                    Text("Invalid credentials", color = Color.Red )
                }
                OutlinedTextField(
                    value = enteredEmail,
                    onValueChange = { if (!it.contains("\n"))
                        enteredEmail = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    maxLines = 1
                )
                OutlinedTextField(
                    value = enteredPassword,
                    onValueChange = { enteredPassword = it },
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                )
                Button(onClick = {
                    coroutineScope.launch {
                        val loginResult = Gson().fromJson(apiRequest("internal/login?email=${enteredEmail}&password=${enteredPassword}", context), APILogin::class.java)
                        if (loginResult.status == "success") {
                            Log.d(TAG, "Login successful")
                            sharedPref.edit().putString("email", enteredEmail)
                            sharedPref.edit().putString("password", enteredPassword)
                            sharedPref.edit().putString("user", Gson().toJson(loginResult.user!!))
                            navController.navigate("announcements")
                        } else {
                            invalidCredentials.value = true
                            Log.d(TAG, "Stored login credentials invalid")
                            enteredPassword = ""
                        }
                    }
                },
                    modifier = Modifier.padding(all = 10.dp)
                ){
                    Text("Login")
                }
            }
            else -> {
                Text("Error attempting to load login.")
            }
        }
    }
}