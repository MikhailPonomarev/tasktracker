package com.mp.tasktracker.dao.repository.model

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity(name = "\"user\"")
class UserEntity(
    @Column(length = 255)
    var name: String
) : BaseEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserEntity

        return id == other.id && uuid == other.uuid && name == other.name
    }

    override fun hashCode(): Int {
        var result = id.hashCode()

        result = 31 * result + uuid.hashCode()
        result = 31 * result + name.hashCode()

        return result
    }

    override fun toString(): String = "UserEntity(id=$id, name=$name)"
}
