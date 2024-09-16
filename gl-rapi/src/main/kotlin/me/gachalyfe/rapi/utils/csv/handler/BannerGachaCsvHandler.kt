package me.gachalyfe.rapi.utils.csv.handler

import me.gachalyfe.rapi.domain.model.BannerGacha
import me.gachalyfe.rapi.domain.service.BannerGachaService
import me.gachalyfe.rapi.utils.csv.CsvHandler
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class BannerGachaCsvHandler(
    private val service: BannerGachaService,
) : CsvHandler<BannerGacha> {
    override fun import(data: List<BannerGacha>): Int = service.saveAll(data)

    override fun export(): List<BannerGacha> {
        val sort = Sort.by("date").and(Sort.by("pickUpName").descending())
        return service.findAll(sort)
    }
}
