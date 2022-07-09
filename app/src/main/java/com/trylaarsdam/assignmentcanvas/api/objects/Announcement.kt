package com.trylaarsdam.assignmentcanvas.api.objects

data class Announcement (
    val id: Int,
    val title: String,
    val url: String,
    val course: Course,
    val message: String,
    val author: Author
)