package com.trylaarsdam.assignmentcanvas.api.responses

import com.trylaarsdam.assignmentcanvas.api.objects.Announcement

data class APIAnnouncements(
    val data: List<Announcement>,
    val status: String
)