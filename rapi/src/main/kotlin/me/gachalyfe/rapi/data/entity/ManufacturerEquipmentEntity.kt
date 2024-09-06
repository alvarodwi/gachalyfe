package me.gachalyfe.rapi.data.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "manufacturer_equipments")
data class ManufacturerEquipmentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val manufacturer: String,
    val classType: String,
    val slotType: String,
    val sourceId: Long,
    val sourceType: Int,
)
