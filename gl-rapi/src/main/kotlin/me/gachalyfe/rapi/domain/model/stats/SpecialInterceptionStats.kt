package me.gachalyfe.rapi.domain.model.stats

data class SpecialInterceptionStats(
    val bossName: String,
    val totalAttempts: Int,
    val totalEquipments: Int,
    val totalManufacturerEquipments: Int,
    val totalModules: Int,
    val totalEmptyDrops: Int,
    val totalManufacturerArms: Int,
)
