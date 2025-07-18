package com.mp.tasktracker.dao.repository

import com.mp.tasktracker.dao.repository.model.TaskEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<TaskEntity, Long>