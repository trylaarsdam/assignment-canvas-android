package com.trylaarsdam.assignmentcanvas.api.objects

data class Assignment(
    val id: Int,
    val description: String?,
    val due_at: String?,
    val points_possible: Int?,
    val created_at: String,
    val course: Course,
    val name: String?
)
