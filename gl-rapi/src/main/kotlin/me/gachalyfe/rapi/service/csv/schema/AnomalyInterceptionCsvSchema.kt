package me.gachalyfe.rapi.service.csv.schema

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
) {
    constructor() : this(LocalDate.now(), "", 0, "", false, 0)
}
