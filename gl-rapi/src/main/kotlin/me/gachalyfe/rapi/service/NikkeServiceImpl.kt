package me.gachalyfe.rapi.service

import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.data.repository.NikkeRepository
import me.gachalyfe.rapi.domain.model.Nikke
import me.gachalyfe.rapi.domain.service.NikkeService
import org.springframework.stereotype.Service

@Service
class NikkeServiceImpl(
    private val repository: NikkeRepository,
) : NikkeService {
    override fun findAll(): List<Nikke> {
        val data = repository.findAll()
        return data.map { it.toModel() }
    }

    override fun findAllByName(
        name: String,
        strict: Boolean,
    ): List<Nikke> {
        val data = if (strict) repository.findByName(name) else repository.findByNameContaining(name)
        return data.map { it.toModel() }
    }

    override fun findByIdIn(ids: List<Long>): List<Nikke> {
        val data = repository.findByIdIn(ids)
        return data.map { it.toModel() }
    }
}
