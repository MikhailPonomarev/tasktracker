package com.mp.tasktracker.dao.repository.model

import com.mp.tasktracker.dao.repository.type.TaskStatus
import jakarta.persistence.*

@Entity(name = "task")
open class TaskEntity(
    @Column(length = 500)
    var title: String,

    @Column(length = 5000)
    var description: String? = null,

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    var status: TaskStatus,

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    var assignee: UserEntity? = null,

    @ManyToMany
    @JoinTable(
        name = "observer_to_task",
        joinColumns = [JoinColumn(name = "task_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var observers: MutableList<UserEntity> = mutableListOf(),

    @ManyToMany
    @JoinTable(
        name = "tag_to_task",
        joinColumns = [JoinColumn(name = "task_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    var tags: MutableList<TagEntity> = mutableListOf()
) : BaseEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TaskEntity

        if (id != other.id) return false
        if (uuid != other.uuid) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (status != other.status) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()

        result = 31 * result + uuid.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + status.hashCode()

        return result
    }

    override fun toString(): String = "TaskEntity(id=$id, title=$title)"
}
