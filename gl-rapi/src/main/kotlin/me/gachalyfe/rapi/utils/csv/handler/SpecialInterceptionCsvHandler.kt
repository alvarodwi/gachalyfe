package me.gachalyfe.rapi.utils.csv.handler

import me.gachalyfe.rapi.domain.model.SpecialInterception
import me.gachalyfe.rapi.domain.service.SpecialInterceptionService
import me.gachalyfe.rapi.utils.csv.CsvHandler
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class SpecialInterceptionCsvHandler(
    private val service: SpecialInterceptionService,
) : CsvHandler<SpecialInterception> {
    override fun import(data: List<SpecialInterception>): Int = service.saveAll(data)

    override fun export(): List<SpecialInterception> {
        val sort = Sort.by("date").and(Sort.by("id"))
        return service.findAll(sort)
    }
}
