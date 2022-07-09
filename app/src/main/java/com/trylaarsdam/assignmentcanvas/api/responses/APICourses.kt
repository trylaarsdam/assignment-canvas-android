package com.trylaarsdam.assignmentcanvas.api.responses

import com.trylaarsdam.assignmentcanvas.api.objects.Course

data class APICourses(
    val data: List<Course>,
    val status: String
)