package me.gachalyfe.rapi.utils.csv.handler

import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.service.AnomalyInterceptionService
import me.gachalyfe.rapi.domain.service.ManufacturerEquipmentService
import me.gachalyfe.rapi.domain.service.SpecialInterceptionService
import me.gachalyfe.rapi.utils.csv.CsvHandler
import me.gachalyfe.rapi.utils.exception.CsvHandlingException
import me.gachalyfe.rapi.utils.lazyLogger
import org.springframework.stereotype.Component

@Component
class ManufacturerEquipmentCsvHandler(
    private val service: ManufacturerEquipmentService,
    private val anomalyInterceptionService: AnomalyInterceptionService,
    private val specialInterceptionService: SpecialInterceptionService,
) : CsvHandler<ManufacturerEquipment> {
    private val log by lazyLogger()

    override fun import(data: List<ManufacturerEquipment>): Int {
        // first for the anomaly drops
        val aiDrops = processAnomalyDrops(data)
        val siDrops = processSpecialInterceptionDrops(data)
        val armsDrops = data.filter { it.sourceType == EquipmentSourceType.ARMS }
        val furnaceDrops = data.filter { it.sourceType == EquipmentSourceType.FURNACE }
        log.info("imported ${aiDrops.size} equipment from anomaly interceptions")
        log.info("imported ${siDrops.size} equipment from special interceptions")
        log.info("imported ${armsDrops.size} equipment from opening manufacturer arms")
        log.info("imported ${furnaceDrops.size} equipment from opening manufacturer furnace")
        service.saveAll(aiDrops)
        service.saveAll(siDrops)
        service.saveAll(armsDrops)
        service.saveAll(furnaceDrops)
        return data.size
    }

    override fun export(): List<ManufacturerEquipment> = service.findAll()

    private fun processAnomalyDrops(data: List<ManufacturerEquipment>): List<ManufacturerEquipment> {
        var dropsFromSameDateCount = 0
        var tempDate = ""
        var sourceIds = emptyList<Long>()
        return data
            .filter { it.sourceType == EquipmentSourceType.AI_DROPS }
            .map { equipment ->
                if (equipment.date == tempDate) {
                    dropsFromSameDateCount += 1
                } else {
                    dropsFromSameDateCount = 0
                }
                tempDate = equipment.date
                if (dropsFromSameDateCount == 0) {
                    sourceIds = anomalyInterceptionService.findIdsByDateAndEquipmentDropped(equipment.date)
                    if (sourceIds.isEmpty()) {
                        throw CsvHandlingException(
                            "There are invalid entry on the equipment with date=$tempDate, no anomaly interceptions entry saved in that date",
                        )
                    }
                } else {
                    if (sourceIds.size <= dropsFromSameDateCount) {
                        throw CsvHandlingException(
                            "There are invalid entry on the equipment with date=$tempDate",
                        )
                    }
                }
                equipment.copy(sourceId = sourceIds[dropsFromSameDateCount])
            }.toList()
    }

    private fun processSpecialInterceptionDrops(data: List<ManufacturerEquipment>): List<ManufacturerEquipment> {
        var dropsFromSameDateCount = 0
        var tempDate = ""
        var dropOnThatDate = 0
        var sourceId = 0L
        return data
            .filter { it.sourceType == EquipmentSourceType.SI_DROPS }
            .map { equipment ->
                if (equipment.date == tempDate) {
                    dropsFromSameDateCount += 1
                } else {
                    dropsFromSameDateCount = 0
                }
                tempDate = equipment.date
                if (dropsFromSameDateCount == 0) {
                    val entryOnThatDate =
                        specialInterceptionService.findByDateAndEquipmentDropped(tempDate)
                            ?: throw CsvHandlingException(
                                "There are invalid entry on the equipment with date=$tempDate, no special interceptions with equipment drop is saved in that date",
                            )
                    sourceId = entryOnThatDate.id
                    dropOnThatDate = entryOnThatDate.t9ManufacturerEquipment
                } else {
                    if (dropOnThatDate <= dropsFromSameDateCount) {
                        throw CsvHandlingException(
                            "There are invalid entry on the equipment with date=$tempDate, special interceptions on that date only dropped $dropOnThatDate times",
                        )
                    }
                }
                equipment.copy(sourceId = sourceId)
            }.toList()
    }
}
