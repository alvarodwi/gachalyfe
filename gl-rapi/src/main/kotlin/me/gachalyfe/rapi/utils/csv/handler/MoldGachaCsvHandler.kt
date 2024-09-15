package me.gachalyfe.rapi.utils.csv.handler

import me.gachalyfe.rapi.domain.model.MoldGacha
import me.gachalyfe.rapi.domain.service.MoldGachaService
import me.gachalyfe.rapi.utils.csv.CsvHandler
import org.springframework.stereotype.Component

@Component
class MoldGachaCsvHandler(
    private val service: MoldGachaService,
) : CsvHandler<MoldGacha> {
    override fun import(data: List<MoldGacha>): Int = service.saveAll(data)

    override fun export(): List<MoldGacha> = service.findAll()
}
