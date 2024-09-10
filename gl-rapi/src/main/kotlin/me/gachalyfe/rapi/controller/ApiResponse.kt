package me.gachalyfe.rapi.controller

import me.gachalyfe.rapi.controller.ApiResponse.Error
import me.gachalyfe.rapi.controller.ApiResponse.Success
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

sealed class ApiResponse<out T> {
    data class Success<out T>(
        val status: Int,
        val message: String,
        val data: T,
    ) : ApiResponse<T>()

    data class Error(
        val status: Int,
        val message: String,
    ) : ApiResponse<Nothing>()
}

fun <T> ApiResponse<T>.buildResponse() =
    when (this) {
        is Success -> ResponseEntity(this as ApiResponse<T>, HttpStatus.valueOf(this.status))
        is Error -> ResponseEntity(this as ApiResponse<T>, HttpStatus.valueOf(this.status))
    }
