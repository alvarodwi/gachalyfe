package me.gachalyfe.rapi.domain.dto

data class ManufacturerEquipmentDTO(
    val manufacturer: String,
    val classType: String,
    val slotType: String,
    val sourceId: Long,
    val sourceType: Int,
)
