package me.gachalyfe.rapi.controller.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AnomalyInterceptionDTO(
    val id: Long?,
    @field:NotBlank
    val date: String,
    @field:NotBlank
    val bossName: String,
    @field:Min(1) @field:Max(9)
    val stage: Int,
    val dropType: String,
    @field:NotNull
    val dropped: Boolean,
    @field:Min(0) @field:Max(3)
    val modules: Int,
    @field:Valid
    val equipment: ManufacturerEquipmentDTO?,
)
