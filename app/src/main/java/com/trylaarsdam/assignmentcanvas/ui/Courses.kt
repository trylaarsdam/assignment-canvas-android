package com.trylaarsdam.assignmentcanvas.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.trylaarsdam.assignmentcanvas.api.apiRequest
import com.trylaarsdam.assignmentcanvas.api.objects.Course
import com.trylaarsdam.assignmentcanvas.api.responses.APICourses
import kotlinx.coroutines.launch

@Composable
fun Courses(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

    var courses = remember {mutableStateOf(APICourses(listOf<Course>(), "loading"))}

//    LazyColumn {
//        items(courses) { course ->
//            FeedCard(navController)
//        }
//    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            courses.value = Gson().fromJson(apiRequest("api/courses/"), APICourses::class.java)
        }
    }
    if(courses.value.status == "loading") {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(modifier = Modifier
            .padding(15.dp)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            items(courses.value.data) { course ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .clickable { },
                    elevation = 10.dp
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                        Text(course.name)
                    }
                }
            }
        }
    }
}