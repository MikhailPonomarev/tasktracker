package com.mp.tasktracker.util

import com.mp.tasktracker.dao.repository.type.TaskStatus
import com.mp.tasktracker.exception.StatusNotFoundException

object TaskStatusUtil {

    fun findStatusOrThrow(statusName: String): TaskStatus = TaskStatus.entries.find { it.name == statusName }
        ?: throw StatusNotFoundException(statusName)
}