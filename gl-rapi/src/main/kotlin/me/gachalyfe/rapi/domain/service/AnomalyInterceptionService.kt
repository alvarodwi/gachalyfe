package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.domain.model.AnomalyInterception

interface AnomalyInterceptionService {
    fun createAttempt(model: AnomalyInterception): AnomalyInterception

    fun getAttempts(): List<AnomalyInterception>

    fun getAttempt(id: Long): AnomalyInterception

    fun updateAttempt(
        id: Long,
        model: AnomalyInterception,
    ): AnomalyInterception

    fun deleteAttempt(id: Long): Boolean
}
