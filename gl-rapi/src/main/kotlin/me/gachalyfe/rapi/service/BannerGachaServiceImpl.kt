package me.gachalyfe.rapi.service

import me.gachalyfe.rapi.data.mapper.toEntity
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.data.repository.BannerGachaRepository
import me.gachalyfe.rapi.data.spec.BannerGachaSpecs
import me.gachalyfe.rapi.domain.model.BannerGacha
import me.gachalyfe.rapi.domain.model.stats.BannerGachaStats
import me.gachalyfe.rapi.domain.service.BannerGachaService
import me.gachalyfe.rapi.domain.service.NikkeService
import me.gachalyfe.rapi.utils.equalsIgnoreOrder
import me.gachalyfe.rapi.utils.exception.ResourceNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BannerGachaServiceImpl(
    private val repository: BannerGachaRepository,
    private val nikkeService: NikkeService,
) : BannerGachaService {
    override fun findAll(pageable: Pageable): Page<BannerGacha> {
        val data = repository.findAll(pageable)
        return data.map { it.toModel() }
    }

    override fun findAll(sort: Sort): List<BannerGacha> {
        val data = repository.findAll(sort)
        return data.map { it.toModel() }
    }

    override fun findByBannerName(
        bannerName: String,
        pageable: Pageable,
    ): Page<BannerGacha> {
        val specs =
            when (bannerName.lowercase()) {
                "social" -> BannerGachaSpecs.isSocialPulls()
                "regular" -> BannerGachaSpecs.isRegularPulls()
                "event" -> BannerGachaSpecs.isEventPulls()
                else -> throw IllegalStateException("Unknown banner name")
            }
        val data = repository.findAll(specs, pageable)
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

    override fun generateStats(): List<BannerGachaStats> {
        val data = repository.findAll()
        val groupedData = data.groupBy { it.pickUpName }

        val socialPulls = groupedData["Social"].orEmpty()
        val regularPulls = groupedData["Regular"].orEmpty()
        val eventPulls = groupedData.filterKeys { it !in listOf("Social", "Regular") }.values.flatten()

        return listOf(
            BannerGachaStats(
                category = "Social",
                totalGemsUsed = 0,
                totalTicketUsed = 0,
                totalAttempts = socialPulls.sumOf { it.totalAttempt },
                totalSSR = socialPulls.sumOf { it.totalSSR },
            ),
            BannerGachaStats(
                category = "Regular",
                totalGemsUsed = regularPulls.sumOf { it.gemsUsed },
                totalTicketUsed = regularPulls.sumOf { it.ticketUsed },
                totalAttempts = regularPulls.sumOf { it.totalAttempt },
                totalSSR = regularPulls.sumOf { it.totalSSR },
            ),
            BannerGachaStats(
                category = "Event",
                totalGemsUsed = eventPulls.sumOf { it.gemsUsed },
                totalTicketUsed = eventPulls.sumOf { it.ticketUsed },
                totalAttempts = eventPulls.sumOf { it.totalAttempt },
                totalSSR = eventPulls.sumOf { it.totalSSR },
            ),
            BannerGachaStats(
                category = "All",
                totalGemsUsed = data.sumOf { it.gemsUsed },
                totalTicketUsed = data.sumOf { it.ticketUsed },
                totalAttempts = data.sumOf { it.totalAttempt },
                totalSSR = data.sumOf { it.totalSSR }
            )
        )
    }
}
