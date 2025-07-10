package com.mp.tasktracker.dao.repository.model

import java.util.UUID

data class TagEntity(
    val id: Long = 0L,
    val uuid: UUID = UUID.randomUUID(),
    val name: String,
    val isDeleted: Boolean = false
)
