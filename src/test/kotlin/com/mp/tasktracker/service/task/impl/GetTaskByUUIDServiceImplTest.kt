package com.mp.tasktracker.service.task.impl

import com.mp.tasktracker.dao.repository.TaskRepository
import com.mp.tasktracker.dao.repository.model.TaskEntity
import com.mp.tasktracker.dao.repository.type.TaskStatus
import com.mp.tasktracker.exception.TaskNotFoundException
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID

@ExtendWith(MockKExtension::class)
class GetTaskByUUIDServiceImplTest {

    @MockK
    lateinit var taskRepository: TaskRepository

    @InjectMockKs
    lateinit var sut: GetTaskByUUIDServiceImpl

    @Test
    fun `execute - should return existing task`() {
        val existingTask = TaskEntity(
            title = "task 1",
            status = TaskStatus.TODO
        )

        every { taskRepository.findByUuid(existingTask.uuid) } returns existingTask

        val result = sut.execute(existingTask.uuid.toString())

        result.id shouldBe existingTask.uuid.toString()
    }

    @Test
    fun `execute - should throw TaskNotFoundException if task with uuid not exists`() {
        val taskUuid = UUID.randomUUID()

        every { taskRepository.findByUuid(taskUuid) } returns null

        assertThrows<TaskNotFoundException> { sut.execute(taskUuid.toString()) }
    }
}