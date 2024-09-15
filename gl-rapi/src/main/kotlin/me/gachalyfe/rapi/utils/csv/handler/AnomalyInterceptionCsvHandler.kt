package me.gachalyfe.rapi.utils.csv.handler

import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.domain.service.AnomalyInterceptionService
import me.gachalyfe.rapi.utils.csv.CsvHandler
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class AnomalyInterceptionCsvHandler(
    val service: AnomalyInterceptionService,
) : CsvHandler<AnomalyInterception> {
    override fun import(data: List<AnomalyInterception>): Int = service.saveAll(data)

    override fun export(): List<AnomalyInterception> {
        val sort = Sort.by("date").and(Sort.by("id"))
        return service.findAll(sort)
    }
}
