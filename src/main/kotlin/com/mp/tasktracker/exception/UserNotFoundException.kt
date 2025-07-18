package com.mp.tasktracker.exception

class UserNotFoundException(private val id: String) : IllegalArgumentException() {

    override val message: String
        get() = "User not found with uuid=$id"
}