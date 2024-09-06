package me.gachalyfe.rapi.data.impl

import me.gachalyfe.rapi.domain.dto.ManufacturerEquipmentDTO
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.service.ManufacturerEquipmentService
import org.springframework.stereotype.Service

@Service
class ManufacturerEquipmentServiceImpl : ManufacturerEquipmentService {
    override fun addEquipment(dto: ManufacturerEquipmentDTO): ManufacturerEquipment {
        TODO("Not yet implemented")
    }

    override fun getEquipments(): List<ManufacturerEquipment> {
        TODO("Not yet implemented")
    }

    override fun updateEquipment(id: Long, dto: ManufacturerEquipmentDTO): ManufacturerEquipment {
        TODO("Not yet implemented")
    }

    override fun deleteEquipment(id: Long): Boolean {
        TODO("Not yet implemented")
    }
}
