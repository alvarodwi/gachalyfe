package me.gachalyfe.rapi.data.repository

import me.gachalyfe.rapi.data.entity.ManufacturerEquipmentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ManufacturerEquipmentRepository : JpaRepository<ManufacturerEquipmentEntity, Long> {
    @Query("select m from manufacturer_equipments m order by m.date desc limit :limit")
    fun findLatest(
        @Param("limit") limit: Int = 10,
    ): List<ManufacturerEquipmentEntity>

    @Query(
        "select m from manufacturer_equipments m where m.sourceType = :sourceType and m.sourceId = :sourceId",
    )
    fun findBySourceIdAndSourceType(
        @Param("sourceType") sourceType: Int,
        @Param("sourceId") sourceId: Long,
    ): List<ManufacturerEquipmentEntity>

    fun findAllByOrderByDateAsc(): List<ManufacturerEquipmentEntity>
}
