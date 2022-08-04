package com.trylaarsdam.assignmentcanvas.ui

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun <T> merge(first: List<T>, second: List<T>): List<T> {
    val list: MutableList<T> = ArrayList()
    list.addAll(first)
    list.addAll(second)
    return list
}

@Composable
fun Feed(navController: NavController) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    var announcements = remember {mutableStateOf(APIAnnouncements(listOf<Announcement>(), "loading"))}
    var assignments = remember {mutableStateOf(APIAssignments(listOf<Assignment>(), "loading"))}
    var allItems = remember {mutableStateOf(listOf<Any>())}

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            announcements.value = Gson().fromJson(apiRequest("api/announcements", context), APIAnnouncements::class.java)
            assignments.value = Gson().fromJson(apiRequest("api/assignments", context), APIAssignments::class.java)
            allItems.value = merge(announcements.value.data, assignments.value.data)

            val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME
            val blankDate: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy")

            allItems.value = allItems.value.sortedByDescending {
                if(it is Announcement) {
                    LocalDate.parse(it.created_at, dateTimeFormatter)
                }
                else if(it is Assignment) {
                    LocalDate.parse(it.created_at, dateTimeFormatter)
                }
                else {
                    LocalDate.parse("2066", blankDate)
                }
            }
        }
    }
    Column() {
        Text(
            "Your Feed",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp),
            fontSize = 30.sp
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                onClick = {
                    navController.navigate("announcements")
                },
            ) {
                Text("Announcements")
            }
            OutlinedButton(
                onClick = {
                    navController.navigate("assignments")
                }
            ) {
                Text("Assignments")
            }
        }
        if (announcements.value.status == "loading" || assignments.value.status == "loading") {
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
                items(allItems.value) { item ->
                    if(item is Announcement) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .clickable {
                                    navController.navigate("announcement/${item.course.id}/${item.id}")
                                },
                            elevation = 10.dp
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(item.course.name)
                                    Text(item.author.display_name)
                                }
                                Text("${item.title}", fontWeight = FontWeight.Bold)
                                Divider(modifier = Modifier.padding(vertical = 10.dp))
                                HtmlViewer(
                                    html = item.message,
                                    lines = 5,
                                    modifier = Modifier.clickable {
                                        navController.navigate("announcement/${item.course.id}/${item.id}")
                                    })
                                Text(
                                    item.created_at,
                                    fontSize = 14.sp,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }
                    }
                    else if(item is Assignment) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .clickable {
                                    navController.navigate("assignment/${item.course.id}/${item.id}")
                                },
                            elevation = 10.dp
                        ) {
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(item.course.name)
                                    Text("Assignment")
                                }
                                Text(item.name ?: "Unnamed Assignment", fontWeight = FontWeight.Bold)
                                Divider(modifier = Modifier.padding(vertical = 10.dp))
                                HtmlViewer(html = item.description ?: "", lines = 5, modifier = Modifier.clickable {
                                    navController.navigate("assignment/${item.course.id}/${item.id}")
                                })
                                Text(item.created_at ?: "No due date", fontSize = 14.sp, modifier = Modifier.align(Alignment.End))
                            }
                        }
                    }
                }
            }
        }
    }
}