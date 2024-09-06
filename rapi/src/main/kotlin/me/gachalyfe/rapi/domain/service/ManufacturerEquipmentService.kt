package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.domain.dto.ManufacturerEquipmentDTO
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment

interface ManufacturerEquipmentService {
    fun addEquipment(dto: ManufacturerEquipmentDTO): ManufacturerEquipment
    fun getEquipments(): List<ManufacturerEquipment>
    fun updateEquipment(id: Long, dto: ManufacturerEquipmentDTO): ManufacturerEquipment
    fun deleteEquipment(id: Long): Boolean
}
