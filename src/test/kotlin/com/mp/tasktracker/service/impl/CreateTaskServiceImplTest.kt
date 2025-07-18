package com.mp.tasktracker.service.impl

import com.mp.tasktracker.dao.controller.model.CreateTaskDTO
import com.mp.tasktracker.dao.repository.TagRepository
import com.mp.tasktracker.dao.repository.TaskRepository
import com.mp.tasktracker.dao.repository.UserRepository
import com.mp.tasktracker.dao.repository.model.TagEntity
import com.mp.tasktracker.dao.repository.model.TaskEntity
import com.mp.tasktracker.dao.repository.model.UserEntity
import com.mp.tasktracker.dao.repository.type.TaskStatus
import com.mp.tasktracker.exception.StatusNotFoundException
import com.mp.tasktracker.exception.TagNotFoundException
import com.mp.tasktracker.exception.UserNotFoundException
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
    
    private val taskName = "test task"

    @Test
    fun `execute - should create task`() {
        val assigneeStub = UserEntity(name = "assignee")
        val observersStub = listOf(UserEntity(name = "observer1"))
        val tagsStub = listOf(TagEntity(name = "tag1"))
        val createTaskDTO = CreateTaskDTO(
            title = taskName,
            description = "test description",
            status = TaskStatus.IN_PROGRESS.name,
            assigneeId = assigneeStub.uuid.toString(),
            observersIds = observersStub.map { it.uuid.toString() },
            tagsIds = tagsStub.map { it.uuid.toString() }
        )

        val taskEntitySlot = slot<TaskEntity>()

        every { userRepository.findByUuid(UUID.fromString(createTaskDTO.assigneeId)) } returns assigneeStub
        every { userRepository.findByUuid(observersStub.first().uuid) } returns observersStub.first()
        every { tagRepository.findByUuid(tagsStub.first().uuid) } returns tagsStub.first()
        every { taskRepository.save(capture(taskEntitySlot)) } returnsArgument 0

        sut.execute(createTaskDTO)

        with(taskEntitySlot.captured) {
            assertSoftly {
                title shouldBe createTaskDTO.title
                description shouldBe createTaskDTO.description
                status shouldBe TaskStatus.IN_PROGRESS
                assignee?.uuid.toString() shouldBe createTaskDTO.assigneeId
                observers.map { it.uuid.toString() } shouldBe createTaskDTO.observersIds
                tags.map { it.uuid.toString() } shouldBe createTaskDTO.tagsIds
            }
        }
    }

    @Test
    fun `execute - should create with only required data`() {
        val createTaskDTO = CreateTaskDTO(title = taskName)
        val taskEntitySlot = slot<TaskEntity>()

        every { taskRepository.save(capture(taskEntitySlot)) } returnsArgument 0

        sut.execute(createTaskDTO)

        with(taskEntitySlot.captured) {
            assertSoftly {
                title shouldBe createTaskDTO.title
                description shouldBe null
                status shouldBe TaskStatus.TODO
                assignee shouldBe null
                observers shouldBe emptyList()
                tags shouldBe emptyList()
            }
        }
    }

    @Test
    fun `execute - should throw UserNotFoundException if assignee not exists`() {
        val createTaskDTO = CreateTaskDTO(
            title = taskName,
            assigneeId =  UUID.randomUUID().toString()
        )

        every { userRepository.findByUuid(any()) } returns null

        assertThrows<UserNotFoundException> { sut.execute(createTaskDTO) }
    }

    @Test
    fun `execute - should throw UserNotFoundException if observer not exists`() {
        val createTaskDTO = CreateTaskDTO(
            title = taskName,
            observersIds = listOf(UUID.randomUUID().toString())
        )

        every { userRepository.findByUuid(any()) } returns null

        assertThrows<UserNotFoundException> { sut.execute(createTaskDTO) }
    }

    @Test
    fun `execute - should throw TagNotFoundException if tag not exists`() {
        val createTaskDTO = CreateTaskDTO(
            title = taskName,
            tagsIds = listOf(UUID.randomUUID().toString())
        )

        every { tagRepository.findByUuid(any()) } returns null

        assertThrows<TagNotFoundException> { sut.execute(createTaskDTO) }
    }

    @Test
    fun `execute - should throw StatusNotFoundException if tag not exists`() {
        val createTaskDTO = CreateTaskDTO(
            title = taskName,
            status = "NOT_EXISTING_STATUS"
        )

        assertThrows<StatusNotFoundException> { sut.execute(createTaskDTO) }
    }
}