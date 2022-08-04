package com.trylaarsdam.assignmentcanvas

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Feed
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object Announcements : NavigationItem("announcements", Icons.Outlined.Menu, "Announcements")
    object Courses : NavigationItem("courses", Icons.Outlined.School, "Courses")
    object Settings: NavigationItem("settings", Icons.Outlined.Settings, "Settings")
    object Feed: NavigationItem("feed", Icons.Outlined.Feed, "Feed")
}