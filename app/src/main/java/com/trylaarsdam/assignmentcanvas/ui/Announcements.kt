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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.trylaarsdam.assignmentcanvas.api.apiRequest
import com.trylaarsdam.assignmentcanvas.api.objects.Announcement
import com.trylaarsdam.assignmentcanvas.api.objects.Course
import com.trylaarsdam.assignmentcanvas.api.responses.APIAnnouncements
import com.trylaarsdam.assignmentcanvas.api.responses.APICourses
import kotlinx.coroutines.launch

@Composable
fun Announcements(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var announcements = remember {mutableStateOf(APIAnnouncements(listOf<Announcement>(), "loading"))}

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            announcements.value = Gson().fromJson(apiRequest("api/announcements/"), APIAnnouncements::class.java)
        }
    }
    Column() {
        Text(
            "Announcements",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp),
            fontSize = 30.sp
        )
        if (announcements.value.status == "loading") {
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
                items(announcements.value.data) { announcement ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .clickable { },
                        elevation = 10.dp
                    ) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(announcement.course.name)
                                Text(announcement.author.display_name)
                            }
                            Text("${announcement.title}", fontWeight = FontWeight.Bold)
                            Divider(modifier = Modifier.padding(vertical = 10.dp))
                            HtmlText(html = announcement.message, modifier = Modifier.heightIn(0.dp, 95.dp))
                            Text(announcement.created_at, fontSize = 14.sp, modifier = Modifier.align(Alignment.End))
                        }
                    }
                }
            }
        }
    }
}