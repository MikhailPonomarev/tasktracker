package com.mp.tasktracker.service.task.impl

import com.mp.tasktracker.dao.repository.TaskRepository
import com.mp.tasktracker.exception.TaskNotFoundException
import com.mp.tasktracker.service.task.DeleteTaskByUuidService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DeleteTaskByUuidServiceImpl(
    private val taskRepository: TaskRepository
) : DeleteTaskByUuidService {

    override fun execute(uuid: String) {
        val task = taskRepository.findByUuid(UUID.fromString(uuid)) ?: throw TaskNotFoundException(uuid)
        task.isDeleted = true
        taskRepository.save(task)
    }
}