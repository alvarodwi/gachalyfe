package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.model.stats.EquipmentSourceStats
import me.gachalyfe.rapi.domain.model.stats.EquipmentStats
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface ManufacturerEquipmentService {
    fun findAll(pageable: Pageable): Page<ManufacturerEquipment>

    fun findAll(sort: Sort): List<ManufacturerEquipment>

    fun findBySourceType(
        sourceType: EquipmentSourceType,
        pageable: Pageable,
    ): Page<ManufacturerEquipment>

    fun findBySourceIdAndSourceType(
        sourceId: Long,
        sourceType: EquipmentSourceType,
    ): List<ManufacturerEquipment>

    fun save(model: ManufacturerEquipment): ManufacturerEquipment

    fun saveAll(data: List<ManufacturerEquipment>): List<ManufacturerEquipment>

    fun update(
        id: Long,
        model: ManufacturerEquipment,
    ): ManufacturerEquipment

    fun delete(id: Long): Boolean

    fun generateStats(sourceType: EquipmentSourceType): EquipmentStats

    fun generateSourceStats(): EquipmentSourceStats
}
