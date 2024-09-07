package me.gachalyfe.rapi.data.impl

import me.gachalyfe.rapi.controller.exception.ResourceNotFoundException
import me.gachalyfe.rapi.data.DataMapper.asModel
import me.gachalyfe.rapi.data.dto.ManufacturerEquipmentDTO
import me.gachalyfe.rapi.data.entity.ManufacturerEquipmentEntity
import me.gachalyfe.rapi.data.repository.ManufacturerEquipmentRepository
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.service.ManufacturerEquipmentService
import org.springframework.stereotype.Service

@Service
class ManufacturerEquipmentServiceImpl(
    private val repository: ManufacturerEquipmentRepository,
) : ManufacturerEquipmentService {
    override fun getEquipments(): List<ManufacturerEquipment> {
        val data = repository.findLast10()
        return data.map { it.asModel() }
    }

    override fun updateEquipment(
        id: Long,
        dto: ManufacturerEquipmentDTO,
    ): ManufacturerEquipment {
        val data = repository.findById(id)
        if (data.isPresent) {
            val update =
                ManufacturerEquipmentEntity(
                    id = data.get().id,
                    date = data.get().date,
                    manufacturer = dto.manufacturer,
                    classType = dto.classType,
                    slotType = dto.slotType,
                    sourceId = data.get().sourceId,
                    sourceType = data.get().sourceType,
                )
            repository.save(update)
            return update.asModel()
        } else {
            throw ResourceNotFoundException("There's no such manufacturer equipments with id=$id")
        }
    }

    override fun deleteEquipment(id: Long): Boolean {
        val data = repository.findById(id)
        if (data.isPresent) {
            repository.deleteById(id)
            return true
        } else {
            throw ResourceNotFoundException("There's no such manufacturer equipments with id=$id")
        }
    }
}
