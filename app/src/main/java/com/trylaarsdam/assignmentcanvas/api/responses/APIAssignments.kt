package com.trylaarsdam.assignmentcanvas.api.responses

import com.trylaarsdam.assignmentcanvas.api.objects.Assignment

data class APIAssignments(
    val data: List<Assignment>,
    val status: String
)