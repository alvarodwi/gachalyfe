package me.gachalyfe.rapi.domain.model.stats

data class AnomalyInterceptionStats(
    val dropType: String,
    val totalAttempts: Int,
    val totalCustomLock: Int,
    val totalModulesShards: Int,
    val totalManufacturerArms: Int,
    val totalEquipmentDrops: Int,
    val totalModules: Int,
)
