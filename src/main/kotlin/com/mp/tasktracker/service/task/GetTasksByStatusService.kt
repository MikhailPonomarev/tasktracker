package com.mp.tasktracker.service.task

import com.mp.tasktracker.dao.controller.model.TaskDTO

interface GetTasksByStatusService {

    fun execute(statusName: String): List<TaskDTO>
}