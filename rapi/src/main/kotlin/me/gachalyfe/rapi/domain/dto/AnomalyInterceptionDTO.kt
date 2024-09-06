package me.gachalyfe.rapi.domain.dto

data class AnomalyInterceptionDTO(
    val date: String,
    val bossName: String,
    val stage: Int,
    val dropType: String,
    val dropped: Boolean,
    val modules: Int,
)
