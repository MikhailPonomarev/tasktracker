package com.mp.tasktracker.dao.repository.model

import jakarta.persistence.Entity

@Entity(name = "tag")
open class TagEntity(
    val name: String,
) : BaseEntity()
