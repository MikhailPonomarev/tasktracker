package com.mp.tasktracker.service.task.impl

import com.mp.tasktracker.dao.controller.mapper.toDTO
import com.mp.tasktracker.dao.controller.model.TaskDTO
import com.mp.tasktracker.dao.controller.model.UpdateTaskDTO
import com.mp.tasktracker.dao.repository.TagRepository
import com.mp.tasktracker.dao.repository.TaskRepository
import com.mp.tasktracker.dao.repository.UserRepository
import com.mp.tasktracker.dao.repository.mapper.toDomain
import com.mp.tasktracker.exception.TagNotFoundException
import com.mp.tasktracker.exception.TaskNotFoundException
import com.mp.tasktracker.exception.UserNotFoundException
import com.mp.tasktracker.service.task.UpdateTaskService
import com.mp.tasktracker.util.TaskStatusUtil
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UpdateTaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository,
    private val tagRepository: TagRepository
) : UpdateTaskService {

    override fun execute(uuid: String, dto: UpdateTaskDTO): TaskDTO {
        val taskToUpdate = taskRepository.findByUuid(UUID.fromString(uuid))
            ?: throw TaskNotFoundException(uuid)

        val newAssignee = dto.assigneeId?.let { findUserOrThrow(it) }

        val newObservers = buildList {
            dto.observersIds?.let { ids ->
                ids.forEach { add(findUserOrThrow(it)) }
            }
        }.toMutableList()

        val newStatus = dto.status?.let { TaskStatusUtil.findStatusOrThrow(it) } ?: taskToUpdate.status

        val newTags = buildList {
            dto.tagsIds?.let { ids ->
                ids.forEach {
                    val tag = tagRepository.findByUuid(UUID.fromString(it)) ?: throw TagNotFoundException(it)
                    add(tag)
                }
            }
        }.toMutableList()

        return taskToUpdate.apply {
            title = dto.title
            description = dto.description
            status = newStatus
            assignee = newAssignee
            observers = newObservers
            tags = newTags
        }.run {
            taskRepository.save(this)
            this.toDomain().toDTO()
        }
    }

    private fun findUserOrThrow(uuid: String) = userRepository.findByUuid(UUID.fromString(uuid))
        ?: throw UserNotFoundException(uuid)
}