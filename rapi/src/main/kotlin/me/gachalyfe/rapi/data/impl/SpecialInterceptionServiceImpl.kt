package me.gachalyfe.rapi.data.impl

import me.gachalyfe.rapi.controller.exception.ResourceNotFoundException
import me.gachalyfe.rapi.data.DataMapper.asModel
import me.gachalyfe.rapi.data.dto.SpecialInterceptionDTO
import me.gachalyfe.rapi.data.entity.ManufacturerEquipmentEntity
import me.gachalyfe.rapi.data.entity.SpecialInterceptionEntity
import me.gachalyfe.rapi.data.repository.ManufacturerEquipmentRepository
import me.gachalyfe.rapi.data.repository.SpecialInterceptionRepository
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.SpecialInterception
import me.gachalyfe.rapi.domain.service.SpecialInterceptionService
import org.springframework.stereotype.Service

@Service
class SpecialInterceptionServiceImpl(
    private val repo1: SpecialInterceptionRepository,
    private val repo2: ManufacturerEquipmentRepository,
) : SpecialInterceptionService {
    override fun createAttempt(dto: SpecialInterceptionDTO): SpecialInterception {
        val newData =
            SpecialInterceptionEntity(
                date = dto.date,
                bossName = dto.bossName,
                t9Equipment = dto.t9Equipment,
                modules = dto.modules,
                t9ManufacturerEquipment = dto.t9ManufacturerEquipment,
                empty = dto.empty
            )
        val savedData = repo1.save(newData)

        if (dto.equipments.isNotEmpty()) {
            dto.equipments.forEach { e ->
                val equipment =
                    ManufacturerEquipmentEntity(
                        date = savedData.date,
                        manufacturer = e.manufacturer,
                        classType = e.classType,
                        slotType = e.slotType,
                        sourceId = savedData.id,
                        sourceType = EquipmentSourceType.SI_DROPS.code,
                    )
                repo2.save(equipment)
            }
        }

        return newData.asModel()
    }

    override fun getAttempts(): List<SpecialInterception> {
        val data = repo1.findLast10().map { it.asModel() }
        return data
    }

    override fun getAttempt(id: Long): SpecialInterception {
        val data = repo1.findById(id)
        if (data.isPresent) {
            if (data.get().t9ManufacturerEquipment > 0) {
                val equipments = repo2.findBySourceTypeAndSourceId(EquipmentSourceType.SI_DROPS.code, id).map { it.asModel() }
                return data.get().asModel().copy(equipments = equipments)
            } else {
                return data.get().asModel()
            }
        } else {
            throw ResourceNotFoundException("There's no such anomaly interception attempt with id=$id")
        }
    }

    override fun updateAttempt(
        id: Long,
        dto: SpecialInterceptionDTO,
    ): SpecialInterception {
        val data = repo1.findById(id)
        if (data.isPresent) {
            val update =
                SpecialInterceptionEntity(
                    id = data.get().id,
                    date = dto.date,
                    bossName = dto.bossName,
                    t9Equipment = dto.t9Equipment,
                    modules = dto.modules,
                    t9ManufacturerEquipment = dto.t9ManufacturerEquipment,
                    empty = dto.empty
                )
            repo1.save(update)
            return update.asModel()
        } else {
            throw ResourceNotFoundException("There's no such anomaly interception attempt with id=$id")
        }
    }

    override fun deleteAttempt(id: Long): Boolean {
        val data = repo1.findById(id)
        if (data.isPresent) {
            // delete the equipments first
            val equipmentIds = repo2.findBySourceTypeAndSourceId(EquipmentSourceType.SI_DROPS.code, data.get().id).map { it.id }
            equipmentIds.forEach(repo2::deleteById)
            // then delete the attempt
            repo1.deleteById(id)
            return true
        } else {
            throw ResourceNotFoundException("There's no such anomaly interception attempt with id=$id")
        }
    }
}
