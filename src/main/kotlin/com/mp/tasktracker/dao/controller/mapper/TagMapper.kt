package com.mp.tasktracker.dao.controller.mapper

import com.mp.tasktracker.dao.controller.model.TagDTO
import com.mp.tasktracker.domain.Tag

fun Tag.toDTO() = TagDTO(
    id = this.id,
    name = this.name
)