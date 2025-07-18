package com.mp.tasktracker.dao.controller

import com.mp.tasktracker.dao.controller.model.CreateTaskDTO
import com.mp.tasktracker.dao.controller.model.TaskDTO
import com.mp.tasktracker.service.CreateTaskService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val createTaskService: CreateTaskService
) {

    @PostMapping
    fun createTask(@Valid @RequestBody dto: CreateTaskDTO): ResponseEntity<TaskDTO> {
        val taskDTO = createTaskService.execute(dto)
        return ResponseEntity.ok(taskDTO)
    }
}