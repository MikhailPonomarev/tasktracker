package com.mp.tasktracker.service.task.impl

import com.mp.tasktracker.dao.controller.mapper.toDTO
import com.mp.tasktracker.dao.controller.model.TaskDTO
import com.mp.tasktracker.dao.repository.TaskRepository
import com.mp.tasktracker.dao.repository.mapper.toDomain
import com.mp.tasktracker.service.task.GetTasksByStatusService
import com.mp.tasktracker.util.TaskStatusUtil
import org.springframework.stereotype.Service

@Service
class GetTasksByStatusServiceImpl(
    private val taskRepository: TaskRepository
) : GetTasksByStatusService {

    override fun execute(statusName: String): List<TaskDTO> {
        val status = TaskStatusUtil.findStatusOrThrow(statusName)
        return taskRepository.findByStatusAndIsDeletedFalse(status)
            .map { it.toDomain().toDTO() }
    }
}