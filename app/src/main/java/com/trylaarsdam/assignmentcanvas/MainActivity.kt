package com.trylaarsdam.assignmentcanvas

import android.R
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.trylaarsdam.assignmentcanvas.ui.*
import com.trylaarsdam.assignmentcanvas.ui.theme.AssignmentCanvasTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            AssignmentCanvasTheme {
                Scaffold(
                    bottomBar = { if(navBackStackEntry?.destination?.route != "login") {BottomNavigationBar(navController)} }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Surface(color = MaterialTheme.colors.background) {
                            MainNavView(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainNavView(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("announcements") { Announcements(navController) }
        composable("courses") { Courses(navController) }
        composable("testUI") { testUI() }
        composable("settings") { Settings(navController) }
        composable("login") { Login(navController) }
        composable("announcement/{courseID}/{announcementID}") { backStackEntry ->
            AnnouncementViewer(navController, backStackEntry.arguments?.getString("announcementID")!!.toInt(), backStackEntry.arguments?.getString("courseID")!!.toInt())
        }
        /*...*/
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "$name")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AssignmentCanvasTheme {
        Greeting("Android")
    }
}