package com.mp.tasktracker.dao.controller

import com.mp.tasktracker.dao.controller.model.CreateTaskDTO
import com.mp.tasktracker.dao.controller.model.TaskDTO
import com.mp.tasktracker.service.CreateTaskService
import com.mp.tasktracker.service.GetAllTasksService
import com.mp.tasktracker.service.GetTaskByUUIDService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val createTaskService: CreateTaskService,
    private val getTaskByUUIDService: GetTaskByUUIDService,
    private val getAllTasksService: GetAllTasksService
) {

    @PostMapping
    fun createTask(@Valid @RequestBody dto: CreateTaskDTO): ResponseEntity<TaskDTO> =
        ResponseEntity.ok(createTaskService.execute(dto))

    @GetMapping("/{uuid}")
    fun getTaskByUuid(@PathVariable uuid: String): ResponseEntity<TaskDTO> =
        ResponseEntity.ok(getTaskByUUIDService.execute(uuid))

    @GetMapping
    fun getAllNotDeletedTasks(): ResponseEntity<List<TaskDTO>> =
        ResponseEntity.ok(getAllTasksService.execute())
}

//    @PutMapping("/{uuid}")
//    fun updateTask(
//        @PathVariable uuid: String,
//        @Valid @RequestBody dto: CreateTaskDTO
//    ): ResponseEntity<TaskDTO> = ResponseEntity.ok()
//
//    @DeleteMapping("/{uuid}")
//    fun deleteTask(@PathVariable uuid: String)
//}