package me.gachalyfe.rapi.controller.dto.equipment

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank

data class ManufacturerArmsDTO(
    @field:NotBlank
    val date: String,
    @field:Valid
    val equipments: List<ManufacturerEquipmentDTO>,
)
