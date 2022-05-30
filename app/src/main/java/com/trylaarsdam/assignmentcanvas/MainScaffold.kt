package com.trylaarsdam.assignmentcanvas

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.trylaarsdam.assignmentcanvas.data.TabItem
import com.trylaarsdam.assignmentcanvas.pages.ProfileView
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(scope: CoroutineScope, drawerState: DrawerState, selectedItem: MutableState<TabItem>) {
    Scaffold(
        bottomBar = {
            AppBar(scope, drawerState, selectedItem)
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