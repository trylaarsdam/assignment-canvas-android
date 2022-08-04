package com.trylaarsdam.assignmentcanvas.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.trylaarsdam.assignmentcanvas.api.apiRequest
import com.trylaarsdam.assignmentcanvas.api.objects.Announcement
import com.trylaarsdam.assignmentcanvas.api.objects.Assignment
import com.trylaarsdam.assignmentcanvas.api.objects.Course
import com.trylaarsdam.assignmentcanvas.api.responses.APIAnnouncements
import com.trylaarsdam.assignmentcanvas.api.responses.APIAssignments
import com.trylaarsdam.assignmentcanvas.api.responses.APICourses
import kotlinx.coroutines.launch

@Composable
fun Assignments(navController: NavController) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    var assignments = remember {mutableStateOf(APIAssignments(listOf<Assignment>(), "loading"))}

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            assignments.value = Gson().fromJson(apiRequest("api/assignments", context), APIAssignments::class.java)
        }
    }
    Column() {
        Text(
            "Assignments",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp),
            fontSize = 30.sp
        )
        if (assignments.value.status == "loading") {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(assignments.value.data) { assignment ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .clickable {
                                navController.navigate("announcement/${assignment.course.id}/${assignment.id}")
                            },
                        elevation = 10.dp
                    ) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(assignment.course.name)
                                Text(assignment.due_at ?: "No due date")
                            }
                            Text("${assignment.name}", fontWeight = FontWeight.Bold)
                            Divider(modifier = Modifier.padding(vertical = 10.dp))
                            HtmlViewer(html = assignment.description ?: "", lines = 5, modifier = Modifier.clickable {
                                navController.navigate("announcement/${assignment.course.id}/${assignment.id}")
                            })
                            Text(assignment.created_at, fontSize = 14.sp, modifier = Modifier.align(Alignment.End))
                        }
                    }
                }
            }
        }
    }
}