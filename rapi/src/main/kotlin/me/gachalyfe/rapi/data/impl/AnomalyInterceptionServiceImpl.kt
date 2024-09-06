package me.gachalyfe.rapi.data.impl

import me.gachalyfe.rapi.controller.exception.NotFoundException
import me.gachalyfe.rapi.data.DataMapper.asModel
import me.gachalyfe.rapi.data.dto.AnomalyInterceptionDTO
import me.gachalyfe.rapi.data.entity.AnomalyInterceptionEntity
import me.gachalyfe.rapi.data.entity.ManufacturerEquipmentEntity
import me.gachalyfe.rapi.data.repository.AnomalyInterceptionRepository
import me.gachalyfe.rapi.data.repository.ManufacturerEquipmentRepository
import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.service.AnomalyInterceptionService
import me.gachalyfe.rapi.utils.lazyLogger
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class AnomalyInterceptionServiceImpl(
    private val repo1: AnomalyInterceptionRepository,
    private val repo2: ManufacturerEquipmentRepository,
) : AnomalyInterceptionService {
    private val log by lazyLogger()

    override fun createAttempt(dto: AnomalyInterceptionDTO): AnomalyInterception {
        val newData =
            AnomalyInterceptionEntity(
                date = dto.date,
                bossName = dto.bossName,
                stage = dto.stage,
                dropType = dto.dropType,
                dropped = dto.dropped,
                modules = dto.modules,
            )
        val savedData = repo1.save(newData)

        if (dto.equipments.isPresent) {
            dto.equipments.get().also { e ->
                val equipment =
                    ManufacturerEquipmentEntity(
                        date = savedData.date,
                        manufacturer = e.manufacturer,
                        classType = e.classType,
                        slotType = e.slotType,
                        sourceId = savedData.id,
                        sourceType = EquipmentSourceType.AI_DROPS.code,
                    )
                repo2.save(equipment)
                return newData.asModel().copy(equipment = Optional.of(equipment.asModel()))
            }
        }

        return newData.asModel()
    }

    override fun getAttempts(): List<AnomalyInterception> {
        val data = repo1.findLast10().map { it.asModel() }
        return data
    }

    override fun getAttempt(id: Long): AnomalyInterception {
        val data = repo1.findById(id)
        if (data.isPresent) {
            if (data.get().dropped && data.get().bossName != "Kraken") {
                val equipment =
                    repo2
                        .findBySourceTypeAndSourceId(EquipmentSourceType.AI_DROPS.code, id)
                        .map { it.asModel() }
                log.info("get attempt $equipment")
                return data
                    .get()
                    .asModel()
                    .copy(equipment = Optional.of(equipment.first()))
            }
            return data.get().asModel()
        } else {
            throw NotFoundException("There's no such anomaly interception attempt with id=$id")
        }
    }

    override fun updateAttempt(
        id: Long,
        dto: AnomalyInterceptionDTO,
    ): AnomalyInterception {
        val data = repo1.findById(id)
        if (data.isPresent) {
            val update =
                AnomalyInterceptionEntity(
                    id = data.get().id,
                    date = dto.date,
                    bossName = dto.bossName,
                    stage = dto.stage,
                    dropType = dto.dropType,
                    dropped = dto.dropped,
                    modules = dto.modules,
                )
            repo1.save(update)
            return update.asModel()
        } else {
            throw NotFoundException("There's no such anomaly interception attempt with id=$id")
        }
    }

    override fun deleteAttempt(id: Long): Boolean {
        val data = repo1.findById(id)
        if (data.isPresent) {
            // delete the equipments first
            val equipmentIds = repo2.findBySourceTypeAndSourceId(EquipmentSourceType.AI_DROPS.code, data.get().id).map { it.id }
            equipmentIds.forEach(repo2::deleteById)
            // then delete the attempt
            repo1.deleteById(id)
            return true
        } else {
            throw NotFoundException("There's no such anomaly interception attempt with id=$id")
        }
    }
}
