package com.mp.tasktracker.service.task.impl

import com.mp.tasktracker.dao.controller.model.CreateTaskDTO
import com.mp.tasktracker.dao.controller.model.TaskDTO
import com.mp.tasktracker.dao.repository.TagRepository
import com.mp.tasktracker.dao.repository.TaskRepository
import com.mp.tasktracker.dao.repository.UserRepository
import com.mp.tasktracker.dao.repository.mapper.toDomain
import com.mp.tasktracker.dao.repository.model.TaskEntity
import com.mp.tasktracker.dao.controller.mapper.toDTO
import com.mp.tasktracker.dao.repository.type.TaskStatus
import com.mp.tasktracker.exception.TagNotFoundException
import com.mp.tasktracker.exception.UserNotFoundException
import com.mp.tasktracker.service.task.CreateTaskService
import com.mp.tasktracker.util.TaskStatusUtil
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreateTaskServiceImpl(
    private val userRepository: UserRepository,
    private val tagRepository: TagRepository,
    private val taskRepository: TaskRepository
) : CreateTaskService {

    override fun execute(dto: CreateTaskDTO): TaskDTO {
        val assignee = dto.assigneeId?.let { findUserOrThrow(it) }

        val observers = dto.observersIds?.let { ids ->
            buildList {
                ids.forEach { add(findUserOrThrow(it)) }
            }.toMutableList()
        } ?: mutableListOf()

        val tags = dto.tagsIds?.let { ids ->
            buildList {
                ids.forEach {
                    val tag = tagRepository.findByUuid(UUID.fromString(it)) ?: throw TagNotFoundException(it)
                    add(tag)
                }
            }.toMutableList()
        } ?: mutableListOf()

        val status = dto.status?.let { TaskStatusUtil.findStatusOrThrow(it) } ?: TaskStatus.TODO

        val taskEntity = TaskEntity(
            title = dto.title,
            description = dto.description,
            status = status,
            assignee = assignee,
            observers = observers,
            tags = tags
        )

        return taskRepository.save(taskEntity)
            .toDomain()
            .toDTO()
    }

    private fun findUserOrThrow(uuid: String) = userRepository.findByUuid(UUID.fromString(uuid))
        ?: throw UserNotFoundException(uuid)
}