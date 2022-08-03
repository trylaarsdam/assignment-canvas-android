package com.trylaarsdam.assignmentcanvas.api.objects

data class Reply(
    val id: Int,
    val message: String?,
    val user_id: Int,
    val parent_id: Int?,
    val created_at: String,
    val updated_at: String,
    val rating_count: Int?,
    val rating_sum: Int?,
    val replies: Array<Reply>?,
    val deleted: Boolean?,
)