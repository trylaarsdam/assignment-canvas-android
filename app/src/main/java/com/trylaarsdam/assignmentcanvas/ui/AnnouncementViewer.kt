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
import kotlinx.coroutines.launch

@Composable
fun AnnouncementViewer(navController: NavController, announcementID: Int, courseID: Int) {
    val coroutineScope = rememberCoroutineScope()
    var announcement = remember { mutableStateOf(APIAnnouncement(null, "loading")) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            announcement.value = Gson().fromJson(apiRequest("api/announcements/$courseID/$announcementID", context), APIAnnouncement::class.java)
        }
    }
    Column() {
        if (announcement.value.status == "loading") {
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
                            announcement.value.data?.course?.name.let {
                                if (it != null) {
                                    Text(it)
                                }
                                else {
                                    Text("Unknown course")
                                }
                            }
                            announcement.value.data?.author?.let { Text(it.display_name) }
                        }
                        Text(
                            "${announcement.value.data?.title}",
                            modifier = Modifier
                                .fillMaxWidth(),
//                                .padding(horizontal = 20.dp)
//                                .padding(top = 20.dp),
                            fontSize = 30.sp
                        )
                        Divider(modifier = Modifier.padding(vertical = 10.dp))
                        announcement.value.data?.let { HtmlViewer(html = it.message, lines = Int.MAX_VALUE) }
                        announcement.value.data?.let {
                            Text(
                                it.created_at, fontSize = 14.sp, modifier = Modifier.align(
                                    Alignment.End))
                        }
                        announcement.value.data?.let { it ->
                            it.replies?.let { replies ->
                                replies.forEach { reply ->
                                    ReplyCard(reply)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}