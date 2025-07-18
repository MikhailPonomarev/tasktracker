package com.mp.tasktracker.dao.controller.mapper

import com.mp.tasktracker.dao.controller.model.UserDTO
import com.mp.tasktracker.domain.User

fun User.toDTO() = UserDTO(
    id = this.id.toString(),
    name = this.name
)