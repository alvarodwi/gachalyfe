package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.domain.model.Nikke

interface NikkeService {
    fun findAll(): List<Nikke>

    fun findAllByName(
        name: String,
        strict: Boolean,
    ): List<Nikke>

    fun findByIdIn(ids: List<Long>): List<Nikke>
}
