package me.gachalyfe.rapi.domain.model

data class AnomalyInterception(
    val id: Long = 0,
    val date: String,
    val bossName: String,
    val stage: Int,
    val dropType: String,
    val dropped: Boolean,
    val modules: Int,
    val equipment: ManufacturerEquipment? = null,
)
