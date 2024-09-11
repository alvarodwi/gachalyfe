package me.gachalyfe.rapi.domain.service.equipment

import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment

interface ManufacturerEquipmentService {
    fun findAll(): List<ManufacturerEquipment>

    fun findAllBySourceIdAndSourceType(
        sourceId: Long,
        sourceType: EquipmentSourceType,
    ): List<ManufacturerEquipment>

    fun findAllByLatest(): List<ManufacturerEquipment>

    fun save(model: ManufacturerEquipment): ManufacturerEquipment

    fun saveAll(data: List<ManufacturerEquipment>): Int

    fun update(
        id: Long,
        model: ManufacturerEquipment,
    ): ManufacturerEquipment

    fun delete(id: Long): Boolean
}
