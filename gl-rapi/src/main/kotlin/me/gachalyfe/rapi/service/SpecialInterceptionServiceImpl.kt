package me.gachalyfe.rapi.service

import me.gachalyfe.rapi.data.entity.SpecialInterceptionEntity
import me.gachalyfe.rapi.data.repository.SpecialInterceptionRepository
import me.gachalyfe.rapi.data.spec.SpecialInterceptionSpecs
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.model.SpecialInterception
import me.gachalyfe.rapi.domain.model.stats.SpecialInterceptionStats
import me.gachalyfe.rapi.domain.service.ManufacturerEquipmentService
import me.gachalyfe.rapi.domain.service.SpecialInterceptionService
import me.gachalyfe.rapi.utils.exception.ResourceNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import toEntity
import toModel

@Service
class SpecialInterceptionServiceImpl(
    private val repository: SpecialInterceptionRepository,
    private val equipmentService: ManufacturerEquipmentService,
) : SpecialInterceptionService {
    override fun findAll(pageable: Pageable): Page<SpecialInterception> {
        val data = repository.findAll(pageable)
        return data.map { it.toModel() }
    }

    override fun findAll(sort: Sort): List<SpecialInterception> {
        val data = repository.findAll(sort)
        return data.map { it.toModel() }
    }

    override fun findByDateAndEquipmentDropped(date: String): SpecialInterception? {
        val specs = SpecialInterceptionSpecs.hasDate(date).and(SpecialInterceptionSpecs.hasManufacturerEquipments())
        val data = repository.findAll(specs).firstOrNull()
        return data?.toModel()
    }

    override fun findById(id: Long): SpecialInterception {
        val data =
            repository.findByIdOrNull(id)
                ?: throw ResourceNotFoundException("There's no such anomaly interception attempt with id=$id")

        if (data.t9ManufacturerEquipment > 0) {
            val equipments =
                equipmentService.findBySourceIdAndSourceType(
                    sourceId = id,
                    sourceType = EquipmentSourceType.SI_DROPS,
                )
            return data.toModel().copy(equipments = equipments)
        } else {
            return data.toModel()
        }
    }

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
                .findBySourceIdAndSourceType(
                    data.id,
                    EquipmentSourceType.SI_DROPS,
                ).map(
                    ManufacturerEquipment::id,
                )
        equipmentIds.forEach(equipmentService::delete)

        repository.deleteById(data.id)
        return true
    }

    override fun generateStats(bossName: String): SpecialInterceptionStats {
        val spec = SpecialInterceptionSpecs.hasBossName(bossName)
        val data = if (bossName != "All") repository.findAll(spec) else repository.findAll()

        val totalEquipments = data.sumOf { it.t9Equipment }
        val totalManufacturerEquipments = data.sumOf { it.t9ManufacturerEquipment }
        val totalModules = data.sumOf { it.modules }
        val totalEmptyDrops = data.sumOf { it.empty }
        val totalAttempts = totalEquipments + totalManufacturerEquipments + totalModules + totalEmptyDrops
        val totalManufacturerArms = totalAttempts * 20 // assumed every attempts is on 9th stage

        return SpecialInterceptionStats(
            bossName = bossName,
            totalAttempts = totalAttempts,
            totalEquipments = totalEquipments,
            totalManufacturerEquipments = totalManufacturerEquipments,
            totalModules = totalModules,
            totalEmptyDrops = totalEmptyDrops,
            totalManufacturerArms = totalManufacturerArms,
        )
    }
}
