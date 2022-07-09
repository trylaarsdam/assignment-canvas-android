package com.trylaarsdam.assignmentcanvas

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.School
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object Feed : NavigationItem("feed", Icons.Outlined.Menu, "Feed")
    object Courses : NavigationItem("courses", Icons.Outlined.School, "Courses")
}