package com.mp.tasktracker.service.task.impl

import com.mp.tasktracker.dao.controller.mapper.toDTO
import com.mp.tasktracker.dao.controller.model.TaskDTO
import com.mp.tasktracker.dao.repository.TaskRepository
import com.mp.tasktracker.dao.repository.mapper.toDomain
import com.mp.tasktracker.exception.TaskNotFoundException
import com.mp.tasktracker.service.task.GetTaskByUUIDService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetTaskByUUIDServiceImpl(
    private val taskRepository: TaskRepository
) : GetTaskByUUIDService {

    override fun execute(uuid: String): TaskDTO = taskRepository.findByUuid(UUID.fromString(uuid))
        ?.toDomain()
        ?.toDTO() ?: throw TaskNotFoundException(uuid)
}