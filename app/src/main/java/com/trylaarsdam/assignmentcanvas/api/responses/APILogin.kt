package com.trylaarsdam.assignmentcanvas.api.responses

import com.trylaarsdam.assignmentcanvas.api.objects.Announcement
import com.trylaarsdam.assignmentcanvas.api.objects.User

data class APILogin(
    val user: User?,
    val message: String?,
    val status: String
)