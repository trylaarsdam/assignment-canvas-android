package com.trylaarsdam.assignmentcanvas.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.trylaarsdam.assignmentcanvas.api.objects.Reply

@Composable
fun ReplyCard(reply: Reply) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 5.dp)
        .padding(top = 5.dp), elevation = 10.dp) {
        Column(modifier = Modifier.padding(all = 10.dp)) {
            if(reply.deleted == true) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("Reply Deleted", fontStyle = FontStyle.Italic)
                    Text(reply.created_at)
                }
            } else {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text(reply.user_id.toString())
                    Text(reply.created_at)
                }
                Divider()
                HtmlViewer(html = reply.message ?: "No reply text.", lines = Int.MAX_VALUE)
            }
            reply.replies?.let { replies ->
                Spacer(modifier = Modifier.height(10.dp))
                replies.forEach {
                    ReplyCard(it)
                }
            }
        }
    }
}