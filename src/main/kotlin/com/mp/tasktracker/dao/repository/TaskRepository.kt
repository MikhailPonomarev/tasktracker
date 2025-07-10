package com.mp.tasktracker.dao.repository

import com.mp.tasktracker.dao.repository.model.TaskEntity

interface TaskRepository {

    fun save(task: TaskEntity): TaskEntity
}