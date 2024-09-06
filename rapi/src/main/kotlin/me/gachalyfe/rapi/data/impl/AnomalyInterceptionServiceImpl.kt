package me.gachalyfe.rapi.data.impl

import me.gachalyfe.rapi.data.repository.AnomalyInterceptionRepository
import me.gachalyfe.rapi.domain.dto.AnomalyInterceptionDTO
import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.domain.service.AnomalyInterceptionService
import org.springframework.stereotype.Service

@Service
class AnomalyInterceptionServiceImpl(
    private val repository: AnomalyInterceptionRepository
) : AnomalyInterceptionService {
    override fun newAttempt(dto: AnomalyInterceptionDTO): AnomalyInterception {
        TODO("Not yet implemented")
    }

    override fun getAttempts(): List<AnomalyInterception> {
        TODO("Not yet implemented")
    }

    override fun updateAttempt(id: Long, dto: AnomalyInterceptionDTO): AnomalyInterception {
        TODO()
    }

    override fun deleteAttempt(id: Long): Boolean {
        TODO("Not yet implemented")
    }
}
