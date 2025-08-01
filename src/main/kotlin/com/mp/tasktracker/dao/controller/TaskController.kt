package com.mp.tasktracker.dao.controller

import com.mp.tasktracker.dao.controller.model.CreateTaskDTO
import com.mp.tasktracker.dao.controller.model.DefaultResponseDTO
import com.mp.tasktracker.dao.controller.model.TaskDTO
import com.mp.tasktracker.dao.controller.model.UpdateTaskDTO
import com.mp.tasktracker.service.task.*
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val createTaskService: CreateTaskService,
    private val getTaskByUUIDService: GetTaskByUUIDService,
    private val getAllTasksService: GetAllTasksService,
    private val updateTaskService: UpdateTaskService,
    private val deleteTaskByUuidService: DeleteTaskByUuidService
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

    @PutMapping("/{uuid}")
    fun updateTask(
        @PathVariable uuid: String,
        @Valid @RequestBody dto: UpdateTaskDTO
    ): ResponseEntity<TaskDTO> = ResponseEntity.ok(updateTaskService.execute(uuid, dto))

    @DeleteMapping("/{uuid}")
    fun deleteTask(@PathVariable uuid: String): DefaultResponseDTO =
        deleteTaskByUuidService.execute(uuid)
            .let { DefaultResponseDTO("Task with uuid=$uuid deleted successfully") }
}