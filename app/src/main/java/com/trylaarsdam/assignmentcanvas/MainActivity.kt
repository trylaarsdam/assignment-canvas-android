package com.trylaarsdam.assignmentcanvas

import android.os.Bundle
import android.system.Os.close
import android.system.Os.open
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trylaarsdam.assignmentcanvas.data.TabItem
import com.trylaarsdam.assignmentcanvas.ui.theme.AssignmentCanvasTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssignmentCanvasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val drawerState = rememberDrawerState(DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
// icons to mimic drawer destinations
                    val items = listOf(TabItem("Feed", Icons.Filled.Menu), TabItem("Profile", Icons.Filled.Person))
                    val selectedItem = remember { mutableStateOf(items[0]) }
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            Column (modifier = Modifier.padding(horizontal = 0.dp, vertical = 20.dp)){
                                items.forEach { item ->
                                    NavigationDrawerItem(
                                        icon = { Icon(item.icon, contentDescription = null) },
                                        label = { Text(item.name) },
                                        selected = item == selectedItem.value,
                                        onClick = {
                                            scope.launch { drawerState.close() }
                                            selectedItem.value = item
                                        },
                                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                    )
                                }
                            }
                        },
                        content = {
                            MainScaffold(scope, drawerState, selectedItem)
                        }
                    )
                }
            }
        }
    }
}

