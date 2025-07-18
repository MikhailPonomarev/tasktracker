package com.mp.tasktracker.dao.repository.model

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.OffsetDateTime
import java.util.UUID

@MappedSuperclass
abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    @Column(nullable = false, unique = true)
    var uuid: UUID = UUID.randomUUID()

    val isDeleted: Boolean = false

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    lateinit var createdAt: OffsetDateTime

    @UpdateTimestamp
    @Column(nullable = true)
    lateinit var updatedAt: OffsetDateTime
}