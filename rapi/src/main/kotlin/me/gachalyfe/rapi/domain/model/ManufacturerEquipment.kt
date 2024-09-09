package me.gachalyfe.rapi.domain.model

data class ManufacturerEquipment(
    val id: Long,
    val date: String,
    val manufacturer: String,
    val classType: String,
    val slotType: String,
    val sourceId: Long,
    val sourceType: EquipmentSourceType,
)
