package me.gachalyfe.rapi.domain.model

import java.util.Optional

data class AnomalyInterception(
    val id: Long,
    val date: String,
    val bossName: String,
    val stage: Int,
    val dropType: String,
    val dropped: Boolean,
    val modules: Int,
    val equipment: Optional<ManufacturerEquipment> = Optional.empty(),
)
