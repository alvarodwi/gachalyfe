package me.gachalyfe.rapi.service.interception

import me.gachalyfe.rapi.data.entity.SpecialInterceptionEntity
import me.gachalyfe.rapi.data.repository.SpecialInterceptionRepository
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.model.SpecialInterception
import me.gachalyfe.rapi.domain.service.equipment.ManufacturerEquipmentService
import me.gachalyfe.rapi.domain.service.interception.SpecialInterceptionService
import me.gachalyfe.rapi.utils.exception.ResourceNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import toEntity
import toModel

@Service
class SpecialInterceptionServiceImpl(
    private val repository: SpecialInterceptionRepository,
    private val equipmentService: ManufacturerEquipmentService,
) : SpecialInterceptionService {
    override fun save(model: SpecialInterception): SpecialInterception {
        val newData = model.toEntity()
        val savedData = repository.save(newData)

        if (model.equipments.isNotEmpty()) {
            val createdEquipments = mutableListOf<ManufacturerEquipment>()
            model.equipments.forEach { e ->
                val equipment =
                    ManufacturerEquipment(
                        date = savedData.date,
                        manufacturer = e.manufacturer,
                        classType = e.classType,
                        slotType = e.slotType,
                        sourceId = savedData.id,
                        sourceType = EquipmentSourceType.SI_DROPS,
                    )
                val newEquipment = equipmentService.save(equipment)
                createdEquipments.add(newEquipment)
                return savedData.toModel().copy(equipments = createdEquipments)
            }
        }

        return savedData.toModel()
    }

    override fun saveAll(data: List<SpecialInterception>): Int {
        val saved = data.map { save(it) }
        return saved.size
    }

    override fun findAll(): List<SpecialInterception> {
        val data = repository.findAllByOrderByDateAsc()
        return data.map { it.toModel() }
    }

    override fun findAllByLatest(): List<SpecialInterception> {
        val data = repository.findLatest()
        return data.map { it.toModel() }
    }

    override fun findByDateAndEquipmentDropped(date: String): SpecialInterception? =
        repository.findByDateAndEquipmentDropped(date)?.toModel()

    override fun findById(id: Long): SpecialInterception {
        val data =
            repository.findByIdOrNull(id)
                ?: throw ResourceNotFoundException("There's no such anomaly interception attempt with id=$id")

        if (data.t9ManufacturerEquipment > 0) {
            val equipments =
                equipmentService.findAllBySourceIdAndSourceType(
                    sourceId = id,
                    sourceType = EquipmentSourceType.SI_DROPS,
                )
            return data.toModel().copy(equipments = equipments)
        } else {
            return data.toModel()
        }
    }

    override fun update(
        id: Long,
        model: SpecialInterception,
    ): SpecialInterception {
        val data =
            repository.findByIdOrNull(id)
                ?: throw ResourceNotFoundException("There's no such anomaly interception attempt with id=$id")

        val update =
            SpecialInterceptionEntity(
                id = data.id,
                date = model.date,
                bossName = model.bossName,
                t9Equipment = model.t9Equipment,
                modules = model.modules,
                t9ManufacturerEquipment = model.t9ManufacturerEquipment,
                empty = model.empty,
            )
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
                    data.id,
                    EquipmentSourceType.SI_DROPS,
                ).map(
                    ManufacturerEquipment::id,
                )
        equipmentIds.forEach(equipmentService::delete)

        repository.deleteById(data.id)
        return true
    }
}
