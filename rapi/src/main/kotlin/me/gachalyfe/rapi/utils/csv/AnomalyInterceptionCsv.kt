package me.gachalyfe.rapi.utils.csv

data class AnomalyInterceptionCsv(
    val date: String,
    val bossName: String,
    val stage: Int,
    val dropType: String,
    val dropped: String,
    val rocks: Int,
)
