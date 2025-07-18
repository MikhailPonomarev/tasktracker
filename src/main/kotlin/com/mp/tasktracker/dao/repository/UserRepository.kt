package com.mp.tasktracker.dao.repository

import com.mp.tasktracker.dao.repository.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {

    fun findByUuid(uuid: UUID): UserEntity?
}