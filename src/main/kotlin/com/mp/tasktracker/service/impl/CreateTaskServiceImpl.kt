package com.mp.tasktracker.service.impl

import com.mp.tasktracker.dao.controller.model.CreateTaskDTO
import com.mp.tasktracker.dao.controller.model.TaskDTO
import com.mp.tasktracker.dao.repository.TagRepository
import com.mp.tasktracker.dao.repository.TaskRepository
import com.mp.tasktracker.dao.repository.UserRepository
import com.mp.tasktracker.dao.repository.mapper.toDomain
import com.mp.tasktracker.dao.repository.model.TaskEntity
import com.mp.tasktracker.dao.controller.mapper.toDTO
import com.mp.tasktracker.dao.repository.type.TaskStatus
import com.mp.tasktracker.exception.StatusNotFoundException
import com.mp.tasktracker.exception.TagNotFoundException
import com.mp.tasktracker.exception.UserNotFoundException
import com.mp.tasktracker.service.CreateTaskService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreateTaskServiceImpl(
    private val userRepository: UserRepository,
    private val tagRepository: TagRepository,
    private val taskRepository: TaskRepository
) : CreateTaskService {

    override fun execute(createTaskDTO: CreateTaskDTO): TaskDTO {
        val assignee = createTaskDTO.assigneeId?.let { getUser(it) }

        val observers = createTaskDTO.observersIds?.let { ids ->
            buildList {
                ids.forEach { add(getUser(it)) }
            }.toMutableList()
        } ?: mutableListOf()

        val tags = createTaskDTO.tagsIds?.let { ids ->
            buildList {
                ids.forEach {
                    val tag = tagRepository.findByUuid(UUID.fromString(it)) ?: throw TagNotFoundException(it)
                    add(tag)
                }
            }.toMutableList()
        } ?: mutableListOf()

        val status = createTaskDTO.status?.let { statusName ->
            TaskStatus.entries.find { it.name == statusName } ?: throw StatusNotFoundException(statusName)
        } ?: TaskStatus.TODO

        val taskEntity = TaskEntity(
            title = createTaskDTO.title,
            description = createTaskDTO.description,
            status = status,
            assignee = assignee,
            observers = observers,
            tags = tags
        )

        return taskRepository.save(taskEntity)
            .toDomain()
            .toDTO()
    }

    private fun getUser(uuid: String) = userRepository.findByUuid(UUID.fromString(uuid))
        ?: throw UserNotFoundException(uuid)
}