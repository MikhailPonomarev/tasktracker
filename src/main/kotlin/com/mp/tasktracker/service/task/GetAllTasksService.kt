package com.mp.tasktracker.service.task

import com.mp.tasktracker.dao.controller.model.TaskDTO

interface GetAllTasksService {

    fun execute(): List<TaskDTO>
}