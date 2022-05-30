package com.trylaarsdam.assignmentcanvas

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(scope: CoroutineScope, drawerState: DrawerState) {
    BottomAppBar(
        icons = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Outlined.Message, contentDescription = "Announcements")
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    Icons.Filled.EditNote,
                    contentDescription = "Assignments",
                )
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