package com.mp.tasktracker.service.task.impl

import com.mp.tasktracker.dao.repository.TaskRepository
import com.mp.tasktracker.exception.TaskNotFoundException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID

@ExtendWith(MockKExtension::class)
class DeleteTaskByUuidServiceImplTest {

    @MockK
    lateinit var taskRepository: TaskRepository

    @InjectMockKs
    lateinit var sut: DeleteTaskByUuidServiceImpl

    @Test
    fun `execute - should mark task as deleted`() {
        every { taskRepository.findByUuid(any()) } returns mockk(relaxed = true)
        every { taskRepository.save(any()) } returnsArgument 0

        sut.execute(UUID.randomUUID().toString())

        verify(exactly = 1) { taskRepository.findByUuid(any()) }
        verify(exactly = 1) { taskRepository.save(any()) }
    }

    @Test
    fun `execute - should throw TaskNotFoundException if task with uuid not found`() {
        every { taskRepository.findByUuid(any()) } returns null

        assertThrows<TaskNotFoundException> { sut.execute(UUID.randomUUID().toString()) }
    }
}