package com.mp.tasktracker.exception

class TagNotFoundException(private val id: String) : IllegalArgumentException() {

    override val message: String
        get() = "Tag not found with uuid=$id"
}