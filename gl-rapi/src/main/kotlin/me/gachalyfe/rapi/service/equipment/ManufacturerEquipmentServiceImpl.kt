package me.gachalyfe.rapi.service.equipment

import me.gachalyfe.rapi.data.entity.ManufacturerEquipmentEntity
import me.gachalyfe.rapi.data.mapper.toEntity
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.data.repository.ManufacturerEquipmentRepository
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.service.equipment.ManufacturerEquipmentService
import me.gachalyfe.rapi.utils.exception.ResourceNotFoundException
import org.springframework.data.domain.Limit
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ManufacturerEquipmentServiceImpl(
    private val repository: ManufacturerEquipmentRepository,
) : ManufacturerEquipmentService {
    override fun findAll(): List<ManufacturerEquipment> {
        val data = repository.findAllByOrderByDateAsc()
        return data.map { it.toModel() }
    }

    override fun findAllByLatest(): List<ManufacturerEquipment> {
        val data = repository.findLatest()
        return data.map { it.toModel() }
    }

    override fun findAllBySourceType(sourceType: EquipmentSourceType): List<ManufacturerEquipment> {
        val data = repository.findBySourceType(sourceType.ordinal, Limit.unlimited())
        return data.map { it.toModel() }
    }

    override fun findRecentBySourceType(sourceType: EquipmentSourceType, limit: Int): List<ManufacturerEquipment> {
        val data = repository.findBySourceType(sourceType.ordinal, Limit.of(limit))
        return data.map{ it.toModel() }
    }

    override fun findAllBySourceIdAndSourceType(
        sourceId: Long,
        sourceType: EquipmentSourceType,
    ): List<ManufacturerEquipment> =
        repository
            .findBySourceIdAndSourceType(
                sourceId = sourceId,
                sourceType = sourceType.ordinal,
            ).map { it.toModel() }

    override fun save(model: ManufacturerEquipment): ManufacturerEquipment {
        val newEquipment = model.toEntity()
        val saved = repository.save(newEquipment)
        return saved.toModel()
    }

    override fun saveAll(data: List<ManufacturerEquipment>): List<ManufacturerEquipment> {
        val saved = data.map { save(it) }
        return saved
    }

    override fun update(
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

    override fun delete(id: Long): Boolean {
        val data =
            repository.findByIdOrNull(id)
                ?: throw ResourceNotFoundException("There's no such manufacturer equipments with id=$id")
        repository.deleteById(data.id)
        return true
    }
}
