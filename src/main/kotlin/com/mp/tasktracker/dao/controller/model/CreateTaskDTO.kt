package com.mp.tasktracker.dao.controller.model

data class CreateTaskDTO(
    val title: String,
    val description: String? = null,
    val status: String? = null,
    val assigneeUUID: String?,
    val observersUUIDs: List<String>? = null,
    val tagsUUIDs: List<String>? = null
)
