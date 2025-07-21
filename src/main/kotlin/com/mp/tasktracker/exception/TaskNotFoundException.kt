package com.mp.tasktracker.exception

class TaskNotFoundException(private val id: String) : IllegalArgumentException() {

    override val message: String
        get() = "Task not found with uuid=$id"
}