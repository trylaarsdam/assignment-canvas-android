package com.trylaarsdam.assignmentcanvas.api.objects

data class User (
    val id: String,
    val canvasURL: String,
    val role: String,
    val apiKey: String,
    val name: String,
    val email: String,
    val password: String
)