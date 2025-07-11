package com.mp.tasktracker.dao.controller.model

data class CreateTaskDTO(
    val title: String,
    val description: String? = null,
    val status: String? = null,
    val assigneeId: String?,
    val observersIds: List<String>? = null,
    val tagsIds: List<String>? = null
)
