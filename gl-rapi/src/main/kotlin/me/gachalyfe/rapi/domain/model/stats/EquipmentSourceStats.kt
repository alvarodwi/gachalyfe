package me.gachalyfe.rapi.domain.model.stats

data class EquipmentSourceStats(
    val totalFromAnomalyInterception: Int,
    val totalFromSpecialInterception: Int,
    val totalManufacturerArmsOpened: Int,
    val totalManufacturerFurnaceOpened: Int,
)
