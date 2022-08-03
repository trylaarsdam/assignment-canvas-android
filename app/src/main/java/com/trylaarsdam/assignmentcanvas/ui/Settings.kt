package com.trylaarsdam.assignmentcanvas.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.shapes.Shape
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson

@Composable
fun Settings(navController: NavController) {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("com.trylaarsdam.assignmentcanvas.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp)
            .fillMaxSize()
    ) {
        Text(
            "Settings",
            modifier = Modifier
                .fillMaxWidth(),
            fontSize = 30.sp
        )
        Divider(modifier = Modifier.padding(all = 10.dp))
        Text("To modify account settings such as your email or Canvas API key, " +
                "visit Assignment Canvas Online")
        OutlinedButton(
            onClick = {
                CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse("https://canvas.toddr.org/profile"))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to online settings")
        }
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom){
            OutlinedButton(
                onClick = {
                    sharedPref.edit().putString("email", null).commit()
                    sharedPref.edit().putString("password", null).commit()
                    sharedPref.edit().putString("api_password", null).commit()
                    sharedPref.edit().putString("user", null).commit()

                    navController.navigate("login")

                },
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 20.dp))
            {
                Text("Logout")
            }
        }

    }
}