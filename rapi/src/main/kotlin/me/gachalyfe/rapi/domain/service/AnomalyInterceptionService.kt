package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.data.dto.AnomalyInterceptionDTO
import me.gachalyfe.rapi.domain.model.AnomalyInterception

interface AnomalyInterceptionService {
    fun createAttempt(dto: AnomalyInterceptionDTO): AnomalyInterception

    fun getAttempts(): List<AnomalyInterception>

    fun getAttempt(id: Long): AnomalyInterception

    fun updateAttempt(
        id: Long,
        dto: AnomalyInterceptionDTO,
    ): AnomalyInterception

    fun deleteAttempt(id: Long): Boolean
}
