package com.mp.tasktracker.dao.repository.mapper

import com.mp.tasktracker.dao.repository.model.TagEntity
import com.mp.tasktracker.domain.Tag

fun TagEntity.toDomain() = Tag(
    id = this.uuid,
    name = this.name
)