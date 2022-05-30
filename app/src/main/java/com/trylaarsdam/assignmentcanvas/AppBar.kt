package com.trylaarsdam.assignmentcanvas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.trylaarsdam.assignmentcanvas.data.FeedCategory
import com.trylaarsdam.assignmentcanvas.data.TabItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(scope: CoroutineScope, drawerState: DrawerState, selectedTab: MutableState<TabItem>, feedCategories: SnapshotStateList<FeedCategory>) {
    BottomAppBar(
        icons = {
            when(selectedTab.value.name) {
                "Profile" -> {

                }
                "Feed" -> {
                    Row {
                        feedCategories.forEach { category ->
                            IconToggleButton(
                                checked = category.active,
                                onCheckedChange = {
                                    category.active = !category.active
                                    println(feedCategories[feedCategories.indexOf(category)].active)

                                },

                                ) {
                                Icon(
                                    category.icon,
                                    contentDescription = category.name,
                                    modifier = Modifier.background(
                                        if (feedCategories[feedCategories.indexOf(category)].active) MaterialTheme.colorScheme.primary else Color.Transparent
                                    )
                                )
                            }
                        }
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