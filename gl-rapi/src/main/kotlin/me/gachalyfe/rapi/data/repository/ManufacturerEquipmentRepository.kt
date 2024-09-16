package me.gachalyfe.rapi.data.repository

import me.gachalyfe.rapi.data.entity.ManufacturerEquipmentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ManufacturerEquipmentRepository :
    JpaRepository<ManufacturerEquipmentEntity, Long>,
    JpaSpecificationExecutor<ManufacturerEquipmentEntity>
