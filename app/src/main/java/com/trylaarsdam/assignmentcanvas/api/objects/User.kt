package com.trylaarsdam.assignmentcanvas.api.objects

data class User (
    val id: Int,
    val canvasURL: String,
    val role: String,
    val apiKey: String,
    val name: String,
    val email: String,
)