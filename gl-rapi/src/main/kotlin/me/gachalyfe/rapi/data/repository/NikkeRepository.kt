package me.gachalyfe.rapi.data.repository

import me.gachalyfe.rapi.data.entity.NikkeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface NikkeRepository : JpaRepository<NikkeEntity, Long> {
    fun findByNameContaining(name: String): List<NikkeEntity>

    fun findByIdIn(ids: List<Long>): List<NikkeEntity>
}
