import me.gachalyfe.rapi.controller.dto.AnomalyInterceptionDTO
import me.gachalyfe.rapi.controller.dto.ManufacturerEquipmentDTO
import me.gachalyfe.rapi.controller.dto.SpecialInterceptionDTO
import me.gachalyfe.rapi.data.entity.AnomalyInterceptionEntity
import me.gachalyfe.rapi.data.entity.SpecialInterceptionEntity
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.model.SpecialInterception

fun AnomalyInterceptionEntity.toModel() =
    AnomalyInterception(
        id = id,
        date = date,
        bossName = bossName,
        stage = stage,
        dropType = dropType,
        dropped = dropped,
        modules = modules,
    )

fun AnomalyInterceptionDTO.toModel() =
    AnomalyInterception(
        id = id ?: 0,
        date = date,
        bossName = bossName,
        stage = stage,
        dropType = dropType,
        dropped = dropped,
        modules = modules,
        equipment = equipment?.toModel()
    )

fun AnomalyInterception.toEntity() =
    AnomalyInterceptionEntity(
        id = id,
        date = date,
        bossName = bossName,
        stage = stage,
        dropType = dropType,
        dropped = dropped,
        modules = modules
    )

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
        equipments = equipments.map(ManufacturerEquipmentDTO::toModel)
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
