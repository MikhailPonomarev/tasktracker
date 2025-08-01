package com.mp.tasktracker.service.task

interface DeleteTaskByUuidService {

    fun execute(uuid: String)
}