package me.gachalyfe.rapi.data.mapper

import me.gachalyfe.rapi.controller.dto.ManufacturerEquipmentDTO
import me.gachalyfe.rapi.data.entity.ManufacturerEquipmentEntity
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment

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
        sourceType = EquipmentSourceType.entries[this.sourceType ?: 0],
    )

fun ManufacturerEquipment.toEntity() =
    ManufacturerEquipmentEntity(
        id = id,
        date = date,
        manufacturer = manufacturer,
        classType = classType,
        slotType = slotType,
        sourceId = sourceId,
        sourceType = sourceType.ordinal
    )
