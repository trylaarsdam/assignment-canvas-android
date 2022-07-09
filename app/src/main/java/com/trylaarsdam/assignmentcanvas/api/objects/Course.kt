package com.trylaarsdam.assignmentcanvas.api.objects

data class Course (
    val id: Int,
    val name: String,
    val uuid: String,
    val course_code: String,
    val course_color: String?
)