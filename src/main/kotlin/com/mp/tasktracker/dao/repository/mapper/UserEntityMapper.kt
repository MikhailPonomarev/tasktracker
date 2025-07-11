package com.mp.tasktracker.dao.repository.mapper

import com.mp.tasktracker.dao.repository.model.UserEntity
import com.mp.tasktracker.domain.User

fun UserEntity.toDomain() = User(
    id = this.uuid.toString(),
    name = this.name
)