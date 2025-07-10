package com.mp.tasktracker.dao.controller.model

data class TaskDTO(
    val uuid: String?,
    val title: String,
    val description: String?,
    val status: String?,
    val assignee: UserDTO?,
    val observers: List<UserDTO>?,
    val tags: List<TagDTO>?
)