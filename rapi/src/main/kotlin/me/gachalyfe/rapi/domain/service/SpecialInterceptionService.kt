package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.data.dto.SpecialInterceptionDTO
import me.gachalyfe.rapi.domain.model.SpecialInterception

interface SpecialInterceptionService {
    fun createAttempt(dto: SpecialInterceptionDTO): SpecialInterception

    fun getAttempts(): List<SpecialInterception>

    fun getAttempt(id: Long): SpecialInterception

    fun updateAttempt(
        id: Long,
        dto: SpecialInterceptionDTO,
    ): SpecialInterception

    fun deleteAttempt(id: Long): Boolean
}
