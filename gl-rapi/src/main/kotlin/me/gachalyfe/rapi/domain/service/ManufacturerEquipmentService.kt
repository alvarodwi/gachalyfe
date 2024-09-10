package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment

interface ManufacturerEquipmentService {
    fun getEquipments(): List<ManufacturerEquipment>

    fun getEquipmentsBySourceIdAndSourceType(
        sourceId: Long,
        sourceType: EquipmentSourceType,
    ): List<ManufacturerEquipment>

    fun createEquipment(model: ManufacturerEquipment): ManufacturerEquipment

    fun updateEquipment(
        id: Long,
        model: ManufacturerEquipment,
    ): ManufacturerEquipment

    fun deleteEquipment(id: Long): Boolean
}
