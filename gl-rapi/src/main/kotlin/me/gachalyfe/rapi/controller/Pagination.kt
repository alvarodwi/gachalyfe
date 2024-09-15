package me.gachalyfe.rapi.controller

import org.springframework.data.domain.Page

data class Pagination<T>(
    val content: List<T>,
    val page: Int,
    val totalPages: Int,
    val totalItems: Long,
)

fun <T> Page<T>.toPagination() =
    Pagination(
        content = content,
        page = number,
        totalPages = totalPages,
        totalItems = totalElements,
    )
