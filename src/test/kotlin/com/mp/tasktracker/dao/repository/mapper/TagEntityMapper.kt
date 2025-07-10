package com.mp.tasktracker.dao.repository.mapper

import com.mp.tasktracker.dao.repository.model.TagEntity
import com.mp.tasktracker.domain.Tag

fun TagEntity.toDomain() = Tag(
    uuid = this.uuid.toString(),
    name = this.name
)