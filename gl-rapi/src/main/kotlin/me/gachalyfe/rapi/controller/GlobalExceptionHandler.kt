package me.gachalyfe.rapi.controller

import me.gachalyfe.rapi.utils.exception.CsvHandlingException
import me.gachalyfe.rapi.utils.exception.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice(annotations = [RestController::class])
class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFoundException(e: ResourceNotFoundException): ResponseEntity<ApiResponse<Nothing>> {
        val response =
            ApiResponse.Error(
                status = HttpStatus.NOT_FOUND.value(),
                message = e.message ?: "There's no such resource(s)",
            )
        return response.buildResponse()
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Nothing>> {
        val message = StringBuilder().append("Validation error:\n")
        e.bindingResult.allErrors.map {
            message.append("${(it as FieldError).field}-> ${it.defaultMessage}\n")
        }
        val response =
            ApiResponse.Error(
                status = HttpStatus.BAD_REQUEST.value(),
                message = message.toString(),
            )

        return response.buildResponse()
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingParams(e: MissingServletRequestParameterException): ResponseEntity<ApiResponse<Nothing>> {
        val response =
            ApiResponse.Error(
                status = HttpStatus.BAD_REQUEST.value(),
                message = "Missing required parameter: ${e.parameterName}",
            )
        return response.buildResponse()
    }

    @ExceptionHandler(CsvHandlingException::class)
    fun handleCsvErrors(e: CsvHandlingException): ResponseEntity<ApiResponse<Nothing>> {
        val response =
            ApiResponse.Error(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = e.message ?: "An error occurred during csv processing",
            )
        return response.buildResponse()
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(e: Exception): ResponseEntity<ApiResponse<Nothing>> {
        e.printStackTrace()
        val response =
            ApiResponse.Error(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "${e.javaClass.canonicalName}: ${e.message}",
            )

        return response.buildResponse()
    }
}
