package com.trylaarsdam.assignmentcanvas.ui

import android.net.Uri
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.ireward.htmlcompose.HtmlText


@Composable
fun HtmlViewer(html: String, modifier: Modifier = Modifier, lines: Int) {
    val context = LocalContext.current

    HtmlText(
        text = html,
        style = TextStyle(color = MaterialTheme.colors.onBackground, fontSize = 15.sp),
        maxLines = lines,
        linkClicked = { link ->
            Log.d("linkClicked", link)
            CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(link))
        }
    )
}