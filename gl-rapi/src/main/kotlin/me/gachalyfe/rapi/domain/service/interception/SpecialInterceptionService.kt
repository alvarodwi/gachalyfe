package me.gachalyfe.rapi.domain.service.interception

import me.gachalyfe.rapi.domain.model.SpecialInterception

interface SpecialInterceptionService {
    fun findAll(): List<SpecialInterception>

    fun findAllByLatest(): List<SpecialInterception>

    fun findByDateAndEquipmentDropped(date: String): SpecialInterception?

    fun findById(id: Long): SpecialInterception

    fun save(model: SpecialInterception): SpecialInterception

    fun saveAll(data: List<SpecialInterception>): Int

    fun updateAttempt(
        id: Long,
        model: SpecialInterception,
    ): SpecialInterception

    fun deleteAttempt(id: Long): Boolean
}
