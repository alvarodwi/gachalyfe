package me.gachalyfe.rapi.service.csv.handler

import me.gachalyfe.rapi.domain.model.SpecialInterception
import me.gachalyfe.rapi.domain.service.csv.CsvHandler
import me.gachalyfe.rapi.domain.service.interception.SpecialInterceptionService
import org.springframework.stereotype.Component

@Component
class SpecialInterceptionCsvHandler(
    private val service: SpecialInterceptionService,
) : CsvHandler<SpecialInterception> {
    override fun import(data: List<SpecialInterception>): Int = service.saveAll(data)

    override fun export(): List<SpecialInterception> = service.findAll()
}
