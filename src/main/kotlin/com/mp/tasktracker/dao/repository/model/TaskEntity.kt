package com.mp.tasktracker.dao.repository.model

import com.mp.tasktracker.dao.repository.type.TaskStatus
import jakarta.persistence.*

@Entity(name = "task")
open class TaskEntity(
    var title: String,
    var description: String? = null,

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

    override fun toString(): String {
        return "TaskEntity(id=$id, title=$title)"
    }
}
