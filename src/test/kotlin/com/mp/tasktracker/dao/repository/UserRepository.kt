package com.mp.tasktracker.dao.repository

import com.mp.tasktracker.dao.repository.model.UserEntity
import java.util.UUID

interface UserRepository {

    fun findByUUID(uuid: UUID): UserEntity?
}