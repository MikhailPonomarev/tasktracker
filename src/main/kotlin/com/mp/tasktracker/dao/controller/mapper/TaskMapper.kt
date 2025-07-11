package com.mp.tasktracker.dao.controller.mapper

import com.mp.tasktracker.dao.controller.model.TaskDTO
import com.mp.tasktracker.domain.Task

fun Task.toDTO() = TaskDTO(
    id = this.id.toString(),
    title = this.title,
    description = this.description,
    status = this.status,
    assignee = this.assignee?.toDTO(),
    observers = this.observers?.map { it.toDTO() },
    tags = this.tags?.map { it.toDTO() }
)