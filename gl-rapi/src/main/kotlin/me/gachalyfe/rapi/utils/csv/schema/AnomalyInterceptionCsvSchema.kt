package me.gachalyfe.rapi.utils.csv.schema

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class AnomalyInterceptionCsvSchema(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDate,
    val bossName: String,
    val stage: Int,
    val dropType: String,
    val dropped: Boolean,
    val modules: Int,
)
