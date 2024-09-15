package me.gachalyfe.rapi.service

import me.gachalyfe.rapi.data.mapper.toEntity
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.data.repository.MoldGachaRepository
import me.gachalyfe.rapi.domain.model.MoldGacha
import me.gachalyfe.rapi.domain.model.MoldType
import me.gachalyfe.rapi.domain.service.MoldGachaService
import me.gachalyfe.rapi.domain.service.NikkeService
import me.gachalyfe.rapi.utils.equalsIgnoreOrder
import me.gachalyfe.rapi.utils.exception.ResourceNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MoldGachaServiceImpl(
    private val repository: MoldGachaRepository,
    private val nikkeService: NikkeService,
) : MoldGachaService {
    override fun findAll(): List<MoldGacha> {
        val data = repository.findAll()
        return data.map { it.toModel() }
    }

    override fun findAllByLatest(): List<MoldGacha> {
        val data = repository.findLatest()
        return data.map { it.toModel() }
    }

    override fun findAllByMoldType(moldType: MoldType): List<MoldGacha> {
        val data = repository.findAllByMoldType(moldType.ordinal)
        return data.map { it.toModel() }
    }

    override fun findById(id: Long): MoldGacha {
        val data = repository.findByIdOrNull(id) ?: throw ResourceNotFoundException("There's no such banner gacha pull with id=$id")
        return data.toModel()
    }

    override fun save(model: MoldGacha): MoldGacha {
        if (model.nikkePulled.isNotEmpty()) {
            val nikkeData = nikkeService.findByIdIn(model.nikkePulled.map { it.id })
            val data = model.copy(nikkePulled = nikkeData)
            val saved = repository.save(data.toEntity())
            return saved.toModel()
        }
        val saved = repository.save(model.toEntity())
        return saved.toModel()
    }

    override fun update(
        id: Long,
        model: MoldGacha,
    ): MoldGacha {
        val data = repository.findByIdOrNull(id) ?: throw ResourceNotFoundException("There's no such banner gacha pull with id=$id")
        val update =
            MoldGacha(
                id = data.id,
                date = model.date,
                type = model.type,
                amount = model.amount,
                totalSSR = model.totalSSR,
                nikkePulled = model.nikkePulled,
            )

        val existingPulledNikkeIds = data.nikkePulled.map { it.id }
        val newPulledNikkeIds = model.nikkePulled.map { it.id }
        if (existingPulledNikkeIds.equalsIgnoreOrder(newPulledNikkeIds)) {
            val nikkeData = nikkeService.findByIdIn(model.nikkePulled.map { it.id })
            val saved = repository.save(update.copy(nikkePulled = nikkeData).toEntity())
            return saved.toModel()
        }

        repository.save(update.toEntity())
        return update
    }

    override fun saveAll(data: List<MoldGacha>): Int {
        val saved = data.map { save(it) }
        return saved.size
    }

    override fun delete(id: Long): Boolean {
        val data = repository.findByIdOrNull(id) ?: throw ResourceNotFoundException("There's no such banner gacha pull with id=$id")
        repository.deleteById(data.id)
        return true
    }
}
