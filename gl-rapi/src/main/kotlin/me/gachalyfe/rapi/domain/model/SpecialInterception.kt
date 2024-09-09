package me.gachalyfe.rapi.domain.model

data class SpecialInterception(
    val id: Long,
    val date: String,
    val bossName: String,
    val t9Equipment: Int,
    val modules: Int,
    val t9ManufacturerEquipment: Int,
    val empty: Int,
    val equipments: List<ManufacturerEquipment> = emptyList(),
)
