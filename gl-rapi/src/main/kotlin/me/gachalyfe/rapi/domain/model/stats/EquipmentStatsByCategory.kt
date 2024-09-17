package me.gachalyfe.rapi.domain.model.stats

data class EquipmentStatsByCategory(
    val manufacturer: String,
    val classType: String,
    val slotType: String,
    val total: Int
)
