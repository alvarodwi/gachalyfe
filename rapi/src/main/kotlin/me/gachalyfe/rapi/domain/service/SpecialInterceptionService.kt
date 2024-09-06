package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.domain.dto.SpecialInterceptionDTO
import me.gachalyfe.rapi.domain.model.SpecialInterception

interface SpecialInterceptionService {
    fun addNewAttempt(dto: SpecialInterceptionDTO) : SpecialInterception
    fun getAttempts() : List<SpecialInterception>
    fun updateAttempt(id: Long, dto: SpecialInterceptionDTO) : SpecialInterception
    fun deleteAttempt(id: Long) : Boolean
}
