package me.gachalyfe.rapi.controller.dto

import jakarta.validation.constraints.NotBlank

data class ManufacturerEquipmentDTO(
    val id: Long?,
    val date: String?,
    @field:NotBlank
    val manufacturer: String,
    @field:NotBlank
    val classType: String,
    @field:NotBlank
    val slotType: String,
    val sourceId: Long?,
    val sourceType: Int?,
)
