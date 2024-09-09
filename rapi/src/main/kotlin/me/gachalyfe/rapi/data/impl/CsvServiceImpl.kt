package me.gachalyfe.rapi.data.impl

import jakarta.transaction.Transactional
import me.gachalyfe.rapi.controller.exception.CsvHandlingException
import me.gachalyfe.rapi.data.entity.AnomalyInterceptionEntity
import me.gachalyfe.rapi.data.entity.ManufacturerEquipmentEntity
import me.gachalyfe.rapi.data.entity.SpecialInterceptionEntity
import me.gachalyfe.rapi.data.repository.AnomalyInterceptionRepository
import me.gachalyfe.rapi.data.repository.ManufacturerEquipmentRepository
import me.gachalyfe.rapi.data.repository.SpecialInterceptionRepository
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.service.CsvService
import me.gachalyfe.rapi.utils.lazyLogger
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.reflect.full.memberProperties

@Service
class CsvServiceImpl(
    private val repo1: AnomalyInterceptionRepository,
    private val repo2: SpecialInterceptionRepository,
    private val repo3: ManufacturerEquipmentRepository,
) : CsvService {
    private val log by lazyLogger()

    private val csvDateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy")
    private val systemDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @Transactional
    override fun importFile(file: MultipartFile, target: String): Boolean {
        val reader = file.inputStream.bufferedReader()
        reader.readLine() //read the header line first
        return when (target) {
            "anomaly" -> readAnomalyInterceptionData(reader)
            "special" -> readSpecialInterceptionData(reader)
            "equipment" -> readManufacturerEquipmentData(reader)
            else -> throw CsvHandlingException("CSV import target unknown")
        }
    }

    override fun exportFile(target: String): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val bufferedWriter = outputStream.bufferedWriter()
        when (target) {
            "anomaly" -> writeCsvData(
                repo1.findAllByOrderByDateAsc(),
                bufferedWriter,
                listOf("id", "date", "bossName", "stage", "dropType", "dropped", "modules")
            )

            "special" -> writeCsvData(
                repo2.findAllByOrderByDateAsc(),
                bufferedWriter,
                listOf("id", "date", "bossName", "t9Equipment", "t9ManufacturerEquipment", "modules", "empty")
            )

            "equipment" -> writeCsvData(
                repo3.findAllByOrderByDateAsc(),
                bufferedWriter,
                listOf("id", "date", "source", "manufacturer", "classType", "slotType")
            )

            else -> throw CsvHandlingException("CSV export target unknown")
        }
        return outputStream.toByteArray()
    }

    private inline fun <reified T : Any> writeCsvData(
        data: List<T>,
        writer: BufferedWriter,
        propertyOrder: List<String>
    ) {
        if (data.isEmpty()) throw CsvHandlingException("Target data is empty, nothing to export")
        writer.write(propertyOrder.joinToString(","))
        writer.newLine()
        data.forEach { item ->
            val values = propertyOrder.map { name ->
                T::class.memberProperties.first { it.name == name }
                    .get(item)
                    .toString()
            }
            writer.write(values.joinToString(","))
            writer.newLine()
        }
        writer.flush()
    }


    private fun readAnomalyInterceptionData(reader: BufferedReader): Boolean {
        val data: List<AnomalyInterceptionEntity> = reader.lineSequence()
            .filter { it.isNotBlank() }
            .map {
                val line = it.split(",", ignoreCase = false, limit = 6)
                AnomalyInterceptionEntity(
                    date = LocalDate.parse(line[0], csvDateFormatter).format(systemDateFormatter),
                    bossName = line[1],
                    stage = line[2].toInt(),
                    dropType = line[3],
                    dropped = line[4] == "Yes",
                    modules = line[5].toInt()
                )
            }.toList()
        log.info("imported ${data.size} anomaly interception entries")
        repo1.saveAll(data)
        return true
    }

    private fun readSpecialInterceptionData(reader: BufferedReader): Boolean {
        val data: List<SpecialInterceptionEntity> = reader.lineSequence()
            .filter { it.isNotBlank() }
            .map {
                val line = it.split(",", ignoreCase = false, limit = 6)
                SpecialInterceptionEntity(
                    date = LocalDate.parse(line[0], csvDateFormatter).format(systemDateFormatter),
                    bossName = line[1],
                    t9Equipment = line[2].toInt(),
                    t9ManufacturerEquipment = line[3].toInt(),
                    modules = line[4].toInt(),
                    empty = line[5].toInt()
                )
            }.toList()
        log.info("imported ${data.size} special interception entries")
        repo2.saveAll(data)
        return true
    }

    private fun readManufacturerEquipmentData(reader: BufferedReader): Boolean {
        val data: List<ManufacturerEquipmentEntity> = reader.lineSequence()
            .filter { it.isNotBlank() }
            .map {
                val line = it.split(",", ignoreCase = false, limit = 5)

                ManufacturerEquipmentEntity(
                    date = LocalDate.parse(line[0], csvDateFormatter).format(systemDateFormatter),
                    manufacturer = line[2],
                    classType = line[3],
                    slotType = line[4],
                    sourceId = -1, //
                    sourceType = assignSourceTypeFromString(line[1]).code,
                )
            }.toList()

        // process sourceId
        // first for the anomaly drops
        val aiDrops = processAnomalyDrops(data)
        val siDrops = processSpecialInterceptionDrops(data)
        val armsDrops = data.filter { it.sourceType == EquipmentSourceType.ARMS.code }
        val furnaceDrops = data.filter { it.sourceType == EquipmentSourceType.FURNACE.code }
        log.info("imported ${aiDrops.size} equipment from anomaly interceptions")
        log.info("imported ${siDrops.size} equipment from special interceptions")
        log.info("imported ${armsDrops.size} equipment from opening manufacturer arms")
        log.info("imported ${furnaceDrops.size} equipment from opening manufacturer arms")
        repo3.saveAll(aiDrops)
        repo3.saveAll(siDrops)
        repo3.saveAll(armsDrops)
        repo3.saveAll(furnaceDrops)

        return true
    }

    private fun assignSourceTypeFromString(source: String) =
        when (source.lowercase()) {
            "special" -> EquipmentSourceType.SI_DROPS
            "anomaly" -> EquipmentSourceType.AI_DROPS
            "furnace" -> EquipmentSourceType.FURNACE
            else -> EquipmentSourceType.ARMS
        }

    private fun processAnomalyDrops(data: List<ManufacturerEquipmentEntity>): List<ManufacturerEquipmentEntity> {
        var dropsFromSameDateCount = 0
        var tempDate = ""
        var sourceIds = emptyList<Long>()
        return data.filter { it.sourceType == EquipmentSourceType.AI_DROPS.code }
            .map { equipment ->
                if (equipment.date == tempDate) {
                    dropsFromSameDateCount += 1
                } else {
                    dropsFromSameDateCount = 0
                }
                tempDate = equipment.date
                if (dropsFromSameDateCount == 0) {
                    sourceIds = repo1.findIdsByDateAndEquipmentDrops(equipment.date)
                    if (sourceIds.isEmpty()) {
                        throw CsvHandlingException("There are invalid entry on the equipment with date=$tempDate, no anomaly interceptions entry saved in that date")
                    }
                } else {
                    if (sourceIds.size <= dropsFromSameDateCount)
                        throw CsvHandlingException("There are invalid entry on the equipment with date=$tempDate")
                }
                equipment.copy(sourceId = sourceIds[dropsFromSameDateCount])
            }.toList()
    }

    private fun processSpecialInterceptionDrops(data: List<ManufacturerEquipmentEntity>): List<ManufacturerEquipmentEntity> {
        var dropsFromSameDateCount = 0
        var tempDate = ""
        var dropOnThatDate = 0
        var sourceId = 0L
        return data.filter { it.sourceType == EquipmentSourceType.SI_DROPS.code }
            .map { equipment ->
                if (equipment.date == tempDate) {
                    dropsFromSameDateCount += 1
                } else {
                    dropsFromSameDateCount = 0
                }
                tempDate = equipment.date
                if (dropsFromSameDateCount == 0) {
                    val entryOnThatDate = repo2.findByDateAndEquipmentDrops(tempDate)
                    entryOnThatDate.ifPresent {
                        sourceId = it.id
                        dropOnThatDate = it.t9ManufacturerEquipment
                    }
                    if (entryOnThatDate.isEmpty) {
                        throw CsvHandlingException("There are invalid entry on the equipment with date=$tempDate, no special interceptions with equipment drop is saved in that date")
                    }

                } else {
                    if (dropOnThatDate <= dropsFromSameDateCount)
                        throw CsvHandlingException("There are invalid entry on the equipment with date=$tempDate, special interceptions on that date only dropped $dropOnThatDate times")
                }
                equipment.copy(sourceId = sourceId)
            }.toList()
    }
}
