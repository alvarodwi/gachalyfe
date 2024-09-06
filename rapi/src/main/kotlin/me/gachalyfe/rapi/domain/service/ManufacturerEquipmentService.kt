package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.data.dto.ManufacturerEquipmentDTO
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment

interface ManufacturerEquipmentService {
    fun getEquipments(): List<ManufacturerEquipment>

    fun updateEquipment(
        id: Long,
        dto: ManufacturerEquipmentDTO,
    ): ManufacturerEquipment

    fun deleteEquipment(id: Long): Boolean
}
