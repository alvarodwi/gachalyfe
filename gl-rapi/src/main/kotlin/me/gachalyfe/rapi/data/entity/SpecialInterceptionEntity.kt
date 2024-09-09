package me.gachalyfe.rapi.data.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "special_interceptions")
data class SpecialInterceptionEntity(
    @Id
    @Column(name = "id", columnDefinition = "INTEGER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val date: String,
    val bossName: String,
    val t9Equipment: Int,
    val modules: Int,
    val t9ManufacturerEquipment: Int,
    val empty: Int,
)
