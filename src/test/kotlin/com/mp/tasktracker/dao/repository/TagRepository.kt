package com.mp.tasktracker.dao.repository

import com.mp.tasktracker.dao.repository.model.TagEntity
import java.util.UUID

interface TagRepository {

    fun findByUUID(uuid: UUID): TagEntity?
}