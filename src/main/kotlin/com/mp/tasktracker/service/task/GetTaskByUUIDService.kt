package com.mp.tasktracker.service.task

import com.mp.tasktracker.dao.controller.model.TaskDTO

interface GetTaskByUUIDService {

    fun execute(uuid: String): TaskDTO
}