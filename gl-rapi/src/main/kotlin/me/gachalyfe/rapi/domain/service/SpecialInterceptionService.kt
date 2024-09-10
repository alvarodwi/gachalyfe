package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.domain.model.SpecialInterception

interface SpecialInterceptionService {
    fun createAttempt(model: SpecialInterception): SpecialInterception

    fun getAttempts(): List<SpecialInterception>

    fun getAttempt(id: Long): SpecialInterception

    fun updateAttempt(
        id: Long,
        model: SpecialInterception,
    ): SpecialInterception

    fun deleteAttempt(id: Long): Boolean
}
