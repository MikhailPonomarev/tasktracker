package com.mp.tasktracker.service.task

import com.mp.tasktracker.dao.controller.model.CreateTaskDTO
import com.mp.tasktracker.dao.controller.model.TaskDTO

interface CreateTaskService {

    fun execute(dto: CreateTaskDTO): TaskDTO
}