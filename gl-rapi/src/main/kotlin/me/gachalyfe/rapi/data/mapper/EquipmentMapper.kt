package me.gachalyfe.rapi.data.mapper

import me.gachalyfe.rapi.controller.dto.ManufacturerArmsDTO
import me.gachalyfe.rapi.controller.dto.ManufacturerEquipmentDTO
import me.gachalyfe.rapi.data.entity.ManufacturerEquipmentEntity
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.utils.csv.schema.ManufacturerEquipmentCsvSchema
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun ManufacturerEquipmentEntity.toModel() =
    ManufacturerEquipment(
        id = id,
        date = date,
        manufacturer = manufacturer,
        classType = classType,
        slotType = slotType,
        sourceId = sourceId,
        sourceType = EquipmentSourceType.entries[this.sourceType],
    )

fun ManufacturerEquipmentDTO.toModel() =
    ManufacturerEquipment(
        id = id ?: 0,
        date = date ?: "",
        manufacturer = manufacturer,
        classType = classType,
        slotType = slotType,
        sourceId = sourceId ?: 0,
        sourceType = EquipmentSourceType.entries[sourceType ?: 0],
    )

fun ManufacturerArmsDTO.toModel() =
    equipments.map { e ->
        ManufacturerEquipment(
            id = 0,
            date = date,
            manufacturer = e.manufacturer,
            classType = e.classType,
            slotType = e.slotType,
            sourceId = 0,
            sourceType = EquipmentSourceType.ARMS,
        )
    }

fun ManufacturerEquipment.toEntity() =
    ManufacturerEquipmentEntity(
        id = id,
        date = date,
        manufacturer = manufacturer,
        classType = classType,
        slotType = slotType,
        sourceId = sourceId,
        sourceType = sourceType.ordinal,
    )

fun ManufacturerEquipment.toCsvSchema() =
    ManufacturerEquipmentCsvSchema(
        date = LocalDate.parse(date),
        sourceType = sourceType.name,
        manufacturer = manufacturer,
        classType = classType,
        slotType = slotType,
    )

fun ManufacturerEquipmentCsvSchema.toModel() =
    ManufacturerEquipment(
        id = 0,
        date = date.format(DateTimeFormatter.ISO_DATE),
        manufacturer = manufacturer,
        classType = classType,
        slotType = slotType,
        sourceId = 0,
        sourceType = EquipmentSourceType.valueOf(sourceType),
    )
