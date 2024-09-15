package me.gachalyfe.rapi.service

import me.gachalyfe.rapi.data.mapper.toEntity
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.data.repository.BannerGachaRepository
import me.gachalyfe.rapi.domain.model.BannerGacha
import me.gachalyfe.rapi.domain.service.BannerGachaService
import me.gachalyfe.rapi.domain.service.NikkeService
import me.gachalyfe.rapi.utils.equalsIgnoreOrder
import me.gachalyfe.rapi.utils.exception.ResourceNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BannerGachaServiceImpl(
    private val repository: BannerGachaRepository,
    private val nikkeService: NikkeService,
) : BannerGachaService {
    override fun findAll(): List<BannerGacha> {
        val data = repository.findAll()
        return data.map { it.toModel() }
    }

    override fun findLatest(): List<BannerGacha> {
        val data = repository.findAllByLatest()
        return data.map { it.toModel() }
    }

    override fun findAllByBannerName(bannerName: String): List<BannerGacha> {
        val data =
            when (bannerName) {
                "Social" -> repository.findAllByPickUpNameSocial()
                "Regular" -> repository.findAllByPickUpNameRegular()
                "Limited" -> repository.findAllByPickUpNameLimited()
                else -> repository.findAllByPickUpName(bannerName)
            }
        return data.map { it.toModel() }
    }

    override fun findById(id: Long): BannerGacha {
        val data = repository.findByIdOrNull(id) ?: throw ResourceNotFoundException("There's no such banner gacha pull with id=$id")
        return data.toModel()
    }

    override fun save(model: BannerGacha): BannerGacha {
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
        model: BannerGacha,
    ): BannerGacha {
        val data = repository.findByIdOrNull(id) ?: throw ResourceNotFoundException("There's no such banner gacha pull with id=$id")
        val update =
            BannerGacha(
                id = data.id,
                date = model.date,
                pickUpName = model.pickUpName,
                gemsUsed = model.gemsUsed,
                ticketUsed = model.ticketUsed,
                totalAttempt = model.totalAttempt,
                totalSSR = model.totalSSR,
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

    override fun saveAll(data: List<BannerGacha>): Int {
        val saved = data.map { save(it) }
        return saved.size
    }

    override fun delete(id: Long): Boolean {
        val data = repository.findByIdOrNull(id) ?: throw ResourceNotFoundException("There's no such banner gacha pull with id=$id")
        repository.deleteById(data.id)
        return true
    }
}
