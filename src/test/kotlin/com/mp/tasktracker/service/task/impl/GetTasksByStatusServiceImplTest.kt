package com.mp.tasktracker.service.task.impl

import com.mp.tasktracker.dao.repository.TaskRepository
import com.mp.tasktracker.dao.repository.model.TaskEntity
import com.mp.tasktracker.dao.repository.type.TaskStatus
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetTasksByStatusServiceImplTest {

    @MockK
    lateinit var taskRepository: TaskRepository

    @InjectMockKs
    lateinit var getTasksByStatusServiceImpl: GetTasksByStatusServiceImpl

    @Test
    fun `execute - should find not deleted tasks by status`() {
        val inProgressStatus = TaskStatus.IN_PROGRESS
        val inProgressTasks = listOf(
            TaskEntity(title = "first", status = inProgressStatus),
            TaskEntity(title = "second", status = inProgressStatus)
        )

        every { taskRepository.findByStatusAndIsDeletedFalse(inProgressStatus) } returns inProgressTasks

        val result = getTasksByStatusServiceImpl.execute(inProgressStatus.name)

        assertSoftly {
            result.size shouldBe inProgressTasks.size
            result.forEach { it.status shouldBe inProgressStatus.name }
        }
    }
}