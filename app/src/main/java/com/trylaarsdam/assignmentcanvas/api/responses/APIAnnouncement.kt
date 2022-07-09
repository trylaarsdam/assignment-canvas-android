package com.trylaarsdam.assignmentcanvas.api.responses

import com.trylaarsdam.assignmentcanvas.api.objects.Announcement

data class APIAnnouncement(
    val data: Announcement?,
    val status: String
)