package com.trylaarsdam.assignmentcanvas.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.trylaarsdam.assignmentcanvas.api.apiRequest
import com.trylaarsdam.assignmentcanvas.api.responses.Course
import kotlinx.coroutines.launch

@Composable
fun Courses(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

    var courses: List<Course> = listOf()
    var rawValues = remember { mutableStateOf("")}

//    LazyColumn {
//        items(courses) { course ->
//            FeedCard(navController)
//        }
//    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            rawValues.value = apiRequest("api/courses/")
        }
    }

    Column(modifier = Modifier
        .padding(15.dp)
        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Feed")
        Card() {
            Text(rawValues.value)
        }
    }
}