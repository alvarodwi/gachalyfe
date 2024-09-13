package me.gachalyfe.rapi.service.csv.handler

import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.domain.service.csv.CsvHandler
import me.gachalyfe.rapi.domain.service.interception.AnomalyInterceptionService
import org.springframework.stereotype.Component

@Component
class AnomalyInterceptionCsvHandler(
    val service: AnomalyInterceptionService,
) : CsvHandler<AnomalyInterception> {
    override fun import(data: List<AnomalyInterception>): Int = service.saveAll(data)

    override fun export(): List<AnomalyInterception> = service.findAll()
}
