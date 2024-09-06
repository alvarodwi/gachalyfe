package me.gachalyfe.rapi.data

import me.gachalyfe.rapi.data.entity.AnomalyInterceptionEntity
import me.gachalyfe.rapi.data.entity.ManufacturerEquipmentEntity
import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment

object DataMapper {
    fun AnomalyInterceptionEntity.asModel() =
        AnomalyInterception(
            id = id,
            date = date,
            bossName = bossName,
            stage = stage,
            dropType = dropType,
            dropped = dropped,
            modules = modules,
        )

    fun ManufacturerEquipmentEntity.asModel() =
        ManufacturerEquipment(
            id = id,
            manufacturer = manufacturer,
            classType = classType,
            slotType = slotType,
            sourceId = sourceId,
            sourceType = EquipmentSourceType.from(sourceType),
        )
}
