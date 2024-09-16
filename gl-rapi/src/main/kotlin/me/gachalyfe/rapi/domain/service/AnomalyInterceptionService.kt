package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.domain.model.AnomalyInterception
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface AnomalyInterceptionService {
    fun findAll(pageable: Pageable): Page<AnomalyInterception>

    fun findAll(sort: Sort): List<AnomalyInterception>

    fun findById(id: Long): AnomalyInterception

    fun findIdsByDateAndEquipmentDropped(date: String): List<Long>

    fun save(model: AnomalyInterception): AnomalyInterception

    fun saveAll(data: List<AnomalyInterception>): Int

    fun update(
        id: Long,
        model: AnomalyInterception,
    ): AnomalyInterception

    fun update(id: Long): Boolean
}
