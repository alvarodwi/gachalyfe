package me.gachalyfe.rapi.service

import me.gachalyfe.rapi.data.entity.AnomalyInterceptionEntity
import me.gachalyfe.rapi.data.mapper.toEntity
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.data.repository.AnomalyInterceptionRepository
import me.gachalyfe.rapi.data.spec.AnomalyInterceptionSpecs
import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.model.stats.AnomalyInterceptionStats
import me.gachalyfe.rapi.domain.service.AnomalyInterceptionService
import me.gachalyfe.rapi.domain.service.ManufacturerEquipmentService
import me.gachalyfe.rapi.utils.exception.ResourceNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AnomalyInterceptionServiceImpl(
    private val repository: AnomalyInterceptionRepository,
    private val equipmentService: ManufacturerEquipmentService,
) : AnomalyInterceptionService {
    override fun findAll(pageable: Pageable): Page<AnomalyInterception> {
        val data = repository.findAll(pageable)
        return data.map { it.toModel() }
    }

    override fun findAll(sort: Sort): List<AnomalyInterception> {
        val data = repository.findAll(sort)
        return data.map { it.toModel() }
    }

    override fun findIdsByDateAndEquipmentDropped(date: String): List<Long> {
        val specs = AnomalyInterceptionSpecs.hasDate(date).and(AnomalyInterceptionSpecs.hasEquipmentDropped())
        val data = repository.findAll(specs)
        return data.map { it.id }
    }

    override fun findById(id: Long): AnomalyInterception {
        val data =
            repository.findByIdOrNull(id) ?: throw ResourceNotFoundException("There's no such anomaly interception attempt with id=$id")
        if (data.dropped && data.bossName != "Kraken") {
            val equipment =
                equipmentService
                    .findBySourceIdAndSourceType(
                        sourceId = data.id,
                        sourceType = EquipmentSourceType.AI_DROPS,
                    ).first()
            return data.toModel().copy(equipment = equipment)
        }

        return data.toModel()
    }

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

    override fun update(
        id: Long,
        model: AnomalyInterception,
    ): AnomalyInterception {
        val data =
            repository.findByIdOrNull(id) ?: throw ResourceNotFoundException("There's no such anomaly interception attempt with id=$id")
        val update = model.toEntity().copy(id = data.id)
        repository.save(update)
        return update.toModel()
    }

    override fun delete(id: Long): Boolean {
        val data =
            repository.findByIdOrNull(id) ?: throw ResourceNotFoundException("There's no such anomaly interception attempt with id=$id")
        val equipmentIds =
            equipmentService
                .findBySourceIdAndSourceType(
                    sourceType = EquipmentSourceType.AI_DROPS,
                    sourceId = data.id,
                ).map(ManufacturerEquipment::id)
        equipmentIds.forEach(equipmentService::delete)

        repository.deleteById(data.id)
        return true
    }

    override fun generateStats(dropType: String): AnomalyInterceptionStats {
        val spec = AnomalyInterceptionSpecs.hasDropType(dropType)
        val data = if (dropType != "All") repository.findAll(spec) else repository.findAll()

        val rewards = data.map { calculateAnomalyInterceptionReward(it) }

        val totalAttempts = data.count()
        val totalCustomLock = rewards.sumOf { it.customLock }
        val totalModulesShards = rewards.sumOf { it.modulesShard }
        val totalManufacturerArms = rewards.sumOf { it.manufacturerArms }
        val totalEquipmentDrops = data.count { it.dropType != "Modules" && it.dropped }
        val totalModules = data.sumOf { it.modules }

        return AnomalyInterceptionStats(
            dropType = dropType,
            totalAttempts = totalAttempts,
            totalCustomLock = totalCustomLock,
            totalModulesShards = totalModulesShards,
            totalManufacturerArms = totalManufacturerArms,
            totalEquipmentDrops = totalEquipmentDrops,
            totalModules = totalModules,
        )
    }

    private fun calculateAnomalyInterceptionReward(data: AnomalyInterceptionEntity): AnomalyInterceptionReward {
        val customLockMap =
            mapOf(
                "Modules" to listOf(0, 2, 2, 4, 4, 4, 6, 6, 6, 6),
                "Default" to listOf(0, 1, 1, 3, 3, 3, 4, 4, 4, 4),
            )

        val customLock =
            customLockMap
                .getOrDefault(data.dropType, customLockMap["Default"])
                ?.getOrElse(data.stage) { 0 } ?: 0

        val modulesShardMap = listOf(0, 16, 17, 25, 26, 27, 35, 37, 37, 37)

        val modulesShard: Int =
            if (data.dropType == "Modules") {
                modulesShardMap.getOrElse(data.stage) { 0 }
            } else {
                0
            }

        val manufacturerArmsMap = listOf(0, 20, 21, 26, 27, 28, 33, 34, 34, 34)

        val manufacturerArms: Int =
            if (data.dropType != "Modules") {
                manufacturerArmsMap.getOrElse(data.stage) { 0 }
            } else {
                0
            }

        return AnomalyInterceptionReward(
            customLock = customLock,
            modulesShard = modulesShard,
            manufacturerArms = manufacturerArms,
        )
    }

    private data class AnomalyInterceptionReward(
        val customLock: Int,
        val modulesShard: Int,
        val manufacturerArms: Int,
    )
}
