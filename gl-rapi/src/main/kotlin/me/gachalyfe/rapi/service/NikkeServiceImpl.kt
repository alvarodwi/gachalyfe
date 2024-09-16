package me.gachalyfe.rapi.service

import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.data.repository.NikkeRepository
import me.gachalyfe.rapi.data.spec.NikkeSpecs
import me.gachalyfe.rapi.domain.model.Nikke
import me.gachalyfe.rapi.domain.service.NikkeService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class NikkeServiceImpl(
    private val repository: NikkeRepository,
) : NikkeService {
    override fun findAll(pageable: Pageable): Page<Nikke> {
        val data = repository.findAll(pageable)
        return data.map { it.toModel() }
    }

    override fun findAll(): List<Nikke> {
        val data = repository.findAll()
        return data.map { it.toModel() }
    }

    override fun findByName(name: String): Nikke? {
        val specs = NikkeSpecs.hasName(name)
        val data = repository.findAll(specs).firstOrNull()
        return data?.toModel()
    }

    override fun findByNameLike(
        name: String,
        pageable: Pageable,
    ): Page<Nikke> {
        val specs = NikkeSpecs.nameContains(name)
        val data = repository.findAll(specs, pageable)
        return data.map { it.toModel() }
    }

    override fun findByIdIn(ids: List<Long>): List<Nikke> {
        val specs = NikkeSpecs.idIsIn(ids)
        val data = repository.findAll(specs)
        return data.map { it.toModel() }
    }
}
