package me.gachalyfe.rapi.data.repository

import me.gachalyfe.rapi.data.entity.ManufacturerEquipmentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ManufacturerEquipmentRepository : JpaRepository<ManufacturerEquipmentEntity, Long>
