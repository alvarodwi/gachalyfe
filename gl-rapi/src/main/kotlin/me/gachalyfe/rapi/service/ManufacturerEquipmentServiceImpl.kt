package me.gachalyfe.rapi.service

import me.gachalyfe.rapi.data.entity.ManufacturerEquipmentEntity
import me.gachalyfe.rapi.data.mapper.toEntity
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.data.repository.ManufacturerEquipmentRepository
import me.gachalyfe.rapi.data.spec.ManufacturerEquipmentSpecs
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.model.stats.EquipmentSourceStats
import me.gachalyfe.rapi.domain.model.stats.EquipmentStats
import me.gachalyfe.rapi.domain.model.stats.EquipmentStatsByCategory
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
        val specs = ManufacturerEquipmentSpecs.hasSourceType(sourceType)
        val data = repository.findAll(specs, pageable)
        return data.map { it.toModel() }
    }

    override fun findBySourceIdAndSourceType(
        sourceId: Long,
        sourceType: EquipmentSourceType,
    ): List<ManufacturerEquipment> {
        val specs =
            ManufacturerEquipmentSpecs
                .hasSourceType(sourceType)
                .and(ManufacturerEquipmentSpecs.hasSourceId(sourceId))
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

    override fun generateStats(sourceType: EquipmentSourceType): EquipmentStats {
        val spec = ManufacturerEquipmentSpecs.hasSourceType(sourceType)
        val data = when (sourceType) {
            EquipmentSourceType.UNKNOWN -> repository.findAll()
            else -> repository.findAll(spec)
        }

        val equipmentStatsByCategory: List<EquipmentStatsByCategory> = data.groupingBy {
            Triple(it.manufacturer, it.classType, it.slotType)
        }.eachCount()
            .map { (key, count) ->
                EquipmentStatsByCategory(
                    manufacturer = key.first,
                    classType = key.second,
                    slotType = key.third,
                    total = count
                )
            }

        return EquipmentStats(
            sourceType = sourceType,
            equipmentStatsByCategory = equipmentStatsByCategory,
            total = data.size
        )
    }

    override fun generateSourceStats(): EquipmentSourceStats {
        val data = repository.findAll()
        val totalCounts = data.groupingBy { it.sourceType }.eachCount()
        return EquipmentSourceStats(
            totalManufacturerArmsOpened = totalCounts.getOrDefault(EquipmentSourceType.ARMS.ordinal, 0),
            totalManufacturerFurnaceOpened = totalCounts.getOrDefault(EquipmentSourceType.FURNACE.ordinal, 0),
            totalFromAnomalyInterception = totalCounts.getOrDefault(EquipmentSourceType.AI_DROPS.ordinal, 0),
            totalFromSpecialInterception = totalCounts.getOrDefault(EquipmentSourceType.SI_DROPS.ordinal, 0)
        )
    }
}
