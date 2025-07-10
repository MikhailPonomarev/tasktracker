package com.mp.tasktracker.service

import com.mp.tasktracker.dao.controller.model.CreateTaskDTO
import com.mp.tasktracker.dao.controller.model.TaskDTO

interface CreateTaskService {

    fun execute(createTaskDTO: CreateTaskDTO): TaskDTO
}