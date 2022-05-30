package com.trylaarsdam.assignmentcanvas

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.trylaarsdam.assignmentcanvas.data.TabItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(scope: CoroutineScope, drawerState: DrawerState, selectedTab: MutableState<TabItem>) {
    BottomAppBar(
        icons = {
            when(selectedTab.value.name) {
                "Profile" -> {

                }
                "Feed" -> {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Outlined.Message, contentDescription = "Announcements")
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            Icons.Filled.EditNote,
                            contentDescription = "Assignments",
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if(drawerState.isOpen) {
                        scope.launch { drawerState.close() }
                    }
                    else {
                        scope.launch { drawerState.open() }
                    }
                },
                elevation = BottomAppBarDefaults.floatingActionButtonElevation()
            ) {
                Icon(Icons.Filled.Menu, "Settings")
            }
        },
    )
}