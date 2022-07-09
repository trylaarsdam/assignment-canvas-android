package com.trylaarsdam.assignmentcanvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.trylaarsdam.assignmentcanvas.ui.Announcements
import com.trylaarsdam.assignmentcanvas.ui.BottomNavigationBar
import com.trylaarsdam.assignmentcanvas.ui.Courses
import com.trylaarsdam.assignmentcanvas.ui.testUI
import com.trylaarsdam.assignmentcanvas.ui.theme.AssignmentCanvasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            AssignmentCanvasTheme {
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) {
                    Surface(color = MaterialTheme.colors.background) {
                        MainNavView(navController)
                    }
                }
                // A surface container using the 'background' color from the theme

            }
        }
    }
}

@Composable
fun MainNavView(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "testUI") {
        composable("announcements") { Announcements(navController) }
        composable("courses") { Courses(navController) }
        composable("testUI") { testUI() }
        composable("announcement/{id}", arguments = listOf(navArgument("id") {type = NavType.StringType})) {
            backStackEntry ->
            backStackEntry.arguments?.getString("id")?.let { Greeting(it) }
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