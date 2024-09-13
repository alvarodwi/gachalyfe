package me.gachalyfe.rapi.domain.service.equipment

import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment

interface ManufacturerEquipmentService {
    fun findAll(): List<ManufacturerEquipment>

    fun findAllBySourceType(
        sourceType: EquipmentSourceType
    ) : List<ManufacturerEquipment>

    fun findRecentBySourceType(
        sourceType: EquipmentSourceType,
        limit: Int = 0
    ) : List<ManufacturerEquipment>

    fun findAllBySourceIdAndSourceType(
        sourceId: Long,
        sourceType: EquipmentSourceType,
    ): List<ManufacturerEquipment>

    fun findAllByLatest(): List<ManufacturerEquipment>

    fun save(model: ManufacturerEquipment): ManufacturerEquipment

    fun saveAll(data: List<ManufacturerEquipment>): List<ManufacturerEquipment>

    fun update(
        id: Long,
        model: ManufacturerEquipment,
    ): ManufacturerEquipment

    fun delete(id: Long): Boolean
}
