package com.mp.tasktracker.service.task.impl

import com.mp.tasktracker.dao.controller.mapper.toDTO
import com.mp.tasktracker.dao.controller.model.TaskDTO
import com.mp.tasktracker.dao.repository.TaskRepository
import com.mp.tasktracker.dao.repository.mapper.toDomain
import com.mp.tasktracker.service.task.GetAllTasksService
import org.springframework.stereotype.Service

@Service
class GetAllTasksServiceImpl(
    private val taskRepository: TaskRepository
) : GetAllTasksService {

    override fun execute(): List<TaskDTO> = taskRepository.findByIsDeletedFalse()
        .map { it.toDomain().toDTO() }
}