package me.gachalyfe.rapi.controller.exception

import me.gachalyfe.rapi.data.dto.ApiResponse
import me.gachalyfe.rapi.data.dto.buildResponse
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
    fun handleNotFoundException(e: ResourceNotFoundException): ResponseEntity<ApiResponse<Nothing>> {
        val response = ApiResponse.Error(
            status = HttpStatus.NOT_FOUND.value(),
            message = e.message ?: "There's no such resource(s)"
        )
        return response.buildResponse()
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Nothing>> {
        val message = StringBuilder().append("Validation error:\n")
        e.bindingResult.allErrors.map {
            message.append("${(it as FieldError).field}-> ${it.defaultMessage}\n")
        }
        val response = ApiResponse.Error(
            status = HttpStatus.BAD_REQUEST.value(),
            message = message.toString()
        )

        return response.buildResponse()
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(e: Exception): ResponseEntity<ApiResponse<Nothing>> {
        val response = ApiResponse.Error(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = e.message ?: "Unhandled exception ${e.javaClass.canonicalName}"
        )

        return response.buildResponse()
    }
}
