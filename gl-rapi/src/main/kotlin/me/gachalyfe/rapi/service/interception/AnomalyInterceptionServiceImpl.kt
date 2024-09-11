package me.gachalyfe.rapi.service.interception

import me.gachalyfe.rapi.data.entity.AnomalyInterceptionEntity
import me.gachalyfe.rapi.data.mapper.toEntity
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.data.repository.AnomalyInterceptionRepository
import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.service.equipment.ManufacturerEquipmentService
import me.gachalyfe.rapi.domain.service.interception.AnomalyInterceptionService
import me.gachalyfe.rapi.utils.exception.ResourceNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AnomalyInterceptionServiceImpl(
    private val repository: AnomalyInterceptionRepository,
    private val equipmentService: ManufacturerEquipmentService,
) : AnomalyInterceptionService {
    override fun save(model: AnomalyInterception): AnomalyInterception {
        val newData: AnomalyInterceptionEntity = model.toEntity()
        val savedData = repository.save(newData)

        model.equipment?.let { equipment ->
            val newEquipment =
                ManufacturerEquipment(
                    date = savedData.date,
                    manufacturer = equipment.manufacturer,
                    classType = equipment.classType,
                    slotType = equipment.slotType,
                    sourceId = savedData.id,
                    sourceType = EquipmentSourceType.AI_DROPS,
                )
            val savedEquipment = equipmentService.save(newEquipment)
            return savedData.toModel().copy(equipment = savedEquipment)
        }

        return savedData.toModel()
    }

    override fun saveAll(data: List<AnomalyInterception>): Int {
        val saved = data.map { save(it) }
        return saved.size
    }

    override fun findAll(): List<AnomalyInterception> {
        val data = repository.findAllByOrderByDateAsc()
        return data.map { it.toModel() }
    }

    override fun findAllByLatest(): List<AnomalyInterception> {
        val data = repository.findLatest()
        return data.map { it.toModel() }
    }

    override fun findIdsByDateAndEquipmentDropped(date: String): List<Long> = repository.findIdsByDateAndEquipmentDropped(date)

    override fun findById(id: Long): AnomalyInterception {
        val data =
            repository.findByIdOrNull(id)
                ?: throw ResourceNotFoundException("There's no such anomaly interception attempt with id=$id")
        if (data.dropped && data.bossName != "Kraken") {
            val equipment =
                equipmentService
                    .findAllBySourceIdAndSourceType(
                        sourceId = data.id,
                        sourceType = EquipmentSourceType.AI_DROPS,
                    ).first()
            return data
                .toModel()
                .copy(equipment = equipment)
        }

        return data.toModel()
    }

    override fun delete(
        id: Long,
        model: AnomalyInterception,
    ): AnomalyInterception {
        val data =
            repository.findByIdOrNull(id)
                ?: throw ResourceNotFoundException("There's no such anomaly interception attempt with id=$id")
        val update =
            model
                .toEntity()
                .copy(id = data.id)
        repository.save(update)
        return update.toModel()
    }

    override fun delete(id: Long): Boolean {
        val data =
            repository.findByIdOrNull(id)
                ?: throw ResourceNotFoundException("There's no such anomaly interception attempt with id=$id")
        val equipmentIds =
            equipmentService
                .findAllBySourceIdAndSourceType(
                    sourceType = EquipmentSourceType.AI_DROPS,
                    sourceId = data.id,
                ).map(ManufacturerEquipment::id)
        equipmentIds.forEach(equipmentService::delete)

        repository.deleteById(data.id)
        return true
    }
}
