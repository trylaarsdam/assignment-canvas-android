package com.trylaarsdam.assignmentcanvas.api.responses

import com.trylaarsdam.assignmentcanvas.api.objects.Assignment

data class APIAssignment(
    val data: Assignment?,
    val status: String
)