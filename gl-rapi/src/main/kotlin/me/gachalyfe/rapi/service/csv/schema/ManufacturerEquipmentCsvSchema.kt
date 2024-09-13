package me.gachalyfe.rapi.service.csv.schema

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class ManufacturerEquipmentCsvSchema(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDate,
    val sourceType: String,
    val manufacturer: String,
    val classType: String,
    val slotType: String,
) {
    constructor() : this(LocalDate.now(), "", "", "", "")
}
