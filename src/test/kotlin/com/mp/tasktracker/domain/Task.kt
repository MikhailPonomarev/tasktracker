package com.mp.tasktracker.domain

data class Task(
    val uuid: String?,
    val title: String,
    val description: String?,
    val status: String?,
    val assignee: User?,
    val observers: List<User>?,
    val tags: List<Tag>?
)
