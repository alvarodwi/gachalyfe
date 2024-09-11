import me.gachalyfe.rapi.controller.dto.ManufacturerEquipmentDTO
import me.gachalyfe.rapi.controller.dto.SpecialInterceptionDTO
import me.gachalyfe.rapi.data.entity.SpecialInterceptionEntity
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.domain.model.SpecialInterception
import me.gachalyfe.rapi.service.csv.schema.SpecialInterceptionCsvSchema
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun SpecialInterceptionEntity.toModel() =
    SpecialInterception(
        id = id,
        date = date,
        bossName = bossName,
        t9Equipment = t9Equipment,
        modules = modules,
        t9ManufacturerEquipment = t9ManufacturerEquipment,
        empty = empty,
    )

fun SpecialInterceptionDTO.toModel() =
    SpecialInterception(
        id = id ?: 0,
        date = date,
        bossName = bossName,
        t9Equipment = t9Equipment,
        modules = modules,
        t9ManufacturerEquipment = t9ManufacturerEquipment,
        empty = empty,
        equipments = equipments.map(ManufacturerEquipmentDTO::toModel),
    )

fun SpecialInterception.toEntity() =
    SpecialInterceptionEntity(
        id = id,
        date = date,
        bossName = bossName,
        t9Equipment = t9Equipment,
        modules = modules,
        t9ManufacturerEquipment = t9ManufacturerEquipment,
        empty = empty,
    )

fun SpecialInterceptionCsvSchema.toModel() =
    SpecialInterception(
        id = 0,
        date = date.format(DateTimeFormatter.ISO_DATE),
        bossName = bossName,
        t9Equipment = t9Equipment,
        modules = modules,
        t9ManufacturerEquipment = t9ManufacturerEquipment,
        empty = empty,
        equipments = emptyList(),
    )

fun SpecialInterception.toCsvSchema() =
    SpecialInterceptionCsvSchema(
        date = LocalDate.parse(date),
        bossName = bossName,
        t9Equipment = t9Equipment,
        modules = modules,
        t9ManufacturerEquipment = t9ManufacturerEquipment,
        empty = empty,
    )
