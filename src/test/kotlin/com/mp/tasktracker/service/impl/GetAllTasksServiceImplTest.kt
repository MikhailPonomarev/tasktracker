package com.mp.tasktracker.service.impl

import com.mp.tasktracker.dao.repository.TaskRepository
import com.mp.tasktracker.dao.repository.model.TaskEntity
import com.mp.tasktracker.dao.repository.type.TaskStatus
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetAllTasksServiceImplTest {

    @MockK
    lateinit var taskRepository: TaskRepository

    @InjectMockKs
    lateinit var sut: GetAllTasksServiceImpl

    @Test
    fun `execute - should return all not deleted tasks`() {
        val existingTasksMock = listOf(
            TaskEntity(
                title = "task 1",
                status = TaskStatus.TODO
            ),
            TaskEntity(
                title = "task 2",
                status = TaskStatus.IN_PROGRESS
            )
        )

        every { taskRepository.findByIsDeletedFalse() } returns existingTasksMock

        val result = sut.execute()

        result.size shouldBe existingTasksMock.size
    }
}