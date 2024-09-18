package me.gachalyfe.rapi.domain.model.stats

import me.gachalyfe.rapi.domain.model.EquipmentSourceType

data class EquipmentStats(
    val sourceType: EquipmentSourceType,
    val equipmentStatsByCategory: List<EquipmentStatsByCategory>,
    val total: Int,
)
