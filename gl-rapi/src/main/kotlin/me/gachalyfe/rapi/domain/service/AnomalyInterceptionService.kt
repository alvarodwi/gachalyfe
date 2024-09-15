package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.domain.model.AnomalyInterception

interface AnomalyInterceptionService {
    fun findAll(): List<AnomalyInterception>

    fun findAllByLatest(): List<AnomalyInterception>

    fun findIdsByDateAndEquipmentDropped(date: String): List<Long>

    fun findById(id: Long): AnomalyInterception

    fun save(model: AnomalyInterception): AnomalyInterception

    fun saveAll(data: List<AnomalyInterception>): Int

    fun delete(
        id: Long,
        model: AnomalyInterception,
    ): AnomalyInterception

    fun delete(id: Long): Boolean
}
