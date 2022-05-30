package com.trylaarsdam.assignmentcanvas

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.trylaarsdam.assignmentcanvas.data.FeedCategory
import com.trylaarsdam.assignmentcanvas.data.TabItem
import com.trylaarsdam.assignmentcanvas.pages.ProfileView
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(scope: CoroutineScope, drawerState: DrawerState, selectedItem: MutableState<TabItem>) {
    var feedCategories = remember {
        mutableStateListOf(
            FeedCategory(name = "Assignments", active = true, icon = Icons.Filled.EditNote),
            FeedCategory(name = "Announcements", active = true, icon = Icons.Outlined.Message)
        )
    }
    Scaffold(
        bottomBar = {
            AppBar(scope, drawerState, selectedItem, feedCategories)
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
//            ExtendedFloatingActionButton(
//                onClick = {
//
//                }
//            ) {
//                Text("Inc")
//            }
        },
        content = { innerPadding ->
            when(selectedItem.value.name) {
                 "Feed" -> {

                 }
                 "Profile" -> {
                     ProfileView()
                 }
            }
        }
    )
}