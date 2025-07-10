package com.mp.tasktracker.dao.repository.model

import com.mp.tasktracker.dao.repository.type.TaskStatus
import java.util.UUID

data class TaskEntity(
    val id: Long = 0L,
    val uuid: UUID = UUID.randomUUID(),
    val title: String,
    val description: String?,
    val status: TaskStatus,
    val assignee: UserEntity?,
    val observers: List<UserEntity>?,
    val tags: List<TagEntity>?
)
