package me.gachalyfe.rapi.service.interception

import me.gachalyfe.rapi.data.entity.AnomalyInterceptionEntity
import me.gachalyfe.rapi.data.mapper.InterceptionMapper
import me.gachalyfe.rapi.data.repository.AnomalyInterceptionRepository
import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.service.AnomalyInterceptionService
import me.gachalyfe.rapi.domain.service.ManufacturerEquipmentService
import me.gachalyfe.rapi.utils.exception.ResourceNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AnomalyInterceptionServiceImpl(
    private val repository: AnomalyInterceptionRepository,
    private val mapper: InterceptionMapper,
    private val equipmentService: ManufacturerEquipmentService,
) : AnomalyInterceptionService {
    override fun createAttempt(model: AnomalyInterception): AnomalyInterception {
        val newData: AnomalyInterceptionEntity = mapper.toEntity(model)
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
            val savedEquipment = equipmentService.createEquipment(newEquipment)
            return mapper.toModel(savedData).copy(equipment = savedEquipment)
        }

        return mapper.toModel(savedData)
    }

    override fun getAttempts(): List<AnomalyInterception> {
        val data = repository.findLast10().map(mapper::toModel)
        return data
    }

    override fun getAttempt(id: Long): AnomalyInterception {
        val data =
            repository.findByIdOrNull(id)
                ?: throw ResourceNotFoundException("There's no such anomaly interception attempt with id=$id")
        if (data.dropped && data.bossName != "Kraken") {
            val equipment =
                equipmentService
                    .getEquipmentsBySourceIdAndSourceType(
                        sourceId = data.id,
                        sourceType = EquipmentSourceType.AI_DROPS,
                    ).first()
            return mapper
                .toModel(data)
                .copy(equipment = equipment)
        }

        return mapper.toModel(data)
    }

    override fun updateAttempt(
        id: Long,
        model: AnomalyInterception,
    ): AnomalyInterception {
        val data =
            repository.findByIdOrNull(id)
                ?: throw ResourceNotFoundException("There's no such anomaly interception attempt with id=$id")
        val update =
            mapper
                .toEntity(model)
                .copy(id = data.id)
        repository.save(update)
        return mapper.toModel(update)
    }

    override fun deleteAttempt(id: Long): Boolean {
        val data =
            repository.findByIdOrNull(id)
                ?: throw ResourceNotFoundException("There's no such anomaly interception attempt with id=$id")
        val equipmentIds =
            equipmentService
                .getEquipmentsBySourceIdAndSourceType(
                    sourceType = EquipmentSourceType.AI_DROPS,
                    sourceId = data.id,
                ).map(ManufacturerEquipment::id)
        equipmentIds.forEach(equipmentService::deleteEquipment)

        repository.deleteById(data.id)
        return true
    }
}
