package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.domain.model.Nikke
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface NikkeService {
    fun findAll(pageable: Pageable): Page<Nikke>

    fun findAll(): List<Nikke>

    fun findByNameLike(
        name: String,
        pageable: Pageable,
    ): Page<Nikke>

    fun findByName(name: String): Nikke?

    fun findByIdIn(ids: List<Long>): List<Nikke>
}
