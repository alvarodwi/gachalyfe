package me.gachalyfe.rapi.data

import me.gachalyfe.rapi.data.entity.AnomalyInterceptionEntity
import me.gachalyfe.rapi.data.entity.ManufacturerEquipmentEntity
import me.gachalyfe.rapi.data.entity.SpecialInterceptionEntity
import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.model.SpecialInterception

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
            date = date,
            manufacturer = manufacturer,
            classType = classType,
            slotType = slotType,
            sourceId = sourceId,
            sourceType = EquipmentSourceType.from(sourceType),
        )

    fun SpecialInterceptionEntity.asModel() =
        SpecialInterception(
            id = id,
            date = date,
            bossName = bossName,
            t9Equipment = t9Equipment,
            modules = modules,
            t9ManufacturerEquipment = t9ManufacturerEquipment,
            empty = empty
        )
}
