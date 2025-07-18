package com.mp.tasktracker.dao.controller.handler

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

private val logger = KotlinLogging.logger {}

@ControllerAdvice
class ValidationHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errors = mutableMapOf<String, String>()

        ex.bindingResult.fieldErrors.forEach {
            errors[it.field] = it.defaultMessage ?: "Ошибка валидации"
        }

        logger.error { errors }

        return ResponseEntity.badRequest().body(errors)
    }
}