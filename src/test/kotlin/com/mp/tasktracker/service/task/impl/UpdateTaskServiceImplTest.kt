package com.mp.tasktracker.service.task.impl

import com.mp.tasktracker.dao.controller.model.UpdateTaskDTO
import com.mp.tasktracker.dao.repository.TagRepository
import com.mp.tasktracker.dao.repository.TaskRepository
import com.mp.tasktracker.dao.repository.UserRepository
import com.mp.tasktracker.dao.repository.model.TagEntity
import com.mp.tasktracker.dao.repository.model.TaskEntity
import com.mp.tasktracker.dao.repository.model.UserEntity
import com.mp.tasktracker.dao.repository.type.TaskStatus
import com.mp.tasktracker.exception.TagNotFoundException
import com.mp.tasktracker.exception.TaskNotFoundException
import com.mp.tasktracker.exception.UserNotFoundException
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID

@ExtendWith(MockKExtension::class)
class UpdateTaskServiceImplTest {

    @MockK
    lateinit var taskRepository: TaskRepository

    @MockK
    lateinit var userRepository: UserRepository

    @MockK
    lateinit var tagRepository: TagRepository

    @InjectMockKs
    lateinit var sut: UpdateTaskServiceImpl

    @Test
    fun `execute - should update existing task`() {
        val existingTaskUuid = UUID.randomUUID()
        val existingTask = TaskEntity(
            title = "initial title",
            description = "initial description",
            status = TaskStatus.TODO,
            assignee = UserEntity("initial assignee"),
            observers = mutableListOf(UserEntity(name = "initial observer")),
            tags = mutableListOf(TagEntity(name = "initial tag"))
        ).apply { uuid = existingTaskUuid }

        val newAssigneeUuid = UUID.randomUUID()
        val newObserverUuid = UUID.randomUUID()
        val newTagUuid = UUID.randomUUID()
        val newAssignee = UserEntity(name = "new assignee").apply { uuid = newAssigneeUuid }
        val newObserver = UserEntity(name = "new observer").apply { uuid = newObserverUuid }
        val newTag = TagEntity(name = "new tag").apply { uuid = newTagUuid }

        val updTaskDTO = UpdateTaskDTO(
            title = "new title",
            description = "new description",
            status = TaskStatus.DONE.name,
            assigneeId = newAssigneeUuid.toString(),
            observersIds = listOf(newObserverUuid.toString()),
            tagsIds = listOf(newTagUuid.toString())
        )

        every { taskRepository.findByUuid(any()) } returns existingTask
        every { userRepository.findByUuid(newAssigneeUuid) } returns newAssignee
        every { userRepository.findByUuid(newObserverUuid) } returns newObserver
        every { tagRepository.findByUuid(any()) } returns newTag
        every { taskRepository.save(any()) } returnsArgument 0

        val result = sut.execute(existingTaskUuid.toString(), updTaskDTO)

        with(result) {
            assertSoftly {
                id shouldBe existingTaskUuid.toString()
                title shouldBe updTaskDTO.title
                description shouldBe updTaskDTO.description
                status shouldBe updTaskDTO.status
                assignee?.id shouldBe updTaskDTO.assigneeId
                observers?.map { it.id } shouldBe updTaskDTO.observersIds
                tags?.map { it.id } shouldBe updTaskDTO.tagsIds
            }
        }
    }

    @Test
    fun `execute - should throw TaskNotFoundException if task not found`() {
        val taskUuid = UUID.randomUUID()

        every { taskRepository.findByUuid(taskUuid) } returns null

        assertThrows<TaskNotFoundException> { sut.execute(taskUuid.toString(), mockk()) }
    }

    @Test
    fun `execute - should throw UserNotFoundException if assignee user not found`() {
        val taskUuid = UUID.randomUUID()
        val assigneeUuid = UUID.randomUUID()
        val updTaskDTO = UpdateTaskDTO(title = "test", assigneeId = assigneeUuid.toString())

        every { taskRepository.findByUuid(taskUuid) } returns mockk()
        every { userRepository.findByUuid(assigneeUuid) } returns null

        assertThrows<UserNotFoundException> { sut.execute(taskUuid.toString(), updTaskDTO) }
    }

    @Test
    fun `execute - should throw UserNotFoundException if observer user not found`() {
        val taskUuid = UUID.randomUUID()
        val observerUuid = UUID.randomUUID()
        val updTaskDTO = UpdateTaskDTO(title = "test", observersIds = listOf(observerUuid.toString()))

        every { taskRepository.findByUuid(taskUuid) } returns mockk()
        every { userRepository.findByUuid(observerUuid) } returns null

        assertThrows<UserNotFoundException> { sut.execute(taskUuid.toString(), updTaskDTO) }
    }

    @Test
    fun `execute - should not update task status if new status not present in dto`() {
        val existingTaskUuid = UUID.randomUUID()
        val initialStatus = TaskStatus.TODO
        val existingTask = TaskEntity(
            title = "initial title",
            status = initialStatus,
        ).apply { uuid = existingTaskUuid }

        val updTaskDTO = UpdateTaskDTO(title = "new title")

        every { taskRepository.findByUuid(existingTaskUuid) } returns existingTask
        every { taskRepository.save(any()) } returnsArgument 0

        val result = sut.execute(existingTaskUuid.toString(), updTaskDTO)

        result.status shouldBe initialStatus.name
    }

    @Test
    fun `execute - should throw TagNotFoundException if tag not found`() {
        val taskUuid = UUID.randomUUID()
        val tagUuid = UUID.randomUUID()
        val updTaskDTO = UpdateTaskDTO(title = "test", tagsIds = listOf(tagUuid.toString()))

        every { taskRepository.findByUuid(taskUuid) } returns mockk(relaxed = true)
        every { tagRepository.findByUuid(tagUuid) } returns null

        assertThrows<TagNotFoundException> { sut.execute(taskUuid.toString(), updTaskDTO) }
    }
}