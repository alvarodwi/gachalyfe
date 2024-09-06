package me.gachalyfe.rapi.data.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "manufacturer_equipments")
data class ManufacturerEquipmentEntity(
    @Id
    @Column(name = "id", columnDefinition = "INTEGER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val date: String,
    val manufacturer: String,
    val classType: String,
    val slotType: String,
    @Column(name = "source_id", columnDefinition = "INTEGER")
    val sourceId: Long,
    val sourceType: Int,
)
