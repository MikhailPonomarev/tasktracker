package com.mp.tasktracker.dao.repository

import com.mp.tasktracker.dao.repository.model.TagEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TagRepository : JpaRepository<TagEntity, Long> {

    fun findByUuid(uuid: UUID): TagEntity?
}