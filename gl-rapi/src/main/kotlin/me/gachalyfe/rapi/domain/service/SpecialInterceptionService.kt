package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.domain.model.SpecialInterception
import me.gachalyfe.rapi.domain.model.stats.SpecialInterceptionStats
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface SpecialInterceptionService {
    fun findAll(pageable: Pageable): Page<SpecialInterception>

    fun findAll(sort: Sort): List<SpecialInterception>

    fun findByDateAndEquipmentDropped(date: String): SpecialInterception?

    fun findById(id: Long): SpecialInterception

    fun save(model: SpecialInterception): SpecialInterception

    fun saveAll(data: List<SpecialInterception>): Int

    fun update(
        id: Long,
        model: SpecialInterception,
    ): SpecialInterception

    fun delete(id: Long): Boolean

    fun generateStats(bossName: String): SpecialInterceptionStats
}
