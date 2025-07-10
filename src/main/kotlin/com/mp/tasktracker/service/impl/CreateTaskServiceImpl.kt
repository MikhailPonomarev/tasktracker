package com.mp.tasktracker.service.impl

import com.mp.tasktracker.dao.controller.model.CreateTaskDTO
import com.mp.tasktracker.dao.controller.model.TaskDTO
import com.mp.tasktracker.dao.repository.TagRepository
import com.mp.tasktracker.dao.repository.TaskRepository
import com.mp.tasktracker.dao.repository.UserRepository
import com.mp.tasktracker.dao.repository.mapper.toDomain
import com.mp.tasktracker.dao.repository.model.TaskEntity
import com.mp.tasktracker.dao.controller.mapper.toDTO
import com.mp.tasktracker.dao.repository.model.UserEntity
import com.mp.tasktracker.dao.repository.type.TaskStatus
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
        val assignee = createTaskDTO.assigneeUUID?.let { getUser(it) }

        val observers = createTaskDTO.observersUUIDs?.let { observersUUIDs ->
            buildList {
                observersUUIDs.forEach {
                    val user = getUser(it)
                    add(user)
                }
            }
        }

        val tags = createTaskDTO.tagsUUIDs?.let { tagsUUIDs ->
            buildList {
                tagsUUIDs.forEach {
                    val tag = tagRepository.findByUUID(UUID.fromString(it))
                        ?: throw TagNotFoundException(it)

                    add(tag)
                }
            }
        }

        val status = createTaskDTO.status?.let { statusName ->
            TaskStatus.entries.find { it.name == statusName }
                ?: throw RuntimeException("Status not found by name=${createTaskDTO.status}")
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

    private fun getUser(uuid: String): UserEntity = userRepository.findByUUID(UUID.fromString(uuid))
        ?: throw UserNotFoundException(uuid)
}