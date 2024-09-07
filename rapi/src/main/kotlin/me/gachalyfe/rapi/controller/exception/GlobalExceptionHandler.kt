package me.gachalyfe.rapi.controller.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice(annotations = [RestController::class])
class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFoundException(e: ResourceNotFoundException): ResponseEntity<ErrorMessage> =
        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorMessage(e.message ?: "There's no such resource(s)"))

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<ErrorMessage> {
        val message = StringBuilder().append("Validation error:\n")
        e.bindingResult.allErrors.map {
            message.append("${(it as FieldError).field}-> ${it.defaultMessage}\n")
        }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorMessage(message.toString()))
    }

    data class ErrorMessage(
        val message: String,
    )
}
