package com.trylaarsdam.assignmentcanvas

import android.R
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.trylaarsdam.assignmentcanvas.ui.*
import com.trylaarsdam.assignmentcanvas.ui.theme.AssignmentCanvasTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            val context = LocalContext.current
            var shortcut = ShortcutInfoCompat.Builder(context, "announcements")
                .setShortLabel("Announcements")
                .setLongLabel("Announcements")
                .setIcon(IconCompat.createWithResource(context, R.drawable.ic_lock_lock))
                .setIntent(Intent(Intent.ACTION_VIEW,
                    Uri.parse("app://canvas.toddr.org/app/android/announcements")))
                .build()

            ShortcutManagerCompat.pushDynamicShortcut(context, shortcut)

            shortcut = ShortcutInfoCompat.Builder(context, "feed")
                .setShortLabel("Feed")
                .setLongLabel("Feed")
                .setIcon(IconCompat.createWithResource(context, R.drawable.ic_lock_lock))
                .setIntent(Intent(Intent.ACTION_VIEW,
                    Uri.parse("app://canvas.toddr.org/app/android/feed")))
                .build()

            ShortcutManagerCompat.pushDynamicShortcut(context, shortcut)

            shortcut = ShortcutInfoCompat.Builder(context, "courses")
                .setShortLabel("Courses")
                .setLongLabel("Courses")
                .setIcon(IconCompat.createWithResource(context, R.drawable.ic_lock_lock))
                .setIntent(Intent(Intent.ACTION_VIEW,
                    Uri.parse("app://canvas.toddr.org/app/android/courses")))
                .build()

            ShortcutManagerCompat.pushDynamicShortcut(context, shortcut)



            AssignmentCanvasTheme {
                Scaffold(
                    bottomBar = { if(navBackStackEntry?.destination?.route != "login") {BottomNavigationBar(navController)} }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Surface(color = MaterialTheme.colors.background) {
                            MainNavView(navController, intent)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainNavView(navController: NavHostController, intent: Intent) {
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
    val uri: Uri? = intent.data

    if(uri != null) {
        val parameters: List<String> = uri.getPathSegments()

        val param = parameters[parameters.size - 1]
//                Log.d(TAG, parameters[])

        when (param) {
            "courses" -> {
                navController.navigate("courses")
            }
            "announcements" -> {
                navController.navigate("announcements")
            }
            else -> {
                Log.d(TAG, "Unknown deep link")
            }
        }
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