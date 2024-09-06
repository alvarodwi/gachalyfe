package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.domain.dto.AnomalyInterceptionDTO
import me.gachalyfe.rapi.domain.model.AnomalyInterception

interface AnomalyInterceptionService {
    fun newAttempt(dto: AnomalyInterceptionDTO): AnomalyInterception
    fun getAttempts(): List<AnomalyInterception>
    fun updateAttempt(id: Long, dto: AnomalyInterceptionDTO) : AnomalyInterception
    fun deleteAttempt(id: Long) : Boolean
}
