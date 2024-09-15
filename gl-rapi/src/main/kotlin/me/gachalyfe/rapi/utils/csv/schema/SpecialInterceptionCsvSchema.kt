package me.gachalyfe.rapi.utils.csv.schema

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class SpecialInterceptionCsvSchema(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDate,
    val bossName: String,
    val t9Equipment: Int,
    val modules: Int,
    val t9ManufacturerEquipment: Int,
    val empty: Int,
) {
    constructor() : this(LocalDate.now(), "", 0, 0, 0, 0)
}
