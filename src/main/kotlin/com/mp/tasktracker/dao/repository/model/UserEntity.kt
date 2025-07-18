package com.mp.tasktracker.dao.repository.model

import jakarta.persistence.Entity

@Entity(name = "\"user\"")
class UserEntity(
    val name: String,
) : BaseEntity()
