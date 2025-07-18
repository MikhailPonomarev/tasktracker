package com.mp.tasktracker.exception

class StatusNotFoundException(private val statusName: String) : IllegalArgumentException() {

    override val message: String
        get() = "Status not found by name=$statusName"
}