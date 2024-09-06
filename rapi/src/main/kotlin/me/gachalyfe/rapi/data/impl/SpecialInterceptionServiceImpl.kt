package me.gachalyfe.rapi.data.impl

import me.gachalyfe.rapi.data.repository.SpecialInterceptionRepository
import me.gachalyfe.rapi.domain.dto.SpecialInterceptionDTO
import me.gachalyfe.rapi.domain.model.SpecialInterception
import me.gachalyfe.rapi.domain.service.SpecialInterceptionService
import org.springframework.stereotype.Service

@Service
class SpecialInterceptionServiceImpl(
    private val repository: SpecialInterceptionRepository
) : SpecialInterceptionService{
    override fun addNewAttempt(dto: SpecialInterceptionDTO): SpecialInterception {
        TODO("Not yet implemented")
    }

    override fun getAttempts(): List<SpecialInterception> {
        TODO("Not yet implemented")
    }

    override fun updateAttempt(id: Long, dto: SpecialInterceptionDTO): SpecialInterception {
        TODO("Not yet implemented")
    }

    override fun deleteAttempt(id: Long): Boolean {
        TODO("Not yet implemented")
    }

}
