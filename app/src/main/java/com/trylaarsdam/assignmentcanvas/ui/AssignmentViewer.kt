package com.trylaarsdam.assignmentcanvas.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.trylaarsdam.assignmentcanvas.api.responses.APIAnnouncement
import com.trylaarsdam.assignmentcanvas.api.responses.APIAnnouncements
import com.trylaarsdam.assignmentcanvas.api.responses.APIAssignment
import kotlinx.coroutines.launch

@Composable
fun AssignmentViewer(navController: NavController, assignmentID: Int, courseID: Int) {
    val coroutineScope = rememberCoroutineScope()
    var assignment = remember { mutableStateOf(APIAssignment(null, "loading")) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            assignment.value = Gson().fromJson(apiRequest("api/assignments/$courseID/$assignmentID", context), APIAssignment::class.java)
        }
    }
    Column() {
        if (assignment.value.status == "loading") {
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
                item {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            assignment.value.data?.course?.name.let {
                                if (it != null) {
                                    Text(it)
                                }
                                else {
                                    Text("Unknown course")
                                }
                            }
                            assignment.value.data?.due_at?.let { Text(it) }
                        }
                        Text(
                            "${assignment.value.data?.name}",
                            modifier = Modifier
                                .fillMaxWidth(),
//                                .padding(horizontal = 20.dp)
//                                .padding(top = 20.dp),
                            fontSize = 30.sp
                        )
                        Divider(modifier = Modifier.padding(vertical = 10.dp))
                        assignment.value.data?.let { it.description?.let { it1 -> HtmlViewer(html = it1, lines = Int.MAX_VALUE) } }
                        assignment.value.data?.let {
                            Text(
                                it.created_at, fontSize = 14.sp, modifier = Modifier.align(
                                    Alignment.End))
                        }
                    }
                }
            }
        }
    }
}