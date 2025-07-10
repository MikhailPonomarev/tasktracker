package com.mp.tasktracker.test.service.impl

import com.mp.tasktracker.dao.controller.model.CreateTaskDTO
import com.mp.tasktracker.dao.repository.TagRepository
import com.mp.tasktracker.dao.repository.TaskRepository
import com.mp.tasktracker.dao.repository.UserRepository
import com.mp.tasktracker.dao.repository.model.TagEntity
import com.mp.tasktracker.dao.repository.model.TaskEntity
import com.mp.tasktracker.dao.repository.model.UserEntity
import com.mp.tasktracker.dao.repository.type.TaskStatus
import com.mp.tasktracker.service.impl.CreateTaskServiceImpl
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID

@ExtendWith(MockKExtension::class)
class CreateTaskServiceImplTest {

    @MockK
    lateinit var userRepository: UserRepository

    @MockK
    lateinit var tagRepository: TagRepository

    @MockK
    lateinit var taskRepository: TaskRepository

    @InjectMockKs
    lateinit var sut: CreateTaskServiceImpl

    @Test
    fun `execute - should create task`() {
        val assigneeStub = UserEntity(name = "assignee")

        val observersStub = listOf(UserEntity(name = "observer1"))

        val tagsStub = listOf(TagEntity(name = "tag1"))

        val taskStub = TaskEntity(
            title = "test task",
            description = "test description",
            status = TaskStatus.TODO,
            assignee = assigneeStub,
            observers = observersStub,
            tags = tagsStub
        )

        val createTaskDTO = CreateTaskDTO(
            title = "test task",
            description = "test description",
            assigneeUUID = assigneeStub.uuid.toString(),
            observersUUIDs = observersStub.map { it.uuid.toString() },
            tagsUUIDs = tagsStub.map { it.uuid.toString() }
        )

        every { userRepository.findByUUID(UUID.fromString(createTaskDTO.assigneeUUID)) } returns assigneeStub
        every { userRepository.findByUUID(observersStub.first().uuid) } returns observersStub.first()
        every { tagRepository.findByUUID(tagsStub.first().uuid) } returns tagsStub.first()
        every { taskRepository.save(any()) } returns taskStub

        val taskDTO = sut.execute(createTaskDTO)

        assertSoftly {
            taskDTO.title shouldBe createTaskDTO.title
            taskDTO.description shouldBe createTaskDTO.description
            taskDTO.status shouldBe TaskStatus.TODO.name
            taskDTO.assignee?.uuid shouldBe createTaskDTO.assigneeUUID
            taskDTO.observers?.map { it.uuid } shouldBe createTaskDTO.observersUUIDs
            taskDTO.tags?.map { it.uuid } shouldBe createTaskDTO.tagsUUIDs
        }
    }
}