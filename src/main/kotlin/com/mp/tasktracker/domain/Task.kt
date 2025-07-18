package com.mp.tasktracker.domain

import java.util.UUID

data class Task(
    val id: UUID?,
    val title: String,
    val description: String?,
    val status: String?,
    val assignee: User?,
    val observers: List<User>?,
    val tags: List<Tag>?
)
