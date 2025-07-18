package com.mp.tasktracker.dao.controller.model

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class CreateTaskDTO(
    @field:NotEmpty(message = "Название не может быть пустым")
    @field:Size(max = 500, message = "Название не может быть больше 500 символов")
    val title: String,

    @field:Size(max = 5000, message = "Описание не может быть большее 5000 символов")
    val description: String? = null,

    val status: String? = null,
    val assigneeId: String? = null,
    val observersIds: List<String>? = null,
    val tagsIds: List<String>? = null
)