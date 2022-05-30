package com.trylaarsdam.assignmentcanvas

import android.graphics.drawable.Icon
import android.system.Os.close
import android.system.Os.open
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.trylaarsdam.assignmentcanvas.data.TabItem
import com.trylaarsdam.assignmentcanvas.pages.AccountView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(scope: CoroutineScope, drawerState: DrawerState, selectedItem: MutableState<TabItem>) {
    Scaffold(
        bottomBar = {
            AppBar(scope, drawerState)
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {

                }
            ) {
                Text("Inc")
            }
        },
        content = { innerPadding ->
            when(selectedItem.value.name) {
                 "Feed" -> {

                 }
                 "Account" -> {
                     AccountView()
                 }
            }
        }
    )
}