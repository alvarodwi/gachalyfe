package me.gachalyfe.rapi.data.dto

import jakarta.validation.constraints.NotBlank
import java.util.Optional

data class ManufacturerEquipmentDTO(
    val date: Optional<String> = Optional.empty(),
    @field:NotBlank
    val manufacturer: String,
    @field:NotBlank
    val classType: String,
    @field:NotBlank
    val slotType: String,
)
