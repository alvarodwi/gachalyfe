package me.gachalyfe.rapi.service

import me.gachalyfe.rapi.data.entity.ManufacturerEquipmentEntity
import me.gachalyfe.rapi.data.mapper.toEntity
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.data.repository.ManufacturerEquipmentRepository
import me.gachalyfe.rapi.data.spec.ManufacturerEquipmentSpecs
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.service.ManufacturerEquipmentService
import me.gachalyfe.rapi.utils.exception.ResourceNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ManufacturerEquipmentServiceImpl(
    private val repository: ManufacturerEquipmentRepository,
) : ManufacturerEquipmentService {
    override fun findAll(pageable: Pageable): Page<ManufacturerEquipment> {
        val data = repository.findAll(pageable)
        return data.map { it.toModel() }
    }

    override fun findAll(sort: Sort): List<ManufacturerEquipment> {
        val data = repository.findAll(sort)
        return data.map { it.toModel() }
    }

    override fun findBySourceType(
        sourceType: EquipmentSourceType,
        pageable: Pageable,
    ): Page<ManufacturerEquipment> {
        val specs = ManufacturerEquipmentSpecs.isInType(sourceType)
        val data = repository.findAll(specs, pageable)
        return data.map { it.toModel() }
    }

    override fun findBySourceIdAndSourceType(
        sourceId: Long,
        sourceType: EquipmentSourceType,
    ): List<ManufacturerEquipment> {
        val specs =
            ManufacturerEquipmentSpecs
                .isInType(sourceType)
                .and(ManufacturerEquipmentSpecs.withSourceId(sourceId))
        val data = repository.findAll(specs)
        return data.map { it.toModel() }
    }

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
