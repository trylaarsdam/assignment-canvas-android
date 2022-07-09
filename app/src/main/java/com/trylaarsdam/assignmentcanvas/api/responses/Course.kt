package com.trylaarsdam.assignmentcanvas.api.responses

class Course(id: Number, name: String, public_description: String?){
    val name = name
    val id = id
    val description = public_description
}