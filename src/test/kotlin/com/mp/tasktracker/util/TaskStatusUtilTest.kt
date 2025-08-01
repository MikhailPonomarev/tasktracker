package com.mp.tasktracker.util

import com.mp.tasktracker.dao.repository.type.TaskStatus
import com.mp.tasktracker.exception.StatusNotFoundException
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TaskStatusUtilTest {

    @Test
    fun `should find existing status`() {
        val existingStatusName = "DONE"

        val actual = TaskStatusUtil.findStatusOrThrow(existingStatusName)

        actual shouldBe TaskStatus.DONE
    }

    @Test
    fun `should throw StatusNotFoundException`() {
        assertThrows<StatusNotFoundException> { TaskStatusUtil.findStatusOrThrow("TEST") }
    }
}