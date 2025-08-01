package com.mp.tasktracker.dao.repository

import com.mp.tasktracker.dao.repository.model.TaskEntity
import com.mp.tasktracker.dao.repository.type.TaskStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TaskRepository : JpaRepository<TaskEntity, Long> {

    fun findByUuid(uuid: UUID): TaskEntity?

    fun findByIsDeletedFalse(): List<TaskEntity>

    fun findByStatusAndIsDeletedFalse(status: TaskStatus): List<TaskEntity>
}