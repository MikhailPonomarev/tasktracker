package com.mp.tasktracker.service.impl

import com.mp.tasktracker.dao.controller.model.CreateTaskDTO
import com.mp.tasktracker.dao.repository.TagRepository
import com.mp.tasktracker.dao.repository.TaskRepository
import com.mp.tasktracker.dao.repository.UserRepository
import com.mp.tasktracker.dao.repository.model.TagEntity
import com.mp.tasktracker.dao.repository.model.TaskEntity
import com.mp.tasktracker.dao.repository.model.UserEntity
import com.mp.tasktracker.dao.repository.type.TaskStatus
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
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
        //given
        val assigneeStub = UserEntity(name = "assignee")
        val observersStub = listOf(UserEntity(name = "observer1"))
        val tagsStub = listOf(TagEntity(name = "tag1"))
        val createTaskDTO = CreateTaskDTO(
            title = "test task",
            description = "test description",
            assigneeId = assigneeStub.uuid.toString(),
            observersIds = observersStub.map { it.uuid.toString() },
            tagsIds = tagsStub.map { it.uuid.toString() }
        )

        val taskEntitySlot = slot<TaskEntity>()

        every { userRepository.findByUuid(UUID.fromString(createTaskDTO.assigneeId)) } returns assigneeStub
        every { userRepository.findByUuid(observersStub.first().uuid) } returns observersStub.first()
        every { tagRepository.findByUuid(tagsStub.first().uuid) } returns tagsStub.first()
        every { taskRepository.save(capture(taskEntitySlot)) } returnsArgument 0

        //when
        sut.execute(createTaskDTO)

        //then
        with(taskEntitySlot.captured) {
            assertSoftly {
                this.title shouldBe createTaskDTO.title
                this.description shouldBe createTaskDTO.description
                this.status.name shouldBe TaskStatus.TODO.name
                this.assignee?.uuid.toString() shouldBe createTaskDTO.assigneeId
                this.observers.map { it.uuid.toString() } shouldBe createTaskDTO.observersIds
                this.tags.map { it.uuid.toString() } shouldBe createTaskDTO.tagsIds
            }
        }
    }
}