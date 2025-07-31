package com.mp.tasktracker.service.task

import com.mp.tasktracker.dao.controller.model.TaskDTO
import com.mp.tasktracker.dao.controller.model.UpdateTaskDTO

interface UpdateTaskService {

    fun execute(uuid: String, dto: UpdateTaskDTO): TaskDTO
}