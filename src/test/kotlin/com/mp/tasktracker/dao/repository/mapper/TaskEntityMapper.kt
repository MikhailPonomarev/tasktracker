package com.mp.tasktracker.dao.repository.mapper

import com.mp.tasktracker.dao.repository.model.TaskEntity
import com.mp.tasktracker.domain.Task

fun TaskEntity.toDomain() = Task(
    uuid = this.uuid.toString(),
    title = this.title,
    description = this.description,
    status = this.status.name,
    assignee = this.assignee?.toDomain(),
    observers = this.observers?.map { it.toDomain() },
    tags = this.tags?.map { it.toDomain() }
)
