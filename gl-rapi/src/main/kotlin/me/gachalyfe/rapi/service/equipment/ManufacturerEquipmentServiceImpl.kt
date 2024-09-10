package me.gachalyfe.rapi.service.equipment

import me.gachalyfe.rapi.data.entity.ManufacturerEquipmentEntity
import me.gachalyfe.rapi.data.mapper.toEntity
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.data.repository.ManufacturerEquipmentRepository
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.service.ManufacturerEquipmentService
import me.gachalyfe.rapi.utils.exception.ResourceNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ManufacturerEquipmentServiceImpl(
    private val repository: ManufacturerEquipmentRepository,
) : ManufacturerEquipmentService {
    override fun getEquipments(): List<ManufacturerEquipment> {
        val data = repository.findLast10()
        return data.map { it.toModel() }
    }

    override fun getEquipmentsBySourceIdAndSourceType(
        sourceId: Long,
        sourceType: EquipmentSourceType,
    ): List<ManufacturerEquipment> =
        repository
            .findBySourceIdAndSourceType(
                sourceId = sourceId,
                sourceType = sourceType.ordinal,
            ).map { it.toModel() }

    override fun createEquipment(model: ManufacturerEquipment): ManufacturerEquipment {
        val newEquipment = model.toEntity()
        val saved = repository.save(newEquipment)
        return saved.toModel()
    }

    override fun updateEquipment(
        id: Long,
        model: ManufacturerEquipment,
    ): ManufacturerEquipment {
        val data = repository.findByIdOrNull(id) ?: throw ResourceNotFoundException("There's no such manufacturer equipments with id=$id")
        val update =
            ManufacturerEquipmentEntity(
                id = data.id,
                date = data.date,
                manufacturer = model.manufacturer,
                classType = model.classType,
                slotType = model.slotType,
                sourceId = data.sourceId,
                sourceType = data.sourceType,
            )
        repository.save(update)
        return update.toModel()
    }

    override fun deleteEquipment(id: Long): Boolean {
        val data =
            repository.findByIdOrNull(id)
                ?: throw ResourceNotFoundException("There's no such manufacturer equipments with id=$id")
        repository.deleteById(data.id)
        return true
    }
}
